/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Ciudad;
import Entidades.Egresado;
import Entidades.Formacion;
import Entidades.NivelFormacion;
import Entidades.Sede;
import Entidades.Sexo;
import Entidades.TipoPoblacion;
import Entidades.Tipodocumento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class EgresadoJpaController implements Serializable {

    public EgresadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egresado egresado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudadID = egresado.getCiudadID();
            if (ciudadID != null) {
                ciudadID = em.getReference(ciudadID.getClass(), ciudadID.getId());
                egresado.setCiudadID(ciudadID);
            }
            Formacion formacionID = egresado.getFormacionID();
            if (formacionID != null) {
                formacionID = em.getReference(formacionID.getClass(), formacionID.getId());
                egresado.setFormacionID(formacionID);
            }
            NivelFormacion nivelFormacionID = egresado.getNivelFormacionID();
            if (nivelFormacionID != null) {
                nivelFormacionID = em.getReference(nivelFormacionID.getClass(), nivelFormacionID.getId());
                egresado.setNivelFormacionID(nivelFormacionID);
            }
            Sede sedeID = egresado.getSedeID();
            if (sedeID != null) {
                sedeID = em.getReference(sedeID.getClass(), sedeID.getId());
                egresado.setSedeID(sedeID);
            }
            Sexo sexoID = egresado.getSexoID();
            if (sexoID != null) {
                sexoID = em.getReference(sexoID.getClass(), sexoID.getId());
                egresado.setSexoID(sexoID);
            }
            TipoPoblacion tipoPoblacionID = egresado.getTipoPoblacionID();
            if (tipoPoblacionID != null) {
                tipoPoblacionID = em.getReference(tipoPoblacionID.getClass(), tipoPoblacionID.getId());
                egresado.setTipoPoblacionID(tipoPoblacionID);
            }
            Tipodocumento tipoDocumentoID = egresado.getTipoDocumentoID();
            if (tipoDocumentoID != null) {
                tipoDocumentoID = em.getReference(tipoDocumentoID.getClass(), tipoDocumentoID.getId());
                egresado.setTipoDocumentoID(tipoDocumentoID);
            }
            em.persist(egresado);
            if (ciudadID != null) {
                ciudadID.getEgresadoList().add(egresado);
                ciudadID = em.merge(ciudadID);
            }
            if (formacionID != null) {
                formacionID.getEgresadoList().add(egresado);
                formacionID = em.merge(formacionID);
            }
            if (nivelFormacionID != null) {
                nivelFormacionID.getEgresadoList().add(egresado);
                nivelFormacionID = em.merge(nivelFormacionID);
            }
            if (sedeID != null) {
                sedeID.getEgresadoList().add(egresado);
                sedeID = em.merge(sedeID);
            }
            if (sexoID != null) {
                sexoID.getEgresadoList().add(egresado);
                sexoID = em.merge(sexoID);
            }
            if (tipoPoblacionID != null) {
                tipoPoblacionID.getEgresadoList().add(egresado);
                tipoPoblacionID = em.merge(tipoPoblacionID);
            }
            if (tipoDocumentoID != null) {
                tipoDocumentoID.getEgresadoList().add(egresado);
                tipoDocumentoID = em.merge(tipoDocumentoID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEgresado(egresado.getNumeroCedula()) != null) {
                throw new PreexistingEntityException("Egresado " + egresado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<String> obtenerCorreosFiltrados(int sexo, int sede, int formacion, int experiencia, int trabajando, int ciudad) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT e.correo FROM Egresado e WHERE 1=1");
            Map<String, Object> parametros = new HashMap<>();

            if (sexo != 0) {
                jpql.append(" AND e.sexoID.id = :sexo");
                parametros.put("sexo", sexo);
            }
            if (sede != 0) {
                jpql.append(" AND e.sedeID.id = :sede");
                parametros.put("sede", sede);
            }
            if (formacion != 0) {
                jpql.append(" AND e.formacionID.id = :formacion");
                parametros.put("formacion", formacion);
            }
            if (experiencia == 0 || experiencia == 1) {
                jpql.append(" AND e.experiencia = :experiencia");
                parametros.put("experiencia", experiencia == 1);
            }
            if (trabajando == 0 || trabajando == 1) {
                jpql.append(" AND e.trabajando = :trabajando");
                parametros.put("trabajando", trabajando == 1);
            }

            if (ciudad != 0) {
                jpql.append(" AND e.ciudadID.id = :ciudad");
                parametros.put("ciudad", ciudad);
            }

            Query query = em.createQuery(jpql.toString());
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void edit(Egresado egresado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egresado persistentEgresado = em.find(Egresado.class, egresado.getNumeroCedula());
            Ciudad ciudadIDOld = persistentEgresado.getCiudadID();
            Ciudad ciudadIDNew = egresado.getCiudadID();
            Formacion formacionIDOld = persistentEgresado.getFormacionID();
            Formacion formacionIDNew = egresado.getFormacionID();
            NivelFormacion nivelFormacionIDOld = persistentEgresado.getNivelFormacionID();
            NivelFormacion nivelFormacionIDNew = egresado.getNivelFormacionID();
            Sede sedeIDOld = persistentEgresado.getSedeID();
            Sede sedeIDNew = egresado.getSedeID();
            Sexo sexoIDOld = persistentEgresado.getSexoID();
            Sexo sexoIDNew = egresado.getSexoID();
            TipoPoblacion tipoPoblacionIDOld = persistentEgresado.getTipoPoblacionID();
            TipoPoblacion tipoPoblacionIDNew = egresado.getTipoPoblacionID();
            Tipodocumento tipoDocumentoIDOld = persistentEgresado.getTipoDocumentoID();
            Tipodocumento tipoDocumentoIDNew = egresado.getTipoDocumentoID();
            if (ciudadIDNew != null) {
                ciudadIDNew = em.getReference(ciudadIDNew.getClass(), ciudadIDNew.getId());
                egresado.setCiudadID(ciudadIDNew);
            }
            if (formacionIDNew != null) {
                formacionIDNew = em.getReference(formacionIDNew.getClass(), formacionIDNew.getId());
                egresado.setFormacionID(formacionIDNew);
            }
            if (nivelFormacionIDNew != null) {
                nivelFormacionIDNew = em.getReference(nivelFormacionIDNew.getClass(), nivelFormacionIDNew.getId());
                egresado.setNivelFormacionID(nivelFormacionIDNew);
            }
            if (sedeIDNew != null) {
                sedeIDNew = em.getReference(sedeIDNew.getClass(), sedeIDNew.getId());
                egresado.setSedeID(sedeIDNew);
            }
            if (sexoIDNew != null) {
                sexoIDNew = em.getReference(sexoIDNew.getClass(), sexoIDNew.getId());
                egresado.setSexoID(sexoIDNew);
            }
            if (tipoPoblacionIDNew != null) {
                tipoPoblacionIDNew = em.getReference(tipoPoblacionIDNew.getClass(), tipoPoblacionIDNew.getId());
                egresado.setTipoPoblacionID(tipoPoblacionIDNew);
            }
            if (tipoDocumentoIDNew != null) {
                tipoDocumentoIDNew = em.getReference(tipoDocumentoIDNew.getClass(), tipoDocumentoIDNew.getId());
                egresado.setTipoDocumentoID(tipoDocumentoIDNew);
            }
            egresado = em.merge(egresado);
            if (ciudadIDOld != null && !ciudadIDOld.equals(ciudadIDNew)) {
                ciudadIDOld.getEgresadoList().remove(egresado);
                ciudadIDOld = em.merge(ciudadIDOld);
            }
            if (ciudadIDNew != null && !ciudadIDNew.equals(ciudadIDOld)) {
                ciudadIDNew.getEgresadoList().add(egresado);
                ciudadIDNew = em.merge(ciudadIDNew);
            }
            if (formacionIDOld != null && !formacionIDOld.equals(formacionIDNew)) {
                formacionIDOld.getEgresadoList().remove(egresado);
                formacionIDOld = em.merge(formacionIDOld);
            }
            if (formacionIDNew != null && !formacionIDNew.equals(formacionIDOld)) {
                formacionIDNew.getEgresadoList().add(egresado);
                formacionIDNew = em.merge(formacionIDNew);
            }
            if (nivelFormacionIDOld != null && !nivelFormacionIDOld.equals(nivelFormacionIDNew)) {
                nivelFormacionIDOld.getEgresadoList().remove(egresado);
                nivelFormacionIDOld = em.merge(nivelFormacionIDOld);
            }
            if (nivelFormacionIDNew != null && !nivelFormacionIDNew.equals(nivelFormacionIDOld)) {
                nivelFormacionIDNew.getEgresadoList().add(egresado);
                nivelFormacionIDNew = em.merge(nivelFormacionIDNew);
            }
            if (sedeIDOld != null && !sedeIDOld.equals(sedeIDNew)) {
                sedeIDOld.getEgresadoList().remove(egresado);
                sedeIDOld = em.merge(sedeIDOld);
            }
            if (sedeIDNew != null && !sedeIDNew.equals(sedeIDOld)) {
                sedeIDNew.getEgresadoList().add(egresado);
                sedeIDNew = em.merge(sedeIDNew);
            }
            if (sexoIDOld != null && !sexoIDOld.equals(sexoIDNew)) {
                sexoIDOld.getEgresadoList().remove(egresado);
                sexoIDOld = em.merge(sexoIDOld);
            }
            if (sexoIDNew != null && !sexoIDNew.equals(sexoIDOld)) {
                sexoIDNew.getEgresadoList().add(egresado);
                sexoIDNew = em.merge(sexoIDNew);
            }
            if (tipoPoblacionIDOld != null && !tipoPoblacionIDOld.equals(tipoPoblacionIDNew)) {
                tipoPoblacionIDOld.getEgresadoList().remove(egresado);
                tipoPoblacionIDOld = em.merge(tipoPoblacionIDOld);
            }
            if (tipoPoblacionIDNew != null && !tipoPoblacionIDNew.equals(tipoPoblacionIDOld)) {
                tipoPoblacionIDNew.getEgresadoList().add(egresado);
                tipoPoblacionIDNew = em.merge(tipoPoblacionIDNew);
            }
            if (tipoDocumentoIDOld != null && !tipoDocumentoIDOld.equals(tipoDocumentoIDNew)) {
                tipoDocumentoIDOld.getEgresadoList().remove(egresado);
                tipoDocumentoIDOld = em.merge(tipoDocumentoIDOld);
            }
            if (tipoDocumentoIDNew != null && !tipoDocumentoIDNew.equals(tipoDocumentoIDOld)) {
                tipoDocumentoIDNew.getEgresadoList().add(egresado);
                tipoDocumentoIDNew = em.merge(tipoDocumentoIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = egresado.getNumeroCedula();
                if (findEgresado(id) == null) {
                    throw new NonexistentEntityException("The egresado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egresado egresado;
            try {
                egresado = em.getReference(Egresado.class, id);
                egresado.getNumeroCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egresado with id " + id + " no longer exists.", enfe);
            }
            Ciudad ciudadID = egresado.getCiudadID();
            if (ciudadID != null) {
                ciudadID.getEgresadoList().remove(egresado);
                ciudadID = em.merge(ciudadID);
            }
            Formacion formacionID = egresado.getFormacionID();
            if (formacionID != null) {
                formacionID.getEgresadoList().remove(egresado);
                formacionID = em.merge(formacionID);
            }
            NivelFormacion nivelFormacionID = egresado.getNivelFormacionID();
            if (nivelFormacionID != null) {
                nivelFormacionID.getEgresadoList().remove(egresado);
                nivelFormacionID = em.merge(nivelFormacionID);
            }
            Sede sedeID = egresado.getSedeID();
            if (sedeID != null) {
                sedeID.getEgresadoList().remove(egresado);
                sedeID = em.merge(sedeID);
            }
            Sexo sexoID = egresado.getSexoID();
            if (sexoID != null) {
                sexoID.getEgresadoList().remove(egresado);
                sexoID = em.merge(sexoID);
            }
            TipoPoblacion tipoPoblacionID = egresado.getTipoPoblacionID();
            if (tipoPoblacionID != null) {
                tipoPoblacionID.getEgresadoList().remove(egresado);
                tipoPoblacionID = em.merge(tipoPoblacionID);
            }
            Tipodocumento tipoDocumentoID = egresado.getTipoDocumentoID();
            if (tipoDocumentoID != null) {
                tipoDocumentoID.getEgresadoList().remove(egresado);
                tipoDocumentoID = em.merge(tipoDocumentoID);
            }
            em.remove(egresado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egresado> findEgresadoEntities() {
        return findEgresadoEntities(true, -1, -1);
    }

    public List<Egresado> findEgresadoEntities(int maxResults, int firstResult) {
        return findEgresadoEntities(false, maxResults, firstResult);
    }

    private List<Egresado> findEgresadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egresado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Egresado findEgresado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egresado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egresado> rt = cq.from(Egresado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
