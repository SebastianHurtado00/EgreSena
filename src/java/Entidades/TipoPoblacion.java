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
@Table(name = "tipo_poblacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPoblacion.findAll", query = "SELECT t FROM TipoPoblacion t"),
    @NamedQuery(name = "TipoPoblacion.findById", query = "SELECT t FROM TipoPoblacion t WHERE t.id = :id"),
    @NamedQuery(name = "TipoPoblacion.findByNombre", query = "SELECT t FROM TipoPoblacion t WHERE t.nombre = :nombre")})
public class TipoPoblacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPoblacionID")
    private List<Egresado> egresadoList;

    public TipoPoblacion() {
    }

    public TipoPoblacion(Integer id) {
        this.id = id;
    }

    public TipoPoblacion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

        TipoPoblacion other = (TipoPoblacion) object;

        // Comparar los atributos para determinar si son iguales
        if (this.id != other.id) {
            return false;
        }

        // Comparar más atributos si es necesario
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }

        // Agrega más comparaciones para otros atributos si es necesario
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoPoblacion[ id=" + id + " ]";
    }

}
