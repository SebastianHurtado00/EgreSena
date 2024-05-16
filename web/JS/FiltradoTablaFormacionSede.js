/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


    // Identificadores de los elementos HTML
    const filtroInput1 = document.getElementById("filtro1");
    const filtroInput2 = document.getElementById("filtro2");
    const filasTabla1 = document.querySelectorAll("#tabla1 tbody tr");
    const filasTabla2 = document.querySelectorAll("#tabla2 tbody tr");

// Función de filtro para la tabla 1
    filtroInput1.addEventListener("input", function () {
        const filtro = filtroInput1.value.trim().toLowerCase();
        filasTabla1.forEach(function (fila) {
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });

// Función de filtro para la tabla 2
    filtroInput2.addEventListener("input", function () {
        const filtro = filtroInput2.value.trim().toLowerCase();
        filasTabla2.forEach(function (fila) {
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });