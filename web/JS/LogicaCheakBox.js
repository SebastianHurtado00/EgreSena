/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function mostrarOcultarInput(siSeleccionado, container, siCheck, noCheck) {

    var inputContainer = document.getElementById(container);
    inputContainer.style.display = siSeleccionado ? 'block' : 'none';

    // Si el checkbox "No" está seleccionado, deselecciona el checkbox "Sí"
    if (!siSeleccionado) {
        document.getElementById(siCheck).checked = false;
        console.log(document.getElementById(siCheck).checked.value)
    } else {
        document.getElementById(noCheck).checked = false;
    }
}