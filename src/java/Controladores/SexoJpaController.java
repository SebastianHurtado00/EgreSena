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
import Entidades.Sexo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class SexoJpaController implements Serializable {

    public SexoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sexo sexo) throws PreexistingEntityException, Exception {
        if (sexo.getEgresadoList() == null) {
            sexo.setEgresadoList(new ArrayList<Egresado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Egresado> attachedEgresadoList = new ArrayList<Egresado>();
            for (Egresado egresadoListEgresadoToAttach : sexo.getEgresadoList()) {
                egresadoListEgresadoToAttach = em.getReference(egresadoListEgresadoToAttach.getClass(), egresadoListEgresadoToAttach.getNumeroCedula());
                attachedEgresadoList.add(egresadoListEgresadoToAttach);
            }
            sexo.setEgresadoList(attachedEgresadoList);
            em.persist(sexo);
            for (Egresado egresadoListEgresado : sexo.getEgresadoList()) {
                Sexo oldSexoIDOfEgresadoListEgresado = egresadoListEgresado.getSexoID();
                egresadoListEgresado.setSexoID(sexo);
                egresadoListEgresado = em.merge(egresadoListEgresado);
                if (oldSexoIDOfEgresadoListEgresado != null) {
                    oldSexoIDOfEgresadoListEgresado.getEgresadoList().remove(egresadoListEgresado);
                    oldSexoIDOfEgresadoListEgresado = em.merge(oldSexoIDOfEgresadoListEgresado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSexo(sexo.getId()) != null) {
                throw new PreexistingEntityException("Sexo " + sexo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sexo sexo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sexo persistentSexo = em.find(Sexo.class, sexo.getId());
            List<Egresado> egresadoListOld = persistentSexo.getEgresadoList();
            List<Egresado> egresadoListNew = sexo.getEgresadoList();
            List<String> illegalOrphanMessages = null;
            for (Egresado egresadoListOldEgresado : egresadoListOld) {
                if (!egresadoListNew.contains(egresadoListOldEgresado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Egresado " + egresadoListOldEgresado + " since its sexoID field is not nullable.");
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
            sexo.setEgresadoList(egresadoListNew);
            sexo = em.merge(sexo);
            for (Egresado egresadoListNewEgresado : egresadoListNew) {
                if (!egresadoListOld.contains(egresadoListNewEgresado)) {
                    Sexo oldSexoIDOfEgresadoListNewEgresado = egresadoListNewEgresado.getSexoID();
                    egresadoListNewEgresado.setSexoID(sexo);
                    egresadoListNewEgresado = em.merge(egresadoListNewEgresado);
                    if (oldSexoIDOfEgresadoListNewEgresado != null && !oldSexoIDOfEgresadoListNewEgresado.equals(sexo)) {
                        oldSexoIDOfEgresadoListNewEgresado.getEgresadoList().remove(egresadoListNewEgresado);
                        oldSexoIDOfEgresadoListNewEgresado = em.merge(oldSexoIDOfEgresadoListNewEgresado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sexo.getId();
                if (findSexo(id) == null) {
                    throw new NonexistentEntityException("The sexo with id " + id + " no longer exists.");
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
            Sexo sexo;
            try {
                sexo = em.getReference(Sexo.class, id);
                sexo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sexo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egresado> egresadoListOrphanCheck = sexo.getEgresadoList();
            for (Egresado egresadoListOrphanCheckEgresado : egresadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sexo (" + sexo + ") cannot be destroyed since the Egresado " + egresadoListOrphanCheckEgresado + " in its egresadoList field has a non-nullable sexoID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sexo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sexo> findSexoEntities() {
        return findSexoEntities(true, -1, -1);
    }

    public List<Sexo> findSexoEntities(int maxResults, int firstResult) {
        return findSexoEntities(false, maxResults, firstResult);
    }

    private List<Sexo> findSexoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sexo.class));
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

    public Sexo findSexo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sexo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSexoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sexo> rt = cq.from(Sexo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
