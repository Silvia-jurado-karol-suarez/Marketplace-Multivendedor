// ======================================================
// CARRITO
// ======================================================

let carrito = [];


// ======================================================
// PRODUCTO SELECCIONADO PARA PROTOTYPE
// ======================================================

let productoPrototypeSeleccionado = null;


// ======================================================
// CARGAR PRODUCTOS DESDE MONGODB
// ======================================================

async function cargarProductos() {

    try {

        const respuesta =
            await fetch("/api/productos");

        if (!respuesta.ok) {

            throw new Error(
                "No se pudieron cargar los productos."
            );

        }

        const productos =
            await respuesta.json();


        const contenedor =
            document.getElementById(
                "listaProductos"
            );


        contenedor.innerHTML = "";


        if (productos.length === 0) {

            contenedor.innerHTML = `

                <div class="estado-vacio">

                    <span>📦</span>

                    <h3>
                        No hay productos disponibles
                    </h3>

                    <p>
                        Actualmente no existen productos
                        en el Marketplace.
                    </p>

                </div>

            `;

            productoPrototypeSeleccionado = null;

            actualizarProductoPrototype();

            return;
        }


        // ==================================================
        // SELECCIONAR PRODUCTO PARA PROTOTYPE
        // ==================================================

        /*
         * Tomamos el primer producto real de MongoDB.
         *
         * Si tu primer producto es "Camisa",
         * Prototype trabajará con esa camisa.
         */

        productoPrototypeSeleccionado =
            productos[0];


        actualizarProductoPrototype();


        // ==================================================
        // CREAR TARJETAS DE PRODUCTOS
        // ==================================================

        productos.forEach(
            producto => {

                const tarjeta =
                    document.createElement(
                        "article"
                    );


                tarjeta.className =
                    "producto-card";


                tarjeta.innerHTML = `

                    <div class="producto-imagen">

                        ${obtenerIcono(
                            producto.categoria
                        )}

                    </div>


                    <div class="producto-info">

                        <span class="producto-categoria">

                            ${producto.categoria ||
                              "Sin categoría"}

                        </span>


                        <h3>

                            ${producto.nombre}

                        </h3>


                        <p>

                            ${producto.descripcion ||
                              "Sin descripción"}

                        </p>


                        <div class="producto-footer">

                            <strong>

                                $${Number(
                                    producto.precio
                                ).toLocaleString(
                                    "es-CO"
                                )}

                            </strong>


                            <button
                                class="btn btn-principal btn-agregar"
                                ${producto.stock <= 0
                                    ? "disabled"
                                    : ""}
                            >

                                ${producto.stock <= 0
                                    ? "Agotado"
                                    : "Agregar"}

                            </button>

                        </div>

                    </div>

                `;


                // ==================================================
                // BOTÓN AGREGAR
                // ==================================================

                const boton =
                    tarjeta.querySelector(
                        ".btn-agregar"
                    );


                if (
                    producto.stock > 0
                ) {

                    boton.addEventListener(
                        "click",
                        function () {

                            agregarAlCarrito(
                                producto
                            );

                        }
                    );

                }


                contenedor.appendChild(
                    tarjeta
                );

            }
        );


    } catch (error) {

        console.error(
            "Error cargando productos:",
            error
        );


        const contenedor =
            document.getElementById(
                "listaProductos"
            );


        contenedor.innerHTML = `

            <div class="estado-vacio">

                <span>⚠️</span>

                <h3>
                    Error al cargar productos
                </h3>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// ACTUALIZAR PRODUCTO DEL PROTOTYPE
// ======================================================

function actualizarProductoPrototype() {

    const contenedor =
        document.getElementById(
            "productoPrototypeInfo"
        );


    if (!contenedor) {
        return;
    }


    if (
        !productoPrototypeSeleccionado
    ) {

        contenedor.innerHTML = `

            <span class="producto-categoria">
                PRODUCTO ORIGINAL
            </span>

            <h3>
                No hay productos disponibles
            </h3>

            <p>
                Crea primero un producto en el catálogo.
            </p>

        `;

        return;
    }


    const producto =
        productoPrototypeSeleccionado;


    contenedor.innerHTML = `

        <span class="producto-categoria">
            PRODUCTO ORIGINAL
        </span>

        <h3>
            ${producto.nombre}
        </h3>

        <p>
            ${producto.categoria || "Sin categoría"}
            ·
            $${Number(
                producto.precio
            ).toLocaleString("es-CO")}
        </p>

        <small>
            ID:
            ${producto.id}
        </small>

    `;

}


// ======================================================
// OBTENER ICONO SEGÚN CATEGORÍA
// ======================================================

function obtenerIcono(categoria) {

    switch (categoria) {

        case "Tecnología":
            return "💻";

        case "Hogar":
            return "🏠";

        case "Ropa":
            return "👕";

        case "Accesorios":
            return "🎧";

        default:
            return "📦";

    }

}


// ======================================================
// AGREGAR PRODUCTO AL CARRITO
// ======================================================

function agregarAlCarrito(producto) {

    const productoExistente =
        carrito.find(
            p => p.id === producto.id
        );


    // ==================================================
    // SI YA EXISTE
    // ==================================================

    if (productoExistente) {

        if (
            productoExistente.cantidad
            >= Number(producto.stock)
        ) {

            alert(
                "No puedes agregar más unidades. " +
                "Has alcanzado el stock disponible."
            );

            return;

        }


        productoExistente.cantidad++;

    }


    // ==================================================
    // SI ES NUEVO
    // ==================================================

    else {

        carrito.push({

            id:
                producto.id,

            nombre:
                producto.nombre,

            descripcion:
                producto.descripcion,

            precio:
                Number(producto.precio),

            categoria:
                producto.categoria,

            stock:
                Number(producto.stock),

            cantidad:
                1

        });

    }


    mostrarCarrito();

}


// ======================================================
// MOSTRAR CARRITO
// ======================================================

function mostrarCarrito() {

    const contenedor =
        document.getElementById(
            "carrito"
        );


    const cantidadProductos =
        document.getElementById(
            "cantidadProductos"
        );


    const totalElemento =
        document.getElementById(
            "total"
        );


    if (
        !contenedor ||
        !cantidadProductos ||
        !totalElemento
    ) {

        return;

    }


    // ==================================================
    // CARRITO VACÍO
    // ==================================================

    if (
        carrito.length === 0
    ) {

        contenedor.innerHTML = `

            <div class="estado-vacio">

                <span>
                    🛒
                </span>

                <h3>
                    Tu pedido está vacío
                </h3>

                <p>
                    Agrega productos desde
                    el catálogo.
                </p>

            </div>

        `;


        cantidadProductos.textContent =
            "0";


        totalElemento.textContent =
            "$0";


        return;

    }


    // ==================================================
    // CONSTRUIR CARRITO
    // ==================================================

    let html = "";

    let cantidadTotal = 0;

    let total = 0;


    carrito.forEach(
        (producto, index) => {

            const subtotal =
                producto.precio *
                producto.cantidad;


            cantidadTotal +=
                producto.cantidad;


            total +=
                subtotal;


            html += `

                <div class="producto-carrito">

                    <div>

                        <h3>
                            ${producto.nombre}
                        </h3>

                        <p>
                            $${producto.precio.toLocaleString(
                                "es-CO"
                            )}
                            por unidad
                        </p>

                    </div>


                    <div>

                        <button
                            onclick="disminuirProducto(${index})"
                        >
                            −
                        </button>


                        <strong>
                            ${producto.cantidad}
                        </strong>


                        <button
                            onclick="aumentarProducto(${index})"
                        >
                            +
                        </button>

                    </div>


                    <strong>

                        $${subtotal.toLocaleString(
                            "es-CO"
                        )}

                    </strong>


                    <button
                        onclick="eliminarDelCarrito(${index})"
                    >
                        Eliminar
                    </button>

                </div>

            `;

        }
    );


    contenedor.innerHTML =
        html;


    cantidadProductos.textContent =
        cantidadTotal;


    totalElemento.textContent =
        "$" +
        total.toLocaleString(
            "es-CO"
        );

}


// ======================================================
// AUMENTAR CANTIDAD
// ======================================================

function aumentarProducto(index) {

    const producto =
        carrito[index];


    if (!producto) {
        return;
    }


    if (
        producto.cantidad >=
        producto.stock
    ) {

        alert(
            "No hay más unidades disponibles."
        );

        return;

    }


    producto.cantidad++;

    mostrarCarrito();

}


// ======================================================
// DISMINUIR CANTIDAD
// ======================================================

function disminuirProducto(index) {

    const producto =
        carrito[index];


    if (!producto) {
        return;
    }


    if (
        producto.cantidad > 1
    ) {

        producto.cantidad--;

    } else {

        carrito.splice(
            index,
            1
        );

    }


    mostrarCarrito();

}


// ======================================================
// ELIMINAR PRODUCTO
// ======================================================

function eliminarDelCarrito(index) {

    if (
        index < 0 ||
        index >= carrito.length
    ) {

        return;

    }


    carrito.splice(
        index,
        1
    );


    mostrarCarrito();

}


// ======================================================
// CREAR PEDIDO
// ======================================================

async function crearPedido() {

    if (
        carrito.length === 0
    ) {

        alert(
            "El carrito está vacío."
        );

        return;

    }


    const productosIds =
        carrito.map(
            producto =>
                producto.id
        );


    try {

        const respuesta =
            await fetch(
                "/pedidos",
                {

                    method:
                        "POST",

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify({

                            productosIds:
                                productosIds

                        })

                }
            );


        if (
            !respuesta.ok
        ) {

            const mensaje =
                await respuesta.text();


            throw new Error(
                mensaje ||
                "No se pudo crear el pedido."
            );

        }


        const pedido =
            await respuesta.json();


        document.getElementById(
            "resultadoPedido"
        ).innerHTML = `

            <div class="resultado">

                <h3>
                    ✅ Pedido creado correctamente
                </h3>

                <p>
                    <strong>
                        ID del pedido:
                    </strong>

                    ${pedido.id}
                </p>

                <p>
                    <strong>
                        Total:
                    </strong>

                    $${Number(
                        pedido.total
                    ).toLocaleString(
                        "es-CO"
                    )}
                </p>

                <p>
                    <strong>
                        Estado:
                    </strong>

                    ${pedido.estado}
                </p>

            </div>

        `;


        localStorage.setItem(
            "pedidoActual",
            JSON.stringify(
                pedido
            )
        );


        const campoPedido =
            document.getElementById(
                "pedidoEnvio"
            );


        if (
            campoPedido
        ) {

            campoPedido.value =
                pedido.id;

        }


    } catch (error) {

        console.error(
            "Error creando pedido:",
            error
        );


        document.getElementById(
            "resultadoPedido"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ Error al crear el pedido.
                </p>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// SELECCIONAR MÉTODO DE PAGO
// FACTORY METHOD
// ======================================================

function seleccionarPago(metodo) {

    const select =
        document.getElementById(
            "metodoPago"
        );


    if (!select) {
        return;
    }


    select.value =
        metodo.toLowerCase();


    const resultado =
        document.getElementById(
            "resultadoPago"
        );


    if (resultado) {

        resultado.innerHTML = `

            <div class="resultado">

                <p>

                    Método seleccionado:

                    <strong>
                        ${metodo}
                    </strong>

                </p>

            </div>

        `;

    }

}


// ======================================================
// REALIZAR PAGO
// ======================================================

async function realizarPago() {

    const pedidoGuardado =
        localStorage.getItem(
            "pedidoActual"
        );


    if (!pedidoGuardado) {

        alert(
            "Primero debe crear un pedido."
        );

        return;

    }


    const pedido =
        JSON.parse(
            pedidoGuardado
        );


    const metodoElemento =
        document.getElementById(
            "metodoPago"
        );


    if (!metodoElemento) {

        alert(
            "No se encontró el método de pago."
        );

        return;

    }


    const metodo =
        metodoElemento.value;


    try {

        const respuesta =
            await fetch(
                "/pagos",
                {

                    method:
                        "POST",

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify({

                            pedidoId:
                                pedido.id,

                            metodo:
                                metodo,

                            monto:
                                pedido.total

                        })

                }
            );


        if (
            !respuesta.ok
        ) {

            const mensaje =
                await respuesta.text();


            throw new Error(
                mensaje ||
                "No se pudo procesar el pago."
            );

        }


        const pago =
            await respuesta.json();


        document.getElementById(
            "resultadoPago"
        ).innerHTML = `

            <div class="resultado">

                <h3>
                    ✅ Pago registrado
                </h3>

                <p>
                    <strong>
                        Método:
                    </strong>

                    ${pago.metodo}
                </p>

                <p>
                    <strong>
                        Monto:
                    </strong>

                    $${Number(
                        pago.monto
                    ).toLocaleString(
                        "es-CO"
                    )}
                </p>

                <p>
                    <strong>
                        Estado:
                    </strong>

                    ${pago.estado}
                </p>

            </div>

        `;


    } catch (error) {

        console.error(
            "Error procesando pago:",
            error
        );


        document.getElementById(
            "resultadoPago"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ No fue posible procesar el pago.
                </p>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// CALCULAR ENVÍO
// ABSTRACT FACTORY
// ======================================================

async function calcularEnvio() {

    const tipoElemento =
        document.getElementById(
            "tipoEnvioFactory"
        );


    const pedidoElemento =
        document.getElementById(
            "pedidoEnvio"
        );


    if (!tipoElemento) {

        alert(
            "No se encontró el tipo de envío."
        );

        return;

    }


    if (!pedidoElemento) {

        alert(
            "No se encontró el campo del pedido."
        );

        return;

    }


    const tipo =
        tipoElemento.value;


    const pedido =
        pedidoElemento.value.trim();


    const peso = 1;


    if (!pedido) {

        alert(
            "Ingrese el ID del pedido."
        );

        return;

    }


    try {

        const respuesta =
            await fetch(
                `/envios/${tipo}/${peso}/${pedido}`
            );


        if (
            !respuesta.ok
        ) {

            throw new Error(
                "No se pudo calcular el envío."
            );

        }


        const resultado =
            await respuesta.text();


        document.getElementById(
            "resultadoEnvio"
        ).innerHTML = `

            <div class="resultado">

                <h3>
                    📦 Información del envío
                </h3>

                <p>
                    ${resultado}
                </p>

            </div>

        `;


    } catch (error) {

        console.error(
            "Error calculando envío:",
            error
        );


        document.getElementById(
            "resultadoEnvio"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ Error al calcular el envío.
                </p>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// SINGLETON
// ======================================================

async function probarSingleton() {

    try {

        const respuesta =
            await fetch(
                "/configuracion"
            );


        if (
            !respuesta.ok
        ) {

            throw new Error(
                "Endpoint no disponible."
            );

        }


        const resultado =
            await respuesta.text();


        document.getElementById(
            "resultadoSingleton"
        ).innerHTML = `

            <div class="resultado">

                ${resultado}

            </div>

        `;


    } catch (error) {

        console.error(
            "Error en Singleton:",
            error
        );


        document.getElementById(
            "resultadoSingleton"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ No se pudo consultar
                    la configuración.
                </p>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// PROTOTYPE
// ======================================================

async function duplicarProducto() {

    // ==================================================
    // VALIDAR PRODUCTO SELECCIONADO
    // ==================================================

    if (
        !productoPrototypeSeleccionado
    ) {

        alert(
            "No hay ningún producto disponible " +
            "para duplicar."
        );

        return;

    }


    const id =
        productoPrototypeSeleccionado.id;


    if (!id) {

        alert(
            "El producto seleccionado no tiene ID."
        );

        return;

    }


    // ==================================================
    // DESACTIVAR BOTÓN MIENTRAS PROCESA
    // ==================================================

    const boton =
        document.getElementById(
            "btnDuplicarProducto"
        );


    if (boton) {

        boton.disabled = true;

        boton.textContent =
            "Duplicando...";

    }


    try {

        const respuesta =
            await fetch(
                `/prototype/duplicar/${id}`,
                {

                    method:
                        "POST"

                }
            );


        if (
            !respuesta.ok
        ) {

            let mensaje =
                "No se pudo duplicar el producto.";


            try {

                const texto =
                    await respuesta.text();


                if (texto) {
                    mensaje = texto;
                }

            } catch (e) {
                // Mantener mensaje original.
            }


            throw new Error(
                mensaje
            );

        }


        const producto =
            await respuesta.json();


        // ==================================================
        // MOSTRAR RESULTADO
        // ==================================================

        document.getElementById(
            "resultadoPrototype"
        ).innerHTML = `

            <div class="resultado">

                <h3>
                    ✅ Producto duplicado correctamente
                </h3>

                <p>
                    <strong>
                        Producto original:
                    </strong>

                    ${productoPrototypeSeleccionado.nombre}
                </p>

                <p>
                    <strong>
                        Nuevo producto:
                    </strong>

                    ${producto.nombre}
                </p>

                <p>
                    <strong>
                        Nuevo ID:
                    </strong>

                    ${producto.id}
                </p>

                <p>
                    <strong>
                        Precio:
                    </strong>

                    $${Number(
                        producto.precio
                    ).toLocaleString(
                        "es-CO"
                    )}
                </p>

                <p>
                    <strong>
                        Categoría:
                    </strong>

                    ${producto.categoria ||
                    "Sin categoría"}
                </p>

            </div>

        `;


        // ==================================================
        // RECARGAR CATÁLOGO
        // ==================================================

        await cargarProductos();


    } catch (error) {

        console.error(
            "Error en Prototype:",
            error
        );


        document.getElementById(
            "resultadoPrototype"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ ${error.message}
                </p>

            </div>

        `;

    } finally {

        if (boton) {

            boton.disabled = false;

            boton.textContent =
                "Duplicar producto";

        }

    }

}


