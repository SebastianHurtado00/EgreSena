package Entidades;

import Entidades.Ciudad;
import Entidades.Formacion;
import Entidades.NivelFormacion;
import Entidades.Sede;
import Entidades.Sexo;
import Entidades.TipoPoblacion;
import Entidades.Tipodocumento;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-05-14T11:20:43")
@StaticMetamodel(Egresado.class)
public class Egresado_ { 

    public static volatile SingularAttribute<Egresado, Sexo> sexoID;
    public static volatile SingularAttribute<Egresado, Tipodocumento> tipoDocumentoID;
    public static volatile SingularAttribute<Egresado, TipoPoblacion> tipoPoblacionID;
    public static volatile SingularAttribute<Egresado, String> descripcionExperiencia;
    public static volatile SingularAttribute<Egresado, Formacion> formacionID;
    public static volatile SingularAttribute<Egresado, String> direccionResidencia;
    public static volatile SingularAttribute<Egresado, Long> numeroCedula;
    public static volatile SingularAttribute<Egresado, String> formacionCursada;
    public static volatile SingularAttribute<Egresado, String> nombreUniversidad;
    public static volatile SingularAttribute<Egresado, String> nombreCarrera;
    public static volatile SingularAttribute<Egresado, Ciudad> ciudadID;
    public static volatile SingularAttribute<Egresado, String> nombre;
    public static volatile SingularAttribute<Egresado, Boolean> cadenaUniversitaria;
    public static volatile SingularAttribute<Egresado, Boolean> cadenaFormacion;
    public static volatile SingularAttribute<Egresado, Sede> sedeID;
    public static volatile SingularAttribute<Egresado, String> apellido;
    public static volatile SingularAttribute<Egresado, String> correo;
    public static volatile SingularAttribute<Egresado, Boolean> experiencia;
    public static volatile SingularAttribute<Egresado, NivelFormacion> nivelFormacionID;
    public static volatile SingularAttribute<Egresado, Integer> numeroCertificados;
    public static volatile SingularAttribute<Egresado, Boolean> trabajando;
    public static volatile SingularAttribute<Egresado, Long> numeroTelefono;

}