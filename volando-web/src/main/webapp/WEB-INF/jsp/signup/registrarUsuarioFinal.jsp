<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
    <script src="${pageContext.request.contextPath}/scripts/registrarUsuarioFinal.js" defer></script>
</head>

<body>
<div class=" h-screen flex justify-center items-center bg-[var(--azul-oscuro)]">
    <form id="form" class="flex flex-col items-center gap-4 py-6  rounded-lg shadow-lg bg-white w-[85%] md:w-128 ">
        <h1 class="text-center text-3xl font-semibold text-[var(--azul-oscuro)]">Registrar</h1>


        <div id="aerolinea-inputs" class="hidden w-[90%] md:w-2/3 flex items-center flex-col gap-4 space-y-2">
            <div
                    class="flex  w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-plane icon text-[var(--azul-oscuro)]"></i>
                <input type="text" id="aerolinea-desc" placeholder="Ingrese la descripción de la aerolínea"
                       class="flex-grow outline-none">
            </div>

            <div>
                <div
                        class="flex  w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                    <i class="fa fa-globe icon text-[var(--azul-oscuro)]"></i>
                    <input type="text" id="aerolinea-web" placeholder="Ingrese la página web de la aerolínea"
                           class="flex-grow outline-none">
                </div>

            </div>

        </div>

        <div id="cliente-inputs" class="hidden w-[90%] md:w-2/3 flex items-center flex-col gap-4 space-y-2">
            <div
                    class="flex  w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-user icon text-[var(--azul-oscuro)]"></i>
                <input type="text" id="cliente-apellido" placeholder="Ingrese su apellido"
                       class="flex-grow outline-none">
            </div>

            <div
                    class="flex  w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-calendar icon text-[var(--azul-oscuro)]"></i>
                <input type="date" id="cliente-fecha-nac" placeholder="Ingrese su fecha de nacimiento"
                       class="flex-grow outline-none">
            </div>

            <div
                    class="flex  w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-flag icon text-[var(--azul-oscuro)]"></i>
                <input type="text" id="cliente-nacionalidad" placeholder="Ingrese su nacionalidad"
                       class="flex-grow outline-none">
            </div>

            <div
                    class="flex w-full  items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-passport icon text-[var(--azul-oscuro)]"></i>
                <select id="cliente-tipo-doc" aria-label="Seleccione tipo de documento"
                        class="flex-grow outline-none bg-transparent text-gray-700 py-2 px-2 rounded focus:bg-gray-100">
                    <option value="" disabled selected>Seleccione tipo de documento</option>
                    <option value="DNI">DNI</option>
                    <option value="Pasaporte">Pasaporte</option>
                    <option value="Cédula de identidad">Cédula de identidad</option>
                </select>
            </div>

            <div
                    class="flex  w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-id-card icon text-[var(--azul-oscuro)]"></i>
                <input type="text" id="cliente-num-doc" placeholder="Ingrese número de documento"
                       class="flex-grow outline-none">
            </div>

        </div>

        <button type="submit"
                class="hover:bg-[var(--azul-claro)] w-2/3 text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)] mt-2">Registrar</button>
    </form>
</div>


</body>

</html>
