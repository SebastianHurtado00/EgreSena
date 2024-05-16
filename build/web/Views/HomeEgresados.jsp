<%-- 
    Document   : HomeEgresados
    Created on : 20/03/2024, 08:45:55 AM
    Author     : ASUS
--%>

<%@page import="javax.persistence.Id"%>
<%@page import="Controladores.EgresadoJpaController"%>
<%@page import="Entidades.Egresado"%>
<%@page import="Entidades.NivelFormacion"%>
<%@page import="Entidades.Ciudad"%>
<%@page import="Entidades.Formacion"%>
<%@page import="Entidades.Sede"%>
<%@page import="Entidades.TipoPoblacion"%>
<%@page import="Entidades.Sexo"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Tipodocumento"%>
<%@page import="Controladores.TipoPoblacionJpaController"%>
<%@page import="Controladores.NivelFormacionJpaController"%>
<%@page import="Controladores.CiudadJpaController"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Controladores.SedeJpaController"%>
<%@page import="Controladores.SexoJpaController"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
<%@page import="Entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    HttpSession sessionObtenida = request.getSession();
    Usuarios usuarioEntrante = new Usuarios();
    if (sessionObtenida.getAttribute("Egresado") == null) {
        response.sendRedirect("../index.jsp");
    } else {

        usuarioEntrante = (Usuarios) sessionObtenida.getAttribute("Egresado");

        TipodocumentoJpaController controlTipoDoc = new TipodocumentoJpaController();
        SexoJpaController controlSexo = new SexoJpaController();
        SedeJpaController controlSede = new SedeJpaController();
        FormacionJpaController controlFormacion = new FormacionJpaController();
        CiudadJpaController controlCiudad = new CiudadJpaController();
        NivelFormacionJpaController controlNivelFomacion = new NivelFormacionJpaController();
        TipoPoblacionJpaController controlTipoPoblacion = new TipoPoblacionJpaController();

        EgresadoJpaController controlEgrasado = new EgresadoJpaController();
        Integer IdEgrsado = usuarioEntrante.getCedula();
        String IdStr = String.valueOf(IdEgrsado);
        Long IdLong = Long.parseLong(IdStr);

        Egresado EgresadoEnonctrado = controlEgrasado.findEgresado(IdLong);

        List<Tipodocumento> listaTipoDoc = controlTipoDoc.findTipodocumentoEntities();
        List<Sexo> listaSexo = controlSexo.findSexoEntities();
        List<TipoPoblacion> listaTipoPoblacion = controlTipoPoblacion.findTipoPoblacionEntities();
        List<Sede> ListaSedes = controlSede.findSedeEntities();
        List<Formacion> listaFormacion = controlFormacion.findFormacionEntities();
        List<Ciudad> listaCiudades = controlCiudad.findCiudadEntities();
        List<NivelFormacion> listaNivelFormacion = controlNivelFomacion.findNivelFormacionEntities();
%>
<html>
    <head>

        <title>Home Egresados</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS v5.2.1 -->
        <link rel="shortcut icon" href="../IMG/work-from-home.webp" type="image/x-icon">
        <link rel="stylesheet" href="../CSS/HomeAdministrador.css"/>
        <link rel="stylesheet" href="../CSS/Footer.css"/>
        <link rel="stylesheet" href="../CSS/UsuarioConectado.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    </head>

    <!-- Demo header-->
    <section class="section-0 d-flex justify-content-between">
        <h2 class="text-start mt-4" style="margin-left: 7px; font-family: serif">Regional Sucre</h2>
        <img  src="../IMG/Logo_Sena_Sin_Fondo.png" width="150px" height="200px" alt="alt" class="align-self-end img-fluid "/> 
    </section>

    <!--Menu-->
    <header style="font-family: monospace" class="header sticky-top">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid d-flex justify-content-between align-items-center">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <!--Boton Usuario y menu de Datos personales y cerrado de sesion -->
                <div class="navbar-brand order-lg-2" href="#">
                    <strong class="h6 mb-0 font-weight-bold">
                        <div class="dropdown-center">
                            <button id="btn-message" class="button-message" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <div class="content-avatar">
                                    <div class="status-user"></div>
                                    <div class="avatar">
                                        <svg class="user-img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                        <path d="M12,12.5c-3.04,0-5.5,1.73-5.5,3.5s2.46,3.5,5.5,3.5,5.5-1.73,5.5-3.5-2.46-3.5-5.5-3.5Zm0-.5c1.66,0,3-1.34,3-3s-1.34-3-3-3-3,1.34-3,3,1.34,3,3,3Z"></path>
                                        </svg>
                                    </div>
                                </div>
                                <div class="notice-content " style="font-family: monospace">
                                    <div class="username text-small"><%=usuarioEntrante.getNombre()%></div>
                                    <div class="text-center text-small text-gray">Egresado</div>
                                </div>
                            </button>
                            <ul class="dropdown-menu text-center" style="font-family: monospace">
                                <li><a class="dropdown-item" href="../CerradoSession.jsp">Cerrado de Sesion</a></li>
                            </ul>
                        </div>
                    </strong>
                </div>
                <!--Items del menu -->
                <div class="collapse navbar-collapse" id="navbarText">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="HomeEgresados.jsp">Home</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <%
        //Validacion de para que la contraseña no sea igual a la cedula
        String DocStr = String.valueOf(usuarioEntrante.getCedula());
        if (usuarioEntrante.DencryptarClave(usuarioEntrante.getPassword(), DocStr)) {
    %>
    <script>

        $(document).ready(function () {
            $('#ModalCambio').modal('show');
        });
    </script>

    <%
        }

    %>
    <!-- Modal cambio de contraseña -->
    <div class="modal" id="ModalCambio" tabindex="-1" data-bs-backdrop="static">
        <form action="<%=request.getContextPath()%>/RestablecimientosContrase_as" method="post">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Por seguridad su contraseña la debe actualizar</h5>
                    </div>
                    <div class="modal-body">
                        <div class="col-md-12">
                            <img src="../IMG/id-facial.webp" class="mb-1" alt="alt" width="30px" height="30px"/>
                            <label for="numeroDocumento" class="form-label">N° Documento</label>
                            <input type="text" name="numeroDocumentoCambio" id="numeroDocumentoCambio" value="<%=EgresadoEnonctrado.getNumeroCedula()%>" class="form-control mb-3" readonly max="99999999999">

                            <img src="../IMG/establecer-la-contrasena.webp" alt="alt" width="30px" height="30px"/>
                            <label for="PasswordNueva" class="form-label">Contraseña nueva</label>
                            <input type="password" name="PasswordNueva" id="PasswordNueva" class="form-control mb-3" maxlength="50" required="">

                            <img src="../IMG/contrasena.png" alt="alt" width="30px" height="30px"/>
                            <label for="ConfirmacionPassword" class="form-label">Confirmacion de contraseña</label>
                            <input type="password" id="ConfirmacionPassword" name="ConfirmacionPassword" class="form-control mb-3" maxlength="50" required="">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button value="RestablcerPasswordHome" name="BtnRestablecer" class="btn btn-success">Cambiar contraseña</button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <body style="background: #F1F1F1">
        <div class="card mb-5 mt-3 mx-auto" style="max-width: 900px ; min-width: 200px; font-family: monospace">
            <h5 class="card-title text-center mt-3" ><img src="../IMG/graduado.webp" alt="alt" width="50px" height="50px"/>Datos de egresado</h5>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/ModificacionEgresado" method="post">
                    <!--Seccion de datos Personales -->
                    <div class="row">
                        <h5 class="card-title mb-3">Informacion Personal</h5>
                        <div class="col-md-6">
                            <img src="../IMG/id-facial.webp" alt="alt" width="30px" height="30px"/>
                            <label for="numeroDocumento" class="form-label">N° Documento</label>
                            <input type="number" name="numeroDocumento" id="numeroDocumento" class="form-control mb-3"value="<%=EgresadoEnonctrado.getNumeroCedula()%>" readonly max="99999999999">

                            <img src="../IMG/nombre.webp" alt="alt" width="30px" height="30px"/>
                            <label for="nombres" class="form-label">Nombres</label>
                            <input type="text" value="<%=EgresadoEnonctrado.getNombre()%>" name="nombres" id="nombres" class="form-control mb-3" readonly maxlength="45">

                            <img src="../IMG/etiqueta-de-nombre.webp" alt="alt" width="30px" height="30px"/>
                            <label for="apellidos" class="form-label">Apellidos</label>
                            <input type="text" value="<%=EgresadoEnonctrado.getApellido()%>"  id="apellidos"name="apellidos" class="form-control mb-3" readonly maxlength="45">
                        </div>

                        <div class="col-md-6">
                            <img src="../IMG/tarjeta-de-identificacion.webp" alt="alt" width="30px" height="30px"/>
                            <label for="tipoDocumento" class="form-label">Tipo de Documento</label>
                            <select name="tipoDocumento" id="tipoDocumento" class="form-select mb-3" required>
                                <option value="<%=EgresadoEnonctrado.getTipoDocumentoID().getId()%>" ><%=EgresadoEnonctrado.getTipoDocumentoID().getNombre()%></option>
                                <% for (Tipodocumento TipoDoc : listaTipoDoc) {
                                        if (EgresadoEnonctrado.getTipoDocumentoID().getId() != TipoDoc.getId()) {
                                %>
                                <option value="<%=TipoDoc.getId()%>"><%=TipoDoc.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                            <img src="../IMG/sexo.webp" alt="alt" width="30px" height="30px"/>
                            <label for="sexo" class="form-label">Sexo</label>
                            <select  name="sexo" id="sexo" class="form-select mb-3" required>
                                <option  value="<%=EgresadoEnonctrado.getSexoID().getId()%>" selected ><%=EgresadoEnonctrado.getSexoID().getNombre()%></option>
                                <%
                                    for (Sexo sex : listaSexo) {
                                        if (EgresadoEnonctrado.getSexoID().getId() != sex.getId()) {
                                %>
                                <option value="<%=sex.getId()%>"><%=sex.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>

                            <img src="../IMG/personas.webp" alt="alt" width="30px" height="30px"/>
                            <label for="tipoPoblacion" class="form-label">Tipo de Población</label>
                            <select name="tipoPoblacion" id="tipoPoblacion" class="form-select mb-3" required>
                                <option value="<%=EgresadoEnonctrado.getTipoPoblacionID().getId()%>" selected><%=EgresadoEnonctrado.getTipoPoblacionID().getNombre()%></option>
                                <%
                                    for (TipoPoblacion TipoPoblacion : listaTipoPoblacion) {
                                        if (EgresadoEnonctrado.getTipoPoblacionID().getId() != TipoPoblacion.getId()) {


                                %>
                                <option value="<%=TipoPoblacion.getId()%>"><%=TipoPoblacion.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <h5 class="card-title mb-3">Informacion Residencia y contactos</h5>
                        <div class="col-md-6">
                            <img src="../IMG/edificios.webp" alt="alt" width="30px" height="30px"/>
                            <label for="ciudad" class="form-label">Ciudad</label>
                            <select name="ciudad" id="ciudad" class="form-select mb-3" required>
                                <option value="<%=EgresadoEnonctrado.getCiudadID().getId()%>" selected><%=EgresadoEnonctrado.getCiudadID().getNombre()%></option>
                                <%
                                    for (Ciudad ciudad : listaCiudades) {
                                        if (EgresadoEnonctrado.getCiudadID().getId() != ciudad.getId()) {
                                %>
                                <option value="<%=ciudad.getId()%>"><%=ciudad.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>

                            <img src="../IMG/marcador-de-posicion.webp" alt="alt" width="30px" height="30px"/>
                            <label for="direccion" class="form-label">Dirección</label>
                            <input name="direccion" value="<%=EgresadoEnonctrado.getDireccionResidencia()%>" type="text" id="direccion" class="form-control mb-3" maxlength="100" placeholder="Opcional">
                        </div>

                        <div class="col-md-6">
                            <img src="../IMG/marcador-de-posicion.webp" alt="alt" width="30px" height="30px"/>

                            <label for="numCertificados" class="form-label">N° de Certificados</label>
                            <input  name="numCertificados" type="number" id="numCertificados" value="<%=EgresadoEnonctrado.getNumeroCertificados()%>" class="form-control mb-3" max="99999999999"" placeholder="Opcional">

                            <img src="../IMG/gmail.webp" alt="alt" width="30px" height="30px"/>
                            <label for="correo" class="form-label">Correo</label>
                            <input name="correo" type="email" id="correo" class="form-control mb-3"  value="<%=EgresadoEnonctrado.getCorreo()%>" maxlength="45">
                        </div>

                        <div class="row">
                            <div class = "col-md-3">
                            </div>
                            <div class = "col-md-6">
                                <img src="../IMG/atencion-al-cliente.webp" alt="alt" width="30px" height="30px"/>
                                <label for="numContacto" class="form-label mx-auto">Número de Contacto</label>
                                <input name="numContacto" type="tel" id="numContacto"value="<%=EgresadoEnonctrado.getNumeroTelefono()%>" class="form-control mb-3">
                            </div>
                            <div class = "col-md-3">
                            </div>
                        </div>


                    </div>
                    <!--Informacion Laboral -->
                    <h5 class="card-title mb-3">Informacion Academica</h5>
                    <div class="row mb-5">
                        <div class="col-md-4">
                            <img src="../IMG/SedeFormacion.webp" alt="alt" width="30px" height="30px"/>
                            <label for="sede" class="form-label">Sede</label>
                            <select name="sede" id="sede" class="form-select mb-3">
                                <option value="<%=EgresadoEnonctrado.getSedeID().getId()%>" selected ><%=EgresadoEnonctrado.getSedeID().getNombre()%></option>
                                <%
                                    for (Sede sede : ListaSedes) {
                                        if (EgresadoEnonctrado.getSedeID().getId() != sede.getId()) {
                                %>
                                <option value="<%=sede.getId()%>"><%=sede.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>

                            <!-- Pregunta y check Box sobre cadena de formacion -->
                            <div>
                                <!-- Pregunta -->
                                <label class="form-label mx-2">¿Cadena de formación?</label>
                                <br>
                                <!-- Checkbox "Sí" -->
                                <div class="form-check form-check-inline mx-4">
                                    <input name="CadenaFormacion" class="form-check-input" type="checkbox" id="siCheckbox" <% if (EgresadoEnonctrado.getCadenaFormacion()) { %> checked <% } %> onchange="mostrarOcultarInput(this.checked, 'miInputContainer', 'siCheckbox', 'noCheckbox'), validarCheckboxes();">
                                    <label class="form-check-label" for="siCheckbox">Sí</label>
                                </div>

                                <!-- Checkbox "No" -->
                                <div class="form-check form-check-inline">
                                    <input name="CadenaFormacion" class="form-check-input" type="checkbox" id="noCheckbox" <% if (!EgresadoEnonctrado.getCadenaFormacion()) { %> checked <% }%> onchange="mostrarOcultarInput(!this.checked, 'miInputContainer', 'siCheckbox', 'noCheckbox'), validarCheckboxes();">
                                    <label class="form-check-label" for="noCheckbox">No</label>
                                </div>
                                <!-- Contenedor del input -->
                                <div id="miInputContainer" style="display: none; margin-top: 10px;">
                                    <input name="FormacionCursada" class="form-control mb-3" type="text" id="miInput" placeholder="Formacion Cursada">
                                </div>
                            </div>

                        </div>

                        <div class="col-md-4">
                            <img src="../IMG/formacion.webp" alt="alt" width="30px" height="30px"/>
                            <label for="formacion" class="form-label">Formación</label>
                            <select name="formacion" id="formacion" class="form-select mb-3">
                                <option value="<%=EgresadoEnonctrado.getFormacionID().getId()%>" selected ><%=EgresadoEnonctrado.getFormacionID().getNombre()%></option>
                                <%
                                    for (Formacion formacion : listaFormacion) {
                                        if (EgresadoEnonctrado.getFormacionID().getId() != formacion.getId()) {


                                %>
                                <option value="<%=formacion.getId()%>"><%=formacion.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                            <!-- Pregunta y check Box sobre Experiencia -->
                            <div >
                                <!-- Pregunta -->
                                <label class="form-label mx-2">¿Experiencia Laboral?</label>
                                <br>
                                <!-- Checkbox "Sí" -->
                                <div class="form-check form-check-inline mx-4">
                                    <input name="Experiencia" class="form-check-input" type="checkbox" id="siCheckboxExp" <% if (EgresadoEnonctrado.getExperiencia()) { %> checked <% } %> onchange="mostrarOcultarInput(this.checked, 'miInputContainerExp', 'siCheckboxExp', 'noCheckboxExp'), validarCheckboxes()">
                                    <label class="form-check-label" for="siCheckbox">Sí</label>
                                </div>

                                <!-- Checkbox "No" -->
                                <div class="form-check form-check-inline">
                                    <input name="Experiencia" class="form-check-input" type="checkbox" id="noCheckboxExp" <% if (!EgresadoEnonctrado.getExperiencia()) { %> checked <% }%> onchange="mostrarOcultarInput(!this.checked, 'miInputContainerExp', 'siCheckboxExp', 'noCheckboxExp'), validarCheckboxes()">
                                    <label class="form-check-label" for="noCheckbox">No</label>
                                </div>

                                <!-- Contenedor del input -->
                                <div id="miInputContainerExp" style="display: none; margin-top: 10px;">
                                    <textarea id="" name="ExperienciaDescrip" rows="5" cols="" class="form-control" placeholder="Describa su experiencia maximo(300 caracteres)" maxlength="300"></textarea>

                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <img src="../IMG/crecimiento.webp" alt="alt" width="30px" height="30px"/>
                            <label for="nivelFormacion" class="form-label">Nivel de Formación</label>
                            <select name="nivelFormacion" id="nivelFormacion" class="form-select mb-3">
                                <option value="<%=EgresadoEnonctrado.getNivelFormacionID().getId()%>" selected><%=EgresadoEnonctrado.getNivelFormacionID().getNombre()%></option>
                                <%
                                    for (NivelFormacion NivelFormacion : listaNivelFormacion) {
                                        if (EgresadoEnonctrado.getNivelFormacionID().getId() != NivelFormacion.getId()) {


                                %>
                                <option value="<%=NivelFormacion.getId()%>"><%=NivelFormacion.getNombre()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>

                            <!-- Pregunta y check Box sobre Carrera Universitaria -->
                            <div>
                                <!-- Pregunta -->
                                <label class="form-label mx-2">¿Carrera Univerisitaria?</label>
                                <br>
                                <!-- Checkbox "Sí" -->
                                <div class="form-check form-check-inline mx-4">
                                    <input name="CarreraUniversitaria" class="form-check-input" type="checkbox" id="siCheckboxUni" <% if (EgresadoEnonctrado.getCadenaUniversitaria()) { %> checked <% }%> value="<%=EgresadoEnonctrado.getCadenaUniversitaria()%>" onchange="mostrarOcultarInput(this.checked, 'miInputContainerUni', 'siCheckboxUni', 'noCheckboxUni'), validarCheckboxes()">
                                    <label class="form-check-label" for="siCheckboxUni">Sí</label>
                                </div>

                                <!-- Checkbox "No" -->
                                <div class="form-check form-check-inline">
                                    <input name="CarreraUniversitaria" class="form-check-input" type="checkbox" id="noCheckboxUni" <% if (!EgresadoEnonctrado.getCadenaUniversitaria()) { %> checked <% }%> value="0" onchange="mostrarOcultarInput(!this.checked, 'miInputContainerUni', 'siCheckboxUni', 'noCheckboxUni'), validarCheckboxes()">
                                    <label class="form-check-label" for="noCheckboxUni">No</label>
                                </div>

                                <!-- Contenedor del input -->
                                <div id="miInputContainerUni" style="display: none; margin-top: 10px;">
                                    <input class="form-control mb-3" type="text" maxlength="100" id="miInput" name="NombreUni" placeholder="Nombre Univerisad">
                                    <input class="form-control mb-3" type="text" maxlength="100" id="miInput" name="NombreCarrera" placeholder="Nombre Carrera">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-5">
                        <!-- Pregunta y check Box su estdo de trabajo -->
                        <div class="d-flex justify-content-center" >
                            <!-- Pregunta -->
                            <label class="form-label mx-2">¿Se encuntra trabajando actualmente?</label>
                            <br>
                            <!-- Checkbox "Sí" -->
                            <div class="form-check form-check-inline mx-4">
                                <input class="form-check-input" type="checkbox" name="trabajando" id="siCheckboxTraba" value="1" <% if (EgresadoEnonctrado.getTrabajando()) { %> checked <% } %> onchange="validarCheckboxes(); uncheckCheckbox('noCheckboxTraba'); uncheckCheckbox('noCheckboxTraba')">
                                <label class="form-check-label" for="siCheckboxTraba">Sí</label>
                            </div>

                            <!-- Checkbox "No" -->
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="noCheckboxTraba" name="trabajando" value="0" <% if (!EgresadoEnonctrado.getTrabajando()) { %> checked <% }%> onchange="validarCheckboxes();
                                        uncheckCheckbox('siCheckboxTraba');
                                        uncheckCheckbox('siCheckboxTraba\n\
')">
                                <label class="form-check-label" for="noCheckboxTraba">No</label>
                            </div>
                        </div>
                    </div>

                    <div class="d-grid mx-auto"> 
                        <button value="Modificar" name="BtnEgresados" class="btn btn-success mx-auto" >Modificar</button>

                    </div>


                </form>
            </div>

        </div>
    </body>
    <jsp:include page="../Components/footer2.jsp"></jsp:include>
    <%            String res = request.getParameter("respuesta");

        if (res != null) {
            switch (res) {
                case "SinCambios":
    %>
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header  text-white" style="background: #35C35D">
                <strong class="me-auto ">Upps!!</strong>
                <small>Ahora</small>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                No se encontraron Cambios!!
            </div>
        </div>
    </div>
    <%
            break;
        case "ModificacionExitosa":

    %>
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header  text-white" style="background: #35C35D">
                <strong class="me-auto ">Exito!!</strong>
                <small>Ahora</small>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                Modificacion Exitosa!!
            </div>
        </div>
    </div>
    <%                        break;

        case "ContrasenhaNoiguales":
    %>
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header  text-white" style="background: #35C35D">
                <strong class="me-auto ">upps!!</strong>
                <small>Ahora</small>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                Confirmacion no valida!!
            </div>
        </div>
    </div>

    <%
                    break;
                default:
                    break;
            }

        }

    %>


    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
            integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="../JS/LogicaCheakBox.js"></script>
    <script src="../JS/IniciarToast.js"></script>
</html>

<script src="../JS/validarCheakBox.js"></script>

<%}%>