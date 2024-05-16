
function eliminar() {
    Swal.fire({
        title: "Desea eliminar",
        text: "¿Está seguro de eliminar este administrador?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, Eliminar!"
    }).then((result) => {
        if (result.isConfirmed) {
            // Obtener el ID del documento
            var documentoID = document.getElementById("Num_Cedula").value;
            // Redirigir automáticamente a la página de eliminación con el ID del documento
            window.location.href = "../CrudAdministradores?ID=" + documentoID + "&BtnAdmin=BtnEliminarAdmin";
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {

    // Selecciona todos los botones "Editar" que tienen ciertos atributos específicos.
    var editarButtons = document.querySelectorAll('i[data-bs-target="#ModalModificacion"][data-info*="TipoDoc"]');

    // Método que llenará nuestro ventana emergente con la información del votante
    function llenarModal(data) {
        // Llena los campos del modal con los datos del votante
        document.getElementById("Num_Cedula").value = data.Cedula;
        document.getElementById("Tipo_Doc").value = data.TipoDoc;
        document.getElementById("Nombre").value = data.Nombre;
        document.getElementById("Apellidos").value = data.Apellido;
        document.getElementById("Correo").value = data.correo;
        document.getElementById("Telefono").value = data.telefono;
    }

    // Itera sobre cada botón "Editar".
    editarButtons.forEach(function (button) {
        // Agrega un evento de clic a cada botón "Editar".
        button.addEventListener("click", function () {
            // Obtiene la información del votante desde el atributo "data-info" en formato JSON.
            // this.getAttribute("data-info") = De este boton al que se le dio click dame su data-info
            var data = JSON.parse(this.getAttribute("data-info"));
            // Con la data recibida del botón se llena el modal con su método
            llenarModal(data);
        });
    });
});
