/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Formacion;
import java.util.ArrayList;
import java.util.List;
import Entidades.Egresado;
import Entidades.Sede;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class SedeJpaController implements Serializable {

    public SedeJpaController() {
         this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sede sede) throws PreexistingEntityException, Exception {
        if (sede.getFormacionList() == null) {
            sede.setFormacionList(new ArrayList<Formacion>());
        }
        if (sede.getEgresadoList() == null) {
            sede.setEgresadoList(new ArrayList<Egresado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Formacion> attachedFormacionList = new ArrayList<Formacion>();
            for (Formacion formacionListFormacionToAttach : sede.getFormacionList()) {
                formacionListFormacionToAttach = em.getReference(formacionListFormacionToAttach.getClass(), formacionListFormacionToAttach.getId());
                attachedFormacionList.add(formacionListFormacionToAttach);
            }
            sede.setFormacionList(attachedFormacionList);
            List<Egresado> attachedEgresadoList = new ArrayList<Egresado>();
            for (Egresado egresadoListEgresadoToAttach : sede.getEgresadoList()) {
                egresadoListEgresadoToAttach = em.getReference(egresadoListEgresadoToAttach.getClass(), egresadoListEgresadoToAttach.getNumeroCedula());
                attachedEgresadoList.add(egresadoListEgresadoToAttach);
            }
            sede.setEgresadoList(attachedEgresadoList);
            em.persist(sede);
            for (Formacion formacionListFormacion : sede.getFormacionList()) {
                Sede oldSedeIDOfFormacionListFormacion = formacionListFormacion.getSedeID();
                formacionListFormacion.setSedeID(sede);
                formacionListFormacion = em.merge(formacionListFormacion);
                if (oldSedeIDOfFormacionListFormacion != null) {
                    oldSedeIDOfFormacionListFormacion.getFormacionList().remove(formacionListFormacion);
                    oldSedeIDOfFormacionListFormacion = em.merge(oldSedeIDOfFormacionListFormacion);
                }
            }
            for (Egresado egresadoListEgresado : sede.getEgresadoList()) {
                Sede oldSedeIDOfEgresadoListEgresado = egresadoListEgresado.getSedeID();
                egresadoListEgresado.setSedeID(sede);
                egresadoListEgresado = em.merge(egresadoListEgresado);
                if (oldSedeIDOfEgresadoListEgresado != null) {
                    oldSedeIDOfEgresadoListEgresado.getEgresadoList().remove(egresadoListEgresado);
                    oldSedeIDOfEgresadoListEgresado = em.merge(oldSedeIDOfEgresadoListEgresado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSede(sede.getId()) != null) {
                throw new PreexistingEntityException("Sede " + sede + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sede sede) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede persistentSede = em.find(Sede.class, sede.getId());
            List<Formacion> formacionListOld = persistentSede.getFormacionList();
            List<Formacion> formacionListNew = sede.getFormacionList();
            List<Egresado> egresadoListOld = persistentSede.getEgresadoList();
            List<Egresado> egresadoListNew = sede.getEgresadoList();
            List<String> illegalOrphanMessages = null;
            for (Formacion formacionListOldFormacion : formacionListOld) {
                if (!formacionListNew.contains(formacionListOldFormacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Formacion " + formacionListOldFormacion + " since its sedeID field is not nullable.");
                }
            }
            for (Egresado egresadoListOldEgresado : egresadoListOld) {
                if (!egresadoListNew.contains(egresadoListOldEgresado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Egresado " + egresadoListOldEgresado + " since its sedeID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Formacion> attachedFormacionListNew = new ArrayList<Formacion>();
         
            formacionListNew = attachedFormacionListNew;
            sede.setFormacionList(formacionListNew);
            List<Egresado> attachedEgresadoListNew = new ArrayList<Egresado>();
          
            egresadoListNew = attachedEgresadoListNew;
            sede.setEgresadoList(egresadoListNew);
            sede = em.merge(sede);
      
      
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sede.getId();
                if (findSede(id) == null) {
                    throw new NonexistentEntityException("The sede with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sede;
            try {
                sede = em.getReference(Sede.class, id);
                sede.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sede with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Formacion> formacionListOrphanCheck = sede.getFormacionList();
            for (Formacion formacionListOrphanCheckFormacion : formacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Formacion " + formacionListOrphanCheckFormacion + " in its formacionList field has a non-nullable sedeID field.");
            }
            List<Egresado> egresadoListOrphanCheck = sede.getEgresadoList();
            for (Egresado egresadoListOrphanCheckEgresado : egresadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Egresado " + egresadoListOrphanCheckEgresado + " in its egresadoList field has a non-nullable sedeID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sede> findSedeEntities() {
        return findSedeEntities(true, -1, -1);
    }

    public List<Sede> findSedeEntities(int maxResults, int firstResult) {
        return findSedeEntities(false, maxResults, firstResult);
    }

    private List<Sede> findSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sede.class));
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

    public Sede findSede(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sede.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sede> rt = cq.from(Sede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
