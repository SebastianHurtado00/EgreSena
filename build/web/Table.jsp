<%-- 
    Document   : Table
    Created on : 14/05/2024, 11:25:50 AM
    Author     : ASUS
--%>

<%@page import="Controladores.ColumnNameService"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String tablaConsultar = request.getParameter("Table");
    ColumnNameService control = new ColumnNameService();
    int[] columnIndices = {1, 2};
    String table = control.generateTable(tablaConsultar, columnIndices);
%>

<html>
    <header>
        <link href="CSS/app.css" rel="stylesheet">      
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    </header>
    <body>
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
                <div class="table-responsive">
                    <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 400px" >
                        <%=table%>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <section>
        <!-- Modal 1 -->
        <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="myModalLabel1">Modal 1</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Contenido del modal 1...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary">Guardar cambios</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal 2 -->
        <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="myModalLabel2">Modal 2</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Contenido del modal 2...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary">Guardar cambios</button>
                    </div>
                </div>
            </div>
        </div>

    </section>
    <!-- Bootstrap JavaScript Libraries -->
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
    <script src="JS/app.js"></script>
    <script src="JS/ReconocimientoVoz.js"></script>
    <script src="JS/FiltradoTablas.js"></script>
</html>

