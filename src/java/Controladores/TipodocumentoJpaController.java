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
import Entidades.Tipodocumento;
import java.util.ArrayList;
import java.util.List;
import Entidades.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaEgresadosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) throws PreexistingEntityException, Exception {
        if (tipodocumento.getEgresadoList() == null) {
            tipodocumento.setEgresadoList(new ArrayList<Egresado>());
        }
        if (tipodocumento.getUsuariosList() == null) {
            tipodocumento.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Egresado> attachedEgresadoList = new ArrayList<Egresado>();
            for (Egresado egresadoListEgresadoToAttach : tipodocumento.getEgresadoList()) {
                egresadoListEgresadoToAttach = em.getReference(egresadoListEgresadoToAttach.getClass(), egresadoListEgresadoToAttach.getNumeroCedula());
                attachedEgresadoList.add(egresadoListEgresadoToAttach);
            }
            tipodocumento.setEgresadoList(attachedEgresadoList);
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : tipodocumento.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getCedula());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            tipodocumento.setUsuariosList(attachedUsuariosList);
            em.persist(tipodocumento);
            for (Egresado egresadoListEgresado : tipodocumento.getEgresadoList()) {
                Tipodocumento oldTipoDocumentoIDOfEgresadoListEgresado = egresadoListEgresado.getTipoDocumentoID();
                egresadoListEgresado.setTipoDocumentoID(tipodocumento);
                egresadoListEgresado = em.merge(egresadoListEgresado);
                if (oldTipoDocumentoIDOfEgresadoListEgresado != null) {
                    oldTipoDocumentoIDOfEgresadoListEgresado.getEgresadoList().remove(egresadoListEgresado);
                    oldTipoDocumentoIDOfEgresadoListEgresado = em.merge(oldTipoDocumentoIDOfEgresadoListEgresado);
                }
            }
            for (Usuarios usuariosListUsuarios : tipodocumento.getUsuariosList()) {
                Tipodocumento oldTipoDocIDOfUsuariosListUsuarios = usuariosListUsuarios.getTipoDocID();
                usuariosListUsuarios.setTipoDocID(tipodocumento);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
                if (oldTipoDocIDOfUsuariosListUsuarios != null) {
                    oldTipoDocIDOfUsuariosListUsuarios.getUsuariosList().remove(usuariosListUsuarios);
                    oldTipoDocIDOfUsuariosListUsuarios = em.merge(oldTipoDocIDOfUsuariosListUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipodocumento(tipodocumento.getId()) != null) {
                throw new PreexistingEntityException("Tipodocumento " + tipodocumento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getId());
            List<Egresado> egresadoListOld = persistentTipodocumento.getEgresadoList();
            List<Egresado> egresadoListNew = tipodocumento.getEgresadoList();
            List<Usuarios> usuariosListOld = persistentTipodocumento.getUsuariosList();
            List<Usuarios> usuariosListNew = tipodocumento.getUsuariosList();
            List<String> illegalOrphanMessages = null;
            for (Egresado egresadoListOldEgresado : egresadoListOld) {
                if (!egresadoListNew.contains(egresadoListOldEgresado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Egresado " + egresadoListOldEgresado + " since its tipoDocumentoID field is not nullable.");
                }
            }
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosListOldUsuarios + " since its tipoDocID field is not nullable.");
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
            tipodocumento.setEgresadoList(egresadoListNew);
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getCedula());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            tipodocumento.setUsuariosList(usuariosListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Egresado egresadoListNewEgresado : egresadoListNew) {
                if (!egresadoListOld.contains(egresadoListNewEgresado)) {
                    Tipodocumento oldTipoDocumentoIDOfEgresadoListNewEgresado = egresadoListNewEgresado.getTipoDocumentoID();
                    egresadoListNewEgresado.setTipoDocumentoID(tipodocumento);
                    egresadoListNewEgresado = em.merge(egresadoListNewEgresado);
                    if (oldTipoDocumentoIDOfEgresadoListNewEgresado != null && !oldTipoDocumentoIDOfEgresadoListNewEgresado.equals(tipodocumento)) {
                        oldTipoDocumentoIDOfEgresadoListNewEgresado.getEgresadoList().remove(egresadoListNewEgresado);
                        oldTipoDocumentoIDOfEgresadoListNewEgresado = em.merge(oldTipoDocumentoIDOfEgresadoListNewEgresado);
                    }
                }
            }
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    Tipodocumento oldTipoDocIDOfUsuariosListNewUsuarios = usuariosListNewUsuarios.getTipoDocID();
                    usuariosListNewUsuarios.setTipoDocID(tipodocumento);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
                    if (oldTipoDocIDOfUsuariosListNewUsuarios != null && !oldTipoDocIDOfUsuariosListNewUsuarios.equals(tipodocumento)) {
                        oldTipoDocIDOfUsuariosListNewUsuarios.getUsuariosList().remove(usuariosListNewUsuarios);
                        oldTipoDocIDOfUsuariosListNewUsuarios = em.merge(oldTipoDocIDOfUsuariosListNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getId();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egresado> egresadoListOrphanCheck = tipodocumento.getEgresadoList();
            for (Egresado egresadoListOrphanCheckEgresado : egresadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Egresado " + egresadoListOrphanCheckEgresado + " in its egresadoList field has a non-nullable tipoDocumentoID field.");
            }
            List<Usuarios> usuariosListOrphanCheck = tipodocumento.getUsuariosList();
            for (Usuarios usuariosListOrphanCheckUsuarios : usuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Usuarios " + usuariosListOrphanCheckUsuarios + " in its usuariosList field has a non-nullable tipoDocID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
