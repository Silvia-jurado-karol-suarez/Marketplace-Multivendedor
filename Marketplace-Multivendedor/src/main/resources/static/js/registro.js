document
    .getElementById("formRegistro")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const nombre =
            document.getElementById("nombre").value;

        const correo =
            document.getElementById("correo").value;

        const password =
            document.getElementById("password").value;


        const datos = {

            nombre: nombre,
            correo: correo,
            password: password

        };


        try {

            const respuesta = await fetch(
                "/auth/registro",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(datos)
                }
            );


            const resultado =
                await respuesta.text();


            document.getElementById("mensaje")
                .innerHTML = resultado;


        } catch (error) {

            document.getElementById("mensaje")
                .innerHTML =
                "No fue posible conectar con el servidor.";

        }

    });