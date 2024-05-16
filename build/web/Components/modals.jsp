<%-- 
    Document   : modals
    Created on : 24/04/2024, 04:04:01 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Usuarios"%>
<%@page import="Entidades.Tipodocumento"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
<%@page import="java.util.List"%>
<%

    HttpSession sessionObtenidaModals = request.getSession();

    Usuarios usuarioEntranteModals = new Usuarios();

    if (sessionObtenidaModals == null || (sessionObtenidaModals.getAttribute("Admin") == null && sessionObtenidaModals.getAttribute("SuperAdmin") == null)) {
        // Si no se encuentra una sesión válida, redirige al índice
        response.sendRedirect("../index.jsp");

    } else {
        if ((sessionObtenidaModals.getAttribute("Admin") != null) || (sessionObtenidaModals.getAttribute("SuperAdmin") != null)) {

            Usuarios userAdmin = (Usuarios) sessionObtenidaModals.getAttribute("Admin");
            Usuarios userSuperAdmin = (Usuarios) sessionObtenidaModals.getAttribute("SuperAdmin");

            if (userAdmin != null) {
                usuarioEntranteModals = userAdmin;
            } else {
                usuarioEntranteModals = userSuperAdmin;
            }

        } else {
            //Si no hay session administrativa se maata la sssion que entre
            HttpSession sessionCerrar = request.getSession(false); // Obtén la sesión sin crear una nueva si no existe.
            if (sessionCerrar != null) {
                sessionCerrar.invalidate(); // Invalida la sesión actual
            }
            response.sendRedirect("index.jsp"); // Redirige al índice

        }

      
      
        TipodocumentoJpaController controlTipoDocs = new TipodocumentoJpaController();
   
        List<Tipodocumento> ListaTiposDocs2 = controlTipoDocs.findTipodocumentoEntities();
%>

<!--Modales-->
<!-- Registro Administradores -->
<div class="modal fade" id="RegistroAdministradores" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" style="font-family: initial">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/CrudAdministradores" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Registro de administradores</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 col-sm-12">

                            <label for="CCadmin" class="form-label">Numero de Cedula *</label>
                            <input  class="form-control mb-2" id="CCadmin" type="number" name="CedulaAdmin" max="999999999999" required>

                            <label for="NombreAdmin" class="form-label">Nombre *</label>
                            <input  class="form-control mb-2" id="NombreAdmin" type="text" name="NombreAdmin" maxlength="45" required>

                            <label for="emailAdmin" class="form-label">email *</label>
                            <input  class="form-control mb-2" id="emailAdmin" type="email" name="EmailAdmin" maxlength="200" required>
                        </div>
                        <div class="col-md-6 col-sm-12">                          
                            <label class="form-label" for="TipoDocAdmin">Tipo de Documento *</label>
                            <select id="TipoDocAdmin" name="TipodocumentAdmin" class="form-select mb-2" required>
                                <option value="" selected disabled>Seleccione una opción</option>
                                <%

                                    for (Tipodocumento tipoDoc : ListaTiposDocs2) {
                                        out.print("<option value ='" + tipoDoc.getId() + "'> ");
                                        out.print(tipoDoc.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>                           
                            <label for="ApellidoAdmin" class="form-label">Apellido *</label>
                            <input  class="form-control mb-2" id="ApellidoAdmin" type="text" name="ApellidoAdmin" maxlength="45" required>

                            <label for="TelefonoAdmin" class="form-label">Telefono *</label>
                            <input  class="form-control mb-2" id="TelefonoAdmin" type="number" name="TelefonoAdmin" max="999999999999" required>
                        </div>


                    </div>
                </div>
                <div class="modal-footer">
                    <button type="" name="BtnAdmin" value="BtnGuardarAdmin" class="btn btn-success">Registrar</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Registro SuperAdministradores -->
<div class="modal fade" id="RegistroSuperAdministradores" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" style="font-family: initial">
    <div class="modal-dialog">
        <form action="<%=request.getContextPath()%>/RegistroTemporalSuperAdmin" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Registro de Coordinador</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 col-sm-12">

                            <label for="CCsuper" class="form-label">Cedula *</label>
                            <input  class="form-control mb-2" id="CCsuper" type="number" name="CedulaSuper" max="999999999999" required>


                            <label for="NombreSuper" class="form-label">Nombre *</label>
                            <input  class="form-control mb-2" id="NombreSuper" type="text" name="NombreSuper" maxlength="45" required>


                            <label for="emailSuper" class="form-label">Email *</label>
                            <input  class="form-control mb-2" id="emailSuper" type="text" name="emailSuper" maxlength="200" required>

                        </div>
                        <div class="col-md-6 col-sm-12">

                            <label class="form-label" for="TipoDocSuper">Tipo Documento *</label>
                            <select id="TipoDocSuper" name="TipoDocSuper" class="form-select mb-2" required="">
                                <option value="" selected disabled>Seleccione una opción</option>
                                <%
                                    for (Tipodocumento tipoDoc : ListaTiposDocs2) {
                                        out.print("<option value ='" + tipoDoc.getId() + "'> ");
                                        out.print(tipoDoc.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>


                            <label for="ApellidoAdmin" class="form-label">Apellido *</label>
                            <input  class="form-control mb-2" id="ApellidoSuper" type="text" name="ApellidoSuper" maxlength="45" required>

                            <label for="TelefonoSuper" class="form-label">Telefono *</label>
                            <input  class="form-control mb-2" id="TelefonoSuper" type="number" name="TelefonoSuper" max="999999999999" required>
                        </div>


                    </div>
                </div>
                <div class="modal-footer">
                    <button value="GuardarSuper" name="BtnSuperAdmin" class="btn btn-success">Registrar</button>
                </div>
            </div>
        </form>
    </div>
</div>

<%}%>