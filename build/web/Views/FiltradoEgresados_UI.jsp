<%-- 
    Document   : FiltradoEgresados_UI
    Created on : 2/05/2024, 02:59:31 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Egresado"%>
<%@page import="Controladores.EgresadoJpaController"%>
<%@page import="Entidades.Sede"%>
<%@page import="Entidades.Ciudad"%>
<%@page import="Entidades.NivelFormacion"%>
<%@page import="Entidades.Formacion"%>
<%@page import="Entidades.TipoPoblacion"%>
<%@page import="Entidades.Tipodocumento"%>
<%@page import="Entidades.Sexo"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.CiudadJpaController"%>
<%@page import="Controladores.TipoPoblacionJpaController"%>
<%@page import="Controladores.NivelFormacionJpaController"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Controladores.SedeJpaController"%>
<%@page import="Controladores.SexoJpaController"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
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

            TipodocumentoJpaController controlTipoDoc = new TipodocumentoJpaController();
            SexoJpaController controlSexo = new SexoJpaController();
            SedeJpaController controlSede = new SedeJpaController();
            FormacionJpaController controlFormacion = new FormacionJpaController();
            CiudadJpaController controlCiudad = new CiudadJpaController();
            NivelFormacionJpaController controlNivelFomacion = new NivelFormacionJpaController();
            TipoPoblacionJpaController controlTipoPoblacion = new TipoPoblacionJpaController();

            List<Tipodocumento> listaTipoDoc = controlTipoDoc.findTipodocumentoEntities();
            List<Sexo> listaSexo = controlSexo.findSexoEntities();
            List<TipoPoblacion> listaTipoPoblacion = controlTipoPoblacion.findTipoPoblacionEntities();
            List<Sede> ListaSedes = controlSede.findSedeEntities();
            List<Formacion> listaFormacion = controlFormacion.findFormacionEntities();
            List<Ciudad> listaCiudades = controlCiudad.findCiudadEntities();
            List<NivelFormacion> listaNivelFormacion = controlNivelFomacion.findNivelFormacionEntities();

    %>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/0702706600.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="shortcut icon" href="../IMG/graduado.webp" type="image/x-icon">
        <link href="../CSS/app.css" rel="stylesheet">
        <link rel="stylesheet" href="../CSS/ButtonSend.css"/>
        <link rel="stylesheet" href="../CSS/loader.css"/>
        <link rel="stylesheet" href="../CSS/pagination.css"/>
        <title>Filtrado de Egresados</title>
    </head>
    <body>

        <div class="wrapper">
            <%@include file="../Components/SideBar.jsp" %>
            <%@include file="../Components/modals.jsp" %>
            <div class="main">
                <%@include file="../Components/navbar.jsp" %>

                <!--Card contenedora de la tabla y su logica -->
                <main class="content">
                    <div class="container-fluid p-0" style="font-family: initial">
                        <section class="section-0 d-flex justify-content-between">
                            <h1 class="mt-5"><strong>Comunicacion con Egresados</strong></h1>
                            <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                        </section>
                        <div class="row">
                            <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Filtre la Informacion</h5>
                                    </div>
                                    <div class="card-title">
                                        <div class="input-group mb-3 mt-2 p-2" style="display: none">
                                            <i id="start-btn" class="fa-solid fa-microphone-lines btn btn-success py-3" onclick="voz()"></i>
                                            <input type="text" class="form-control" placeholder="Busqueda de Egresados"
                                                   name="Dato_User" id="filtro">
                                        </div>  
                                    </div>
                                    <div class="card-body ">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <img src="../IMG/sexo.webp" alt="alt" width="25px" height="25px"/> 
                                                <label for="sexo" class="form-label">Sexo</label>
                                                <select id="sexo" class="form-select mb-3" onchange="filtrarTabla()" >
                                                    <option value="" selected  ">Seleccione una opción</option>
                                                    <%                                for (Sexo sex : listaSexo) {
                                                    %>
                                                    <option value="<%=sex.getId()%>"><%=sex.getNombre()%></option>
                                                    <%                                }
                                                    %>
                                                </select>
                                                <img src="../IMG/marcador-de-posicion.webp" alt="alt" width="25px" height="25px"/> 
                                                <label for="sede" class="form-label">Sede</label>
                                                <select id="sede" class="form-select mb-3"onchange="filtrarTabla()" >
                                                    <option value="" selected >Seleccione una opción</option>
                                                    <%
                                                        for (Sede sede : ListaSedes) {
                                                    %>
                                                    <option value="<%=sede.getId()%>"><%=sede.getNombre()%></option>
                                                    <%                                }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <img src="../IMG/formacion.webp" alt="alt" width="25px" height="25px"/> 
                                                <label for="formacion" class="form-label">Formacion</label>
                                                <select id="formacion" class="form-select mb-3" onchange="filtrarTabla()"> 
                                                    <option value="" selected >Seleccione una opción</option>
                                                    <%
                                                        for (Formacion forma : listaFormacion) {
                                                    %>
                                                    <option value="<%=forma.getId()%>"><%=forma.getNombre()%></option>
                                                    <%                                }
                                                    %>
                                                </select>

                                                <img src="../IMG/experiencia.webp" alt="alt" width="25px" height="25px"/> 
                                                <label for="Experiencia" class="form-label">Experiencia</label>
                                                <select id="Experiencia" class="form-select mb-3"onchange="filtrarTabla()">
                                                    <option value="" selected >Seleccione una opción</option>
                                                    <option value="1">Si</option>
                                                    <option value="0">No</option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <img src="../IMG/labor.webp" alt="alt" width="25px" height="25px"/> 
                                                <label for="Labrando" class="form-label">Laborando</label>
                                                <select id="Laborando" class="form-select mb-3"onchange="filtrarTabla()">
                                                    <option value="3" selected >Seleccione una opción</option>
                                                    <option value="1">Si</option>
                                                    <option value="0">No</option>
                                                </select>

                                                <img src="../IMG/edificios.webp" alt="alt" width="25px" height="25px"/> 
                                                <label for="Ciudad" class="form-label">Ciudad</label>
                                                <select id="Ciudad" class="form-select mb-3" onchange="filtrarTabla()">
                                                    <option value="" selected >Seleccione una opción</option>
                                                    <%
                                                        for (Ciudad ciudad : listaCiudades) {
                                                    %>
                                                    <option value="<%=ciudad.getId()%>"><%=ciudad.getNombre()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <!--Input necesario para la paginacion ya que da el maximo de registros que se mostrara -->
                                       
                                        <div class="table-responsive" style="height: 500px" >
                                            <div class="table-wrapper-scroll-y my-custom-scrollbar p-2">
                                                <table id="miTabla" class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">N° Cedula</th>
                                                            <th scope="col">Tipo Documento</th>
                                                            <th scope="col">Nombre</th>
                                                            <th scope="col">Apellido</th>
                                                            <th scope="col">Sexo</th>                             
                                                            <th scope="col">Tipo Poblacion</th>
                                                            <th scope="col">Sede</th>
                                                            <th scope="col">Formacion</th>
                                                            <th scope="col">Ciudad</th>
                                                            <th scope="col" >Correo</th>
                                                            <th scope="col">Contacto</th>
                                                            <th>Experiencia</th>
                                                            <th>¿Trabajando?</th>
                                                            <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Funciones</th>

                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            EgresadoJpaController controlEgresados = new EgresadoJpaController();
                                                            List<Egresado> listaEgresados = controlEgresados.findEgresadoEntities();
                                                            for (Egresado egresados : listaEgresados) {

                                                        %>

                                                        <tr>
                                                            <td><%=egresados.getNumeroCedula()%></td>
                                                            <td><%=egresados.getTipoDocumentoID().getNombre()%></td>
                                                            <td><%=egresados.getNombre()%></td>
                                                            <td><%=egresados.getApellido()%></td>
                                                            <td><%=egresados.getSexoID().getNombre()%></td>
                                                            <td><%=egresados.getTipoPoblacionID().getNombre()%></td>
                                                            <td><%=egresados.getSedeID().getNombre()%></td>
                                                            <td><%=egresados.getFormacionID().getNombre()%></td>
                                                            <td><%=egresados.getCiudadID().getNombre()%></td>
                                                            <td><%=egresados.getCorreo()%></td>
                                                            <td><%=egresados.getNumeroTelefono()%></td>
                                                            <td>
                                                                <% if (egresados.getExperiencia()) { %>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sí
                                                                <% } else { %>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No
                                                                <% }%>
                                                            </td>
                                                            <td>
                                                                <% if (egresados.getTrabajando()) { %>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sí
                                                                <% } else { %>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No
                                                                <% }%>
                                                            </td>
                                                            <td><i class="fa-solid fa-gear fa-lg mx-5" type="button" data-bs-toggle="modal"
                                                                   data-bs-target="#ModalModificacion" data-info = '{
                                                                   "Cedula" : "<%=egresados.getNumeroCedula()%>",
                                                                   "nombre" : "<%=egresados.getNombre()%>",
                                                                   "apellido" : "<%=egresados.getApellido()%>",
                                                                   "tipoDocumento" : "<%=egresados.getTipoDocumentoID().getId()%>",
                                                                   "sexo" : "<%=egresados.getSexoID().getId()%>",
                                                                   "correo" : "<%=egresados.getCorreo()%>",
                                                                   "telefono" : "<%=egresados.getNumeroTelefono()%>"
                                                                   }'></i></td>
                                                        </tr>

                                                        <%                                    }
                                                        %>

                                                    </tbody>
                                                </table>
                                                <div class='pagination-container'>
                                                    <nav>
                                                        <ul class="pagination">
                                                            <!--Here the JS Function Will Add the Rows -->
                                                        </ul>
                                                    </nav>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-grid gap-2 d-md-flex justify-content-md-end justify-content-sm-end justify-content-end mt-4 text-center" style="margin-right: 2%">
                                        <button id="obtenerCorreosButton" class="correos mb-3" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                            <div class="svg-wrapper-1">
                                                <div class="svg-wrapper">
                                                    <svg
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        viewBox="0 0 24 24"
                                                        width="20"
                                                        height="20"
                                                        >
                                                    <path fill="none" d="M0 0h24v24H0z"></path>
                                                    <path
                                                        fill="currentColor"
                                                        d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"
                                                        ></path>
                                                    </svg>
                                                </div>
                                            </div>
                                            <span>Correo</span>
                                        </button>
                                    </div>

                                    <div class="mx-auto mt-3" style="display: none">
                                        <button class="btn btn-outline-danger mb-2">Descargar Reporte PDF Filtrado <i class="fa-regular fa-file-pdf"></i></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <%@include file="../Components/footerUI.jsp" %>

            </div>
        </div>

        <!-- Modal Modificacion -->
        <div class="modal fade" id="ModalModificacion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="font-family: initial">
            <div class="modal-dialog modal-dialog-centered">
                <form action="<%=request.getContextPath()%>/ModificacionEgresado" method="">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Modificacion Datos Basicos</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6">                                
                                    <label for="numeroDocumento" class="form-label">N° Documento </label>
                                    <input type="text" name="numeroDocumentoModal" id="numeroDocumentoModal" class="form-control mb-3" readonly max="99999999999">

                                    <label for="nombres" class="form-label">Nombres *</label>
                                    <input type="text" name="nombresModal" id="nombresModal" class="form-control mb-3" maxlength="45">

                                    <label for="apellidos" class="form-label">Apellidos *</label>
                                    <input type="text"  id="apellidos"name="apellidos" class="form-control mb-3" maxlength="45">

                                </div>

                                <div class="col-md-6">                                 
                                    <label for="tipoDocumento" class="form-label">Tipo de Documento *</label>
                                    <select name="tipoDocumentoModal" id="tipoDocumentoModal" class="form-select mb-3" required>
                                        <% for (Tipodocumento TipoDoc : listaTipoDoc) {

                                        %>
                                        <option value="<%=TipoDoc.getId()%>"><%=TipoDoc.getNombre()%></option>
                                        <%

                                            }
                                        %>
                                    </select>

                                    <label for="sexo" class="form-label">Sexo *</label>
                                    <select  name="sexoModal" id="sexoModal" class="form-select mb-3" required>
                                        <%
                                            for (Sexo sex : listaSexo) {

                                        %>
                                        <option value="<%=sex.getId()%>"><%=sex.getNombre()%></option>
                                        <%

                                            }
                                        %>
                                    </select>

                                    <label for="telefonoModal" class="form-label">Telefono *</label>
                                    <input name="telefonoModal" type="number" id="telefonoModal" class="form-control mb-3" >
                                </div>

                                <div class="mx-auto">                                  
                                    <label for="correoModal" class="form-label">Correo *</label>
                                    <input name="correoModal" type="email" id="correoModal" class="form-control mb-3"  maxlength="45">
                                </div>

                            </div>

                        </div>
                        <div class="modal-footer">
                            <button  name="BtnEgresados" value="eliminar" type="button" class="btn btn-danger" onclick="eliminar()">Eliminar</button>
                            <button name="BtnEgresados" value="ModificarAdmin" class="btn btn-warning">Modificar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <!-- Modal envio Correos -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="font-family: initial">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <!--ACTION DE CORREOS SendEmails -->
                <form id="email-form" action="<%=request.getContextPath()%>/SendEmails">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Envios de Correos  &nbsp;&nbsp; <i class="fa-solid fa-envelope fa-xl" style="color: #63E6BE;"></i></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-12">
                                    <input type="hidden" id="listaCorreos" name="ListaCorreos">
                                    <label>Asunto del Correo</label>
                                    <input id="asunto" type="text" name="asunto" class="form-control mb-2" required>
                                </div>

                                <div class="col-12">
                                    <label for="mensajeCorreo" class="form-label">Escriba un mensaje para los Egresados que fueron filtrados</label>
                                    <textarea id="cuerpo" name="cuerpo" rows="5" cols="10" class="form-control" required></textarea>
                                </div>
                            </div>
                            <div class="modal-footer mt-3 mb-2">
                                <button  name="sendEmails" value="action" class="btn fa-regular fa-xl " style="color: #B197FC; margin-right: 2%"><i class="fa-solid fa-paper-plane fa-bounce fa-2xl" style="color: #63E6BE;"></i></button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div id="loading-spinner" class="d-none text-center loading-overlay">
            <div class="loader3">
                <div class="circle1"></div>
                <div class="circle1"></div>
                <div class="circle1"></div>
                <div class="circle1"></div>
                <div class="circle1"></div>
            </div>
        </div>

        <section>

            <%            String res = request.getParameter("respuesta");

                if (res != null) {
                    switch (res) {
                        case "SinCambios":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
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
                    <div class="toast-header  text-white" style="background: #018E42">
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
                case "EliminacionExitosa":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Eliminacion Exitosa!!
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

                case "envioExitoso":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Exito!!</strong>
                        <small>Ahora</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Correos enviados exitosamente!!
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
    <script src="https://cdn.jsdelivr.net/npm/nodemailer@6.9.13/lib/nodemailer.min.js"></script>
    <!-- Incluye el script de Nodemailer desde un CDN -->
    <script src="https://cdn.jsdelivr.net/npm/nodemailer/dist/nodemailer.min.js"></script>
    <script src="../JS/LoadingScreen.js"></script>
    <script src="../JS/TablaPaginada.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/cesiumjs/1.78/Build/Cesium/Cesium.js"></script>
    <script src="../JS/ReconocimientoVoz.js"></script>
    <script src="../JS/FiltradoTablas.js"></script>
    <script src="../JS/FiltrarTablaEgresados.js"></script>
    <script src="../JS/IniciarToast.js"></script>
    <script src="../JS/app.js"></script>
    <script src="../JS/LogicaFiltradoEgresados.js"></script>
    <script>

    </script>
</html>
<%}%>
