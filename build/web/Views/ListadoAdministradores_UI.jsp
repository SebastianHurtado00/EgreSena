<%-- 
    Document   : ListadoAdministradores_UI
    Created on : 3/05/2024, 05:52:15 PM
    Author     : ASUS
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Controladores.UsuariosJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
        HttpSession sessionObtenida = request.getSession();
        Usuarios usuarioEntrante = new Usuarios();
        if (sessionObtenida.getAttribute("SuperAdmin") == null) {
            response.sendRedirect("../index.jsp");
        } else {

            usuarioEntrante = (Usuarios) sessionObtenida.getAttribute("SuperAdmin");

    %>
    <head>
        <title>Listado de administradores</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="shortcut icon" href="../IMG/atencion-al-cliente.webp" type="image/x-icon">
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/0702706600.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <title>Listado de Administradores</title>
        <link rel="stylesheet" href="../CSS/TableScroll.css"/>
        <link href="../CSS/app.css" rel="stylesheet">

    </head>


    <body>
        <div class="wrapper">
            <%@include file="../Components/SideBar.jsp" %>
            <%@include file="../Components/modals.jsp" %>
            <div class="main">
                <%@include file="../Components/navbar.jsp" %>
                <%                    UsuariosJpaController controlUsuarios = new UsuariosJpaController();
                    List<Usuarios> ListaUsuarios = controlUsuarios.findUsuariosEntities();
                    List<Usuarios> ListaAdministradores = new ArrayList<>();
                    for (int i = 0; i < ListaUsuarios.size(); i++) {
                        if (ListaUsuarios.get(i).getRol() == 0) {
                            ListaAdministradores.add(ListaUsuarios.get(i));
                        }
                    }
                %>
                <main class="content">
                    <div class="container-fluid p-0" style="font-family: initial">
                        <section class="section-0 d-flex justify-content-between">
                            <h1 class="mt-5"><strong>Registros de Administradores</strong></h1>
                            <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                        </section>
                        <div class="row">
                            <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Administradores Registrados</h5>
                                    </div>
                                    <div class="card-body px-4">
                                        <div class="input-group mb-3 mt-2 p-2">
                                            <i id="start-btn"  class="btn btn-success py-1" onclick="voz()" style="background: #018E42">
                                                <span class="material-symbols-outlined mt-1">
                                                    mic
                                                </span>
                                            </i>
                                            <input type="text" class="form-control" placeholder="Busqueda de Registros"
                                                   name="Dato_User" id="filtro">
                                        </div>
                                        <div class="table-responsive" >
                                            <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 350px">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>

                                                            <th scope="col">ID Administrador</th>
                                                            <th>Tipo Documento</th>
                                                            <th scope="col">Nombre</th>
                                                            <th scope="col">Apellido</th>
                                                            <th scope="col">Correo</th>
                                                            <th>Telefono</th>
                                                            <th>&nbsp;&nbsp;&nbsp;&nbsp;Configuracion</th>

                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%                                    for (Usuarios admin : ListaAdministradores) {
                                                        %>
                                                        <tr>
                                                            <th><%=admin.getCedula()%></th>
                                                            <td><%=admin.getTipoDocID().getNombre()%></td>
                                                            <td><%=admin.getNombre()%></td>
                                                            <td><%=admin.getApellido()%></td>
                                                            <td><%=admin.getCorreo()%></td>
                                                            <td><%=admin.getTelefono()%></td>
                                                            <td><i class="fa-solid fa-gear fa-lg mx-5"type="button" data-bs-toggle="modal"
                                                                   data-bs-target="#ModalModificacion" data-info = '{
                                                                   "TipoDoc" : "<%=admin.getTipoDocID().getId()%>" ,
                                                                   "Cedula" : "<%=admin.getCedula()%>",
                                                                   "Nombre" : "<%=admin.getNombre()%>",
                                                                   "Apellido" : "<%=admin.getApellido()%>",
                                                                   "correo" : "<%=admin.getCorreo()%>" ,
                                                                   "telefono" : "<%=admin.getTelefono()%>" 
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
                </main>
                <%@include file="../Components/footerUI.jsp" %>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="ModalModificacion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="font-family: initial">
            <div class="modal-dialog modal-dialog-centered">
                <form action="<%=request.getContextPath()%>/CrudAdministradores" method="post">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Datos Administradores</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="Num_Cedula" class="form-label">&nbsp;&nbsp;N° Cedula</label>
                                    <input class="form-control" type="number" id="Num_Cedula" max="999999999999" name="Num_Cedula" readonly>
                                </div>
                                <div class="col-md-6">
                                    <label for="Tipo_Doc" class="form-label">Tipo Documento *</label>
                                    <select name="TipoDoc" class="form-select" id="Tipo_Doc">

                                        <%
                                            TipodocumentoJpaController controlTipoDoc = new TipodocumentoJpaController();
                                            List<Tipodocumento> listaTipoDocumento = controlTipoDoc.findTipodocumentoEntities();
                                            for (Tipodocumento TipoDoc : listaTipoDocumento) {

                                        %>
                                        <option value="<%=TipoDoc.getId()%>"><%=TipoDoc.getNombre()%></option>
                                        <%

                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="row mt-2">
                                <div class="col-md-6">
                                    <label for="Nombre" class="form-label">&nbsp;&nbsp;Nombre *</label>
                                    <input class="form-control" type="text" maxlength="45" id="Nombre" name="Nombre" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="Apellidos" class="form-label">Apellidos *</label>
                                    <input class="form-control" type="text" maxlength="45" id="Apellidos" name="Apellidos"  required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="Correo" class="form-label">&nbsp;&nbsp;Correo *</label>
                                    <input class="form-control" type="email" maxlength="200" id="Correo" name="Correo" required>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <label for="Telefono" class="form-label">Telefono *</label>
                                    <input class="form-control" type="tel" max="9999999999" id="Telefono" name="Telefono" required>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-danger" name="BtnAdmin" type="button" onclick="eliminar()"> Eliminar</button>
                            <button class="btn btn-warning" name="BtnAdmin" value="BtnModificarAdmin"> Modificar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <section>
            <%            String res = request.getParameter("respuesta");

                if (res != null) {
                    switch (res) {
                        case "Modifiacado":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Modificacion exitosa!!
                    </div>
                </div>
            </div>
            <%
                    break;
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
                        No se han encontrado cambios!!
                    </div>
                </div>
            </div>
            <%                        break;
                case "eliminado":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Eliminado exitoso!!
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
    <script src="../JS/FiltradoTablas.js"></script>
    <script src="../JS/IniciarToast.js"></script>
    <script src="../JS/ReconocimientoVoz.js"></script>
    <script src="../JS/LogicaListadoAdministradores.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <%}%>
</html>
