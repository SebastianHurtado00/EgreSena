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
import Controladores.exceptions.NonexistentEntityException;
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
@WebServlet(name = "ModificacionEgresado", urlPatterns = {"/ModificacionEgresado"})
public class ModificacionEgresado extends HttpServlet {

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
            case "Modificar":
                //res.print("Modifaicar");
                modficacionEgresado(request, response);
                break;
            case "ModificarAdmin":
                modificacionEgresadoAdmin(request, response);
                break;
            case "eliminar":
                //  res.print("Eliminar");
                eliminarEgresado(request, response);
                break;
            default:
                break;
        }

    }

    public void modficacionEgresado(HttpServletRequest request, HttpServletResponse response)
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
        Egresado egresadoLlegado = new Egresado();
        Egresado egresadoModificar = new Egresado();
        Usuarios usuarioModificar = new Usuarios();

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

        //Validamos que hayan cambios
        egresadoLlegado = controlEgresados.findEgresado(IdEgresado);
        egresadoModificar.setNumeroCedula(IdEgresado);
        egresadoModificar.setTipoDocumentoID(TipoDocSeleccionado);
        egresadoModificar.setNombre(nombreEgresado);
        egresadoModificar.setApellido(apellidos);
        egresadoModificar.setSexoID(SexpSeleccionado);
        egresadoModificar.setSedeID(sedeSeleccionada);
        egresadoModificar.setFormacionID(formacionSeleccionada);
        egresadoModificar.setNivelFormacionID(NivelFormacionSeleccionado);
        egresadoModificar.setNumeroCertificados(NumeroCertificados);
        egresadoModificar.setCorreo(correoElectronico);
        egresadoModificar.setTipoPoblacionID(poblacionSeleccionada);
        egresadoModificar.setNumeroTelefono(numeroContacto);
        egresadoModificar.setCiudadID(ciudadSeleccionada);
        egresadoModificar.setDireccionResidencia(Direccion);
        egresadoModificar.setCadenaFormacion(checkBoxFormacion);
        egresadoModificar.setFormacionCursada(FormacionCursada);
        egresadoModificar.setCadenaUniversitaria(checkBoxUniversitaria);
        egresadoModificar.setNombreUniversidad(NombreUni);
        egresadoModificar.setNombreCarrera(NombreCarrera);

        egresadoModificar.setExperiencia(checkBoxExperiencia);
        egresadoModificar.setDescripcionExperiencia(ExpDescripcion);
        egresadoModificar.setTrabajando(checkBoxTraba);

        String mensajeUrl;
        if (egresadoLlegado.equals(egresadoModificar)) {
            //No hubo cambios
            mensajeUrl = "SinCambios";
            response.sendRedirect("Views/HomeEgresados_UI.jsp?respuesta=" + mensajeUrl);

        } else {
            try {
                //Modificaion en datos de la Tb usuario
                //Encontramos usuario
                usuarioModificar = controlUsuarios.findUsuarios(IdEgresadoU);
                //Le seteamos los datos requeridos
                usuarioModificar.setCedula(usuarioModificar.getCedula());
                usuarioModificar.setNombre(nombreEgresado);
                usuarioModificar.setApellido(apellidos);
                usuarioModificar.setCorreo(correoElectronico);
                usuarioModificar.setPassword(usuarioModificar.getPassword());
                usuarioModificar.setRol(usuarioModificar.getRol());
                usuarioModificar.setTelefono(numeroContacto);
                usuarioModificar.setTipoDocID(TipoDocSeleccionado);
                controlUsuarios.edit(usuarioModificar);

                controlEgresados.edit(egresadoModificar);
                mensajeUrl = "ModificacionExitosa";
                response.sendRedirect("Views/HomeEgresados_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(ModificacionEgresado.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void modificacionEgresadoAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
        EgresadoJpaController controlEgresado = new EgresadoJpaController();
        TipodocumentoJpaController controlTipoDoc = new TipodocumentoJpaController();
        SexoJpaController controlSexo = new SexoJpaController();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios usuarioModificar = new Usuarios();
        Egresado egresadoModal = new Egresado();
        Egresado egresadoModificar = new Egresado();

        Long IdEgresado = Long.parseLong(request.getParameter("numeroDocumentoModal"));
        int IdEgresadoU = Integer.parseInt(request.getParameter("numeroDocumentoModal"));
        String IdEgresadoStr = request.getParameter("numeroDocumentoModal");
        String nombreEgresado = request.getParameter("nombresModal");
        String apellidos = request.getParameter("apellidos");
        String correoElectronico = request.getParameter("correoModal");
        Long numeroContacto = Long.parseLong(request.getParameter("telefonoModal"));

        int IdTipoDocEncontrado = Integer.parseInt(request.getParameter("tipoDocumentoModal"));
        int IdSexoEncontrado = Integer.parseInt(request.getParameter("sexoModal"));

        Tipodocumento TipoDocSeleccionado = controlTipoDoc.findTipodocumento(IdTipoDocEncontrado);
        Sexo SexpSeleccionado = controlSexo.findSexo(IdSexoEncontrado);

        //Validamos que existan cambios
        egresadoModal = controlEgresado.findEgresado(IdEgresado);
        egresadoModificar = controlEgresado.findEgresado(IdEgresado);
        egresadoModificar.setNombre(nombreEgresado);
        egresadoModificar.setApellido(apellidos);
        egresadoModificar.setCorreo(correoElectronico);
        egresadoModificar.setNumeroTelefono(numeroContacto);
        egresadoModificar.setTipoDocumentoID(TipoDocSeleccionado);
        egresadoModificar.setSexoID(SexpSeleccionado);

        String mensajeUrl;
        PrintWriter res = response.getWriter();
        if (egresadoModal.equals(egresadoModificar)) {
            //res.print("Iguales");
            mensajeUrl = "SinCambios";
            response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=" + mensajeUrl);
        } else {
            try {
                // res.print("Diferentes");
                //Realizamos un guardado doble
                usuarioModificar = controlUsuario.findUsuarios(IdEgresadoU);
                //Le seteamos los datos requeridos
                usuarioModificar.setNombre(nombreEgresado);
                usuarioModificar.setApellido(apellidos);
                usuarioModificar.setCorreo(correoElectronico);
                usuarioModificar.setPassword(usuarioModificar.getPassword());
                usuarioModificar.setRol(usuarioModificar.getRol());
                usuarioModificar.setTelefono(numeroContacto);
                usuarioModificar.setTipoDocID(TipoDocSeleccionado);
                controlUsuario.edit(usuarioModificar);
                controlEgresado.edit(egresadoModificar);

                mensajeUrl = "ModificacionExitosa";
                response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(ModificacionEgresado.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void eliminarEgresado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EgresadoJpaController controlEgrsado = new EgresadoJpaController();
        UsuariosJpaController contrUsuarios = new UsuariosJpaController();
      
        Usuarios usuarioEliminar = new Usuarios();
        Egresado egresadoEliminar = new Egresado();

        int ID_Eliminar = Integer.parseInt(request.getParameter("ID"));
        Long ID_Eliminar_Long = Long.parseLong(request.getParameter("ID"));

        String mensajeUrl;

        try {
            //Procedemos al eliminado en ambas tablas
            contrUsuarios.destroy(ID_Eliminar);
            controlEgrsado.destroy(ID_Eliminar_Long);
            mensajeUrl = "EliminacionExitosa";
            response.sendRedirect("Views/FiltradoEgresados_UI.jsp?respuesta=" + mensajeUrl);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ModificacionEgresado.class.getName()).log(Level.SEVERE, null, ex);
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
