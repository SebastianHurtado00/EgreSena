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
import Entidades.TipoPoblacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class TipoPoblacionJpaController implements Serializable {

    public TipoPoblacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPoblacion tipoPoblacion) throws PreexistingEntityException, Exception {
        if (tipoPoblacion.getEgresadoList() == null) {
            tipoPoblacion.setEgresadoList(new ArrayList<Egresado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Egresado> attachedEgresadoList = new ArrayList<Egresado>();
            for (Egresado egresadoListEgresadoToAttach : tipoPoblacion.getEgresadoList()) {
                egresadoListEgresadoToAttach = em.getReference(egresadoListEgresadoToAttach.getClass(), egresadoListEgresadoToAttach.getNumeroCedula());
                attachedEgresadoList.add(egresadoListEgresadoToAttach);
            }
            tipoPoblacion.setEgresadoList(attachedEgresadoList);
            em.persist(tipoPoblacion);
            for (Egresado egresadoListEgresado : tipoPoblacion.getEgresadoList()) {
                TipoPoblacion oldTipoPoblacionIDOfEgresadoListEgresado = egresadoListEgresado.getTipoPoblacionID();
                egresadoListEgresado.setTipoPoblacionID(tipoPoblacion);
                egresadoListEgresado = em.merge(egresadoListEgresado);
                if (oldTipoPoblacionIDOfEgresadoListEgresado != null) {
                    oldTipoPoblacionIDOfEgresadoListEgresado.getEgresadoList().remove(egresadoListEgresado);
                    oldTipoPoblacionIDOfEgresadoListEgresado = em.merge(oldTipoPoblacionIDOfEgresadoListEgresado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoPoblacion(tipoPoblacion.getId()) != null) {
                throw new PreexistingEntityException("TipoPoblacion " + tipoPoblacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPoblacion tipoPoblacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPoblacion persistentTipoPoblacion = em.find(TipoPoblacion.class, tipoPoblacion.getId());
            List<Egresado> egresadoListOld = persistentTipoPoblacion.getEgresadoList();
            List<Egresado> egresadoListNew = tipoPoblacion.getEgresadoList();
            List<String> illegalOrphanMessages = null;

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Egresado> attachedEgresadoListNew = new ArrayList<Egresado>();

            egresadoListNew = attachedEgresadoListNew;
            tipoPoblacion.setEgresadoList(egresadoListNew);
            tipoPoblacion = em.merge(tipoPoblacion);

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPoblacion.getId();
                if (findTipoPoblacion(id) == null) {
                    throw new NonexistentEntityException("The tipoPoblacion with id " + id + " no longer exists.");
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
            TipoPoblacion tipoPoblacion;
            try {
                tipoPoblacion = em.getReference(TipoPoblacion.class, id);
                tipoPoblacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPoblacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egresado> egresadoListOrphanCheck = tipoPoblacion.getEgresadoList();
            for (Egresado egresadoListOrphanCheckEgresado : egresadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPoblacion (" + tipoPoblacion + ") cannot be destroyed since the Egresado " + egresadoListOrphanCheckEgresado + " in its egresadoList field has a non-nullable tipoPoblacionID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPoblacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPoblacion> findTipoPoblacionEntities() {
        return findTipoPoblacionEntities(true, -1, -1);
    }

    public List<TipoPoblacion> findTipoPoblacionEntities(int maxResults, int firstResult) {
        return findTipoPoblacionEntities(false, maxResults, firstResult);
    }

    private List<TipoPoblacion> findTipoPoblacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPoblacion.class));
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

    public TipoPoblacion findTipoPoblacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPoblacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPoblacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPoblacion> rt = cq.from(TipoPoblacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
