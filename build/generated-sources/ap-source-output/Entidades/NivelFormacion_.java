package Entidades;

import Entidades.Egresado;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-05-14T11:20:43")
@StaticMetamodel(NivelFormacion.class)
public class NivelFormacion_ { 

    public static volatile ListAttribute<NivelFormacion, Egresado> egresadoList;
    public static volatile SingularAttribute<NivelFormacion, Integer> id;
    public static volatile SingularAttribute<NivelFormacion, String> nombre;

}