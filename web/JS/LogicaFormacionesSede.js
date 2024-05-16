/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//Formaciones Tratamiento de Modal
// DOMContentLoaded Este evento se dispara cuando el documento HTML ha sido completamente cargado y analizado.
document.addEventListener("DOMContentLoaded", function () {

    // Selecciona todos los botones "Editar" que tienen ciertos atributos específicos.
    var editarButtonsFormacion = document.querySelectorAll('i[data-bs-target="#ModalModificarFormaciones"][data-info*="IdFormacion"]');

    //Creamos un metodo que llenara nuestro ventana emergente con la informacion del votante
    function llenarModal(data) {
        // Llena los campos del modal con los datos del votante
        document.getElementById("IdFormacionModificar").value = data.IdFormacion;
        document.getElementById("NombreFormacionModificar").value = data.NombreFormacion;
        document.getElementById("SedePertenecienteModificar").value = data.SedePerteneciente;
    }

    // Itera sobre cada botón "Editar".
    editarButtonsFormacion.forEach(function (button) {
        // Agrega un evento de clic a cada botón "Editar".
        button.addEventListener("click", function () {
            // Obtiene la información del votante desde el atributo "data-info" en formato JSON.
            //RECORDAR JS sabe la data-info porque este esta realizando el evento en cada boton editar y obtendra la info del boton al que se le de click
            // this.getAttribute("data-info") = De este boton al que se le dio click dame su data-info
            var data = JSON.parse(this.getAttribute("data-info"));
            //Con la data recibida del boton se llena el modal con su metodo
            llenarModal(data);
        });
    });
});

//Sede Tratamiento de Modal
// DOMContentLoaded Este evento se dispara cuando el documento HTML ha sido completamente cargado y analizado.
document.addEventListener("DOMContentLoaded", function () {

    // Selecciona todos los botones "Editar" que tienen ciertos atributos específicos.
    var editarButtonsSede = document.querySelectorAll('i[data-bs-target="#ModalRegistroModificar"][data-info*="IdSede"]');

    //Creamos un metodo que llenara nuestro ventana emergente con la informacion del votante
    function llenarModal(data) {
        // Llena los campos del modal con los datos del votante
        document.getElementById("IdModificarSede").value = data.IdSede;
        document.getElementById("NombreSedeModificar").value = data.NombreSede;

    }

    // Itera sobre cada botón "Editar".
    editarButtonsSede.forEach(function (button) {
        // Agrega un evento de clic a cada botón "Editar".
        button.addEventListener("click", function () {
            // Obtiene la información del votante desde el atributo "data-info" en formato JSON.
            //RECORDAR JS sabe la data-info porque este esta realizando el evento en cada boton editar y obtendra la info del boton al que se le de click
            // this.getAttribute("data-info") = De este boton al que se le dio click dame su data-info
            var data = JSON.parse(this.getAttribute("data-info"));
            //Con la data recibida del boton se llena el modal con su metodo
            llenarModal(data);
        });
    });
});