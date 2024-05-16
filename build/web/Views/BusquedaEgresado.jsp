<%-- 
    Document   : BusquedaEgresado
    Created on : 10/04/2024, 01:55:41 PM
    Author     : ASUS
--%>

<%@page import="Entidades.Usuarios"%>
<%@page import="Controladores.UsuariosJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    String cedulaStr = request.getParameter("cedula");
    if (cedulaStr != null) {

        UsuariosJpaController controlador = new UsuariosJpaController();
        int cedula = Integer.parseInt(cedulaStr);
        String CC = "cedula";
        Usuarios ususarioEncontrado = controlador.findUsuarios(cedula);
        if (ususarioEncontrado == null || ususarioEncontrado.getRol() != 2) {
            out.print("<td>No encontrado </td>");
            out.print("<td name " + CC + "></td>");
            out.print("<td></td>");
            out.print("<td></td>");
        } else {
            out.print("<td>" + ususarioEncontrado.getTipoDocID().getNombre() + "</td>");
            out.print("<td name = " + CC + "  > " + ususarioEncontrado.getCedula() + "</td>");
            out.print("<td>" + ususarioEncontrado.getNombre() + "</td>");
            out.print("<td>" + ususarioEncontrado.getApellido() + "</td>");
        }
    }
%>