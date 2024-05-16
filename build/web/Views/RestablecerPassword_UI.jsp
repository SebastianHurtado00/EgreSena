<%-- 
    Document   : RestablecerPassword_UI
    Created on : 4/05/2024, 09:38:50 AM
    Author     : ASUS
--%>

<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
// Obtener la URL base
    String urlBase = request.getRequestURL().toString();

// Obtener la cadena de consulta (parámetros)
    String queryString = request.getQueryString();

// Combinar la URL base y la cadena de consulta para obtener la URL completa
    String urlCompleta = urlBase + (queryString != null ? "?" + queryString : "");

// Almacenar la URL completa en la sesión
    HttpSession session1 = request.getSession();
    session1.setAttribute("urlAnterior", urlCompleta);

// Recuperar el token de la URL
    String tokenFromURL = request.getParameter("token");

// Recuperar la cédula del usuario de la URL
    int cedula2 = 0; // Inicializamos la cédula a 0 por defecto
    try {
        cedula2 = Integer.parseInt(request.getParameter("cedula"));
    } catch (NumberFormatException e) {
        // Manejo de error si la cédula no se puede convertir a entero
        e.printStackTrace();
    }

// Almacenar el token en la sesión
    session1.setAttribute("sesionToken", tokenFromURL);

// Verificar si el token es nulo o si ha expirado
    if (tokenFromURL == null) {
        // Si el token es nulo, redirigir al usuario a la página de inicio
        response.sendRedirect("../index.jsp");
    } else {
        // Obtener la fecha de expiración del token en milisegundos desde la época
        long expirationMillis = Long.parseLong(request.getParameter("expiration"));

        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();

        // Convertir la fecha y hora actual a milisegundos
        long nowMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        System.out.println("className.methodName()" + nowMillis);

        // Verificar si el token ha expirado
        if (nowMillis > expirationMillis) {
            // Si el token ha expirado, redirigir al usuario a la página de inicio
            response.sendRedirect("../index.jsp");
        } else {
            // El token es válido, continuar con el proceso de cambio de contraseña
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../CSS/app.css" rel="stylesheet">
        <link rel="shortcut icon" href="../IMG/bloqueo-de-rotacion.webp" type="image/x-icon">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <title>Restablecimiento de contraseñas</title>
    </head>
    <body>
        <div class="wrapper">

            <div class="main">
                <!-- Demo header-->
                <section class="section-0 d-flex justify-content-between bg-success">
                    <h1 class="text-start mt-4" style="margin-left: 7px; font-family: serif">Regional Sucre</h1>
                    <img src="../IMG/Logo_Sena_Sin_Fondo.png" width="150px" height="200px" alt="alt" class="align-self-end img-fluid"/>
                </section>
                <main class="content">
                    <div class="container-fluid p-0" style="font-family: initial">

                        <div class="row">
                            <section class="section-0 d-flex justify-content-between mb-3 mx-xxl-7">
                                <h1 class="mt-3 mx-5 "><strong>Recuperacion de contraseña</strong></h1>
                            </section>
                            <div class="col-12 col-md-12 col-xxl-8 d-flex order-3 order-xxl-2 mx-auto">
                                <div class="card flex-fill w-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Tus Datos</h5>
                                    </div>
                                    <div class="card-body px-4">
                                        <form action="<%=request.getContextPath()%>/RestablecimientosContrase_as" method="post">
                                            <div class="mb-3">
                                                <label for="numeroDocumento" class="form-label">Número de Documento</label>
                                                <input type="text" class="form-control" id="numeroDocumento" value="<%=cedula2%>" name="numeroDocumentoCambio" readonly>
                                            </div>
                                            <div class="mb-3">
                                                <label for="password1" class="form-label">Contraseña Nueva</label>
                                                <input type="password" class="form-control" name="PasswordNueva" id="password1" placeholder="Ingrese su contraseña actual" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="password2" class="form-label">Confirmar Contraseña</label>
                                                <input type="password" class="form-control" name="ConfirmacionPassword" id="password2" placeholder="Ingrese su nueva contraseña" required>
                                            </div>
                                            <button value="RestablecerNewPage" name="BtnRestablecer" class="btn btn-success mt-2">Cambiar Contraseña</button>
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
    </body>
</html>

<%
        }
    }

%>
