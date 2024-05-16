<%-- 
    Document   : SideBar
    Created on : 2/05/2024, 06:50:51 AM
    Author     : ASUS
--%>

<%@page import="Entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sessionObtenidaHome = request.getSession();
    String MenuHome = "";
    String men = "";
    Usuarios usuarioEntranteHome = new Usuarios();

    if (sessionObtenidaHome == null || (sessionObtenidaHome.getAttribute("Admin") == null && sessionObtenidaHome.getAttribute("SuperAdmin") == null)) {
        // Si no se encuentra una sesión válida, redirige al índice
        response.sendRedirect("../index.jsp");

    } else {
        if ((sessionObtenidaHome.getAttribute("Admin") != null) || (sessionObtenidaHome.getAttribute("SuperAdmin") != null)) {

            Usuarios userAdmin = (Usuarios) sessionObtenidaHome.getAttribute("Admin");
            Usuarios userSuperAdmin = (Usuarios) sessionObtenidaHome.getAttribute("SuperAdmin");

            if (userAdmin != null) {
                MenuHome = "HomeAdministradores.jsp";
                usuarioEntranteHome = userAdmin;
                men = "Admin";
            } else {
                MenuHome = "HomeSuperAdmin_UI.jsp";
                usuarioEntranteHome = userSuperAdmin;
                men = "Coordinador";
            }

        } else {
            //Si no hay session administrativa se maata la sssion que entre
            HttpSession sessionCerrar = request.getSession(false); // Obtén la sesión sin crear una nueva si no existe.
            if (sessionCerrar != null) {
                sessionCerrar.invalidate(); // Invalida la sesión actual
            }
            response.sendRedirect("index.jsp"); // Redirige al índice

        }

%>
<nav id="sidebar" class="sidebar js-sidebar">
    <div class="sidebar-content js-simplebar">
        <a class="sidebar-brand mt-3" href="<%=MenuHome%>" style="font-family: monospace">
            <span class="align-middle ">Gestion de Egresados</span>
        </a>

        <ul class="sidebar-nav">
            <li class="sidebar-header">
                Menu   
            </li>
            <li class="sidebar-item active mb-2">
                <a class="sidebar-link" href="../Views/HomeSuperAdmin_UI.jsp">
                    <i class="align-middle" data-feather="sliders"></i> <span
                        class="align-middle">Dashboard</span>
                </a>
            </li>

            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" href="../Views/DatosPersonales_UI.jsp">
                    <i class="align-middle" data-feather="user"></i> <span
                        class="align-middle">Perfil</span>
                </a>
            </li>

            <%    Usuarios userAdminValidarMenu = (Usuarios) sessionObtenidaHome.getAttribute("Admin");
                if (userAdminValidarMenu == null) {
            %>
            <li class="sidebar-header  mb-2">
                Funciones Especiales
            </li>
            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" data-bs-toggle="modal" data-bs-target="#RegistroAdministradores">
                    <i class="align-middle" data-feather="book"></i> 
                    <span  class="align-middle">Registro Administradores</span>
                </a>
            </li>
            <li class="sidebar-item mb-2">
                <a class="sidebar-link" data-bs-toggle="modal" data-bs-target="#RegistroSuperAdministradores">
                    <i class="align-middle" data-feather="book"></i> 
                    <span
                        class="align-middle">Registro Coordinador</span>
                </a>
            </li>
            <li class="sidebar-item mb-2">
                <a class="sidebar-link" href="../Views/ListadoAdministradores_UI.jsp">
                    <i class="align-middle" data-feather="list"></i> <span
                        class="align-middle">Listado administradores</span>
                </a>
            </li>
            </li>
            <%
                }

            %>
            <li class="sidebar-header mb-2">
                Gestion de informacion
            </li>

            <li class="sidebar-item mb-2">
                <a class="sidebar-link" href="../Views/ListadoRegistroCiudades_UI.jsp">
                    <i class="align-middle" data-feather="home"></i> <span
                        class="align-middle">Listado Ciudades</span>
                </a>
            </li>

            <li class="sidebar-item mb-2">
                <a class="sidebar-link" href="../Views/ListadoRegistroFormacionesSedes_UI.jsp">
                    <i class="align-middle" data-feather="map"></i> <span
                        class="align-middle">Formaciones y Sedes</span>
                </a>
            </li>

            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" href="../Views/ListadoRegistroPoblaciones_UI.jsp">
                    <i class="align-middle" data-feather="users"></i> <span
                        class="align-middle">Listado Poblaciones</span>
                </a>
            </li>

            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" href="../Views/RegistrosEgresados_UI.jsp">
                    <i class="align-middle" data-feather="user-check"></i> <span
                        class="align-middle">Registro Egresados</span>
                </a>
            </li>

            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" href="../Views/FiltradoEgresados_UI.jsp">
                    <i class="align-middle" data-feather="filter"></i> <span
                        class="align-middle">Filtrado de Egresados</span>
                </a>
            </li>

            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" href="../Views/RestablecimientoContrasehaEgresados_UI.jsp">
                    <i class="align-middle" data-feather="more-horizontal"></i> <span
                        class="align-middle">Recuperar contraseñas</span>
                </a>
            </li>


            <li class="sidebar-item  mb-2">
                <a class="sidebar-link" href="../CerradoSession.jsp">
                    <i class="align-middle" data-feather="log-in"></i> <span
                        class="align-middle">Cerrar Sesion</span>
                </a>
            </li>
        </ul>
    </div>
</nav>
<%}%>