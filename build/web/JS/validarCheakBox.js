/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function validarCheckboxes() {
    var siCheckboxExp = document.getElementById("siCheckboxExp");
    var noCheckboxExp = document.getElementById("noCheckboxExp");
    var siCheckboxUni = document.getElementById("siCheckboxUni");
    var noCheckboxUni = document.getElementById("noCheckboxUni");
    var siCheckboxCadena = document.getElementById("siCheckbox");
    var noCheckboxCadena = document.getElementById("noCheckbox");
    var siCheckboxTraba = document.getElementById("siCheckboxTraba");
    var noCheckboxTraba = document.getElementById("noCheckboxTraba");
    var submitButton = document.querySelector("[name='BtnEgresados']");

    // Verificar si al menos uno de los checkboxes de cada grupo está seleccionado
    var experienciaLaboralSeleccionada = siCheckboxExp.checked || noCheckboxExp.checked;
    var carreraUniversitariaSeleccionada = siCheckboxUni.checked || noCheckboxUni.checked;
    var cadenaFormacionSeleccionada = siCheckboxCadena.checked || noCheckboxCadena.checked;
    var trabajandoSeleccionado = siCheckboxTraba.checked || noCheckboxTraba.checked;

    // Habilitar el botón de enviar si al menos uno de los checkboxes de cada grupo está seleccionado
    submitButton.disabled = !(experienciaLaboralSeleccionada && carreraUniversitariaSeleccionada && cadenaFormacionSeleccionada && trabajandoSeleccionado);
}


function uncheckCheckbox(checkboxId) {
    // Desmarca el otro checkbox
    document.getElementById(checkboxId).checked = false;
}