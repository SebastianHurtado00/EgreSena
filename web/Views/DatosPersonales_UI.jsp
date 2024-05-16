<%-- 
    Document   : DatosPersonales_UI
    Created on : 2/05/2024, 08:58:28 AM
    Author     : ASUS
--%>

<%@page import="Entidades.Tipodocumento"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
<%@page import="Entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <%
        response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
        HttpSession sessionObtenida = request.getSession();
        Usuarios usuarioEntrante = new Usuarios();

        if (sessionObtenida == null || (sessionObtenida.getAttribute("Admin") == null && sessionObtenida.getAttribute("SuperAdmin") == null)) {
            // Si no se encuentra una sesión válida, redirige al índice
            response.sendRedirect("../CerradoSession.jsp");

        } else {

            if ((sessionObtenida.getAttribute("Admin") != null) || (sessionObtenida.getAttribute("SuperAdmin") != null)) {
                Usuarios userAdmin = (Usuarios) sessionObtenida.getAttribute("Admin");
                Usuarios userSuperAdmin = (Usuarios) sessionObtenida.getAttribute("SuperAdmin");

                if (userAdmin != null) {
                    usuarioEntrante = userAdmin;
                } else {
                    usuarioEntrante = userSuperAdmin;

                }
            }

            TipodocumentoJpaController controlTipoDocDate = new TipodocumentoJpaController();
            List<Tipodocumento> listaTipoDocumento = controlTipoDocDate.findTipodocumentoEntities();
    %>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="shortcut icon" href="../IMG/personas.webp" type="image/x-icon">
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <title>Datos Personales</title>
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
                            <h1 class="mt-5"><strong>Datos Personales</strong></h1>
                            <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                        </section>
                        <div class="row">
                            <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Verifique su Informacion</h5>
                                    </div>
                                    <div class="card-body px-4">
                                        <form action="<%=request.getContextPath()%>/LogicaDatosUsuario" method="post">
                                            <div class="card-body">
                                                <div class="row mb-2">
                                                    <div class="col-md-6">
                                                        <label for="Num_Cedula" class="form-label">N° Cedula</label>
                                                        <input class="form-control" type="number" id="Num_Cedula" max="999999999999" name="Num_Cedula" value="<%=usuarioEntrante.getCedula()%>" readonly="">
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="Tipo_Doc" class="form-label">Tipo Cedula</label>
                                                        <select  name="TipoDoc" class="form-select" id="Tipo_Doc" >
                                                            <option value="<%=usuarioEntrante.getTipoDocID().getId()%>" ><%=usuarioEntrante.getTipoDocID().getNombre()%></option>
                                                            <% for (Tipodocumento TipoDoc : listaTipoDocumento) {
                                                                    if (usuarioEntrante.getTipoDocID().getId() != TipoDoc.getId()) {
                                                            %>
                                                            <option value="<%=TipoDoc.getId()%>"><%=TipoDoc.getNombre()%></option>
                                                            <%
                                                                    }
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="row mt-2">
                                                    <div class="col-md-6">
                                                        <label for="Nombre" class="form-label">Nombre</label>
                                                        <input class="form-control" type="text" maxlength="45" id="Nombre" name="Nombre" value="<%=usuarioEntrante.getNombre()%>" required>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="Apellidos" class="form-label">Apellidos</label>
                                                        <input class="form-control" type="text" maxlength="45" id="Apellidos" name="Apellidos" value="<%=usuarioEntrante.getApellido()%>" required>
                                                    </div>
                                                </div>
                                                <div class="row mt-2">
                                                    <div class="col-md-6">
                                                        <label for="Correo" class="form-label">Correo</label>
                                                        <input class="form-control" type="email" maxlength="200" id="Correo" name="Correo" value="<%=usuarioEntrante.getCorreo()%>" required>
                                                    </div>
                                                    <div class="col-md-6 mb-4">
                                                        <label for="Telefono" class="form-label">Telefono</label>
                                                        <input class="form-control" type="tel" max="9999999999" id="Telefono" name="Telefono" value="<%=usuarioEntrante.getTelefono()%>" required>
                                                    </div>
                                                </div>
                                                <div class="d-grid gap-2 col-4 mx-auto mt-4 mb-4">
                                                    <button class="btn btn-success" name="BtnModificar" value="Modificar" style="background: #018E42" >Modificar</button>
                                                </div>
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
        <%            String res = request.getParameter("respuesta");

            if (res != null) {
                switch (res) {
                    case "SinCambios":
        %>
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header text-white" style="background: #35C35D">
                    <strong class="me-auto ">Upps!!</strong>
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
            case "Edicion":

        %>
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header  text-white" style="background: #3EC59D">
                    <strong class="me-auto ">Exito!!</strong>
                    <small>Ahora</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Modificacion Exitosa !!
                </div>
            </div>
        </div>
        <%                        break;
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
                    Coordinador registrado correctamente!!
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
    </body>
    <script src="../JS/app.js"></script>
    <script src="../JS/IniciarToast.js"></script>
    <%}%>
</html>