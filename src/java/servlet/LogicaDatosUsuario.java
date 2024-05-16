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
@WebServlet(name = "LogicaDatosUsuario", urlPatterns = {"/LogicaDatosUsuario"})
public class LogicaDatosUsuario extends HttpServlet {

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
        String btn = request.getParameter("BtnModificar");

        switch (btn) {
            case "Modificar":
                MoficarDatos(request, response);
                //pruebaStr(request, response);
                break;
            default:
                break;
        }

    }

    public void MoficarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instanciamos datos necesarios
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        TipodocumentoJpaController tipoDocControl = new TipodocumentoJpaController();
        Usuarios usuarioLLegado = new Usuarios();
        Usuarios usuarioModificar = new Usuarios();
        // Obtnemos Datos
        String numeroDocStr = request.getParameter("Num_Cedula");
        int numeroDoc = Integer.parseInt(numeroDocStr);
        int TipoDoc = Integer.parseInt(request.getParameter("TipoDoc"));
        String nombre = request.getParameter("Nombre");
        String apellidos = request.getParameter("Apellidos");
        String correo = request.getParameter("Correo");
        Long Telefono = Long.parseLong(request.getParameter("Telefono"));

        Tipodocumento tipoDocEncontrado = tipoDocControl.findTipodocumento(TipoDoc);

        //Verificamos que si existan cambios
        usuarioLLegado = controlUsuario.findUsuarios(numeroDoc);
        usuarioModificar.setCedula(numeroDoc);
        usuarioModificar.setNombre(nombre);
        usuarioModificar.setApellido(apellidos);
        usuarioModificar.setCorreo(correo);
        usuarioModificar.setRol(usuarioLLegado.getRol());
        usuarioModificar.setPassword(usuarioLLegado.getPassword());
        usuarioModificar.setTelefono(Telefono);
        usuarioModificar.setTipoDocID(tipoDocEncontrado);

        String mensajeUrl;
        if (usuarioLLegado.equals(usuarioModificar)) {
            //No hay cambios
            mensajeUrl = "SinCambios";
            response.sendRedirect("Views/DatosPersonales_UI.jsp?respuesta=" + mensajeUrl);
        } else {
            try {
                //Procedemos a la edicion
                controlUsuario.edit(usuarioModificar);
                mensajeUrl = "Edicion";
                response.sendRedirect("Views/DatosPersonales_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(LogicaDatosUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
