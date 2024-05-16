/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.FormacionJpaController;
import Controladores.SedeJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Formacion;
import Entidades.Sede;
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
@WebServlet(name = "CrudFormacionesSedes", urlPatterns = {"/CrudFormacionesSedes"})
public class CrudFormacionesSedes extends HttpServlet {

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
        String btn = request.getParameter("btnFormacionesSedes");
        PrintWriter res = response.getWriter();

        switch (btn) {
            case "guardarSede":
                // res.print("Guardar Sede");
                guardarSede(request, response);
                break;
            case "ModificarSedes":
                //res.print("Guardar Modificacion Sedes");
                ModificarSede(request, response);
                break;
            case "RegistroFormaciones":
                //res.print("Guardar Registro Formaciones");
                GuardadoFormaciones(request, response);
                break;
            case "FormacionesModificar":
                //res.print("Guardar Modificacion de Formaciones");
                ModificadoFormaciones(request, response);
                break;
            default:
                break;
        }

    }

    public void guardarSede(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instancia de controladores y entidades necesarias
        SedeJpaController controlSedes = new SedeJpaController();
        Sede sedeValidar = new Sede();
        Sede sedeGuardar = new Sede();

        //Obtenemos datos
        int IdSede = Integer.parseInt(request.getParameter("IdRegistrarSede"));
        String NombreSede = request.getParameter("NombreSede");

        //Validamos que no este registrada
        sedeValidar = controlSedes.findSede(IdSede);
        String MensajeUrl;
        if (sedeValidar == null) {
            //Procedemos al guardado
            sedeGuardar.setId(IdSede);
            sedeGuardar.setNombre(NombreSede);
            try {
                controlSedes.create(sedeGuardar);
                MensajeUrl = "RegsitroGuardado";
                response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + MensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(CrudFormacionesSedes.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            //ID ya existente
            MensajeUrl = "Existente";
            response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + MensajeUrl);
        }

    }

    public void ModificarSede(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SedeJpaController controlSedes = new SedeJpaController();
        Sede sedeModal = new Sede();
        Sede sedeModificar = new Sede();

        //Obtenemos datos
        int IdSede = Integer.parseInt(request.getParameter("IdModificarSede"));
        String NombreSede = request.getParameter("NombreSedeModificar");

        //Validamos que se hayan realizado cambios
        sedeModal = controlSedes.findSede(IdSede);
        sedeModificar.setId(IdSede);
        sedeModificar.setNombre(NombreSede);

        String MensajeUrl;
        if (sedeModificar.equals(sedeModal)) {
            //No hay cambios
            MensajeUrl = "SinCambios";
            response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + MensajeUrl);
        } else {
            try {
                //  Procedemos al guardado
                controlSedes.edit(sedeModificar);
                MensajeUrl = "ModificacionExitosa";
                response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + MensajeUrl);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CrudFormacionesSedes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CrudFormacionesSedes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void GuardadoFormaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instanciamos clases necesarias
        FormacionJpaController controlFormaciones = new FormacionJpaController();
        SedeJpaController sedeControl = new SedeJpaController();
        Formacion formacionValidar = new Formacion();
        Formacion formacionGuardar = new Formacion();

        //Obtencion de datos
        int IdFormacion = Integer.parseInt(request.getParameter("IdRegistroFormacion"));
        String nombreFormacion = request.getParameter("NombreFormacionRegistrar");
        int SedeEncontrada = Integer.parseInt(request.getParameter("SedePerteneciente"));
        //Encontramos la sede
        Sede sedeSeleccionada = sedeControl.findSede(SedeEncontrada);

        //Validamos que el Id no se encuentre registrado en formaaiones
        formacionValidar = controlFormaciones.findFormacion(IdFormacion);
        String mensajeUrl;
        if (formacionValidar == null) {
            //Procedemos al guardado
            formacionGuardar.setId(IdFormacion);
            formacionGuardar.setNombre(nombreFormacion);
            formacionGuardar.setSedeID(sedeSeleccionada);
            try {
                controlFormaciones.create(formacionGuardar);
                mensajeUrl = "RegsitroGuardado";
                response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(CrudFormacionesSedes.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            //Ya existe 
            mensajeUrl = "Existente";
            response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + mensajeUrl);

        }

    }

    public void ModificadoFormaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Instanciamos clases necesarias
        FormacionJpaController controlFormaciones = new FormacionJpaController();
        SedeJpaController sedeControl = new SedeJpaController();
        Formacion formacionModal = new Formacion();
        Formacion formacionModificar = new Formacion();

        //Obtencion de datos
        int IdFormacion = Integer.parseInt(request.getParameter("IdFormacionModificar"));
        String nombreFormacion = request.getParameter("NombreFormacionModificar");
        int SedeEncontrada = Integer.parseInt(request.getParameter("SedePertenecienteModificar"));
        //Encontramos la sede
        Sede sedeSeleccionada = sedeControl.findSede(SedeEncontrada);

        //Validamos que si existan cambios
        String MensajeUrl;
        formacionModal = controlFormaciones.findFormacion(IdFormacion);
        formacionModificar.setId(IdFormacion);
        formacionModificar.setNombre(nombreFormacion);
        formacionModificar.setSedeID(sedeSeleccionada);
        if (formacionModal.equals(formacionModificar)) {
            //No hay cambios
            MensajeUrl = "SinCambios";
            response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + MensajeUrl);
        } else {
            try {
                //Procedemos a la modificacion
                controlFormaciones.edit(formacionModificar);
                MensajeUrl = "ModificacionExitosa";
                response.sendRedirect("Views/ListadoRegistroFormacionesSedes_UI.jsp?respuesta=" + MensajeUrl);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CrudFormacionesSedes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CrudFormacionesSedes.class.getName()).log(Level.SEVERE, null, ex);
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
