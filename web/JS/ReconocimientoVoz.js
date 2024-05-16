/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function voz() {
    if ('SpeechRecognition' in window || 'webkitSpeechRecognition' in window) {
        const recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
        const filtroInput = document.getElementById('filtro');

        recognition.lang = 'es-ES';

        recognition.onresult = function (event) {
            const transcript = event.results[0][0].transcript;
            const cleanTranscript = quitarTildesComasYPuntos(transcript); // Llamar a la función para quitar tildes y comas
            filtroInput.value = cleanTranscript.toLowerCase();

            // Simular un evento de entrada para activar la búsqueda
            const inputEvent = new Event('input', {bubbles: true});
            filtroInput.dispatchEvent(inputEvent);
        };

        recognition.onstart = function () {
            // Puedes realizar acciones adicionales si lo deseas
        };

        recognition.onerror = function (event) {
            // Manejo de errores, si es necesario
        };

        // Evitar recarga de la página al presionar Enter
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
            }
        });

        // Iniciar reconocimiento de voz al hacer clic en el botón
        const startBtn = document.getElementById('start-btn');
        startBtn.addEventListener('click', function () {
            recognition.start();
        });
    } else {
        alert('El reconocimiento de voz no es compatible con este navegador.');
    }
}
function quitarTildesComasYPuntos(texto) {
    // Reemplazar tildes, comas y puntos, excepto la Ñ
    return texto
            .normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '')
            .replace(/[,\.\u2026]/g, '')
            .replace(/ñ/gi, 'n') // Conservar la Ñ
            .replace(/ñ/gi, 'ni') // Aproximación para la Ñ
            .replace(/Ñ/g, 'Ni'); // Aproximación para la Ñ mayúscula
}

function busqueda() {
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

}