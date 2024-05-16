/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "egresado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egresado.findAll", query = "SELECT e FROM Egresado e"),
    @NamedQuery(name = "Egresado.findByNumeroCedula", query = "SELECT e FROM Egresado e WHERE e.numeroCedula = :numeroCedula"),
    @NamedQuery(name = "Egresado.findByNombre", query = "SELECT e FROM Egresado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Egresado.findByApellido", query = "SELECT e FROM Egresado e WHERE e.apellido = :apellido"),
    @NamedQuery(name = "Egresado.findByNumeroCertificados", query = "SELECT e FROM Egresado e WHERE e.numeroCertificados = :numeroCertificados"),
    @NamedQuery(name = "Egresado.findByCorreo", query = "SELECT e FROM Egresado e WHERE e.correo = :correo"),
    @NamedQuery(name = "Egresado.findByNumeroTelefono", query = "SELECT e FROM Egresado e WHERE e.numeroTelefono = :numeroTelefono"),
    @NamedQuery(name = "Egresado.findByDireccionResidencia", query = "SELECT e FROM Egresado e WHERE e.direccionResidencia = :direccionResidencia"),
    @NamedQuery(name = "Egresado.findByCadenaFormacion", query = "SELECT e FROM Egresado e WHERE e.cadenaFormacion = :cadenaFormacion"),
    @NamedQuery(name = "Egresado.findByFormacionCursada", query = "SELECT e FROM Egresado e WHERE e.formacionCursada = :formacionCursada"),
    @NamedQuery(name = "Egresado.findByCadenaUniversitaria", query = "SELECT e FROM Egresado e WHERE e.cadenaUniversitaria = :cadenaUniversitaria"),
    @NamedQuery(name = "Egresado.findByNombreUniversidad", query = "SELECT e FROM Egresado e WHERE e.nombreUniversidad = :nombreUniversidad"),
    @NamedQuery(name = "Egresado.findByNombreCarrera", query = "SELECT e FROM Egresado e WHERE e.nombreCarrera = :nombreCarrera"),
    @NamedQuery(name = "Egresado.findByExperiencia", query = "SELECT e FROM Egresado e WHERE e.experiencia = :experiencia"),
    @NamedQuery(name = "Egresado.findByDescripcionExperiencia", query = "SELECT e FROM Egresado e WHERE e.descripcionExperiencia = :descripcionExperiencia"),
    @NamedQuery(name = "Egresado.findByTrabajando", query = "SELECT e FROM Egresado e WHERE e.trabajando = :trabajando")})
