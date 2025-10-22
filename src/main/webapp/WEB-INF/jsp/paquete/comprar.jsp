<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Volando.uy | Comprar Paquete</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
</head>

<body>

<jsp:include page="../components/header.jsp"/>

<!-- Spinner -->
<div role="status" id="spinner" class="w-full h-[80vh] flex justify-center items-center bg-white">
    <svg aria-hidden="true" class="w-8 h-8 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101"
         xmlns="http://www.w3.org/2000/svg">
        <path d="M100 50.59C100 78.20 77.61 100.59 50 100.59C22.39 100.59 0 78.20 0 50.59C0 22.97 22.39 0.59 50 0.59C77.61 0.59 100 22.97 100 50.59Z" fill="currentColor"/>
        <path d="M93.97 39.04C96.39 38.40 97.86 35.91 97.00 33.55C95.29 28.82 92.87 24.36 89.81 20.34C85.84 15.11 80.88 10.72 75.21 7.41C69.54 4.10 63.27 1.94 56.76 1.05C51.76 0.36 46.69 0.44 41.73 1.27C39.26 1.69 37.81 4.19 38.45 6.62C39.08 9.04 41.56 10.47 44.05 10.10C47.85 9.54 51.71 9.52 55.54 10.04C60.86 10.77 65.99 12.54 70.63 15.25C75.27 17.96 79.33 21.56 82.58 25.84C84.91 28.91 86.79 32.29 88.18 35.87C89.08 38.21 91.54 39.67 93.97 39.04Z" fill="currentFill"/>
    </svg>
</div>

<!-- Contenido principal -->
<main id="main-content"
      class="flex flex-col items-center md:items-start md:flex-row max-w-7xl md:mx-auto px-4 sm:px-6 lg:px-8 justify-center mt-5 hidden">

    <!-- Sidebar -->
    <jsp:include page="../components/miPerfil.jsp"/>

    <!-- Sección principal -->
    <section class="flex flex-col items-center w-full max-w-3xl bg-white p-6 rounded-lg shadow-lg mt-5 md:mt-0 md:ml-5">
        <h2 class="text-2xl font-bold mb-6 text-center text-[var(--azul-oscuro)]">Comprar Paquete</h2>

        <form id="formCompraPaquete" method="POST" class="space-y-4 flex flex-col items-center w-full">

            <!-- Seleccionar Paquete -->
            <div class="flex w-full md:w-1/2 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-box text-[var(--azul-oscuro)]"></i>
                <select id="paquete" name="paquete"
                        class="flex-grow outline-none bg-transparent text-gray-700 py-2 px-2 rounded focus:bg-gray-100"
                        required>
                    <option value="" disabled selected>Seleccione un paquete *</option>
                    <c:forEach var="p" items="${paquetes}">
                        <option value="${p.nombre}">${p.nombre}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Botón Comprar -->
            <button id="btnComprar" type="submit"
                    class="hover:bg-[var(--azul-claro)] w-full md:w-1/2 text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)] mt-2">
                <i class="fa fa-credit-card mr-2"></i> Comprar
            </button>
        </form>
    </section>
</main>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const spinner = document.getElementById('spinner');
        const mainContent = document.getElementById('main-content');

        spinner.classList.add('hidden');
        mainContent.classList.remove('hidden');
    });
</script>

</body>
</html>
