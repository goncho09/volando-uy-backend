<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy | Crear paquete</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <script src="${pageContext.request.contextPath}/scripts/header.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>

</head>

<body>
    <jsp:include page="../components/header.jsp" />

    <main class="flex flex-col items-center  p-4 min-h-screen">
        <form id="form" class="space-y-4 flex flex-col w-full max-w-md p-6 bg-white rounded-lg shadow-xl bg-gray-300  mt-2">
            <h2 class="mb-6 text-2xl font-bold text-center text-black">Nuevo Paquete</h2>

            <div class="flex w-full items-center border-b border-gray-300  space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-box  text-[var(--azul-oscuro)]"></i>
                <input type="text" name="nombre" required  class="flex-grow p-2" placeholder="Ingrese el nombre del paquete *">
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-comment  text-[var(--azul-oscuro)]"></i>
                <textarea  id="descripcion" name="descripcion" rows="3"  required class="flex-grow p-2" style="resize: none" placeholder="Ingrese la descripción del paquete *"></textarea>
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-calendar  text-[var(--azul-oscuro)]"></i>
                <input type="number" id="validez" name="validez" required min="1"  placeholder="Ingrese la validez en días *"
                       class="flex-grow p-2">
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-percent  text-[var(--azul-oscuro)]"></i>
                <input type="number" id="descuento" name="descuento" step="1" required min="0" max="100" placeholder="Ingrese el descuento en porcentaje *"
                       class="flex-grow p-2">
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-money-bill-wave  text-[var(--azul-oscuro)]"></i>
                <input type="number" id="precio" name="precio" step="1" required min="1" placeholder="Ingrese el precio del paquete *"
                       class="flex-grow p-2">
            </div>

            <p id="error-msg"
               class="opacity-0 transition-all duration-500 text-red-600 text-base text-center transform translate-y-[-5px]">
            </p>

            <p id="success-msg"
               class="opacity-0 transition-all duration-500 text-green-600 text-base text-center transform translate-y-[-5px]">
            </p>

            <button type="submit"
                    class="w-full px-4 py-2 font-semibold cursor-pointer text-white bg-[var(--azul-oscuro)] rounded-md hover:bg-[var(--azul-medio)] focus:outline-none focus:ring-2 focus:ring-[var(--azul-claro)]">
                Crear Paquete
            </button>
        </form>
    </main>
</body>

<script>
    document.getElementById('form').addEventListener('submit',async(e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const params = new URLSearchParams(formData);
        const errorMsg = document.getElementById("error-msg");
        const successMsg = document.getElementById("success-msg");

        errorMsg.textContent = "";
        errorMsg.classList.add("opacity-0", "-translate-y-1");
        errorMsg.classList.remove("opacity-100", "translate-y-0");

        successMsg.textContent = "";
        successMsg.classList.add("opacity-0", "-translate-y-1");
        successMsg.classList.remove("opacity-100", "translate-y-0");


        const response = await fetch('${pageContext.request.contextPath}/paquete/crear', {
            method: 'POST',
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        const text = await response.text();

        if (response.ok) {
            successMsg.textContent = 'Paquete creado con éxito';
            successMsg.classList.remove("opacity-0", "-translate-y-1");
            successMsg.classList.add("opacity-100", "translate-y-0");
            e.target.reset();
        } else {
            errorMsg.textContent = text;
            errorMsg.classList.remove("opacity-0", "-translate-y-1");
            errorMsg.classList.add("opacity-100", "translate-y-0");
        }
    });
</script>


</html>