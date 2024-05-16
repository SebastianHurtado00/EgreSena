/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// Función para mostrar la pantalla de carga
function showLoadingSpinner() {
    document.getElementById("loading-spinner").classList.remove("d-none");
}

// Función para ocultar la pantalla de carga
function hideLoadingSpinner() {
    document.getElementById("loading-spinner").classList.add("d-none");
}

// Agrega un listener al formulario para mostrar la pantalla de carga al enviar
document.getElementById("email-form").addEventListener("submit", function () {
    showLoadingSpinner();
});