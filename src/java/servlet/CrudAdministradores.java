/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.TipoPoblacionJpaController;
import Controladores.TipodocumentoJpaController;
import Controladores.UsuariosJpaController;
import Controladores.exceptions.NonexistentEntityException;
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
@WebServlet(name = "CrudAdministradores", urlPatterns = {"/CrudAdministradores"})
public class CrudAdministradores extends HttpServlet {

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
        PrintWriter res = response.getWriter();
        String BtnRecibido = request.getParameter("BtnAdmin");

        switch (BtnRecibido) {
            case "BtnGuardarAdmin":
                // res.print("Guardado de admin");
                RegistroAdmin(request, response);
                break;
            case "BtnEliminarAdmin":
                // res.print("Eliminado de admin");
                EliminarAdmin(request, response);
                break;
            case "BtnModificarAdmin":
                // res.print("Modificacion de admin");
                modificarAdmin(request, response);
                break;
            default:
                break;
        }
    }

    public void RegistroAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TipodocumentoJpaController controlTipoDocumento = new TipodocumentoJpaController();
        UsuariosJpaController controlUsuarios = new UsuariosJpaController();
        //Obtencion de datos
        int CedulaAdmin = Integer.parseInt(request.getParameter("CedulaAdmin"));
        String NombreAdmin = request.getParameter("NombreAdmin");
        String EmailAdmin = request.getParameter("EmailAdmin");
        String ApellidoAdmin = request.getParameter("ApellidoAdmin");
        Long TelefonoAdmin = Long.parseLong(request.getParameter("TelefonoAdmin"));
        String password = request.getParameter("PasswordAdmin");

        //Encontramos Tipo de documento
        int TipoDocSeleccionado = Integer.parseInt(request.getParameter("TipodocumentAdmin"));
        Tipodocumento TipoDocEncontrado = controlTipoDocumento.findTipodocumento(TipoDocSeleccionado);
        //Validamos que la Cedula ingresada no esta ya registrada

        Usuarios validacionUsuario = controlUsuarios.findUsuarios(CedulaAdmin);
        String MensajeUrl;
        if (validacionUsuario == null) {

            //Procede al guardado
            Usuarios AdminGuardar = new Usuarios();
            //Encriptasmos contraseña
            String passwordEncriptada = AdminGuardar.EncryptarClave(String.valueOf(CedulaAdmin));
            AdminGuardar.setCedula(CedulaAdmin);
            AdminGuardar.setNombre(NombreAdmin);
            AdminGuardar.setApellido(ApellidoAdmin);
            AdminGuardar.setTipoDocID(TipoDocEncontrado);
            AdminGuardar.setCorreo(EmailAdmin);
            AdminGuardar.setTelefono(TelefonoAdmin);
            AdminGuardar.setPassword(passwordEncriptada);
            AdminGuardar.setRol(0);
            try {
                controlUsuarios.create(AdminGuardar);
                MensajeUrl = "AdminGuaradado";
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
            } catch (Exception ex) {
                Logger.getLogger(CrudAdministradores.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            MensajeUrl = "CCRegistrada";
            String urlDeRetorno = request.getHeader("referer");
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

    public void modificarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instanciamos controladores
        TipodocumentoJpaController controlTipoDocumento = new TipodocumentoJpaController();
        UsuariosJpaController controlUsuarios = new UsuariosJpaController();
        Usuarios usuarioModificar = new Usuarios();
        Usuarios usuariosValidar = new Usuarios();

        // Obtenemos datos
        int cedula = Integer.parseInt(request.getParameter("Num_Cedula"));
        int IdTipoDoc = Integer.parseInt(request.getParameter("TipoDoc"));
        String nombre = request.getParameter("Nombre");
        String apellido = request.getParameter("Apellidos");
        String correro = request.getParameter("Correo");
        Long telefono = Long.parseLong(request.getParameter("Telefono"));

        Tipodocumento tipoDocumentoSeleccionado = controlTipoDocumento.findTipodocumento(IdTipoDoc);

        usuariosValidar = controlUsuarios.findUsuarios(cedula);

        usuarioModificar.setCedula(cedula);
        usuarioModificar.setNombre(nombre);
        usuarioModificar.setApellido(apellido);
        usuarioModificar.setCorreo(correro);
        usuarioModificar.setTelefono(telefono);
        usuarioModificar.setTipoDocID(tipoDocumentoSeleccionado);
        usuarioModificar.setRol(usuarioModificar.getRol());
        usuarioModificar.setPassword(usuariosValidar.getPassword());
        PrintWriter res = response.getWriter();
        String mensajeUrl;
        if (usuariosValidar.equals(usuarioModificar)) {
            //Iguales 
            //res.print("Sin cambios");
            mensajeUrl = "SinCambios";
            response.sendRedirect("Views/ListadoAdministradores_UI.jsp?respuesta=" + mensajeUrl);
        } else {
            try {

                controlUsuarios.edit(usuarioModificar);
                mensajeUrl = "Modifiacado";
                response.sendRedirect("Views/ListadoAdministradores_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(CrudAdministradores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void EliminarAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensajeUrl;
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        int id = Integer.parseInt(request.getParameter("ID"));

        try {
            controlUsuario.destroy(id);
            mensajeUrl = "eliminado";
            response.sendRedirect("Views/ListadoAdministradores_UI.jsp?respuesta=" + mensajeUrl);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CrudAdministradores.class.getName()).log(Level.SEVERE, null, ex);
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
