<%-- 
    Document   : navbar
    Created on : 2/05/2024, 07:53:19 AM
    Author     : ASUS
--%>

<%@page import="Entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    HttpSession sessionObtenidaNav = request.getSession();
    Usuarios usuarioEntranteNav = new Usuarios();
    String rol = "";

    if ((sessionObtenidaNav.getAttribute("Admin") != null) || (sessionObtenidaNav.getAttribute("SuperAdmin") != null)) {
        Usuarios userAdmin = (Usuarios) sessionObtenidaNav.getAttribute("Admin");
        Usuarios userSuperAdmin = (Usuarios) sessionObtenidaNav.getAttribute("SuperAdmin");

        if (userAdmin != null) {
            usuarioEntranteNav = userAdmin;
            rol = "Administrador";
        } else {
            usuarioEntranteNav = userSuperAdmin;
            rol = "Coordinador";
        }
    }
%>
<nav class="navbar navbar-expand navbar-light navbar-bg">
    <a class="sidebar-toggle js-sidebar-toggle">
        <i class="hamburger align-self-center"></i>
    </a>

    <div class="navbar-collapse collapse">
        <ul class="navbar-nav navbar-align">

            <li class="nav-item dropdown">
                <a class="nav-icon dropdown-toggle d-inline-block d-sm-none" href="#"
                   data-bs-toggle="dropdown">
                    <i class="align-middle" data-feather="settings"></i>
                </a>

                <a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#"
                   data-bs-toggle="dropdown">
                    <span class="text-dark"><%=usuarioEntranteNav.getNombre() + " " + usuarioEntranteNav.getApellido()%></span>
                </a>
                <div class="dropdown-menu dropdown-menu-end">
                    <a class="dropdown-item" href="../Views/DatosPersonales_UI.jsp"><i
                            class="align-middle me-1" data-feather="user"></i>Perfil</a>
                    <a class="dropdown-item" href="#"><i class="align-middle me-1"
                                                         data-feather="pie-chart"></i>Analiticas</a>

                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="../CerradoSession.jsp"><i class="align-middle me-1" data-feather="log-in"></i> Cerrar sesion</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
