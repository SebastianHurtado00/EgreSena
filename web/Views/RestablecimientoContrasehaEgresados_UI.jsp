<%-- 
    Document   : RestablecimientoContrasehaEgresados_UI
    Created on : 3/05/2024, 10:07:58 PM
    Author     : ASUS
--%>

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

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="https://kit.fontawesome.com/0702706600.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <link rel="stylesheet" href="../CSS/TableScroll.css"/>
        <link rel="shortcut icon" href="../IMG/bloqueo-de-rotacion.webp" type="image/x-icon">
        <title>Restablecimiento de contraseñas</title>
        <link href="../CSS/app.css" rel="stylesheet">

    </head>

    <body>
        <div class="wrapper">
            <%@include file="../Components/SideBar.jsp" %>
            <%@include file="../Components/modals.jsp" %>
            <div class="main">
                <%@include file="../Components/navbar.jsp" %>
                <script>
                    function consultarUsuario() {
                        var cedulaScript = document.getElementById("EgresadoCedula").value;
                        $.ajax(
                                {
                                    type: "POST",
                                    url: "BusquedaEgresado.jsp",
                                    data: "cedula=" + cedulaScript,
                                    dataType: "html",
                                    success: function (data) {
                                        $("#DatosEgresados").empty();
                                        $("#DatosEgresados").append(data);
                                    }
                                });

                    }
                </script>
                <main class="content">
                    <div class="container-fluid p-0" style="font-family: initial">
                        <section class="section-0 d-flex justify-content-between">
                            <h1 class="mt-5"><strong>Recuperacion de contraseña</strong></h1>
                            <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                        </section>
                        <div class="row">
                            <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Formulario</h5>
                                    </div>
                                    <div class="card-body px-4">
                                        <form action="<%=request.getContextPath()%>/RestablecimientosContrase_as" method="post">
                                            <div class="input-group mb-3">
                                                <input required="" id="EgresadoCedula" class="form-control" type="number" name="CC" placeholder="Ingrese un numero de cedula">
                                                <button type="button" class="btn btn-outline-success" style="background: #018E42 ; color: white" onclick="consultarUsuario()">Buscar</button>
                                            </div>
                                            <div class="table-responsive mb-2" >
                                                <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 180px">
                                                    <table class="table table-striped-columns " style="height: 100px">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">#</th>
                                                                <th scope="col">N° Cedula</th>
                                                                <th scope="col">Nombre</th>
                                                                <th scope="col">Apellido</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <!-- Campos del usuario encontrado -->
                                                            <tr id="DatosEgresados"></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="d-grid gap-2 mx-auto mb-5">
                                                <button name="BtnRestablecer" value="RestablercerAdmin" class="btn btn-outline-warning mx-auto" >Restablecer Contraseña</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>


                <%@include file="../Components/footerUI.jsp" %>

            </div>
        </div>

        <section>
            <%
                String res = request.getParameter("respuesta");

                if (res != null) {
                    switch (res) {
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
                case "Vacios":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Campos Vacios!!
                    </div>
                </div>
            </div>
            <%
                    break;

                case "UsuarioNoValido":

            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Usuario No valido!!
                    </div>
                </div>
            </div>
            <%                    break;
                case "Exito":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Se ha restablecido la contraseña exitosamente!!
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
    <%}%>
</html>