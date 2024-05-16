<%-- 
    Document   : ListadoRegistroCiudades_UI
    Created on : 3/05/2024, 07:23:49 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Ciudad"%>
<%@page import="Controladores.CiudadJpaController"%>
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
        <title>Listado de Ciudades</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="shortcut icon" href="../IMG/edificios.webp" type="image/x-icon">
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="https://kit.fontawesome.com/0702706600.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <link rel="stylesheet" href="../CSS/TableScroll.css"/>
        <link rel="shortcut icon" href="../IMG/edificios.webp" type="image/x-icon">
        <title>Listado de Ciudades</title>
        <link href="../CSS/app.css" rel="stylesheet">

    </head>


    <body>
        <div class="wrapper">
            <%@include file="../Components/SideBar.jsp" %>
            <%@include file="../Components/modals.jsp" %>
            <div class="main">
                <%@include file="../Components/navbar.jsp" %>
                <main class="content">
                    <div class="container-fluid p-0" style="font-family: initial">
                        <section class="section-0 d-flex justify-content-between">
                            <h1 class="mt-5"><strong>Registros de Ciudades</strong></h1>
                            <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                        </section>
                        <div class="row">
                            <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Ciudades Registradas</h5>
                                    </div>
                                    <div class="card-body px-4">
                                        <div class="input-group mb-3 mt-2 p-2">
                                            <i id="start-btn" class="btn btn-success py-1" onclick="voz()" style="background: #018E42">
                                                <span class="material-symbols-outlined py-1">
                                                    mic
                                                </span>
                                            </i>
                                            <input type="text" class="form-control" placeholder="Busqueda de Ciudades"
                                                   name="Dato_User" id="filtro">
                                            <button class="btn" type="button" data-bs-toggle="modal"
                                                    data-bs-target="#ModalRegistro" style="background: #018E42"><a style="color: white ; text-decoration: none">Regitrar Ciudad</a></button>
                                        </div>
                                        <div class="table-responsive" >
                                            <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 400px" >
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>

                                                            <th scope="col">ID  Ciudad</th>
                                                            <th scope="col">Nombre Ciudad</th>
                                                            <th scope="col">&nbsp;&nbsp;&nbsp;&nbsp;Configuracion</th>

                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%                                    CiudadJpaController controlCiudades = new CiudadJpaController();
                                                            List<Ciudad> listaCiudades = controlCiudades.findCiudadEntities();

                                                            for (Ciudad ciudades : listaCiudades) {

                                                        %>

                                                        <tr>

                                                            <td class="align-middle"><%=ciudades.getId()%></td>
                                                            <td class="align-middle"><%=ciudades.getNombre()%></td>
                                                            <td class="align-middle"><i class="fa-solid fa-gear fa-lg mx-5"type="button" data-bs-toggle="modal"
                                                                                        data-bs-target="#ModalModificacion" data-info = '{
                                                                                        "IdCiudad" : "<%=ciudades.getId()%>" ,
                                                                                        "NombreCiudad" : "<%=ciudades.getNombre()%>"
                                                                                        }'></i></td>
                                                        </tr>
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
                </main>
                <!-- Modal Registro -->
                <div class="modal fade" id="ModalRegistro" tabindex="-1" aria-labelledby="ModalRegistro" aria-hidden="true" style="font-family: monospace">
                    <div class="modal-dialog modal-dialog-centered">
                        <form action="<%=request.getContextPath()%>/CrudCiudades">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="ModalRegistro">Registro Ciudades</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body mx-auto">
                                    <div class="row">
                                        <div class="col-8 mx-auto">                                          
                                            <label for="IdCiudad" class="form-label">ID de Ciudad *</label>
                                            <input type="number" id="IdCiudad" name="IdCiudad" class="form-control mb-4" max="99999999999" required>
                                        </div>
                                        <div class="col-8 mx-auto">
                                            <label for="NombreCiudad" class="form-label">Nombre de Ciudad *</label>
                                            <input type="text" id="NombreCiudad" name="NombreCiudad" class="form-control mb-2" maxlength="45" required>
                                        </div>

                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <button value="GuardarCiudad" name="BtnCiudades" class="btn btn-success">Guardar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Modal Modificacion de Ciudades -->
                <div class="modal fade" id="ModalModificacion" tabindex="-1" aria-labelledby="ModalModificacion" aria-hidden="true" style="font-family: monospace">
                    <div class="modal-dialog modal-dialog-centered">
                        <form action="<%=request.getContextPath()%>/CrudCiudades" method="post">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="ModalRegistro">Modificar Ciudades</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body mx-auto">
                                    <div class="row">
                                        <div class="col-8 mx-auto">                                          
                                            <label for="IdCiudadModificar" class="form-label">ID de Ciudad *</label>
                                            <input type="number" id="IdCiudadModificar" name="IdCiudadModificar" class="form-control mb-4" max="99999999999" readonly>
                                        </div>
                                        <div class="col-8 mx-auto">                                           
                                            <label for="NombreCiudadModificar" class="form-label">Nombre de Ciudad *</label>
                                            <input type="text" id="NombreCiudadModificar" name="NombreCiudadModificar" class="form-control mb-2" maxlength="45" required>
                                        </div>

                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <button value="ModificarCiudad" name="BtnCiudades" class="btn btn-warning">Modificar</button>
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
                        case "GuardadoCiudad":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Ciudad registrada Correctamente!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "IdRegistrado":

            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Esta Ciudad ya ha sido registrada!!
                    </div>
                </div>
            </div>
            <%                        break;

                case "registrosIguales":
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

                case "ModificacionExitosa":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exitoo!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Ciudad modificada con exito!!
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
    <script src="../JS/app.js"></script>
    <script src="../JS/ReconocimientoVoz.js"></script>
    <script src="../JS/FiltradoTablas.js"></script>
    <script src="../JS/IniciarToast.js"></script>
    <script>
// DOMContentLoaded Este evento se dispara cuando el documento HTML ha sido completamente cargado y analizado.
                                                document.addEventListener("DOMContentLoaded", function () {

                                                    // Selecciona todos los botones "Editar" que tienen ciertos atributos específicos.
                                                    var editarButtons = document.querySelectorAll('i[data-bs-target="#ModalModificacion"][data-info*="IdCiudad"]');

                                                    //Creamos un metodo que llenara nuestro ventana emergente con la informacion del votante
                                                    function llenarModal(data) {
                                                        // Llena los campos del modal con los datos del votante
                                                        document.getElementById("IdCiudadModificar").value = data.IdCiudad;
                                                        document.getElementById("NombreCiudadModificar").value = data.NombreCiudad;
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
