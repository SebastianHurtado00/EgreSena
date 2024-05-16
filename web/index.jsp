<%-- 
    Document   : index
    Created on : 10/12/2023, 12:05:05 PM
    Author     : ASUS
--%>

<%@page import="org.eclipse.persistence.internal.oxm.schema.model.Include"%>
<html lang="en">
    <%
        //En dado caso que se regrese al index se mata la session
        // response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");

    %>
    <head>
        <title>Inicio sesion</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS v5.2.1 -->
        <link rel="shortcut icon" href="IMG/pagina-de-inicio.webp" type="image/x-icon">
        <link rel="stylesheet" href="CSS/Login.css">
        <link rel="stylesheet" href="CSS/Footer.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="JS/main.js"></script>

    </head>

    <body style="background-color: #f5f5f5;">

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">

                    <form method="post" action="LogicaIngreso" class="login100-form">
                        <img class="mb-4"src="IMG/LogoSena.png" width="35%" height="25%" alt="alt" style="margin-left: 65%"/>
                        <span class="login100-form-title p-b-43 mb-5">
                            Inicio de sesion
                        </span>
                        <div class="wrap-input100 validate-input mt-3 mb-4">
                            <input name="CC" class="input100" type="number" max="9999999999" name="" placeholder="Usuario" required>
                        </div>
                        <div class="wrap-input100 validate-input mb-4">
                            <input name="contrasenha" class="input100" type="password" maxlength="200" name="pass" placeholder="Password" required>                        
                        </div>
                        <div class="flex-sb-m w-full p-t-3 p-b-32 mb-4">
                            <div>
                                <a type="button" data-bs-toggle="modal" data-bs-target="#ModalOlvidoContrasenha">
                                    ¿Olvido su contraseña?
                                </a>
                            </div>
                        </div>
                        <div class="container-login100-form-btn">
                            <button value="action" name="BtnIngreso" class="login100-form-btn" style="background: #018E42">
                                Inicio de sesion
                            </button>
                        </div>

                    </form>
                    <div class="login100-more" style="background-image: url('https://agenciapublicadeempleo.sena.edu.co/imgLayout/Boletines%20de%20prensa/_DSC0302.jpg');">
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="ModalOlvidoContrasenha" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form action="<%=request.getContextPath()%>/RestablecimientosContrase_as">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel" style="font-family: monospace">Ingrese su numero de documento</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p class="mb-2">Se le sera enviado un correo(Al que se encuentra registrado con su usuario) que confirmara su identidad y hacer el restablecimiento de contraseña</p>
                            <p class="mt-2">Si no tiene acceso a su correo registrado por favor contactar con bienestar</p>
                            <img src="IMG/id-facial.webp" class="mb-1" alt="alt" width="30px" height="30px"/>
                            <label for="numeroDocumentoCambio" class="form-label">N° Documento</label>
                            <input type="number" name="numeroDocumentoCambio" id="numeroDocumentoCambio" class="form-control mb-3" max="9999999999" required>   

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button name="BtnRestablecer" value="RestablcerPasswordIndex" class="btn" style="background: #018E42 ; color: white">Enviar correo de recuperacion</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <jsp:include page="Components/footer.jsp"></jsp:include>
        <%            String res = request.getParameter("respuesta");

            if (res != null) {
                switch (res) {
                    case "contrasenhaIncorrecta":
        %>
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header  text-white" style="background: #018E42">
                    <strong class="me-auto ">Upps!!</strong>
                    <small>Ahora</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Contraseña Incorrecta!!
                </div>
            </div>
        </div>
        <%
                break;
            case "UserNoExistente":

        %>
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header  text-white" style="background: #018E42">
                    <strong class="me-auto ">Upss!!</strong>
                    <small>Ahora</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Usuario No existente!!
                </div>
            </div>
        </div>
        <%                        break;
            case "ContrasehaModificada":
        %>
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header  text-white" style="background: #018E42">
                    <strong class="me-auto ">Informacion!!</strong>
                    <small>Ahora</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Se ha modificado su contraseña por favor vuelva a iniciar sesion!!
                </div>
            </div>
        </div>
        <%
                break;
            case "enviado":
        %>
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header  text-white" style="background: #018E42">
                    <strong class="me-auto ">Informacion!!</strong>
                    <small>Ahora</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Se ha enviado a su correo los pasos para cambiar su contraseña!!
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
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

        <!-- Bootstrap JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-/WfsFq4XLtXJzweC9UJ7o4JvW/2HGM8eNQwuqzoU4llitlLVvc/UnzgZ81gMdd6R" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="JS/IniciarToast.js"></script>


    </body>

</html>
