 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "LogicaIngreso", urlPatterns = {"/LogicaIngreso"})
public class LogicaIngreso extends HttpServlet {

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

        String Btn = request.getParameter("BtnIngreso");
        PrintWriter res = response.getWriter();
        switch (Btn) {
            case "action":
                LogicaIngreso(request, response);
                break;
            default:
                break;
        }

    }

    public void LogicaIngreso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuariosJpaController controlUser = new UsuariosJpaController();
        int cedula = Integer.parseInt(request.getParameter("CC"));
        String contraseña = request.getParameter("contrasenha");

        Usuarios user = controlUser.findUsuarios(cedula);
        String mensajeUrl;
        if (user != null) {
            if (user.DencryptarClave(user.getPassword(), contraseña)) {

                switch (user.getRol()) {
                    case 0:
                        //Redireccionamos a super admin con su session
                        HttpSession SesseionAdmin = request.getSession();
                        SesseionAdmin.setAttribute("Admin", user);
                        response.sendRedirect("Views/HomeAdministradores_UI.jsp");
                        break;

                    case 1:
                        //Redireccionamos a super admin con su session
                        HttpSession SessionSuperAdmin = request.getSession();
                        SessionSuperAdmin.setAttribute("SuperAdmin", user);
                        SessionSuperAdmin.setAttribute("especial", 1);
                        response.sendRedirect("Views/HomeSuperAdmin_UI.jsp");
                        break;

                    case 2:
                        //Redireccionamos a super admin con su session
                        HttpSession sessionEgresado = request.getSession();
                        sessionEgresado.setAttribute("Egresado", user);
                        response.sendRedirect("Views/HomeEgresados_UI.jsp");
                        break;
                    default:
                        break;
                }

            } else {
                mensajeUrl = "contrasenhaIncorrecta";
                response.sendRedirect("index.jsp?respuesta=" + mensajeUrl);
            }

        } else {
            mensajeUrl = "UserNoExistente";
            response.sendRedirect("index.jsp?respuesta=" + mensajeUrl);
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
