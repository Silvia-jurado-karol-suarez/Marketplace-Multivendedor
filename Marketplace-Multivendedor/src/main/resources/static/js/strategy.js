
// PATRÓN STRATEGY: comparación de estrategias de descuento
// Marketplace Multivendedor

async function calcularStrategy() {

    const tipoCampo = document.getElementById("tipoEstrategia");
    const valorCampo = document.getElementById("valorEstrategia");
    const resultado = document.getElementById("resultadoStrategy");

    const tipo = tipoCampo.value;
    const subtotal = calcularSubtotal();

    let valor = Number(valorCampo.value);

    if (tipo === "ninguno") {
        valor = 0;
    }

    if (!Number.isFinite(subtotal) || subtotal < 0) {
        resultado.textContent = "No se pudo obtener un subtotal válido.";
        return;
    }

    if (!Number.isFinite(valor) || valor < 0) {
        resultado.textContent = "Ingresa un valor de descuento válido.";
        return;
    }

    if (tipo === "porcentaje" && valor > 100) {
        resultado.textContent = "El porcentaje debe estar entre 0 y 100.";
        return;
    }

    resultado.innerHTML = "<p>Calculando estrategias...</p>";

    try {

        const estrategias = [
            {
                tipo: "porcentaje",
                valor: tipo === "porcentaje" ? valor : 10,
                nombre: "Descuento porcentual"
            },
            {
                tipo: "fijo",
                valor: tipo === "fijo" ? valor : 5000,
                nombre: "Descuento fijo"
            },
            {
                tipo: "ninguno",
                valor: 0,
                nombre: "Sin descuento"
            }
        ];

        const formatoMoneda = new Intl.NumberFormat("es-CO", {
            style: "currency",
            currency: "COP",
            maximumFractionDigits: 0
        });

        const resultados = await Promise.all(
            estrategias.map(async estrategia => {

                const parametros = new URLSearchParams({
                    tipo: estrategia.tipo,
                    subtotal: subtotal,
                    valor: estrategia.valor
                });

                const respuesta = await fetch(
                    `/api/strategy/calcular?${parametros.toString()}`
                );

                if (!respuesta.ok) {
                    throw new Error(
                        `Error al ejecutar ${estrategia.nombre}`
                    );
                }

                const datos = await respuesta.json();

                return {
                    ...datos,
                    tipo: estrategia.tipo,
                    nombre: estrategia.nombre,
                    seleccionada: estrategia.tipo === tipo
                };
            })
        );

        const seleccionada = resultados.find(
            estrategia => estrategia.seleccionada
        );

        resultado.innerHTML = `

            <div class="strategy-result">

                <div class="strategy-heading">
                    <h3>Comparación de estrategias</h3>
                    <p>
                        El patrón Strategy permite utilizar diferentes
                        algoritmos de descuento mediante una misma interfaz.
                    </p>
                </div>

                <div class="strategy-cards">

                    ${resultados.map(estrategia => `

                        <div class="strategy-card
                            ${estrategia.seleccionada ? "strategy-selected" : ""}">

                            <span class="strategy-label">
                                ${estrategia.seleccionada
                                    ? "ESTRATEGIA SELECCIONADA"
                                    : "ESTRATEGIA DISPONIBLE"}
                            </span>

                            <h4>${estrategia.nombre}</h4>

                            <p class="strategy-discount">
                                -${formatoMoneda.format(estrategia.descuento)}
                            </p>

                            <div class="strategy-detail">
                                <span>Subtotal</span>
                                <strong>
                                    ${formatoMoneda.format(estrategia.subtotal)}
                                </strong>
                            </div>

                            <div class="strategy-detail">
                                <span>Descuento</span>
                                <strong>
                                    ${formatoMoneda.format(estrategia.descuento)}
                                </strong>
                            </div>

                            <div class="strategy-total">
                                <span>Total a pagar</span>
                                <strong>
                                    ${formatoMoneda.format(estrategia.total)}
                                </strong>
                            </div>

                        </div>

                    `).join("")}

                </div>

                <div class="strategy-final">

                    <h3>Resultado de la estrategia elegida</h3>

                    <p>
                        <strong>${seleccionada.nombre}</strong>
                        fue aplicada mediante el contexto de descuentos.
                    </p>

                    <div class="strategy-final-total">
                        ${formatoMoneda.format(seleccionada.total)}
                    </div>

                </div>

                <div class="strategy-explanation">

                    <h4>¿Cómo se evidencia el patrón Strategy?</h4>

                    <p>
                        <strong>Estrategia:</strong>
                        cada clase implementa una forma diferente de calcular
                        el descuento.
                    </p>

                    <p>
                        <strong>Contexto:</strong>
                        DescuentoContexto utiliza la estrategia seleccionada
                        sin necesitar cambiar su lógica de cálculo.
                    </p>

                    <p>
                        <strong>Intercambio:</strong>
                        se puede cambiar entre descuento porcentual, fijo
                        y sin descuento utilizando el mismo proceso.
                    </p>

                </div>

            </div>
        `;

    } catch (error) {

        console.error("Error en Strategy:", error);

        resultado.textContent =
            "Error al calcular las estrategias: " + error.message;
    }
}

function actualizarTipoStrategy(tipo) {

    const selector = document.getElementById("tipoEstrategia");
    const campo = document.getElementById("valorEstrategia");
    const ayuda = document.getElementById("strategyHint");

    selector.value = tipo;

    if (tipo === "porcentaje") {
        campo.disabled = false;
        campo.value = 10;
        campo.placeholder = "Ejemplo: 10";
        ayuda.textContent = "Ingresa el porcentaje que deseas aplicar.";
    } else if (tipo === "fijo") {
        campo.disabled = false;
        campo.value = 5000;
        campo.placeholder = "Ejemplo: 5000";
        ayuda.textContent = "Ingresa el valor fijo que deseas descontar.";
    } else {
        campo.value = 0;
        campo.disabled = true;
        ayuda.textContent = "Esta estrategia no aplica descuentos.";
    }
}
