<%-- 
    Document   : obtener_correos
    Created on : 15/04/2024, 05:35:18 PM
    Author     : ASUS
--%>

<%@page import="com.google.gson.Gson"%>
<%@page import="Controladores.EgresadoJpaController"%>
<%@page import="Entidades.Egresado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener parámetros y realizar la lógica de negocio
    String sexoStr = request.getParameter("sexo");
    int sexo = 0; // Valor por defecto en caso de que no se pueda convertir
    if (sexoStr != null && !sexoStr.isEmpty()) {
        try {
            sexo = Integer.parseInt(sexoStr);
        } catch (NumberFormatException e) {
            // En caso de que no se pueda convertir a entero, mantener el valor por defecto
        }
    }

    String sedeStr = request.getParameter("sede");
    int sede = 0;
    if (sedeStr != null && !sedeStr.isEmpty()) {
        try {
            sede = Integer.parseInt(sedeStr);
        } catch (NumberFormatException e) {
            // En caso de que no se pueda convertir a entero, mantener el valor por defecto
        }
    }

    String formacionStr = request.getParameter("formacion");
    int formacion = 0;
    if (formacionStr != null && !formacionStr.isEmpty()) {
        try {
            formacion = Integer.parseInt(formacionStr);
        } catch (NumberFormatException e) {
            // En caso de que no se pueda convertir a entero, mantener el valor por defecto
        }
    }

    String experienciaStr = request.getParameter("experiencia");
    int experiencia = 0;
    if (experienciaStr != null && !experienciaStr.isEmpty()) {
        try {
            experiencia = Integer.parseInt(experienciaStr);
        } catch (NumberFormatException e) {
            // En caso de que no se pueda convertir a entero, mantener el valor por defecto
        }
    }

    String laborandoStr = request.getParameter("laborando");
    int laborando = 0;
    if (laborandoStr != null && !laborandoStr.isEmpty()) {
        try {
            laborando = Integer.parseInt(laborandoStr);
        } catch (NumberFormatException e) {
            // En caso de que no se pueda convertir a entero, mantener el valor por defecto
        }
    }

    String ciudadStr = request.getParameter("ciudad");
    int ciudad = 0;
    if (ciudadStr != null && !ciudadStr.isEmpty()) {
        try {
            ciudad = Integer.parseInt(ciudadStr);
        } catch (NumberFormatException e) {
            // En caso de que no se pueda convertir a entero, mantener el valor por defecto
        }
    }

    // Llamar al controlador para obtener los correos filtrados
    EgresadoJpaController controlEgresado = new EgresadoJpaController();
    List<String> correos = controlEgresado.obtenerCorreosFiltrados(sexo, sede, formacion, experiencia, laborando, ciudad);

    // Generar respuesta JSON
    String correosJson = new Gson().toJson(correos);
    out.print(correosJson);

%>
