document
    .getElementById("formLogin")
    .addEventListener("submit", async function(event) {

        event.preventDefault();


        const correo =
            document.getElementById("correo").value;

        const password =
            document.getElementById("password").value;


        const datos = {

            correo: correo,
            password: password

        };


        try {

            const respuesta = await fetch(
                "/auth/login",
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


            if (respuesta.ok) {

                window.location.href =
                    "marketplace.html";

            } else {

                document.getElementById("mensaje")
                    .innerHTML = resultado;

            }


        } catch (error) {

            document.getElementById("mensaje")
                .innerHTML =
                "No fue posible conectar con el servidor.";

        }

    });