package Entidades;

import Entidades.Egresado;
import Entidades.Sede;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-05-14T11:20:43")
@StaticMetamodel(Formacion.class)
public class Formacion_ { 

    public static volatile SingularAttribute<Formacion, Sede> sedeID;
    public static volatile ListAttribute<Formacion, Egresado> egresadoList;
    public static volatile SingularAttribute<Formacion, Integer> id;
    public static volatile SingularAttribute<Formacion, String> nombre;

}