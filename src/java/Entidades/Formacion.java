/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "formacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formacion.findAll", query = "SELECT f FROM Formacion f"),
    @NamedQuery(name = "Formacion.findById", query = "SELECT f FROM Formacion f WHERE f.id = :id"),
    @NamedQuery(name = "Formacion.findByNombre", query = "SELECT f FROM Formacion f WHERE f.nombre = :nombre")})
public class Formacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @JoinColumn(name = "Sede_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sede sedeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formacionID")
    private List<Egresado> egresadoList;

    public Formacion() {
    }

    public Formacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sede getSedeID() {
        return sedeID;
    }

    public void setSedeID(Sede sedeID) {
        this.sedeID = sedeID;
    }

    @XmlTransient
    public List<Egresado> getEgresadoList() {
        return egresadoList;
    }

    public void setEgresadoList(List<Egresado> egresadoList) {
        this.egresadoList = egresadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
    
    Formacion other = (Formacion) object;
    
    // Comparar los atributos para determinar si son iguales
    if (!Objects.equals(this.id, other.id)) {
        return false;
    }
    
    if (!Objects.equals(this.nombre, other.nombre)) {
        return false;
    }
    
    if (!Objects.equals(this.sedeID, other.sedeID)) {
        return false;
    }
    
    // Agrega más comparaciones para otros atributos si es necesario
    
    return true;
}


    @Override
    public String toString() {
        return "Entidades.Formacion[ id=" + id + " ]";
    }
    
}
