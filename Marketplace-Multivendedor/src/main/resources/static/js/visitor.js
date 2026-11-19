const API_VISITOR = "/api/visitor/reporte";

let reportes = [];
let filtroActual = "TODOS";

// Ejecutar al cargar la página
document.addEventListener("DOMContentLoaded", () => {
    cargarReporte();

    document.querySelectorAll(".filtro").forEach(boton => {
        boton.addEventListener("click", () => {
            document.querySelectorAll(".filtro").forEach(item => {
                item.classList.remove("activo");
            });

            boton.classList.add("activo");

            filtroActual = boton.dataset.filtro;

            mostrarResultados();
        });
    });
});

// Consultar el reporte del backend
async function cargarReporte() {

    const boton = document.getElementById("btnActualizar");

    boton.disabled = true;
    boton.textContent = "Consultando...";

    mostrarMensaje("Consultando información de MongoDB...", "");

    try {

        const respuesta = await fetch(API_VISITOR);

        if (!respuesta.ok) {
            const detalle = await respuesta.text();

            throw new Error(
                "Error " + respuesta.status + ": " + detalle
            );
        }

        const datos = await respuesta.json();

        // Actualizar estadísticas
        document.getElementById("totalProductos").textContent =
            datos.totalProductos ?? 0;

        document.getElementById("totalPedidos").textContent =
            datos.totalPedidos ?? 0;

        document.getElementById("totalVendedores").textContent =
            datos.totalVendedores ?? 0;

        document.getElementById("totalElementos").textContent =
            datos.totalElementos ?? 0;

        // Guardar resultados
        reportes = Array.isArray(datos.resultados)
            ? datos.resultados
            : [];

        mostrarResultados();

        mostrarMensaje(
            "Reporte generado correctamente. Se encontraron " +
            reportes.length + " registros.",
            "exito"
        );

    } catch (error) {

        mostrarMensaje(
            "No fue posible cargar el reporte: " + error.message,
            "error"
        );

        document.getElementById("resultados").innerHTML =
            '<p class="vacio">No se pudieron cargar los resultados.</p>';

    } finally {

        boton.disabled = false;
        boton.textContent = "↻ Actualizar reporte";
    }
}

// Organizar y mostrar los resultados
function mostrarResultados() {

    const contenedor = document.getElementById("resultados");

    contenedor.innerHTML = "";

    const filtrados = reportes.filter(registro => {

        if (filtroActual === "TODOS") {
            return true;
        }

        return registro.startsWith(filtroActual + " |");
    });

    if (filtrados.length === 0) {

        contenedor.innerHTML =
            '<p class="vacio">No hay registros para esta categoría.</p>';

        return;
    }

    filtrados.forEach(registro => {

        const partes = registro.split("|");

        const tipo = partes[0].trim();

        const tarjeta = document.createElement("article");
        tarjeta.className = "resultado-card";

        const etiqueta = document.createElement("span");
        etiqueta.className = "tipo";

        if (tipo === "PRODUCTO") {
            etiqueta.classList.add("tipo-producto");
        } else if (tipo === "PEDIDO") {
            etiqueta.classList.add("tipo-pedido");
        } else {
            etiqueta.classList.add("tipo-vendedor");
        }

        etiqueta.textContent = tipo;

        const titulo = document.createElement("h3");
        titulo.textContent = obtenerTitulo(registro, tipo);

        const informacion = document.createElement("p");
        informacion.textContent = registro;

        tarjeta.appendChild(etiqueta);
        tarjeta.appendChild(titulo);
        tarjeta.appendChild(informacion);

        contenedor.appendChild(tarjeta);
    });
}

// Obtener un título para cada registro
function obtenerTitulo(registro, tipo) {

    const coincidencia = registro.match(/\bNombre:\s*([^|]+)/);

    if (coincidencia) {
        return coincidencia[1].trim();
    }

    const id = registro.match(/\bID:\s*([^|]+)/);

    if (id) {
        return tipo + " #" + id[1].trim();
    }

    return tipo;
}

// Mostrar mensajes
function mostrarMensaje(texto, tipo) {

    const mensaje = document.getElementById("mensaje");

    mensaje.textContent = texto;
    mensaje.className = "mensaje";

    if (tipo) {
        mensaje.classList.add(tipo);
    }
}