// ======================================================
// ADAPTER
// ======================================================

async function probarAdapter() {

    const elemento =
        document.getElementById(
            "montoAdapter"
        );


    if (!elemento) {
        return;
    }


    const monto =
        elemento.value;


    if (
        !monto ||
        Number(monto) <= 0
    ) {

        alert(
            "Ingrese un monto válido."
        );

        return;

    }


    try {

        const respuesta =
            await fetch(
                `/adapter/pagar?monto=${encodeURIComponent(
                    monto
                )}`
            );


        if (
            !respuesta.ok
        ) {

            throw new Error(
                "No se pudo procesar la transacción."
            );

        }


        const resultado =
            await respuesta.text();


        document.getElementById(
            "resultadoAdapter"
        ).innerHTML = `

            <div class="resultado">

                ${resultado}

            </div>

        `;


    } catch (error) {

        console.error(
            "Error en Adapter:",
            error
        );


        document.getElementById(
            "resultadoAdapter"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ Error al utilizar Adapter.
                </p>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// BRIDGE
// ======================================================

async function probarBridge(canal) {

    try {

        const respuesta =
            await fetch(
                `/bridge/notificar?canal=${encodeURIComponent(
                    canal
                )}`
            );


        if (
            !respuesta.ok
        ) {

            throw new Error(
                "No se pudo enviar la notificación."
            );

        }


        const resultado =
            await respuesta.text();


        document.getElementById(
            "resultadoBridge"
        ).innerHTML = `

            <div class="resultado">

                <h3>
                    📢 Notificación enviada
                </h3>

                <p>
                    <strong>
                        Canal:
                    </strong>

                    ${canal}
                </p>

                <p>
                    ${resultado}
                </p>

            </div>

        `;


    } catch (error) {

        console.error(
            "Error en Bridge:",
            error
        );


        document.getElementById(
            "resultadoBridge"
        ).innerHTML = `

            <div class="resultado">

                <p>
                    ❌ Error al enviar
                    la notificación.
                </p>

                <p>
                    ${error.message}
                </p>

            </div>

        `;

    }

}


// ======================================================
// INICIAR PÁGINA
// ======================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        cargarProductos();

        mostrarCarrito();

    }
);
