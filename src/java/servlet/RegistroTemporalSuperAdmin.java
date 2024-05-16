/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.TipodocumentoJpaController;
import Controladores.UsuariosJpaController;
import Entidades.Tipodocumento;
import Entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "RegistroTemporalSuperAdmin", urlPatterns = {"/RegistroTemporalSuperAdmin"})
public class RegistroTemporalSuperAdmin extends HttpServlet {

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
        String btn = request.getParameter("BtnSuperAdmin");
        switch (btn) {
            case "GuardarSuper":
                registroSuperAdmin(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    public void registroSuperAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuariosJpaController userController = new UsuariosJpaController();
        TipodocumentoJpaController TipoDocController = new TipodocumentoJpaController();
//Obtencion de datos
        int cedulaSuper = Integer.parseInt(request.getParameter("CedulaSuper"));
        String nombreSuper = request.getParameter("NombreSuper");
        String emailSuper = request.getParameter("emailSuper");
        int IDtipoDocEncontrado = Integer.parseInt(request.getParameter("TipoDocSuper"));
        String apellidoSuper = request.getParameter("ApellidoSuper");
        Long TelefonoSuper = Long.parseLong(request.getParameter("TelefonoSuper"));
//Validamos que la cc no este en la DB

        Tipodocumento DocEncontrado = TipoDocController.findTipodocumento(IDtipoDocEncontrado);

        Usuarios validacion = userController.findUsuarios(cedulaSuper);
        String MensajeUrl;
        if (validacion == null) {
//Encriptamos y guardamos
            Usuarios SuperAdminGuardar = new Usuarios();
            String passwordEncryptada = SuperAdminGuardar.EncryptarClave(String.valueOf(cedulaSuper));
            SuperAdminGuardar.setCedula(cedulaSuper);
            SuperAdminGuardar.setNombre(nombreSuper);
            SuperAdminGuardar.setApellido(apellidoSuper);
            SuperAdminGuardar.setTipoDocID(DocEncontrado);
            SuperAdminGuardar.setCorreo(emailSuper);
            SuperAdminGuardar.setTelefono(TelefonoSuper);
            SuperAdminGuardar.setPassword(passwordEncryptada);
            SuperAdminGuardar.setRol(1);
            try {
                userController.create(SuperAdminGuardar);
                String mensajeUrl = "ExitoSuperAdmin";
                String urlDeRetorno = request.getHeader("referer");

// Verifica si la URL ya contiene un parámetro de respuesta
                if (urlDeRetorno.contains("respuesta=")) {
                    // Elimina el parámetro de respuesta existente de la URL
                    urlDeRetorno = urlDeRetorno.replaceAll("[?&]respuesta=.*?(?:&|$)", "");
                }

// Ahora agrega el nuevo parámetro de respuesta
                if (urlDeRetorno.contains("?")) {
                    // La URL ya tiene una cadena de consulta, agrega el parámetro adecuadamente
                    response.sendRedirect(urlDeRetorno + "&respuesta=" + mensajeUrl);
                } else {
                    // La URL no tiene una cadena de consulta, agrega el parámetro con "?"
                    response.sendRedirect(urlDeRetorno + "?respuesta=" + mensajeUrl);
                }
            } catch (Exception ex) {
                Logger.getLogger(RegistroTemporalSuperAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            MensajeUrl = "CCRegistrada";
            String urlDeRetorno = request.getHeader("referer");
            // Redirigimos a la página JSP que llamó al servlet
            response.sendRedirect(urlDeRetorno + "?respuesta=" + MensajeUrl);
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
