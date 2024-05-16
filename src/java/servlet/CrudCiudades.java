/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.CiudadJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Ciudad;
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
@WebServlet(name = "CrudCiudades", urlPatterns = {"/CrudCiudades"})
public class CrudCiudades extends HttpServlet {

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
        String btn = request.getParameter("BtnCiudades");

        switch (btn) {
            case "GuardarCiudad":
                GuardadoCiudades(request, response);
                break;
            case "ModificarCiudad":
                ModificarCiudad(request, response);
                break;
            default:
                break;
        }

    }

    public void GuardadoCiudades(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instancias necesarias
        CiudadJpaController controlCiudad = new CiudadJpaController();
        Ciudad CiudadValidar = new Ciudad();
        Ciudad CiudadGuardar = new Ciudad();

        //Obtencion de datos
        int Id = Integer.parseInt(request.getParameter("IdCiudad"));
        String nombreCiudad = request.getParameter("NombreCiudad");

        //Validar si no existe el ID en la DB
        CiudadValidar = controlCiudad.findCiudad(Id);
        String mensajeUrl;
        if (CiudadValidar == null) {
            //Procedemos al guardado
            CiudadGuardar.setId(Id);
            CiudadGuardar.setNombre(nombreCiudad);
            try {

                controlCiudad.create(CiudadGuardar);
                mensajeUrl = "GuardadoCiudad";
                response.sendRedirect("Views/ListadoRegistroCiudades_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(CrudCiudades.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            //ID registrado
            mensajeUrl = "IdRegistrado";
            response.sendRedirect("Views/ListadoRegistroCiudades_UI.jsp?respuesta=" + mensajeUrl);
        }

    }

    public void ModificarCiudad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CiudadJpaController controlCiudad = new CiudadJpaController();
        Ciudad CiudadModal = new Ciudad();
        Ciudad CiudadModificar = new Ciudad();
        int Id = Integer.parseInt(request.getParameter("IdCiudadModificar"));
        String nombreCiudad = request.getParameter("NombreCiudadModificar");

        //Validar que los registros no sean iguales
        CiudadModal = controlCiudad.findCiudad(Id);
        CiudadModificar.setId(Id);
        CiudadModificar.setNombre(nombreCiudad);
        String mensajeUrl;
        if (CiudadModal.equals(CiudadModificar)) {
            //Registros Iguales
            mensajeUrl = "registrosIguales";
            response.sendRedirect("Views/ListadoRegistroCiudades_UI.jsp?respuesta=" + mensajeUrl);
        } else {
            try {
                //Procedemos a la modificacion
                controlCiudad.edit(CiudadModificar);
                mensajeUrl = "ModificacionExitosa";
                response.sendRedirect("Views/ListadoRegistroCiudades_UI.jsp?respuesta=" + mensajeUrl);

            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CrudCiudades.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CrudCiudades.class.getName()).log(Level.SEVERE, null, ex);
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
