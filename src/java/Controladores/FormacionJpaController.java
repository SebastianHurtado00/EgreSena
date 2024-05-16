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
import Entidades.Sede;
import Entidades.Egresado;
import Entidades.Formacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class FormacionJpaController implements Serializable {

    public FormacionJpaController() {
       this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Formacion formacion) throws PreexistingEntityException, Exception {
        if (formacion.getEgresadoList() == null) {
            formacion.setEgresadoList(new ArrayList<Egresado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sedeID = formacion.getSedeID();
            if (sedeID != null) {
                sedeID = em.getReference(sedeID.getClass(), sedeID.getId());
                formacion.setSedeID(sedeID);
            }
            List<Egresado> attachedEgresadoList = new ArrayList<Egresado>();
            for (Egresado egresadoListEgresadoToAttach : formacion.getEgresadoList()) {
                egresadoListEgresadoToAttach = em.getReference(egresadoListEgresadoToAttach.getClass(), egresadoListEgresadoToAttach.getNumeroCedula());
                attachedEgresadoList.add(egresadoListEgresadoToAttach);
            }
            formacion.setEgresadoList(attachedEgresadoList);
            em.persist(formacion);
            if (sedeID != null) {
                sedeID.getFormacionList().add(formacion);
                sedeID = em.merge(sedeID);
            }
            for (Egresado egresadoListEgresado : formacion.getEgresadoList()) {
                Formacion oldFormacionIDOfEgresadoListEgresado = egresadoListEgresado.getFormacionID();
                egresadoListEgresado.setFormacionID(formacion);
                egresadoListEgresado = em.merge(egresadoListEgresado);
                if (oldFormacionIDOfEgresadoListEgresado != null) {
                    oldFormacionIDOfEgresadoListEgresado.getEgresadoList().remove(egresadoListEgresado);
                    oldFormacionIDOfEgresadoListEgresado = em.merge(oldFormacionIDOfEgresadoListEgresado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormacion(formacion.getId()) != null) {
                throw new PreexistingEntityException("Formacion " + formacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formacion formacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formacion persistentFormacion = em.find(Formacion.class, formacion.getId());
            Sede sedeIDOld = persistentFormacion.getSedeID();
            Sede sedeIDNew = formacion.getSedeID();
            List<Egresado> egresadoListOld = persistentFormacion.getEgresadoList();
            List<Egresado> egresadoListNew = formacion.getEgresadoList();
            List<String> illegalOrphanMessages = null;
     
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sedeIDNew != null) {
                sedeIDNew = em.getReference(sedeIDNew.getClass(), sedeIDNew.getId());
                formacion.setSedeID(sedeIDNew);
            }
      
            
            formacion.setEgresadoList(egresadoListNew);
            formacion = em.merge(formacion);
            if (sedeIDOld != null && !sedeIDOld.equals(sedeIDNew)) {
                sedeIDOld.getFormacionList().remove(formacion);
                sedeIDOld = em.merge(sedeIDOld);
            }
            if (sedeIDNew != null && !sedeIDNew.equals(sedeIDOld)) {
                sedeIDNew.getFormacionList().add(formacion);
                sedeIDNew = em.merge(sedeIDNew);
            }
       
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formacion.getId();
                if (findFormacion(id) == null) {
                    throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.");
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
            Formacion formacion;
            try {
                formacion = em.getReference(Formacion.class, id);
                formacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egresado> egresadoListOrphanCheck = formacion.getEgresadoList();
            for (Egresado egresadoListOrphanCheckEgresado : egresadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formacion (" + formacion + ") cannot be destroyed since the Egresado " + egresadoListOrphanCheckEgresado + " in its egresadoList field has a non-nullable formacionID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sede sedeID = formacion.getSedeID();
            if (sedeID != null) {
                sedeID.getFormacionList().remove(formacion);
                sedeID = em.merge(sedeID);
            }
            em.remove(formacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formacion> findFormacionEntities() {
        return findFormacionEntities(true, -1, -1);
    }

    public List<Formacion> findFormacionEntities(int maxResults, int firstResult) {
        return findFormacionEntities(false, maxResults, firstResult);
    }

    private List<Formacion> findFormacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formacion.class));
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

    public Formacion findFormacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formacion> rt = cq.from(Formacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
