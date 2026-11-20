
async function publicarConTemplateMethod() {
    const tipo = document.getElementById("tipoTemplateMethod").value;
    const resultado = document.getElementById("resultadoTemplateMethod");

    const procesos = {
        fisico: [
            {
                titulo: "1. Validar producto",
                descripcion: "Se verifica el nombre, el precio y el stock disponible."
            },
            {
                titulo: "2. Preparar producto",
                descripcion: "Se prepara la información y se calcula la disponibilidad para el envío."
            },
            {
                titulo: "3. Guardar producto",
                descripcion: "Se guarda el producto físico en el Marketplace."
            },
            {
                titulo: "4. Notificar publicación",
                descripcion: "Se confirma que el producto físico fue publicado correctamente."
            }
        ],
        digital: [
            {
                titulo: "1. Validar producto",
                descripcion: "Se verifica el nombre, el precio y el archivo digital."
            },
            {
                titulo: "2. Preparar producto",
                descripcion: "Se prepara la información y se configura el acceso para el comprador."
            },
            {
                titulo: "3. Guardar producto",
                descripcion: "Se guarda el producto digital en el Marketplace."
            },
            {
                titulo: "4. Notificar publicación",
                descripcion: "Se confirma que el producto digital fue publicado correctamente."
            }
        ]
    };

    resultado.innerHTML = `
        <p>Ejecutando el proceso de publicación...</p>
    `;

    try {
        const respuesta = await fetch(
            `/api/template-method/publicar?tipo=${encodeURIComponent(tipo)}`
        );

        if (!respuesta.ok) {
            throw new Error("No se pudo ejecutar la publicación.");
        }

        const mensaje = await respuesta.text();
        const pasos = procesos[tipo];

        resultado.innerHTML = `
            <div class="template-result">
                <h3>Proceso de publicación completado</h3>
                <p>${mensaje}</p>

                <h4>Secuencia del patrón Template Method</h4>

                <div class="template-steps">
                    ${pasos.map((paso, index) => `
                        <div class="template-step">
                            <div class="template-step-number">${index + 1}</div>
                            <div>
                                <strong>${paso.titulo}</strong>
                                <p>${paso.descripcion}</p>
                            </div>
                        </div>
                    `).join("")}
                </div>

                <div class="template-explanation">
                    <strong>¿Dónde se evidencia el patrón?</strong>
                    <p>
                        La clase PublicacionProductoTemplate define
                        el orden de los cuatro pasos mediante el método
                        publicarProducto(). Las clases de producto físico
                        y digital implementan esos pasos de manera diferente.
                    </p>
                </div>
            </div>
        `;

    } catch (error) {
        console.error("Error en Template Method:", error);
        resultado.textContent = error.message;
    }
}
