<%-- 
    Document   : RegistrosEgresados_UI
    Created on : 3/05/2024, 09:49:26 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Sexo"%>
<%@page import="Entidades.TipoPoblacion"%>
<%@page import="Entidades.Sede"%>
<%@page import="Entidades.Formacion"%>
<%@page import="Entidades.Ciudad"%>
<%@page import="Entidades.NivelFormacion"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Controladores.TipoPoblacionJpaController"%>
<%@page import="Controladores.NivelFormacionJpaController"%>
<%@page import="Controladores.CiudadJpaController"%>
<%@page import="Controladores.SedeJpaController"%>
<%@page import="Controladores.SexoJpaController"%>
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
        <link rel="shortcut icon" href="../IMG/birrete.webp" type="image/x-icon">
        <link href="../CSS/app.css" rel="stylesheet">
        <title>Registro Egresados</title>

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
                            <h1 class="mt-5"><strong>Registro de Egresados</strong></h1>
                             <img src="../IMG/LogoSena.png" width="150px" height="150px" class="align-self-end img-fluid "/> 
                        </section>
                        <div class="row">
                            <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Registro</h5>
                                    </div>
                                    <div class="card-body px-4">
                                        <div class="col-md-12 d-md-flex justify-content-end justify-content-sm-end">
                                            <form action="<%=request.getContextPath()%>/ManejoDeExcels" method="post" enctype="multipart/form-data">
                                                <div class="d-grid gap-2 ">
                                                    <div class="input-group mb-2">
                                                        <input type="file" class="form-control" name="csvFile" id="fileInput3" required="1" accept=".csv">
                                                        <button name="BtnExcel" value="action" class="btn btn-outline-success" style="font-family: monospace" onclick="document.getElementById('inputArchivoExcel').click();">Importar datos desde Excel <i class="fa-solid fa-file-excel"></i></button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                        <form action="<%=request.getContextPath()%>/RegistroEgresados" method="post">
                                            <!--Seccion de datos Personales -->
                                            <div class="page" id="page1">
                                                <div class="row">
                                                    <h5 class="card-title mb-4">Informacion Personal</h5>
                                                    <div class="col-md-6">
                                                        <img src="../IMG/id-facial.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="numeroDocumento" class="form-label">N° Documento</label>
                                                        <input type="number" name="numeroDocumento" id="numeroDocumento" class="form-control mb-3" required max="99999999999">

                                                        <img src="../IMG/nombre.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="nombres" class="form-label">Nombres</label>
                                                        <input type="text" id="nombres" name="nombres" class="form-control mb-3" required maxlength="45">

                                                        <img src="../IMG/etiqueta-de-nombre.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="apellidos" class="form-label">Apellidos</label>
                                                        <input type="text" name="apellidos" id="apellidos" class="form-control mb-3" required maxlength="45">
                                                    </div>

                                                    <div class="col-md-6">
                                                        <img src="../IMG/tarjeta-de-identificacion.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="tipoDocumento" class="form-label">Tipo de Documento</label>
                                                        <select name="tipoDocumento" id="tipoDocumento" class="form-select mb-3" required>
                                                            <option value="" selected disabled>Seleccione una opción</option>
                                                            <%

                                                                for (Tipodocumento tipoDoc : listaTipoDoc) {
                                                                    out.print("<option value ='" + tipoDoc.getId() + "'> ");
                                                                    out.print(tipoDoc.getNombre());
                                                                    out.print("</option>");
                                                                }
                                                            %>
                                                        </select>

                                                        <img src="../IMG/sexo.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="sexo" class="form-label">Sexo</label>
                                                        <select name="sexo" id="sexo" class="form-select mb-3" required>
                                                            <option value="" selected disabled>Seleccione una opción</option>
                                                            <%
                                                                for (Sexo sex : listaSexo) {
                                                            %>
                                                            <option value="<%=sex.getId()%>"><%=sex.getNombre()%></option>
                                                            <%
                                                                }
                                                            %>
                                                        </select>

                                                        <img src="../IMG/personas.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="tipoPoblacion" class="form-label">Tipo de Población</label>
                                                        <select name="tipoPoblacion" id="tipoPoblacion" class="form-select mb-3" required>
                                                            <option value="" selected disabled>Seleccione una opción</option>
                                                            <%
                                                                for (TipoPoblacion TipoPoblacion : listaTipoPoblacion) {
                                                            %>
                                                            <option value="<%=TipoPoblacion.getId()%>"><%=TipoPoblacion.getNombre()%></option>
                                                            <%
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <h5 class="card-title mb-4">Informacion Residencia y contactos</h5>
                                                    <div class="col-md-6">

                                                        <img src="../IMG/edificios.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="ciudad" class="form-label">Ciudad</label>
                                                        <select name="ciudad" id="ciudad" class="form-select mb-3" required>
                                                            <option value="" selected disabled>Seleccione una opción</option>
                                                            <%
                                                                for (Ciudad ciudad : listaCiudades) {
                                                            %>
                                                            <option value="<%=ciudad.getId()%>"><%=ciudad.getNombre()%></option>
                                                            <%
                                                                }
                                                            %>
                                                        </select>

                                                        <img src="../IMG/marcador-de-posicion.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="direccion" class="form-label">Dirección</label>
                                                        <input type="text"name="direccion" id="direccion" class="form-control mb-3" maxlength="100" placeholder="Opcional">
                                                    </div>

                                                    <div class="col-md-6">
                                                        <img src="../IMG/certificado.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="numCertificados" class="form-label">N° de Certificados</label>
                                                        <input type="number" name="numCertificados" id="numCertificados" class="form-control mb-3" max="99999999999"" placeholder="Opcional">

                                                        <img src="../IMG/gmail.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="correo" class="form-label">Correo</label>
                                                        <input type="email" name="correo" id="correo" class="form-control mb-3" maxlength="45">
                                                    </div>

                                                    <div class="row">
                                                        <div class = "col-md-3">
                                                        </div>
                                                        <div class = "col-md-6">
                                                            <img src="../IMG/atencion-al-cliente.webp" alt="alt" width="30px" height="30px"/>
                                                            <label for="numContacto" class="form-label mx-auto">Número de Contacto</label>
                                                            <input type="number" name="numContacto" id="numContacto" class="form-control mb-3">
                                                        </div>
                                                        <div class = "col-md-3">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row mb-5">
                                                    <h5 class="card-title mb-4">Informacion Academica y laboral</h5>
                                                    <div class="col-md-4">
                                                        <img src="../IMG/SedeFormacion.webp" alt="alt" width="30px" height="30px"/>
                                                        <label for="sede" class="form-label">Sede</label>
                                                        <select name="sede" id="sede" class="form-select mb-3">
                                                            <option value="" selected disabled>Seleccione una opción</option>
                                                            <%
                                                                for (Sede sede : ListaSedes) {
                                                            %>
                                                            <option value="<%=sede.getId()%>"><%=sede.getNombre()%></option>
                                                            <%
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
                                                                <input class="form-check-input"  name="CadenaFormacion" type="checkbox" id="siCheckbox" value="1" onchange="mostrarOcultarInput(this.checked, 'miInputContainer', 'siCheckbox', 'noCheckbox'), validarCheckboxes()">
                                                                <label class="form-check-label" for="siCheckbox">Sí</label>
                                                            </div>

                                                            <!-- Checkbox "No" -->
                                                            <div class="form-check form-check-inline">
                                                                <input class="form-check-input" name="CadenaFormacion" type="checkbox" id="noCheckbox" value="0" onchange="mostrarOcultarInput(!this.checked, 'miInputContainer', 'siCheckbox', 'noCheckbox'), validarCheckboxes()">
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
                                                            <option  value="" selected disabled>Seleccione una opción</option>
                                                            <%
                                                                for (Formacion formacion : listaFormacion) {
                                                            %>
                                                            <option value="<%=formacion.getId()%>"><%=formacion.getNombre()%></option>
                                                            <%
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
                                                                <input class="form-check-input" name="Experiencia" type="checkbox" id="siCheckboxExp" value="1" onchange="mostrarOcultarInput(this.checked, 'miInputContainerExp', 'siCheckboxExp', 'noCheckboxExp'), validarCheckboxes()">
                                                                <label class="form-check-label" for="siCheckbox">Sí</label>
                                                            </div>

                                                            <!-- Checkbox "No" -->
                                                            <div class="form-check form-check-inline">
                                                                <input class="form-check-input"  name="Experiencia" type="checkbox" id="noCheckboxExp" value="0" onchange="mostrarOcultarInput(!this.checked, 'miInputContainerExp', 'siCheckboxExp', 'noCheckboxExp'), validarCheckboxes()">
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
                                                        <select id="nivelFormacion" name="nivelFormacion" class="form-select mb-3">
                                                            <option value="" selected disabled>Seleccione una opción</option>
                                                            <%
                                                                for (NivelFormacion Niveñformacion : listaNivelFormacion) {
                                                            %>
                                                            <option value="<%=Niveñformacion.getId()%>"><%=Niveñformacion.getNombre()%></option>
                                                            <%
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
                                                                <input class="form-check-input" type="checkbox" name="CarreraUniversitaria" id="siCheckboxUni" value="1" onchange="mostrarOcultarInput(this.checked, 'miInputContainerUni', 'siCheckboxUni', 'noCheckboxUni'), validarCheckboxes()">
                                                                <label class="form-check-label" for="siCheckbox">Sí</label>
                                                            </div>

                                                            <!-- Checkbox "No" -->
                                                            <div class="form-check form-check-inline">
                                                                <input class="form-check-input" type="checkbox" id="noCheckboxUni" name="CarreraUniversitaria" value="0" onchange="mostrarOcultarInput(!this.checked, 'miInputContainerUni', 'siCheckboxUni', 'noCheckboxUni'), validarCheckboxes()">
                                                                <label class="form-check-label" for="noCheckbox">No</label>
                                                            </div>

                                                            <!-- Contenedor del input -->
                                                            <div id="miInputContainerUni" style="display: none; margin-top: 10px;">
                                                                <input class="form-control mb-3" type="text" maxlength="100" id="miInput"name="NombreUni" placeholder="Nombre Univerisad">
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
                                                            <input class="form-check-input" type="checkbox" name="trabajando" id="siCheckboxTraba" value="1" onchange="validarCheckboxes(); uncheckCheckbox('noCheckboxTraba');">
                                                            <label class="form-check-label" for="siCheckbox">Sí</label>
                                                        </div>

                                                        <!-- Checkbox "No" -->
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="checkbox" id="noCheckboxTraba" name="trabajando" value="0" onchange="validarCheckboxes(); uncheckCheckbox('siCheckboxTraba');">
                                                            <label class="form-check-label" for="noCheckbox">No</label>
                                                        </div>
                                                    </div>
                                                </div>
                                                        <button value="RegistrarEgresados" name="BtnEgresados" class="btn " style="background: #018E42;color: white" disabled >Guardar</button>
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
                        case "RegstroExitoso":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
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
                        La importacion del archivo CSV fue exitosa!!
                    </div>
                </div>
            </div>
            <%
                    break;
                case "fallo":
            %>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header  text-white" style="background: #018E42">
                        <strong class="me-auto ">Upss!!</strong>
                        <small>Error</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        Revise su archivo CSV y su logica de llenado no se pudo subir el archivo!!
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
    <script src="../JS/LogicaCheakBox.js"></script>
    <script>
                                                                function validarCheckboxes() {
                                                                    var siCheckboxExp = document.getElementById("siCheckboxExp");
                                                                    var noCheckboxExp = document.getElementById("noCheckboxExp");
                                                                    var siCheckboxUni = document.getElementById("siCheckboxUni");
                                                                    var noCheckboxUni = document.getElementById("noCheckboxUni");
                                                                    var siCheckboxCadena = document.getElementById("siCheckbox");
                                                                    var noCheckboxCadena = document.getElementById("noCheckbox");
                                                                    var siCheckboxTraba = document.getElementById("siCheckboxTraba");
                                                                    var noCheckboxTraba = document.getElementById("noCheckboxTraba");
                                                                    var submitButton = document.querySelector("[name='BtnEgresados']");

                                                                    // Verificar si al menos uno de los checkboxes de cada grupo está seleccionado
                                                                    var experienciaLaboralSeleccionada = siCheckboxExp.checked || noCheckboxExp.checked;
                                                                    var carreraUniversitariaSeleccionada = siCheckboxUni.checked || noCheckboxUni.checked;
                                                                    var cadenaFormacionSeleccionada = siCheckboxCadena.checked || noCheckboxCadena.checked;
                                                                    var trabajandoSeleccionado = siCheckboxTraba.checked || noCheckboxTraba.checked;

                                                                    // Habilitar el botón de enviar si al menos uno de los checkboxes de cada grupo está seleccionado
                                                                    submitButton.disabled = !(experienciaLaboralSeleccionada && carreraUniversitariaSeleccionada && cadenaFormacionSeleccionada && trabajandoSeleccionado);
                                                                }

    </script>
    <script>
        function uncheckCheckbox(checkboxId) {
            // Desmarca el otro checkbox
            document.getElementById(checkboxId).checked = false;
        }
    </script>
    <%}%>
</html>