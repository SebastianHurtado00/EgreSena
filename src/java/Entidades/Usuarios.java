    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package Entidades;

    import com.password4j.Hash;
    import com.password4j.Password;
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
    @Table(name = "usuarios")
    @XmlRootElement
    @NamedQueries({
        @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
        @NamedQuery(name = "Usuarios.findByCedula", query = "SELECT u FROM Usuarios u WHERE u.cedula = :cedula"),
        @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
        @NamedQuery(name = "Usuarios.findByApellido", query = "SELECT u FROM Usuarios u WHERE u.apellido = :apellido"),
        @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo"),
        @NamedQuery(name = "Usuarios.findByTelefono", query = "SELECT u FROM Usuarios u WHERE u.telefono = :telefono"),
        @NamedQuery(name = "Usuarios.findByRol", query = "SELECT u FROM Usuarios u WHERE u.rol = :rol"),
        @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password")})
    public class Usuarios implements Serializable {

        private static final long serialVersionUID = 1L;
        @Id
        @Basic(optional = false)
        @Column(name = "Cedula")
        private Integer cedula;
        @Basic(optional = false)
        @Column(name = "Nombre")
        private String nombre;
        @Basic(optional = false)
        @Column(name = "Apellido")
        private String apellido;
        @Basic(optional = false)
        @Column(name = "correo")
        private String correo;
        @Basic(optional = false)
        @Column(name = "Telefono")
        private long telefono;
        @Basic(optional = false)
        @Column(name = "rol")
        private int rol;
        @Basic(optional = false)
        @Column(name = "Password")
        private String password;
        @JoinColumn(name = "Tipo_Doc_ID", referencedColumnName = "ID")
        @ManyToOne(optional = false)
        private Tipodocumento tipoDocID;

        public Usuarios() {
        }

        public Usuarios(Integer cedula) {
            this.cedula = cedula;
        }

        public Usuarios(Integer cedula, String nombre, String apellido, String correo, long telefono, int rol, String password) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.apellido = apellido;
            this.correo = correo;
            this.telefono = telefono;
            this.rol = rol;
            this.password = password;
        }

        public Integer getCedula() {
            return cedula;
        }

        public void setCedula(Integer cedula) {
            this.cedula = cedula;
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

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public long getTelefono() {
            return telefono;
        }

        public void setTelefono(long telefono) {
            this.telefono = telefono;
        }

        public int getRol() {
            return rol;
        }

        public void setRol(int rol) {
            this.rol = rol;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Tipodocumento getTipoDocID() {
            return tipoDocID;
        }

        public void setTipoDocID(Tipodocumento tipoDocID) {
            this.tipoDocID = tipoDocID;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += (cedula != null ? cedula.hashCode() : 0);
            return hash;
        }

        public String EncryptarClave(String clave) {

            Hash hash = Password.hash(clave).addPepper().withScrypt();
            return hash.getResult();
        }

        public boolean DencryptarClave(String clavehast, String clavelogin) {
            return Password.check(clavelogin, clavehast).addPepper().withScrypt();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            Usuarios other = (Usuarios) object;
            return Objects.equals(cedula, other.cedula)
                    && Objects.equals(nombre, other.nombre)
                    && Objects.equals(apellido, other.apellido)
                    && Objects.equals(correo, other.correo)
                    && Objects.equals(telefono, other.telefono)
                    && Objects.equals(tipoDocID, other.tipoDocID);
        }

        @Override
        public String toString() {
            return "Entidades.Usuarios[ cedula=" + cedula + " ]";
        }

    }
