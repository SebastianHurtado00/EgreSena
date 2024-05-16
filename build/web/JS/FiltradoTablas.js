/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const filtroInput = document.getElementById("filtro");
const filas = document.querySelectorAll("table tbody tr");
filtroInput.addEventListener("input", function () {
    const filtro = filtroInput.value.trim().toLowerCase();
    filas.forEach(function (fila) {
        const textoFila = fila.textContent.toLowerCase();
        if (textoFila.includes(filtro)) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
});
