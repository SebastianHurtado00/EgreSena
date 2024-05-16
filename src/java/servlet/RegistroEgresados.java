/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.CiudadJpaController;
import Controladores.EgresadoJpaController;
import Controladores.FormacionJpaController;
import Controladores.NivelFormacionJpaController;
import Controladores.SedeJpaController;
import Controladores.SexoJpaController;
import Controladores.TipoPoblacionJpaController;
import Controladores.TipodocumentoJpaController;
import Controladores.UsuariosJpaController;
import Entidades.Ciudad;
import Entidades.Egresado;
import Entidades.Formacion;
import Entidades.NivelFormacion;
import Entidades.Sede;
import Entidades.Sexo;
import Entidades.TipoPoblacion;
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
@WebServlet(name = "RegistroEgresados", urlPatterns = {"/RegistroEgresados"})
public class RegistroEgresados extends HttpServlet {

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
        String btn = request.getParameter("BtnEgresados");
        PrintWriter res = response.getWriter();
        switch (btn) {
            case "RegistrarEgresados":
                // res.print("Hola");
                GuardadarDatosEgresado(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }
    
   
    public void GuardadarDatosEgresado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Imstamciamos entidades necesarias
        EgresadoJpaController controlEgresados = new EgresadoJpaController();
        TipodocumentoJpaController controlTipoDoc = new TipodocumentoJpaController();
        SexoJpaController controlSexo = new SexoJpaController();
        SedeJpaController controlSede = new SedeJpaController();
        FormacionJpaController controlFormacion = new FormacionJpaController();
        CiudadJpaController controlCiudad = new CiudadJpaController();
        NivelFormacionJpaController controlNivelFomacion = new NivelFormacionJpaController();
        TipoPoblacionJpaController controlTipoPoblacion = new TipoPoblacionJpaController();
        UsuariosJpaController controlUsuarios = new UsuariosJpaController();
        Egresado egresadoValidar = new Egresado();
        Egresado egresadoGuardar = new Egresado();
        Usuarios usurioEgresado = new Usuarios();

        //Tomammos datos
        Long IdEgresado = Long.parseLong(request.getParameter("numeroDocumento"));
        int IdEgresadoU = Integer.parseInt(request.getParameter("numeroDocumento"));
        String IdEgresadoStr = request.getParameter("numeroDocumento");
        String nombreEgresado = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String Direccion = request.getParameter("direccion");
        int NumeroCertificados = Integer.parseInt(request.getParameter("numCertificados"));
        String correoElectronico = request.getParameter("correo");
        Long numeroContacto = Long.parseLong(request.getParameter("numContacto"));

        int IdTipoDocEncontrado = Integer.parseInt(request.getParameter("tipoDocumento"));
        int IdSexoEncontrado = Integer.parseInt(request.getParameter("sexo"));
        int IdPoblacionEncontrada = Integer.parseInt(request.getParameter("tipoPoblacion"));
        int IdCiudadEncontrada = Integer.parseInt(request.getParameter("ciudad"));
        int IdSedeEncontrada = Integer.parseInt(request.getParameter("sede"));
        int IdFormacionEncontrada = Integer.parseInt(request.getParameter("formacion"));
        int NivelFormacionEncontrado = Integer.parseInt(request.getParameter("nivelFormacion"));

        //Encontrasmos los Selects
        Tipodocumento TipoDocSeleccionado = controlTipoDoc.findTipodocumento(IdTipoDocEncontrado);
        Sexo SexpSeleccionado = controlSexo.findSexo(IdSexoEncontrado);
        TipoPoblacion poblacionSeleccionada = controlTipoPoblacion.findTipoPoblacion(IdPoblacionEncontrada);
        Ciudad ciudadSeleccionada = controlCiudad.findCiudad(IdCiudadEncontrada);
        Sede sedeSeleccionada = controlSede.findSede(IdSedeEncontrada);
        Formacion formacionSeleccionada = controlFormacion.findFormacion(IdFormacionEncontrada);
        NivelFormacion NivelFormacionSeleccionado = controlNivelFomacion.findNivelFormacion(NivelFormacionEncontrado);

        //CheckBox e input Escondido Formacion
        String checkboxFormacionValue = request.getParameter("CadenaFormacion");
        boolean checkBoxFormacion = checkboxFormacionValue != null && checkboxFormacionValue.equals("1");
        String FormacionCursada = request.getParameter("FormacionCursada");

        //CheckBox e input Escondido Experiencia
        String checkboxExperienciaValue = request.getParameter("Experiencia");
        boolean checkBoxExperiencia = checkboxExperienciaValue != null && checkboxExperienciaValue.equals("1");
        String ExpDescripcion = request.getParameter("ExperienciaDescrip");

        //CheckBox e input Escondido Carrera Universitaria
        String checkboxUniversitariaValue = request.getParameter("CarreraUniversitaria");
        boolean checkBoxUniversitaria = checkboxUniversitariaValue != null && checkboxUniversitariaValue.equals("1");
        String NombreUni = request.getParameter("NombreUni");
        String NombreCarrera = request.getParameter("NombreCarrera");

        //CheckBox e input Escondido Carrera Universitaria
        String checkboxTrabajando = request.getParameter("trabajando");
        boolean checkBoxTraba = checkboxTrabajando != null && checkboxTrabajando.equals("1");

        //Verificamos que Id no se repita
        egresadoValidar = controlEgresados.findEgresado(IdEgresado);
        String mensajeUrl;
        if (egresadoValidar != null) {
            //  Id Ya existe
            mensajeUrl = "Existente";
            response.sendRedirect("Views/RegistrosEgresados_UI.jsp?respuesta=" + mensajeUrl);
        } else {
            //Procedemos al guardado
            egresadoGuardar.setNumeroCedula(IdEgresado);
            egresadoGuardar.setTipoDocumentoID(TipoDocSeleccionado);
            egresadoGuardar.setNombre(nombreEgresado);
            egresadoGuardar.setApellido(apellidos);
            egresadoGuardar.setSexoID(SexpSeleccionado);
            egresadoGuardar.setSedeID(sedeSeleccionada);
            egresadoGuardar.setFormacionID(formacionSeleccionada);
            egresadoGuardar.setNivelFormacionID(NivelFormacionSeleccionado);
            egresadoGuardar.setNumeroCertificados(NumeroCertificados);
            egresadoGuardar.setCorreo(correoElectronico);
            egresadoGuardar.setTipoPoblacionID(poblacionSeleccionada);
            egresadoGuardar.setNumeroTelefono(numeroContacto);
            egresadoGuardar.setCiudadID(ciudadSeleccionada);
            egresadoGuardar.setDireccionResidencia(Direccion);
            egresadoGuardar.setCadenaFormacion(checkBoxFormacion);
            egresadoGuardar.setFormacionCursada(FormacionCursada);
            egresadoGuardar.setCadenaUniversitaria(checkBoxUniversitaria);
            egresadoGuardar.setNombreUniversidad(NombreUni);
            egresadoGuardar.setNombreCarrera(NombreCarrera);

            egresadoGuardar.setExperiencia(checkBoxExperiencia);
            egresadoGuardar.setDescripcionExperiencia(ExpDescripcion);
            egresadoGuardar.setTrabajando(checkBoxTraba);

            try {
                controlEgresados.create(egresadoGuardar);
                //Falta el guardado a la Tb usuarios
                String passwordEncriptada = usurioEgresado.EncryptarClave(IdEgresadoStr);

                usurioEgresado.setCedula(IdEgresadoU);
                usurioEgresado.setTipoDocID(TipoDocSeleccionado);
                usurioEgresado.setNombre(nombreEgresado);
                usurioEgresado.setApellido(apellidos);
                usurioEgresado.setTelefono(numeroContacto);
                usurioEgresado.setCorreo(correoElectronico);
                usurioEgresado.setRol(2);
                usurioEgresado.setPassword(passwordEncriptada);
                controlUsuarios.create(usurioEgresado);

                mensajeUrl = "RegstroExitoso";
                response.sendRedirect("Views/RegistrosEgresados_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(RegistroEgresados.class.getName()).log(Level.SEVERE, null, ex);
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
