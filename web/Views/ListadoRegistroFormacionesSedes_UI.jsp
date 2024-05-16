<%-- 
    Document   : ListadoRegistroFormacionesSedes_UI
    Created on : 3/05/2024, 08:17:02 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Formacion"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Controladores.SedeJpaController"%>
<%@page import="Entidades.Sede"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
        HttpSession sessionObtenida = request.getSession();
        Usuarios usuarioEntrante = new Usuarios();
        if (sessionObtenida == null || (sessionObtenida.getAttribute("Admin") == null && sessionObtenida.getAttribute("SuperAdmin") == null)) {
            // Si no se encuentra una sesión válida, redirige al índice
            response.sendRedirect("../CerradoSession.jsp");
        } else {

            usuarioEntrante = (Usuarios) sessionObtenida.getAttribute("SuperAdmin");

    %>
    <head>
        <title>Sedes y Fomaciones</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="shortcut icon" href="../IMG/marcador-de-posicion.webp" type="image/x-icon">
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link href="../CSS/app.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/0702706600.js" crossorigin="anonymous"></script>
    </head>


    <body>
        <div class="wrapper">
            <%@include file="../Components/SideBar.jsp" %>
            <%@include file="../Components/modals.jsp" %>
            <div class="main">
                <%@include file="../Components/navbar.jsp" %>

                <main class="content">
                    <!--Cards y tablas -->
                    <section>
                        <div class="container-fluid p-0" style="font-family: initial">
                            <section class="section-0 d-flex justify-content-between">
                                <h1 class="mt-5"><strong>Registros Formaciones y Sedes</strong></h1>
                                <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                            </section>
                            <div class="row">
                                <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                    <div class="card flex-fill w-100">
                                        <div class="card-header">
                                            <h5 class="card-title mb-0">Sedes Registradas</h5>
                                        </div>
                                        <div class="card-body px-4">
                                            <div class="input-group mb-3 mt-2 p-2">
                                                <input type="text" class="form-control" placeholder="Busqueda de Sedes"
                                                       name="Dato_User" id="filtro1">
                                                <button class="btn  btn-sm p-2" type="button" data-bs-toggle="modal"
                                                        data-bs-target="#ModalRegistroSede" style="background: #018E42"><a style="color: white ; text-decoration: none">Regitrar Sede</a></button>
                                            </div>
                                            <div class="table-responsive" >
                                                <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 300px">
                                                    <table id="tabla1" class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">ID Sedes</th>
                                                                <th scope="col">Nombre Sede</th>
                                                                <th>                     </th>
                                                                <th scope="col">&nbsp;&nbsp;&nbsp;&nbsp;    Configuracion</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>


                                                            <%                                    SedeJpaController controlSedes = new SedeJpaController();
                                                                List<Sede> Listasede = controlSedes.findSedeEntities();

                                                                for (Sede sede : Listasede) {


                                                            %>
                                                            <tr>
                                                                <td class="align-middle"><%=sede.getId()%></td>
                                                                <td class="align-middle"><%=sede.getNombre()%></td>
                                                                <td></td>

                                                                <td class="align-middle"><i class="fa-solid fa-gear fa-lg mx-5"type="button" data-bs-toggle="modal"
                                                                                            data-bs-target="#ModalRegistroModificar" data-info = '{
                                                                                            "IdSede" : "<%=sede.getId()%>" ,
                                                                                            "NombreSede" : "<%=sede.getNombre()%>"
                                                                                            }'></i></td>

                                                            </tr>

                                                            <%                                    }
                                                            %>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="container-fluid p-0" style="font-family: initial">
                            <div class="row">
                                <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                    <div class="card flex-fill w-100">
                                        <div class="card-header">
                                            <h5 class="card-title mb-0">Formaciones Registradas</h5>
                                        </div>
                                        <div class="card-body px-4">

                                            <div class="input-group mb-3 mt-2 p-2">
                                                <input type="text" class="form-control" placeholder="Busqueda de Formaciones"
                                                       name="Dato_User" id="filtro2">
                                                <button class="btn  btn-sm p-2" style="background: #018E42" type="button" data-bs-toggle="modal"
                                                        data-bs-target="#ModalRegistroFormaciones"><a style="color: white ; text-decoration: none">Regitrar Formacion</a></button>
                                            </div>
                                            <div class="table-responsive" >
                                                <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 300px">
                                                    <table id="tabla2" class="table table-striped">
                                                        <thead>
                                                            <tr>

                                                                <th scope="col">ID Formacion</th>
                                                                <th scope="col">Nombre Fomracion</th>
                                                                <th scope="col">Sede perteneciente</th>
                                                                <th scope="col">&nbsp;&nbsp;&nbsp;&nbsp;    Configuracion</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <% FormacionJpaController controlFormacion = new FormacionJpaController();
                                                                List<Formacion> ListaFormacion = controlFormacion.findFormacionEntities();
                                                                for (Formacion formacion : ListaFormacion) {
                                                            %>
                                                            <tr>
                                                                <td class="align-middle"><%= formacion.getId()%></td>
                                                                <td class="align-middle"><%= formacion.getNombre()%></td>
                                                                <td class="align-middle"><%= formacion.getSedeID().getNombre()%></td>
                                                                <td class="align-middle">
                                                                    <i class="fa-solid fa-gear fa-lg mx-5" type="button" data-bs-toggle="modal" data-bs-target="#ModalModificarFormaciones" data-info='{
                                                                       "IdFormacion": "<%= formacion.getId()%>",
                                                                       "NombreFormacion": "<%= formacion.getNombre()%>",
                                                                       "SedePerteneciente": "<%= formacion.getSedeID().getId()%>"
                                                                       }'></i>
                                                                </td>
                                                            </tr>
                                                            <% }%>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>  
                    </section>
                    <!--Seccion de modales -->
                    <section>
                        <!-- Modal Regsitro de Sede -->
                        <div class="modal fade" id="ModalRegistroSede" tabindex="-1" aria-labelledby="ModalRegistroSede" aria-hidden="true" style="font-family: monospace">
                            <div class="modal-dialog modal-dialog-centered">
                                <form action="<%=request.getContextPath()%>/CrudFormacionesSedes" method="post">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="ModalRegistro">Registro Sedes</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body mx-auto">
                                            <div class="row">
                                                <div class="col-8 mx-auto">

                                                    <label for="IdRegistrarSede" class="form-label">&nbsp;ID de Sede *</label>
                                                    <input type="number" id="IdRegistrarSede" name="IdRegistrarSede" class="form-control mb-3" max="99999999999" required>
                                                </div>
                                                <div class="col-8 mx-auto">

                                                    <label for="NombreSede" class="form-label">&nbsp;Nombre de Sede *</label>
                                                    <input type="text" id="NombreSede" name="NombreSede" class="form-control mb-2" maxlength="60" required>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                            <button value="guardarSede" name="btnFormacionesSedes" class="btn btn-success">Guardar</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <!-- Modal Modificar Sede -->
                        <div class="modal fade" id="ModalRegistroModificar" tabindex="-1" aria-labelledby="ModalRegistroModificar" aria-hidden="true" style="font-family: monospace">
                            <div class="modal-dialog modal-dialog-centered">
                                <form action="<%=request.getContextPath()%>/CrudFormacionesSedes" method="post">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="ModalRegistro">Modificar Sede</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body mx-auto">
                                            <div class="row">
                                                <div class="col-8 mx-auto">

                                                    <label for="IdModificarSede" class="form-label">&nbsp;ID de Sede *</label>
                                                    <input type="number" id="IdModificarSede" name="IdModificarSede" class="form-control mb-3" max="99999999999" required>
                                                </div>
                                                <div class="col-8 mx-auto">

                                                    <label for="NombreSedeModificar" class="form-label">Nombre de Sede *</label>
                                                    <input type="text" id="NombreSedeModificar" name="NombreSedeModificar" class="form-control mb-2" maxlength="60" required>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                            <button value="ModificarSedes" name="btnFormacionesSedes" class="btn btn-warning">Modificar</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <!-- Modal Regsitro de Formaciones -->
                        <div class="modal fade" id="ModalRegistroFormaciones" tabindex="-1" aria-labelledby="ModalRegistroFormacion" aria-hidden="true" style="font-family: monospace">
                            <div class="modal-dialog modal-dialog-centered">
                                <form action="<%=request.getContextPath()%>/CrudFormacionesSedes" method="post">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="ModalRegistro">Registro Formacion</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body mx-auto">
                                            <div class="row">
                                                <div class="col-8 mx-auto">

                                                    <label for="Id" class="form-label">ID de Formacion *</label>
                                                    <input type="number" id="Id" name="IdRegistroFormacion" class="form-control mb-2" max="99999999999" required>
                                                </div>
                                                <div class="col-8 mx-auto">

                                                    <label for="NombreFormacionRegistrar" class="form-label">Nombre de Formacion *</label>
                                                    <input type="text" id="NombreFormacionRegistrar" name="NombreFormacionRegistrar" class="form-control mb-2" maxlength="45" required>
                                                </div>
                                                <div class="col-8 mx-auto">

                                                    <label for="SedePerteneciente" class="form-label">Sede perteneciente</label>
                                                    <select id="SedePerteneciente" name="SedePerteneciente" class="form-select" required>
                                                        <option disabled selected value="">Seleccione una opción *</option>
                                                        <% for (Sede sede : Listasede) {%>
                                                        <option value="<%= sede.getId()%>"><%= sede.getNombre()%></option>
                                                        <% }%>
                                                    </select>

                                                </div>

                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                            <button name="btnFormacionesSedes" value="RegistroFormaciones" class="btn btn-success">Guardar</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <!-- Modal Modificar de Formaciones -->
                        <div class="modal fade" id="ModalModificarFormaciones" tabindex="-1" aria-labelledby="ModalModificarFormaciones" aria-hidden="true" style="font-family: monospace">
                            <div class="modal-dialog modal-dialog-centered">
                                <form action="<%=request.getContextPath()%>/CrudFormacionesSedes" method="post">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="ModalRegistro">Modificar Formacion</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body mx-auto">
                                            <div class="row">
                                                <div class="col-8 mx-auto">

                                                    <label for="IdFormacionModificar" class="form-label">ID de Formacion *</label>
                                                    <input type="number" id="IdFormacionModificar" name="IdFormacionModificar" class="form-control mb-2" max="99999999999" required>
                                                </div>
                                                <div class="col-8 mx-auto">

                                                    <label for="NombreFormacionModificar" class="form-label">Nombre de Formacion *</label>
                                                    <input type="text" id="NombreFormacionModificar" name="NombreFormacionModificar" class="form-control mb-2" maxlength="45" required>
                                                </div>
                                                <div class="col-8 mx-auto">

                                                    <label for="SedePertenecienteModificar" class="form-label">Sede perteneciente +</label>
                                                    <select id="SedePertenecienteModificar"name="SedePertenecienteModificar" class="form-select" required>
                                                        <option  selected disabled value="">Seleccione una opción</option>
                                                        <% for (Sede sede : Listasede) {%>
                                                        <option value="<%=sede.getId()%>"><%=sede.getNombre()%></option>
                                                        <%
                                                            }
                                                        %>

                                                    </select>

                                                </div>

                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                            <button name="btnFormacionesSedes" value="FormacionesModificar" class="btn btn-warning">Modificar</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </section>                  
                </main>
                <%@include file="../Components/footerUI.jsp" %>

            </div>
        </div>

        <!--Alertas -->
        <section>
            <%
                String res = request.getParameter("respuesta");

                if (res != null) {
                    switch (res) {
                        case "RegsitroGuardado":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Registro guardado correctamente!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "Existente":

            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Registro ya existente
                    </div>
                </div>
            </div>
            <%                    break;
                case "SinCambios":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Upps!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        No se han encontrado cambios!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "ModificacionExitosa":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Registro Modificado corectamente!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "AdminGuaradado":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Administrador guardado correctamente!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "CCRegistrada":

            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Cedula ya registrada
                    </div>
                </div>
            </div>
            <%                    break;
                case "ExitoSuperAdmin":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #3EC59D">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Super Admin registrado correctamente!!
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
        </section>
    </body>
    <script src="../JS/LogicaFormacionesSede.js"></script>
    <script src="../JS/app.js"></script>
    <script src="../JS/IniciarToast.js"></script>
    <%}%>
</html>
