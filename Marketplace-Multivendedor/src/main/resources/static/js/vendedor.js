const API_PRODUCTOS = "/api/productos";


// ==========================================
// CUANDO CARGA LA PÁGINA
// ==========================================

document.addEventListener("DOMContentLoaded", function () {

    cargarProductos();

});


// ==========================================
// CREAR PRODUCTO
// ==========================================

document
    .getElementById("formProducto")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const producto = {

            nombre:
                document.getElementById("nombre").value,

            descripcion:
                document.getElementById("descripcion").value,

            precio:
                Number(
                    document.getElementById("precio").value
                ),

            stock:
                Number(
                    document.getElementById("stock").value
                ),

            categoria:
                document.getElementById("categoria").value,

            vendedor: null

        };


        try {

            const respuesta = await fetch(
                API_PRODUCTOS,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(producto)
                }
            );


            if (!respuesta.ok) {

                throw new Error(
                    "No se pudo crear el producto"
                );

            }


            const productoCreado =
                await respuesta.json();


            document.getElementById(
                "mensajeProducto"
            ).innerHTML = `

                <div class="mensaje-exito">

                    Producto creado correctamente.

                    <br>

                    ID:
                    ${productoCreado.id}

                </div>

            `;


            document
                .getElementById("formProducto")
                .reset();


            cargarProductos();


        } catch (error) {

            document.getElementById(
                "mensajeProducto"
            ).innerHTML = `

                <div class="mensaje-error">

                    ${error.message}

                </div>

            `;

        }

    });


// ==========================================
// LISTAR PRODUCTOS
// ==========================================

async function cargarProductos() {

    try {

        const respuesta =
            await fetch(API_PRODUCTOS);


        if (!respuesta.ok) {

            throw new Error(
                "No se pudieron cargar los productos"
            );

        }


        const productos =
            await respuesta.json();


        mostrarProductos(productos);


    } catch (error) {

        document.getElementById(
            "listaProductos"
        ).innerHTML = `

            <div class="mensaje-error">

                ${error.message}

            </div>

        `;

    }

}


// ==========================================
// MOSTRAR PRODUCTOS
// ==========================================

function mostrarProductos(productos) {

    const contenedor =
        document.getElementById("listaProductos");


    if (productos.length === 0) {

        contenedor.innerHTML = `

            <div class="vacio">

                Todavía no hay productos registrados.

            </div>

        `;

        return;
    }


    contenedor.innerHTML = "";


    productos.forEach(producto => {

        const tarjeta =
            document.createElement("div");

        tarjeta.className = "producto-card";


        tarjeta.innerHTML = `

            <div>

                <span class="categoria">
                    ${producto.categoria || "Sin categoría"}
                </span>

                <h3>
                    ${producto.nombre}
                </h3>

                <p>
                    ${producto.descripcion}
                </p>

                <strong>
                    $${formatearPrecio(producto.precio)}
                </strong>

                <p>
                    Stock:
                    ${producto.stock}
                </p>

                <small>
                    ID: ${producto.id || "Pendiente"}
                </small>

            </div>


            <div class="acciones">

                <button
                    onclick="duplicarProducto('${producto.id}')"
                    class="btn-prototype"
                >
                    Duplicar
                </button>

                <button
                    onclick="eliminarProducto('${producto.id}')"
                    class="btn-eliminar"
                >
                    Eliminar
                </button>

            </div>

        `;


        contenedor.appendChild(tarjeta);

    });

}


// ==========================================
// ELIMINAR PRODUCTO
// ==========================================

async function eliminarProducto(id) {

    const confirmar =
        confirm(
            "¿Desea eliminar este producto?"
        );


    if (!confirmar) {
        return;
    }


    try {

        const respuesta =
            await fetch(
                `${API_PRODUCTOS}/${id}`,
                {
                    method: "DELETE"
                }
            );


        if (!respuesta.ok) {

            throw new Error(
                "No se pudo eliminar el producto"
            );

        }


        cargarProductos();


    } catch (error) {

        alert(error.message);

    }

}


// ==========================================
// PROTOTYPE
// ==========================================

function duplicarProducto(id) {

    window.location.href =
        `/prototype/duplicar?id=${id}`;

}


// ==========================================
// FORMATO DEL PRECIO
// ==========================================

function formatearPrecio(precio) {

    return new Intl.NumberFormat(
        "es-CO"
    ).format(precio);

}