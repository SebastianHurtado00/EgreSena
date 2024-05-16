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
import Entidades.Egresado;
import Entidades.NivelFormacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class NivelFormacionJpaController implements Serializable {

    public NivelFormacionJpaController() {
         this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelFormacion nivelFormacion) throws PreexistingEntityException, Exception {
        if (nivelFormacion.getEgresadoList() == null) {
            nivelFormacion.setEgresadoList(new ArrayList<Egresado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Egresado> attachedEgresadoList = new ArrayList<Egresado>();
            for (Egresado egresadoListEgresadoToAttach : nivelFormacion.getEgresadoList()) {
                egresadoListEgresadoToAttach = em.getReference(egresadoListEgresadoToAttach.getClass(), egresadoListEgresadoToAttach.getNumeroCedula());
                attachedEgresadoList.add(egresadoListEgresadoToAttach);
            }
            nivelFormacion.setEgresadoList(attachedEgresadoList);
            em.persist(nivelFormacion);
            for (Egresado egresadoListEgresado : nivelFormacion.getEgresadoList()) {
                NivelFormacion oldNivelFormacionIDOfEgresadoListEgresado = egresadoListEgresado.getNivelFormacionID();
                egresadoListEgresado.setNivelFormacionID(nivelFormacion);
                egresadoListEgresado = em.merge(egresadoListEgresado);
                if (oldNivelFormacionIDOfEgresadoListEgresado != null) {
                    oldNivelFormacionIDOfEgresadoListEgresado.getEgresadoList().remove(egresadoListEgresado);
                    oldNivelFormacionIDOfEgresadoListEgresado = em.merge(oldNivelFormacionIDOfEgresadoListEgresado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNivelFormacion(nivelFormacion.getId()) != null) {
                throw new PreexistingEntityException("NivelFormacion " + nivelFormacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelFormacion nivelFormacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelFormacion persistentNivelFormacion = em.find(NivelFormacion.class, nivelFormacion.getId());
            List<Egresado> egresadoListOld = persistentNivelFormacion.getEgresadoList();
            List<Egresado> egresadoListNew = nivelFormacion.getEgresadoList();
            List<String> illegalOrphanMessages = null;
            for (Egresado egresadoListOldEgresado : egresadoListOld) {
                if (!egresadoListNew.contains(egresadoListOldEgresado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Egresado " + egresadoListOldEgresado + " since its nivelFormacionID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Egresado> attachedEgresadoListNew = new ArrayList<Egresado>();
            for (Egresado egresadoListNewEgresadoToAttach : egresadoListNew) {
                egresadoListNewEgresadoToAttach = em.getReference(egresadoListNewEgresadoToAttach.getClass(), egresadoListNewEgresadoToAttach.getNumeroCedula());
                attachedEgresadoListNew.add(egresadoListNewEgresadoToAttach);
            }
            egresadoListNew = attachedEgresadoListNew;
            nivelFormacion.setEgresadoList(egresadoListNew);
            nivelFormacion = em.merge(nivelFormacion);
            for (Egresado egresadoListNewEgresado : egresadoListNew) {
                if (!egresadoListOld.contains(egresadoListNewEgresado)) {
                    NivelFormacion oldNivelFormacionIDOfEgresadoListNewEgresado = egresadoListNewEgresado.getNivelFormacionID();
                    egresadoListNewEgresado.setNivelFormacionID(nivelFormacion);
                    egresadoListNewEgresado = em.merge(egresadoListNewEgresado);
                    if (oldNivelFormacionIDOfEgresadoListNewEgresado != null && !oldNivelFormacionIDOfEgresadoListNewEgresado.equals(nivelFormacion)) {
                        oldNivelFormacionIDOfEgresadoListNewEgresado.getEgresadoList().remove(egresadoListNewEgresado);
                        oldNivelFormacionIDOfEgresadoListNewEgresado = em.merge(oldNivelFormacionIDOfEgresadoListNewEgresado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelFormacion.getId();
                if (findNivelFormacion(id) == null) {
                    throw new NonexistentEntityException("The nivelFormacion with id " + id + " no longer exists.");
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
            NivelFormacion nivelFormacion;
            try {
                nivelFormacion = em.getReference(NivelFormacion.class, id);
                nivelFormacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelFormacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egresado> egresadoListOrphanCheck = nivelFormacion.getEgresadoList();
            for (Egresado egresadoListOrphanCheckEgresado : egresadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NivelFormacion (" + nivelFormacion + ") cannot be destroyed since the Egresado " + egresadoListOrphanCheckEgresado + " in its egresadoList field has a non-nullable nivelFormacionID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(nivelFormacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelFormacion> findNivelFormacionEntities() {
        return findNivelFormacionEntities(true, -1, -1);
    }

    public List<NivelFormacion> findNivelFormacionEntities(int maxResults, int firstResult) {
        return findNivelFormacionEntities(false, maxResults, firstResult);
    }

    private List<NivelFormacion> findNivelFormacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelFormacion.class));
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

    public NivelFormacion findNivelFormacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelFormacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelFormacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelFormacion> rt = cq.from(NivelFormacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
