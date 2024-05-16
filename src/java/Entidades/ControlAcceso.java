/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "control_acceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlAcceso.findAll", query = "SELECT c FROM ControlAcceso c"),
    @NamedQuery(name = "ControlAcceso.findById", query = "SELECT c FROM ControlAcceso c WHERE c.id = :id"),
    @NamedQuery(name = "ControlAcceso.findByUsuario", query = "SELECT c FROM ControlAcceso c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "ControlAcceso.findByAccion", query = "SELECT c FROM ControlAcceso c WHERE c.accion = :accion"),
    @NamedQuery(name = "ControlAcceso.findByFechaIngreso", query = "SELECT c FROM ControlAcceso c WHERE c.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "ControlAcceso.findByTablaModificada", query = "SELECT c FROM ControlAcceso c WHERE c.tablaModificada = :tablaModificada"),
    @NamedQuery(name = "ControlAcceso.findByIdRegistroModificado", query = "SELECT c FROM ControlAcceso c WHERE c.idRegistroModificado = :idRegistroModificado")})
public class ControlAcceso implements Serializable {

    @Column(name = "Use")
    private Integer use;

    @Column(name = "id_admin")
    private String idAdmin;

    @Column(name = "Fecha_cierre")
    private String fechacierre;
    @Column(name = "Hora_Cierre")
    private String horaCierre;

    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Hora_Ingreso")
    private String horaIngreso;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Cedula_Ingresante")
    private Integer cedulaIngresante;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "Usuario")
    private Integer usuario;
    @Column(name = "Accion", length = 100)
    private String accion;
    @Column(name = "Fecha_Ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "Tabla_Modificada", length = 45)
    private String tablaModificada;
    @Column(name = "Id_Registro_Modificado", length = 45)
    private String idRegistroModificado;

    public ControlAcceso() {
    }

    public ControlAcceso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTablaModificada() {
        return tablaModificada;
    }

    public void setTablaModificada(String tablaModificada) {
        this.tablaModificada = tablaModificada;
    }

    public String getIdRegistroModificado() {
        return idRegistroModificado;
    }

    public void setIdRegistroModificado(String idRegistroModificado) {
        this.idRegistroModificado = idRegistroModificado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlAcceso)) {
            return false;
        }
        ControlAcceso other = (ControlAcceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ControlAcceso[ id=" + id + " ]";
    }

    public Integer getCedulaIngresante() {
        return cedulaIngresante;
    }

    public void setCedulaIngresante(Integer cedulaIngresante) {
        this.cedulaIngresante = cedulaIngresante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(String fechacierre) {
        this.fechacierre = fechacierre;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }
    
}
