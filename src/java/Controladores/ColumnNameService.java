package Controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class ColumnNameService {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ColumnNameService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<String> getColumnNames(String tableName, int[] columnIndices) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'sistemaegresados' AND table_name = '" + tableName + "'";
        List<String> allColumns = em.createNativeQuery(sql).getResultList();
        List<String> selectedColumns = new ArrayList<>();

        for (int index : columnIndices) {
            selectedColumns.add(allColumns.get(index - 1)); // Ajuste para índices basados en 1
        }

        em.close();
        emf.close();

        return selectedColumns;
    }

    public Map<String, String> getForeignKeyMappings(String tableName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT column_name, referenced_table_name, referenced_column_name "
                + "FROM information_schema.key_column_usage "
                + "WHERE table_schema = 'sistemaegresados' AND table_name = '" + tableName + "' AND referenced_table_name IS NOT NULL";

        List<Object[]> results = em.createNativeQuery(sql).getResultList();
        Map<String, String> foreignKeyMappings = new HashMap<>();

        for (Object[] result : results) {
            String columnName = (String) result[0];
            String referencedTableName = (String) result[1];
            String referencedColumnName = (String) result[2];
            // Asume que la columna que deseas mostrar se llama "nombre" en la tabla referenciada
            String foreignKeyQuery = "SELECT " + referencedColumnName + ", nombre FROM " + referencedTableName + " WHERE " + referencedColumnName + " = ?";
            foreignKeyMappings.put(columnName, foreignKeyQuery + "|" + referencedColumnName);
        }

        em.close();
        emf.close();

        return foreignKeyMappings;
    }

    public List<Object[]> getTableData(String tableName, List<String> columnNames) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Object[]> data = null;

        try {
            emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
            em = emf.createEntityManager();

            // Obtener los mapeos de las claves foráneas
            Map<String, String> foreignKeyMappings = getForeignKeyMappings(tableName);

            // Construir la consulta SQL para obtener los datos de la tabla principal
            StringBuilder sqlBuilder = new StringBuilder("SELECT ");
            for (int i = 0; i < columnNames.size(); i++) {
                if (i > 0) {
                    sqlBuilder.append(", ");
                }
                sqlBuilder.append(columnNames.get(i));
            }
            sqlBuilder.append(" FROM ").append(tableName);

            Query query = em.createNativeQuery(sqlBuilder.toString());
            data = query.getResultList();

            // Reemplazar los valores de las claves foráneas por los nombres correspondientes
            for (Object[] row : data) {
                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    if (foreignKeyMappings.containsKey(columnName)) {
                        String[] foreignKeyInfo = foreignKeyMappings.get(columnName).split("\\|");
                        String foreignKeyQuery = foreignKeyInfo[0];
                        String foreignKeyColumn = foreignKeyInfo[1];
                        Object foreignKeyValue = row[i];
                        List<Object[]> foreignKeyResult = em.createNativeQuery(foreignKeyQuery)
                                .setParameter(1, foreignKeyValue)
                                .getResultList();
                        if (!foreignKeyResult.isEmpty()) {
                            row[i] = foreignKeyResult.get(0)[1]; // Asumiendo que el nombre está en la segunda columna
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }

        return data;
    }

    public String generateTable(String tableName, int[] columnIndices) {
        StringBuilder tableHtml = new StringBuilder();

        try {
            List<String> columnNames;
            List<Object[]> data;

            // Verificar si la tabla es "usuarios"
            if ("usuarios".equalsIgnoreCase(tableName)) {
                // Si es "usuarios", mostrar una tabla vacía
                columnNames = Collections.emptyList();
                data = Collections.emptyList();
            } else {
                // Obtener los nombres de las columnas y los datos de la tabla
                columnNames = getColumnNames(tableName, columnIndices);
                data = getTableData(tableName, columnNames);
            }

            // Modificar los nombres de las columnas para eliminar "_ID"
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                if (columnName.endsWith("_ID")) {
                    columnNames.set(i, columnName.substring(0, columnName.length() - 3));
                }
            }

            // Agregar columna de configuración
            columnNames.add("Configuración");

            // Generar el HTML de la tabla
            tableHtml.append("<table class='table table-bordered'>");
            tableHtml.append("<thead><tr>");

            for (String columnName : columnNames) {
                tableHtml.append("<th>").append(columnName).append("</th>");
            }
            tableHtml.append("</tr></thead>");
            tableHtml.append("<tbody>");

            if (!data.isEmpty()) {
                for (Object[] row : data) {
                    tableHtml.append("<tr>");
                    for (Object column : row) {
                        tableHtml.append("<td>").append(column).append("</td>");
                    }
                    tableHtml.append("<td>");
                    tableHtml.append("<button type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#ModalModificacion\">Botón 1</button>");

                    tableHtml.append("</td>");
                    tableHtml.append("</tr>");
                }
            } else {
                // Si no hay datos, mostrar una fila vacía
                int totalColumns = columnNames.size();
                tableHtml.append("<tr><td colspan='").append(totalColumns).append("'>No hay datos disponibles</td></tr>");
            }

            tableHtml.append("</tbody></table>");

        } catch (Exception e) {
            // Si ocurre un error, mostrar un mensaje de error
            tableHtml.append("<div class='alert alert-danger' role='alert'>");
            tableHtml.append("Error al obtener la tabla o tabla inexistente: ").append(tableName);
            tableHtml.append("</div>");
        }

        return tableHtml.toString();
    }

    private boolean isTableNameValid(String tableName) {
        // Verifica que el nombre de la tabla sólo contiene caracteres alfanuméricos y guiones bajos
        return tableName != null && tableName.matches("^[a-zA-Z0-9_]+$");
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

}
