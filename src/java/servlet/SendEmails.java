/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SendEmails", urlPatterns = {"/SendEmails"})
public class SendEmails extends HttpServlet {

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

        String btn = request.getParameter("sendEmails");
        switch (btn) {
            case "action":
                envioCorreosGolpe(request, response);
                break;
            default:
                break;
        }

    }

    public void envioCorreos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String asunto = request.getParameter("asunto");
        String cuerpo = request.getParameter("cuerpo");
        String listaCorreos = request.getParameter("ListaCorreos");

        // Divide el string en un array utilizando la coma como delimitador
        String[] correosArray = listaCorreos.split(",");

        final String username = "pruebasdevssc@gmail.com";
        final String password = "bike gsic nlfr lzna";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message messageObj = new MimeMessage(session);
            messageObj.setFrom(new InternetAddress(username));
            messageObj.setSubject(asunto);
            messageObj.setText(cuerpo);

            for (String correo : correosArray) {
                try {
                    messageObj.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
                    Transport.send(messageObj);
                    System.out.println("Se envió el mensaje a " + correo);
                } catch (AddressException e) {
                    System.err.println("Dirección de correo electrónico inválida: " + correo);
                } catch (MessagingException e) {
                    System.err.println("Error al enviar el correo electrónico a " + correo + ": " + e.getMessage());
                }
            }
            response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=envioExitoso");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void envioCorreosSTPM(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String asunto = request.getParameter("asunto");
        String cuerpo = request.getParameter("cuerpo");
        String listaCorreos = request.getParameter("ListaCorreos");

        // Divide el string en un array utilizando la coma como delimitador
        String[] correosArray = listaCorreos.split(",");

        final String username = "pruebasdevssc@gmail.com";
        final String password = "bike gsic nlfr lzna";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(asunto);
            message.setText(cuerpo);

            for (String correo : correosArray) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            }

            Transport.send(message);
            response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=envioExitoso");
        } catch (MessagingException e) {
            e.printStackTrace();
            response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=envioFallido");
        }
    }

    public void envioCorreosGolpe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String asunto = request.getParameter("asunto");
        String cuerpo = request.getParameter("cuerpo");
        String listaCorreos = request.getParameter("ListaCorreos");

        // Divide el string en un array utilizando la coma como delimitador
        String[] correosArray = listaCorreos.split(",");

        final String username = "pruebasdevssc@gmail.com";
        final String password = "bike gsic nlfr lzna";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message messageObj = new MimeMessage(session);
            messageObj.setFrom(new InternetAddress(username));
            messageObj.setSubject(asunto);
            messageObj.setText(cuerpo);

            for (String correo : correosArray) {
                try {
                    messageObj.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
                } catch (AddressException e) {
                    System.err.println("Dirección de correo electrónico inválida: " + correo);
                }
            }

            // Enviar todos los correos de una sola vez
            Transport.send(messageObj);

            // Mostrar mensajes de éxito después de enviar todos los correos
            for (String correo : correosArray) {
                System.out.println("Se envió el mensaje a " + correo);
            }

            response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=envioExitoso");
        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
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
