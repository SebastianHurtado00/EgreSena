package Entidades;

import Entidades.Egresado;
import Entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-05-14T11:20:43")
@StaticMetamodel(Tipodocumento.class)
public class Tipodocumento_ { 

    public static volatile ListAttribute<Tipodocumento, Usuarios> usuariosList;
    public static volatile ListAttribute<Tipodocumento, Egresado> egresadoList;
    public static volatile SingularAttribute<Tipodocumento, Integer> id;
    public static volatile SingularAttribute<Tipodocumento, String> nombre;

}