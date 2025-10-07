<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear paquete</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
</head>

<body>
<jsp:include page="../header/header.jsp" />

<main class="flex flex-col items-center  p-4 bg-gray-300 min-h-screen">
        <form action="${pageContext.request.contextPath}/paquete/crear" method="POST" class="space-y-4 flex flex-col w-full max-w-md p-6 bg-white rounded-lg shadow-xl  mt-2">
            <h2 class="mb-6 text-2xl font-bold text-center text-black">Nuevo Paquete</h2>

            <div class="flex w-full items-center border-b border-gray-300  space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-box  text-[var(--azul-oscuro)]"></i>
                <input type="text" name="nombre" required  class="flex-grow p-2" placeholder="Ingrese el nombre del paquete">
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-comment  text-[var(--azul-oscuro)]"></i>
                <textarea  id="descripcion" name="descripcion" rows="3"  required class="flex-grow p-2" style="resize: none" placeholder="Ingrese la descripción del paquete"></textarea>
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-calendar  text-[var(--azul-oscuro)]"></i>
                <input type="number" id="validez" name="validez" required min="1"  placeholder="Ingrese la validez en días"
                       class="flex-grow p-2">
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-percent  text-[var(--azul-oscuro)]"></i>
                <input type="number" id="descuento" name="descuento" step="1" required min="0" max="100" placeholder="Ingrese el descuento en porcentaje"
                       class="flex-grow p-2">
            </div>

            <div class="flex w-full items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-money-bill-wave  text-[var(--azul-oscuro)]"></i>
                <input type="number" id="precio" name="precio" step="1" required min="1" placeholder="Ingrese el precio del paquete"
                       class="flex-grow p-2">
            </div>

            <div class="flex w-full items-center flex-col">
                <div class="flex w-full items-center justify-start space-x-2">
                    <i class="fa fa-route  text-[var(--azul-oscuro)]"></i>
                    <label for="rutas" class="block  font-medium text-[var(--azul-oscuro)]">Seleccione las rutas asociadas:</label>
                </div>
                <select id="rutas" name="rutas" multiple required
                        class="w-full mt-2 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[var(--azul-oscuro)]">
                    <option value="ruta1">Montevideo - Buenos Aireas</option>
                    <option value="ruta2">Montevideo - Santiago de Chile</option>
                    <option value="ruta3">Montevideo - Asunción</option>
                </select>
            </div>

            <button type="submit"
                    class="w-full px-4 py-2 font-semibold cursor-pointer text-white bg-[var(--azul-oscuro)] rounded-md hover:bg-[var(--azul-medio)] focus:outline-none focus:ring-2 focus:ring-[var(--azul-claro)]">
                Crear Paquete
            </button>
        </form>
</main>
</body>

</html>