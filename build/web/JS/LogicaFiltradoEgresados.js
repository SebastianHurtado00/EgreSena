/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$(document).ready(function () {
    $("#obtenerCorreosButton").click(function () {
        $.ajax({
            url: "../obtener_correos.jsp",
            type: "GET",
            data: {
                sexo: $("#sexo").val(),
                sede: $("#sede").val(),
                formacion: $("#formacion").val(),
                experiencia: $("#Experiencia").val(),
                laborando: $("#Laborando").val(),
                ciudad: $("#Ciudad").val()
            },
            success: function (data) {
                /// Handle the response from the server
                var emailsArray = JSON.parse(data); // Parsea el JSON en un array
                var asunto = $("#asunto").val();
                var cuerpo = $("#cuerpo").val();
                // Almacena los correos en un array
                var correos = [];
                for (var i = 0; i < emailsArray.length; i++) {
                    correos.push(emailsArray[i]);
                }

                // Puedes utilizar el array 'correos' como desees, por ejemplo, enviarlo a otra función
                $("#listaCorreos").val(correos.join(', '));
                console.log("Correos obtenidos:", correos);
                console.log("Asunto:", asunto);
                console.log("Cuerpo:", cuerpo);

            },
            error: function (xhr, status, error) {
                // Maneja el error
                console.log(error)
            }
        });
    });
});



function eliminar() {
    Swal.fire({
        title: "Desea eliminar",
        text: "¿Está seguro de eliminar este Egresado?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, Eliminar!"
    }).then((result) => {
        if (result.isConfirmed) {
            // Obtener el ID del documento
            var documentoID = document.getElementById("numeroDocumentoModal").value;
            // Redirigir automáticamente a la página de eliminación con el ID del documento
            window.location.href = "../ModificacionEgresado?ID=" + documentoID + "&BtnEgresados=eliminar";
        }
    });
}

// DOMContentLoaded Este evento se dispara cuando el documento HTML ha sido completamente cargado y analizado.
document.addEventListener("DOMContentLoaded", function () {

    // Selecciona todos los botones "Editar" que tienen ciertos atributos específicos.
    var editarButtons = document.querySelectorAll('i[data-bs-target="#ModalModificacion"][data-info*="Cedula"]');

    //Creamos un metodo que llenara nuestro ventana emergente con la informacion del votante
    function llenarModal(data) {
        // Llena los campos del modal con los datos del votante
        document.getElementById("numeroDocumentoModal").value = data.Cedula;
        document.getElementById("nombresModal").value = data.nombre;
        document.getElementById("apellidos").value = data.apellido;
        document.getElementById("tipoDocumentoModal").value = data.tipoDocumento;
        document.getElementById("sexoModal").value = data.sexo;
        document.getElementById("telefonoModal").value = data.telefono;
        document.getElementById("correoModal").value = data.correo;
    }

    // Itera sobre cada botón "Editar".
    editarButtons.forEach(function (button) {
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


