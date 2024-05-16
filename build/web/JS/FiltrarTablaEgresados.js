function filtrarTabla() {
    var selectSexo = document.getElementById("sexo");
    var selectSede = document.getElementById("sede");
    var selectFormacion = document.getElementById("formacion");
    var selectExperiencia = document.getElementById("Experiencia");
    var selectLaborando = document.getElementById("Laborando");
    var selectCiudad = document.getElementById("Ciudad");

    var rows = document.querySelectorAll("#miTabla tbody tr");

    rows.forEach(function (row) {
        var mostrar = true;

        // Filtrar por sexo
        if (selectSexo.value !== "" && row.cells[4].innerText !== selectSexo.options[selectSexo.selectedIndex].innerText) {
            mostrar = false;
        }

        // Filtrar por sede
        if (selectSede.value !== "" && row.cells[6].innerText !== selectSede.options[selectSede.selectedIndex].innerText) {
            mostrar = false;
        }

        // Filtrar por formación
        if (selectFormacion.value !== "" && row.cells[7].innerText !== selectFormacion.options[selectFormacion.selectedIndex].innerText) {
            mostrar = false;
        }

        // Filtrar por experiencia
        if (selectExperiencia.value !== "" && (selectExperiencia.value === "1" && row.cells[11].innerText.trim().toLowerCase() !== "sí" || selectExperiencia.value === "0" && row.cells[11].innerText.trim().toLowerCase() !== "no")) {
            mostrar = false;
        }

// Filtrar por trabajando
        if (selectLaborando.value !== "" && (selectLaborando.value === "1" && row.cells[12].innerText.trim().toLowerCase() !== "sí" || selectLaborando.value === "0" && row.cells[12].innerText.trim().toLowerCase() !== "no")) {
            mostrar = false;
        }

        // Filtrar por ciudad
        if (selectCiudad.value !== "" && row.cells[8].innerText !== selectCiudad.options[selectCiudad.selectedIndex].innerText) {
            mostrar = false;
        }

        // Mostrar u ocultar la fila según el resultado del filtrado
        if (mostrar) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}
