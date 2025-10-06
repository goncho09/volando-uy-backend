
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy | Registrar usuario </title>

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
    <script src="${pageContext.request.contextPath}/scripts/registrarUsuario.js" defer></script>
</head>

<body>
<div class=" h-screen flex justify-center items-center bg-[var(--azul-oscuro)]">
    <form id="form" class="flex flex-col items-center gap-4 py-6  rounded-lg shadow-lg bg-white w-[85%] md:w-128 ">
        <h1 class="text-center text-3xl font-semibold text-[var(--azul-oscuro)]">Registrar usuario</h1>

        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-user icon text-[var(--azul-oscuro)]"></i>
            <input type="text" id="nickname" placeholder="Ingrese su nickname" required
                   class="flex-grow outline-none">
        </div>

        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-envelope icon text-[var(--azul-oscuro)]"></i>
            <input type="email" id="email" placeholder="Ingrese su correo electrónico" required
                   class="flex-grow outline-none">
        </div>


        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-image icon text-[var(--azul-oscuro)]"></i>
            <label for="image" class="flex-grow text-gray-500 cursor-pointer">
                <span id="file-name" class="block">Seleccione una foto de perfil</span>
                <input type="file" id="image" accept="image/*" class="hidden">
            </label>
        </div>

        <script>
            const fileInput = document.getElementById('image');
            const fileNameSpan = document.getElementById('file-name');

            fileInput.addEventListener('change', () => {
                if (fileInput.files.length > 0) {
                    fileNameSpan.textContent = fileInput.files[0].name;
                } else {
                    fileNameSpan.textContent = 'Seleccione una foto de perfil';
                }
            });
        </script>



        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-lock icon text-[var(--azul-oscuro)]"></i>
            <input type="password" id="password" placeholder="Ingrese su contraseña" required
                   class="flex-grow outline-none">
        </div>

        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-lock icon text-[var(--azul-oscuro)]"></i>
            <input type="password" id="confirm-password" placeholder="Confirme su contraseña" required
                   class="flex-grow outline-none">
        </div>


        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-user-tag icon text-[var(--azul-oscuro)]"></i>
            <select class="flex-grow outline-none bg-transparent text-gray-700 py-2 px-2 rounded focus:bg-gray-100"
                    id="role" aria-label="Seleccione su rol" required>
                <option value="" disabled selected>Seleccione su rol</option>
                <option value="cliente">Cliente</option>
                <option value="aerolinea">Aerolínea</option>
            </select>
        </div>

        <button type="submit"
                class="hover:bg-[var(--azul-claro)] w-2/3 text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)]">Registrar</button>
    </form>
</div>
</body>

</html>
