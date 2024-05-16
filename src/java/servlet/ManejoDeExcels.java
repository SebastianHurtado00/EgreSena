/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.CiudadJpaController;
import Controladores.EgresadoJpaController;
import Controladores.FormacionJpaController;
import Controladores.NivelFormacionJpaController;
import Controladores.SedeJpaController;
import Controladores.SexoJpaController;
import Controladores.TipoPoblacionJpaController;
import Controladores.TipodocumentoJpaController;
import Controladores.UsuariosJpaController;
import Entidades.Ciudad;
import Entidades.Egresado;
import Entidades.Formacion;
import Entidades.NivelFormacion;
import Entidades.Sede;
import Entidades.Sexo;
import Entidades.TipoPoblacion;
import Entidades.Tipodocumento;
import Entidades.Usuarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Part;

/**
 *
 * @author ASUS
 */
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "ManejoDeExcels", urlPatterns = {"/ManejoDeExcels"})
public class ManejoDeExcels extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String btn = request.getParameter("BtnExcel");

        switch (btn) {
            case "action":
//                response.getWriter().write("Presionadi");
                // botonImportar(request, response);
                importa(request, response);
                break;
            default:
                break;
        }

    }

    public void importa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el archivo CSV cargado desde el formulario
        Part filePart = request.getPart("csvFile");

        // Leer los datos del archivo CSV y guardarlos en una lista de cadenas
        List<String> csvData = leerDatosCSV(filePart);

        if (guardarDatosEnBD(csvData)) {

            response.sendRedirect("Views/RegistrosEgresados_UI.jsp?respuesta=Exito");
        } else {

            response.sendRedirect("Views/RegistrosEgresados_UI.jsp?respuesta=fallo");
        }

    }

    private List<String> leerDatosCSV(Part filePart) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream fileContent = filePart.getInputStream();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private boolean guardarDatosEnBD(List<String> csvData) {
        CiudadJpaController controlCiudad = new CiudadJpaController();
        FormacionJpaController controlFormacion = new FormacionJpaController();
        NivelFormacionJpaController controlNivelFomracion = new NivelFormacionJpaController();
        SedeJpaController controlSede = new SedeJpaController();
        SexoJpaController ControlSexo = new SexoJpaController();
        TipoPoblacionJpaController controlTipoPoblacion = new TipoPoblacionJpaController();
        TipodocumentoJpaController controlTipoDoc = new TipodocumentoJpaController();
        EgresadoJpaController controlEgresado = new EgresadoJpaController();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();

        try {
            // Empezamos desde la segunda línea (índice 1) para omitir la primera línea del CSV
            for (int i = 1; i < csvData.size(); i++) {
                String line = csvData.get(i);
                String[] fields = line.split(";"); // Suponiendo que los campos están separados por punto y coma
                Egresado egresado = new Egresado();

                String CC = fields[0];

                // Asignar valores a las propiedades del objeto Egresado
                egresado.setNumeroCedula(Long.parseLong(CC)); // Número de cédula
                egresado.setTipoDocumentoID(controlTipoDoc.findTipodocumento(Integer.parseInt(fields[1])));
                egresado.setNombre(fields[2]);
                egresado.setApellido(fields[3]);
                egresado.setSexoID(ControlSexo.findSexo(Integer.parseInt(fields[4])));
                egresado.setSedeID(controlSede.findSede(Integer.parseInt(fields[5])));
                egresado.setFormacionID(controlFormacion.findFormacion(Integer.parseInt(fields[6])));
                egresado.setNivelFormacionID(controlNivelFomracion.findNivelFormacion(Integer.parseInt(fields[7])));
                egresado.setNumeroCertificados(Integer.parseInt(fields[8]));
                egresado.setCorreo(fields[9]);
                egresado.setTipoPoblacionID(controlTipoPoblacion.findTipoPoblacion(Integer.parseInt(fields[10])));
                egresado.setNumeroTelefono(Long.parseLong(fields[11]));
                egresado.setCiudadID(controlCiudad.findCiudad(Integer.parseInt(fields[12])));
                egresado.setDireccionResidencia(fields[13]);
                egresado.setCadenaFormacion(Boolean.parseBoolean(fields[14]));
                egresado.setFormacionCursada(fields[15]);
                egresado.setCadenaUniversitaria(Boolean.parseBoolean(fields[16]));
                egresado.setNombreUniversidad(fields[17]);
                egresado.setNombreCarrera(fields[18]);
                egresado.setExperiencia(Boolean.parseBoolean(fields[19]));
                egresado.setDescripcionExperiencia(fields[20]);
                egresado.setTrabajando(Boolean.parseBoolean(fields[21]));

                // Persistir el objeto Egresado en la base de datos
                controlEgresado.create(egresado);
                //Persistir Objteo Usuario en la base de datos

                Usuarios userGuardar = new Usuarios();
                String passwordEncriptar = userGuardar.EncryptarClave(CC);
                userGuardar.setCedula(Integer.parseInt(CC));
                userGuardar.setNombre(fields[2]);
                userGuardar.setApellido(fields[3]);
                userGuardar.setCorreo(fields[9]);
                userGuardar.setRol(2);
                userGuardar.setTelefono(Long.parseLong(fields[11]));
                userGuardar.setTipoDocID(controlTipoDoc.findTipodocumento(Integer.parseInt(fields[1])));
                userGuardar.setPassword(passwordEncriptar);

                controlUsuario.create(userGuardar);

            }
            return true; // Si llegamos aquí, todos los registros se guardaron exitosamente
        } catch (Exception e) {

            //e.printStackTrace();
        }
        return false;

    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Redirigir a una página de confirmación u otro destino
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
