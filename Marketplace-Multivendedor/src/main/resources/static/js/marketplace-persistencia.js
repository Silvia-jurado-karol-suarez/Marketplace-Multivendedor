
/* Sincroniza el carrito entre el catálogo y la página de pago.
   No reemplaza las funciones existentes de marketplace.js. */

(function () {
    const STORAGE_KEY = 'carritoMarketplace';

    // Recuperar el carrito guardado
    try {
        const guardado = JSON.parse(
            localStorage.getItem(STORAGE_KEY) || '[]'
        );

        if (
            typeof carrito !== 'undefined' &&
            Array.isArray(guardado)
        ) {
            carrito = guardado;
        }
    } catch (error) {
        console.warn(
            'No se pudo recuperar el carrito guardado.',
            error
        );
    }

    // Guardar los productos del carrito
    function guardarCarrito() {
        try {
            if (
                typeof carrito !== 'undefined' &&
                Array.isArray(carrito)
            ) {
                localStorage.setItem(
                    STORAGE_KEY,
                    JSON.stringify(carrito)
                );
            }
        } catch (error) {
            console.warn(
                'No se pudo guardar el carrito.',
                error
            );
        }
    }

    // Guardar después de actualizar el carrito
    if (typeof window.mostrarCarrito === 'function') {
        const mostrarCarritoOriginal = window.mostrarCarrito;

        window.mostrarCarrito = function () {
            const resultado =
                mostrarCarritoOriginal.apply(this, arguments);

            guardarCarrito();
            actualizarContador();

            return resultado;
        };
    }

    // Actualizar el número de productos del icono del carrito
    function actualizarContador() {
        const contador =
            document.getElementById('contadorCarrito');

        if (
            contador &&
            typeof carrito !== 'undefined'
        ) {
            contador.textContent = carrito.reduce(
                (cantidad, producto) =>
                    cantidad + Number(producto.cantidad || 1),
                0
            );
        }
    }

    // Controlar la ventana lateral del carrito
    document.addEventListener('DOMContentLoaded', function () {
        const abrir =
            document.getElementById('abrirCarrito');

        const cerrar =
            document.getElementById('cerrarCarrito');

        const overlay =
            document.getElementById('cartOverlay');

        const drawer =
            document.getElementById('pedido');

        function abrirDrawer(event) {
            if (event) {
                event.preventDefault();
            }

            if (!drawer || !overlay) {
                return;
            }

            drawer.classList.add('is-open');
            overlay.classList.add('is-visible');

            drawer.setAttribute('aria-hidden', 'false');
            overlay.setAttribute('aria-hidden', 'false');

            document.body.classList.add('cart-open');
        }

        function cerrarDrawer() {
            if (!drawer || !overlay) {
                return;
            }

            drawer.classList.remove('is-open');
            overlay.classList.remove('is-visible');

            drawer.setAttribute('aria-hidden', 'true');
            overlay.setAttribute('aria-hidden', 'true');

            document.body.classList.remove('cart-open');
        }

        if (abrir) {
            abrir.addEventListener('click', abrirDrawer);
        }

        if (cerrar) {
            cerrar.addEventListener('click', cerrarDrawer);
        }

        if (overlay) {
            overlay.addEventListener('click', cerrarDrawer);
        }

        // Cerrar el carrito con la tecla Escape
        document.addEventListener('keydown', function (event) {
            if (event.key === 'Escape') {
                cerrarDrawer();
            }
        });

        actualizarContador();
    });
})();
