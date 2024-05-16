/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.TipoPoblacionJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.TipoPoblacion;

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
@WebServlet(name = "CrudPoblaciones", urlPatterns = {"/CrudPoblaciones"})
public class CrudPoblaciones extends HttpServlet {

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

        String btn = request.getParameter("BotonPoblacion");
        PrintWriter res = response.getWriter();

        switch (btn) {
            case "Registrar":
                // res.print("Hola");
                RegistroPoblaciones(request, response);
                break;
            case "Modificar":
                ModificarPoblacion(request, response);
                break;
            default:
                break;
        }

    }

    public void RegistroPoblaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Instancias de controladores y Entidades necesarias
        TipoPoblacionJpaController controlPoblaciones = new TipoPoblacionJpaController();
        TipoPoblacion PoblacionGuardar = new TipoPoblacion();
        TipoPoblacion PoblacionValidar = new TipoPoblacion();
        //Obtencion de datos
        int IdPoblacion = Integer.parseInt(request.getParameter("IdPoblacion"));
        String NommbrePoblacion = request.getParameter("NombrePoblacion");
        //Validamos que la poblacion no se encuentre registrada
        PoblacionValidar = controlPoblaciones.findTipoPoblacion(IdPoblacion);

        String MensajeUrl;
        if (PoblacionValidar == null) {
            //Procede al guardado
            PoblacionGuardar.setId(IdPoblacion);
            PoblacionGuardar.setNombre(NommbrePoblacion);
            try {

                controlPoblaciones.create(PoblacionGuardar);
                MensajeUrl = "RegistroExitoso";
                response.sendRedirect("Views/ListadoRegistroPoblaciones_UI.jsp?respuesta=" + MensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(CrudPoblaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //Poblacion ya registrada
            MensajeUrl = "PoblacionExistente";
            response.sendRedirect("Views/ListadoRegistroPoblaciones_UI.jsp?respuesta=" + MensajeUrl);
        }

    }

    public void ModificarPoblacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TipoPoblacionJpaController controlPoblaciones = new TipoPoblacionJpaController();
        TipoPoblacion PoblacionGuardar = new TipoPoblacion();
        TipoPoblacion PoblacionModal = new TipoPoblacion();

        //Obtnecion de datos
        int IdPoblacion = Integer.parseInt(request.getParameter("IdPoblacionModificar"));
        String NommbrePoblacion = request.getParameter("NombrePoblacionModificar");
        //Detectamos el modal que se abrio
        PoblacionModal = controlPoblaciones.findTipoPoblacion(IdPoblacion);
        //Hacemos seteo
        PoblacionGuardar.setId(IdPoblacion);
        PoblacionGuardar.setNombre(NommbrePoblacion);
        String MensajeUrl;
        if (PoblacionGuardar.equals(PoblacionModal)) {
            //Son Iguales
            MensajeUrl = "SinCambios";
            response.sendRedirect("Views/ListadoRegistroPoblaciones_UI.jsp?respuesta=" + MensajeUrl);
        } else {
            try {
                controlPoblaciones.edit(PoblacionGuardar);
                MensajeUrl = "Cambios";
                response.sendRedirect("Views/ListadoRegistroPoblaciones_UI.jsp?respuesta=" + MensajeUrl);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CrudPoblaciones.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CrudPoblaciones.class.getName()).log(Level.SEVERE, null, ex);
            }

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
