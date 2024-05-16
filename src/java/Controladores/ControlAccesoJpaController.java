/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.ControlAcceso;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ASUS
 */
public class ControlAccesoJpaController implements Serializable {

    public ControlAccesoJpaController() {
       this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlAcceso controlAcceso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(controlAcceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlAcceso controlAcceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            controlAcceso = em.merge(controlAcceso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlAcceso.getId();
                if (findControlAcceso(id) == null) {
                    throw new NonexistentEntityException("The controlAcceso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlAcceso controlAcceso;
            try {
                controlAcceso = em.getReference(ControlAcceso.class, id);
                controlAcceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlAcceso with id " + id + " no longer exists.", enfe);
            }
            em.remove(controlAcceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlAcceso> findControlAccesoEntities() {
        return findControlAccesoEntities(true, -1, -1);
    }

    public List<ControlAcceso> findControlAccesoEntities(int maxResults, int firstResult) {
        return findControlAccesoEntities(false, maxResults, firstResult);
    }

    private List<ControlAcceso> findControlAccesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlAcceso.class));
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

    public ControlAcceso findControlAcceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlAcceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlAccesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlAcceso> rt = cq.from(ControlAcceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