public class Egresado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NumeroCedula")
    private Long numeroCedula;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "NumeroCertificados")
    private Integer numeroCertificados;
    @Basic(optional = false)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "Numero_Telefono")
    private long numeroTelefono;
    @Basic(optional = false)
    @Column(name = "DireccionResidencia")
    private String direccionResidencia;
    @Column(name = "Cadena_Formacion")
    private Boolean cadenaFormacion;
    @Column(name = "Formacion_Cursada")
    private String formacionCursada;
    @Column(name = "Cadena_Universitaria")
    private Boolean cadenaUniversitaria;
    @Column(name = "Nombre_Universidad")
    private String nombreUniversidad;
    @Column(name = "Nombre_Carrera")
    private String nombreCarrera;
    @Column(name = "Experiencia")
    private Boolean experiencia;
    @Column(name = "Descripcion_Experiencia")
    private String descripcionExperiencia;
    @Column(name = "Trabajando")
    private Boolean trabajando;
    @JoinColumn(name = "Ciudad_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Ciudad ciudadID;
    @JoinColumn(name = "Formacion_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Formacion formacionID;
    @JoinColumn(name = "Nivel_Formacion_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private NivelFormacion nivelFormacionID;
    @JoinColumn(name = "Sede_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sede sedeID;
    @JoinColumn(name = "Sexo_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sexo sexoID;
    @JoinColumn(name = "Tipo_Poblacion_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoPoblacion tipoPoblacionID;
    @JoinColumn(name = "TipoDocumento_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tipodocumento tipoDocumentoID;

    public Egresado() {
    }

    public Egresado(Long numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public Egresado(Long numeroCedula, String nombre, String apellido, String correo, long numeroTelefono, String direccionResidencia) {
        this.numeroCedula = numeroCedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.numeroTelefono = numeroTelefono;
        this.direccionResidencia = direccionResidencia;
    }

    public Long getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(Long numeroCedula) {
        this.numeroCedula = numeroCedula;
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

    public Integer getNumeroCertificados() {
        return numeroCertificados;
    }

    public void setNumeroCertificados(Integer numeroCertificados) {
        this.numeroCertificados = numeroCertificados;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public long getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(long numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public Boolean getCadenaFormacion() {
        return cadenaFormacion;
    }

    public void setCadenaFormacion(Boolean cadenaFormacion) {
        this.cadenaFormacion = cadenaFormacion;
    }

    public String getFormacionCursada() {
        return formacionCursada;
    }

    public void setFormacionCursada(String formacionCursada) {
        this.formacionCursada = formacionCursada;
    }

    public Boolean getCadenaUniversitaria() {
        return cadenaUniversitaria;
    }

    public void setCadenaUniversitaria(Boolean cadenaUniversitaria) {
        this.cadenaUniversitaria = cadenaUniversitaria;
    }

    public String getNombreUniversidad() {
        return nombreUniversidad;
    }

    public void setNombreUniversidad(String nombreUniversidad) {
        this.nombreUniversidad = nombreUniversidad;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Boolean getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Boolean experiencia) {
        this.experiencia = experiencia;
    }

    public String getDescripcionExperiencia() {
        return descripcionExperiencia;
    }

    public void setDescripcionExperiencia(String descripcionExperiencia) {
        this.descripcionExperiencia = descripcionExperiencia;
    }

    public Boolean getTrabajando() {
        return trabajando;
    }

    public void setTrabajando(Boolean trabajando) {
        this.trabajando = trabajando;
    }

    public Ciudad getCiudadID() {
        return ciudadID;
    }

    public void setCiudadID(Ciudad ciudadID) {
        this.ciudadID = ciudadID;
    }

    public Formacion getFormacionID() {
        return formacionID;
    }

    public void setFormacionID(Formacion formacionID) {
        this.formacionID = formacionID;
    }

    public NivelFormacion getNivelFormacionID() {
        return nivelFormacionID;
    }

    public void setNivelFormacionID(NivelFormacion nivelFormacionID) {
        this.nivelFormacionID = nivelFormacionID;
    }

    public Sede getSedeID() {
        return sedeID;
    }

    public void setSedeID(Sede sedeID) {
        this.sedeID = sedeID;
    }

    public Sexo getSexoID() {
        return sexoID;
    }

    public void setSexoID(Sexo sexoID) {
        this.sexoID = sexoID;
    }

    public TipoPoblacion getTipoPoblacionID() {
        return tipoPoblacionID;
    }

    public void setTipoPoblacionID(TipoPoblacion tipoPoblacionID) {
        this.tipoPoblacionID = tipoPoblacionID;
    }

    public Tipodocumento getTipoDocumentoID() {
        return tipoDocumentoID;
    }

    public void setTipoDocumentoID(Tipodocumento tipoDocumentoID) {
        this.tipoDocumentoID = tipoDocumentoID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroCedula != null ? numeroCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Egresado egresado = (Egresado) object;
        return numeroCedula.equals(egresado.numeroCedula)
                && Objects.equals(nombre, egresado.nombre)
                && Objects.equals(apellido, egresado.apellido)
                && Objects.equals(numeroCertificados, egresado.numeroCertificados)
                && Objects.equals(correo, egresado.correo)
                && numeroTelefono == egresado.numeroTelefono
                && Objects.equals(direccionResidencia, egresado.direccionResidencia)
                && Objects.equals(cadenaFormacion, egresado.cadenaFormacion)
                && Objects.equals(formacionCursada, egresado.formacionCursada)
                && Objects.equals(cadenaUniversitaria, egresado.cadenaUniversitaria)
                && Objects.equals(nombreUniversidad, egresado.nombreUniversidad)
                && Objects.equals(nombreCarrera, egresado.nombreCarrera)
                && Objects.equals(experiencia, egresado.experiencia)
                && Objects.equals(descripcionExperiencia, egresado.descripcionExperiencia)
                && Objects.equals(trabajando, egresado.trabajando)
                && Objects.equals(ciudadID, egresado.ciudadID)
                && Objects.equals(formacionID, egresado.formacionID)
                && Objects.equals(nivelFormacionID, egresado.nivelFormacionID)
                && Objects.equals(sedeID, egresado.sedeID)
                && Objects.equals(sexoID, egresado.sexoID)
                && Objects.equals(tipoPoblacionID, egresado.tipoPoblacionID)
                && Objects.equals(tipoDocumentoID, egresado.tipoDocumentoID);
    }

    @Override
    public String toString() {
        return "Entidades.Egresado[ numeroCedula=" + numeroCedula + " ]";
    }

}
