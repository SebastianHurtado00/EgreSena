/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.EgresadoJpaController;
import Controladores.UsuariosJpaController;
import Entidades.Egresado;
import Entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "RestablecimientosContraseñas", urlPatterns = {"/RestablecimientosContrase_as"})
public class RestablecimientosContraseñas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String btn = request.getParameter("BtnRestablecer");
        PrintWriter res = response.getWriter();
        switch (btn) {
            case "RestablcerPasswordHome":
                restablecerPaswordUserHome(request, response);
                break;
            case "RestablcerPasswordIndex":
                buscarCorreo(request, response);
                break;
            case "RestablecerNewPage":
                restablecerPaswordUserHome(request, response);
                break;
            case "RestablercerAdmin":
                restablecerAdmin(request, response);
                break;
            default:
                break;
        }

    }

    public void restablecerAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuariosJpaController usuarioControl = new UsuariosJpaController();
        Usuarios usuario = new Usuarios();
        PrintWriter res = response.getWriter();

        String cedulaStr = request.getParameter("CC");
        String cedula = request.getParameter("cedula");
        String MensajeUrl;
        if (cedula == "" || cedulaStr == "") {
            MensajeUrl = "Vacios";
            response.sendRedirect("Views/RestablecimientoContrasehaEgresados_UI.jsp?respuesta=" + MensajeUrl);
        } else {
            int Cc = Integer.parseInt(cedulaStr);
            usuario = usuarioControl.findUsuarios(Cc);
            if (usuario == null || usuario.getRol() != 2) {
                MensajeUrl = "UsuarioNoValido";
                response.sendRedirect("Views/RestablecimientoContrasehaEgresados_UI.jsp?respuesta=" + MensajeUrl);
            } else {
                //Extraemos CC
                String cedulaEncriptar = usuario.EncryptarClave(cedulaStr);
                //Asignamos contraseña
                usuario.setPassword(cedulaEncriptar);
                try {
                    usuarioControl.edit(usuario);
                    MensajeUrl = "Exito";
                    response.sendRedirect("Views/RestablecimientoContrasehaEgresados_UI.jsp?respuesta=" + MensajeUrl);
                } catch (Exception ex) {
                    Logger.getLogger(RestablecimientosContraseñas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void restablecerPaswordUserHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instanciamos controladores necesarios
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios usuarioEntrante = new Usuarios();

        //Obtenesmos datos y encontramos al usuario
        int numeroDocumento = Integer.parseInt(request.getParameter("numeroDocumentoCambio"));
        String contraseñaNueva = request.getParameter("PasswordNueva");
        String contraseñaConfirmar = request.getParameter("ConfirmacionPassword");

        usuarioEntrante = controlUsuario.findUsuarios(numeroDocumento);
        String MensajeUrl;

        if (contraseñaConfirmar.equals(contraseñaNueva)) {
            try {
                String contrasenhaEncriptada = usuarioEntrante.EncryptarClave(contraseñaConfirmar);
                usuarioEntrante.setPassword(contrasenhaEncriptada);
                controlUsuario.edit(usuarioEntrante);
                MensajeUrl = "ContrasehaModificada";
                response.sendRedirect("index.jsp?respuesta=" + MensajeUrl);
            } catch (Exception e) {
            }

        } else {
            //las contraseñas no son iguales

            MensajeUrl = "ContrasenhaNoiguales";
            String urlDeRetorno = request.getHeader("referer");
            // Redirigimos a la página JSP que llamó al servlet

            // Verifica si la URL ya contiene un parámetro de respuesta
            if (urlDeRetorno.contains("respuesta=")) {
                // Elimina el parámetro de respuesta existente de la URL
                urlDeRetorno = urlDeRetorno.replaceAll("[?&]respuesta=.*?(?:&|$)", "");
            }

            // Ahora agrega el nuevo parámetro de respuesta
            if (urlDeRetorno.contains("?")) {
                // La URL ya tiene una cadena de consulta, agrega el parámetro adecuadamente
                response.sendRedirect(urlDeRetorno + "&respuesta=" + MensajeUrl);
            } else {
                // La URL no tiene una cadena de consulta, agrega el parámetro con "?"
                response.sendRedirect(urlDeRetorno + "?respuesta=" + MensajeUrl);
            }
        }
    }

    public void buscarCorreo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Aquí creas un nuevo objeto Usuarios para enviarlo por correo
        Usuarios usuario = new Usuarios();

        // Supongamos que obtienes la información necesaria para el usuario de alguna manera, por ejemplo, de los parámetros de la solicitud HTTP
        usuario.setCedula(Integer.parseInt(request.getParameter("numeroDocumentoCambio")));

        // Aquí configuras los demás atributos del usuario según lo que necesites
        int cedula = usuario.getCedula();

        // Crear instancias de los controladores JPA
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        EgresadoJpaController controlEgresado = new EgresadoJpaController();

        boolean correoEnviado = false; // Variable para verificar si se envió el correo

        try {
            // Buscar al Usuario por cédula en la base de datos
            Usuarios Usuarios = controlUsuario.findUsuarios(cedula);

            // Verificar si se encontró el estudiante y obtener su correo electrónico
            if (Usuarios != null) {
                String correo = Usuarios.getCorreo();
                // Ahora puedes enviar el correo electrónico al correo asociado
                enviarCorreo(correo, Usuarios, response, request); // Pasar el usuario como parámetro
                correoEnviado = true; // Actualizar la bandera
            }
            // Después de encontrar al usuario
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);

            if (!correoEnviado) {
                // Si no se envió el correo, significa que no se encontró ninguna cédula
                String mensaje = "UserNoExistente";
                response.sendRedirect("index.jsp?respuesta=" + mensaje);
            }
        } catch (Exception ex) {
            // Manejar cualquier excepción que pueda ocurrir durante la búsqueda
            ex.printStackTrace();
            //response.sendRedirect("error.jsp");
        }
    }

    private void enviarCorreo(String correoDestino, Usuarios usuario, HttpServletResponse response,
            HttpServletRequest request) throws IOException {
        String correoOrigen = "pruebasdevssc@gmail.com";
        String password = "bike gsic nlfr lzna";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoOrigen, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(correoOrigen));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            message.setSubject("Recuperación de contraseña");

            // Generar un token único para la URL de recuperación de contraseña
            String token = UUID.randomUUID().toString(); // Generar un UUID aleatorio como token único

            // Obtener la fecha y hora actual
            LocalDateTime now = LocalDateTime.now();

            // Convertir la fecha y hora actual a milisegundos
            long nowMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            // Definir la validez del token en minutos (1 minuto en este caso)
            int validityMinutes = 1;

            // Calcular la fecha y hora de expiración del token
            long expirationMillis = nowMillis + (validityMinutes * 60 * 1000);

            String url = "http://192.168.1.4:8080/SistemaEgresados_Components/Views/RestablecerPassword_UI.jsp?token=" + token
                    + "&cedula=" + usuario.getCedula() + "&expiration=" + expirationMillis;
            ;

            // Construir el contenido del mensaje con la URL única
            String mensaje = "Hola,\n\nHemos recibido una solicitud para restablecer tu contraseña. "
                    + "Si no solicitaste esto, puedes ignorar este correo.\n\n"
                    + "Para cambiar tu contraseña, haz clic en el siguiente enlace:\n"
                    + "<a href=\"" + url + "\" rel=\"noopener\">Cambiar contraseña</a>"
                    + " Si tienes alguna pregunta, contáctanos.(Tendra 5 minutos para realizar el cambio de la contraseña)";

            message.setContent(mensaje, "text/html");

            // Enviar el correo electrónico
            Transport.send(message);

            // Redireccionar a la página de inicio con un mensaje de confirmación
            String mensajeRedireccion = "enviado";
            response.sendRedirect("index.jsp?respuesta=" + mensajeRedireccion);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
