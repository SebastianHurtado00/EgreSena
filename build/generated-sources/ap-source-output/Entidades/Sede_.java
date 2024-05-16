package Entidades;

import Entidades.Egresado;
import Entidades.Formacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-05-14T11:20:43")
@StaticMetamodel(Sede.class)
public class Sede_ { 

    public static volatile ListAttribute<Sede, Formacion> formacionList;
    public static volatile ListAttribute<Sede, Egresado> egresadoList;
    public static volatile SingularAttribute<Sede, Integer> id;
    public static volatile SingularAttribute<Sede, String> nombre;

}