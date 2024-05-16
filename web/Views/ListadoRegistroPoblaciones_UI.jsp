<%-- 
    Document   : ListadoRegistroPoblaciones_UI
    Created on : 3/05/2024, 08:59:03 PM
    Author     : ASUS
--%>

<%@page import="Entidades.TipoPoblacion"%>
<%@page import="Controladores.TipoPoblacionJpaController"%>
<%@page import="Entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%

        response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
        HttpSession sessionObtenida = request.getSession();
        if (sessionObtenida == null || (sessionObtenida.getAttribute("Admin") == null && sessionObtenida.getAttribute("SuperAdmin") == null)) {
            // Si no se encuentra una sesión válida, redirige al índice
            response.sendRedirect("../CerradoSession.jsp");

        } else {


    %>
    <head>
        <title>Listado Y registro de Poblaciones</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="shortcut icon" href="../IMG/personas.webp" type="image/x-icon">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="https://kit.fontawesome.com/0702706600.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <link rel="stylesheet" href="../CSS/TableScroll.css"/>
        <link href="../CSS/app.css" rel="stylesheet">

    </head>


    <body>
        <div class="wrapper">
            <%@include file="../Components/SideBar.jsp" %>
            <%@include file="../Components/modals.jsp" %>
            <div class="main">
                <%@include file="../Components/navbar.jsp" %>
                <!-- Card y tabla --><section>
                    <main class="content">
                        <div class="container-fluid p-0" style="font-family: initial">
                            <section class="section-0 d-flex justify-content-between">
                                <h1 class="mt-5"><strong>Registros de Poblaciones</strong></h1>
                                <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                            </section>
                            <div class="row">
                                <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                    <div class="card flex-fill w-100">
                                        <div class="card-header">
                                            <h5 class="card-title mb-0">Poblaciones Registradas</h5>
                                        </div>
                                        <div class="card-body px-4">
                                            <div class="input-group mb-3 mt-2 p-2">
                                                <i id="start-btn" class="btn btn-success py-1" style="background: #018E42" onclick="voz()"> 
                                                    <span class="material-symbols-outlined mt-1">
                                                        mic
                                                    </span>
                                                </i>
                                                <input type="text" class="form-control" placeholder="Busqueda de Poblaciones"
                                                       name="Dato_User" id="filtro">
                                                <button class="btn btn-sm" type="button" data-bs-toggle="modal"
                                                        data-bs-target="#ModalRegistro" style="background: #018E42"><a style="color: white ; text-decoration: none">Regitrar Poblacion</a></button>
                                            </div>
                                            <div class="table-responsive" >
                                                <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 300px">
                                                    <table class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">ID Poblacion</th>
                                                                <th scope="col">Nombre Poblacion</th>
                                                                <th scope="col">&nbsp;&nbsp;&nbsp;Configuracion</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%                                    TipoPoblacionJpaController controlPoblaciones = new TipoPoblacionJpaController();
                                                                List<TipoPoblacion> ListaPoblaciones = controlPoblaciones.findTipoPoblacionEntities();
                                                                for (TipoPoblacion poblacion : ListaPoblaciones) {


                                                            %>
                                                            <tr>
                                                                <td class="align-middle"><%=poblacion.getId()%></td>
                                                                <td class="align-middle" ><%=poblacion.getNombre()%></td>
                                                                <td class="align-middle"> <i class="fa-solid fa-gear fa-lg mx-5"type="button" data-bs-toggle="modal"
                                                                                             data-bs-target="#ModalModificacion" data-info = '{
                                                                                             "IdPoblacion" : "<%=poblacion.getId()%>" ,
                                                                                             "NombrePoblacion" : "<%=poblacion.getNombre()%>"
                                                                                             }'></i></td>
                                                            </tr>
                                                            <%                                        }
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
                    </main>
                </section>
                <!-- Modal Registro de Poblaciones -->
                <div class="modal fade" id="ModalRegistro" tabindex="-1" aria-labelledby="ModalRegistro" aria-hidden="true" style="font-family: monospace">
                    <div class="modal-dialog modal-dialog-centered">
                        <form action="<%=request.getContextPath()%>/CrudPoblaciones" method="post">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="ModalRegistro">Registro Poblaciones</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body mx-auto">
                                    <div class="row">
                                        <div class="col-8 mx-auto">

                                            <label for="Id" class="form-label">ID de poblacion *</label>
                                            <input type="number" id="Id" name="IdPoblacion" class="form-control mb-4" max="99999999999" required>
                                        </div>
                                        <div class="col-8 mx-auto">

                                            <label for="NombrePoblacion" class="form-label">Nombre de poblacion *</label>
                                            <input type="text" id="NombrePoblacion" name="NombrePoblacion" class="form-control mb-2" maxlength="45" required>
                                        </div>

                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <button value="Registrar" name="BotonPoblacion" class="btn btn-success">Guardar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Modal Modificacion de Poblaciones -->
                <div class="modal fade" id="ModalModificacion" tabindex="-1" aria-labelledby="ModalModificacion" aria-hidden="true" style="font-family: monospace">
                    <div class="modal-dialog modal-dialog-centered">
                        <form action="<%=request.getContextPath()%>/CrudPoblaciones" method="post">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="ModalRegistro">Modificacion de  Poblaciones</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body mx-auto">
                                    <div class="row">
                                        <div class="col-8 mx-auto">
                                            <label for="IdPoblacionModificar" class="form-label">ID de poblacion *</label>
                                            <input type="number" id="IdPoblacionModificar" name="IdPoblacionModificar" class="form-control mb-4" max="99999999999" readonly>
                                        </div>
                                        <div class="col-8 mx-auto">
                                            <label for="NombrePoblacionModificar" class="form-label">Nombre de poblacion *</label>
                                            <input type="text" id="NombrePoblacionModificar" name="NombrePoblacionModificar" class="form-control mb-2" maxlength="45" required>
                                        </div>

                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <button value="Modificar" name="BotonPoblacion" class="btn btn-warning">Modificar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <%@include file="../Components/footerUI.jsp" %>

            </div>
        </div>

        <section>
            <%

                String res = request.getParameter("respuesta");
                if (res != null) {
                    switch (res) {
                        case "RegistroExitoso":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Poblacion registrada Correctamente!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "PoblacionExistente":

            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Esta poblacion ya ha sido registrada!!
                    </div>
                </div>
            </div>
            <%                        break;

                case "SinCambios":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        No se han encontrado Cambios!!
                    </div>
                </div>
            </div>
            <%
                    break;

                case "Cambios":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exitoo!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Poblacion modificada con exito!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "AdminGuaradado":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
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
                    <div class="toast-header  text-white" style="background: #018E42">
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
                    <div class="toast-header  text-white" style="background: #018E42">
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="../JS/app.js"></script>
    <script src="../JS/ReconocimientoVoz.js"></script>
    <script src="../JS/FiltradoTablas.js"></script>
    <script src="../JS/IniciarToast.js"></script>
    <script>
                                                    // DOMContentLoaded Este evento se dispara cuando el documento HTML ha sido completamente cargado y analizado.
                                                    document.addEventListener("DOMContentLoaded", function () {

                                                        // Selecciona todos los botones "Editar" que tienen ciertos atributos específicos.
                                                        var editarButtons = document.querySelectorAll('i[data-bs-target="#ModalModificacion"][data-info*="IdPoblacion"]');

                                                        //Creamos un metodo que llenara nuestro ventana emergente con la informacion del votante
                                                        function llenarModal(data) {
                                                            // Llena los campos del modal con los datos del votante
                                                            document.getElementById("IdPoblacionModificar").value = data.IdPoblacion;
                                                            document.getElementById("NombrePoblacionModificar").value = data.NombrePoblacion;
                                                        }

                                                        // Itera sobre cada botón "Editar".
                                                        editarButtons.forEach(function (button) {
                                                            // Agrega un evento de clic a cada botón "Editar".
                                                            button.addEventListener("click", function () {
                                                                // Obtiene la información del votante desde el atributo "data-info" en formato JSON.
                                                                //RECORDAR JS sabe la data-info porque este esta realizando el evento en cada boton editar y obtendra la info del boton al que se le de click
                                                                // this.getAttribute("data-info") = De este boton al que se le dio click dame su data-info
                                                                var data = JSON.parse(this.getAttribute("data-info"));
                                                                //Con la data recibida del boton se llena el modal con su metodo
                                                                llenarModal(data);
                                                            });
                                                        });
                                                    });

    </script>
    <%}%>
</html>
