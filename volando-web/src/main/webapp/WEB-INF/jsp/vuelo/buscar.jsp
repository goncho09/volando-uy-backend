<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Volando.uy | Buscar vuelo</title>


    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
</head>
<body>

<jsp:include page="../components/header.jsp"/>

<main class="flex flex-col items-center md:items-start md:flex-row max-w-7xl md:mx-auto px-4 sm:px-6 lg:px-8 justify-center  mt-5">

    <!-- Sidebar -->
    <jsp:include page="../components/miPerfil.jsp"/>


    <section class="flex flex-col w-full max-w-3xl bg-white p-6 rounded-lg shadow-lg mt-5 md:mt-0 md:ml-5">
        <h2 class="text-2xl font-bold mb-6 text-center text-[#12445d]">Buscar Vuelo</h2>
        <form id="form" class="space-y-4">
            <div class="flex w-full md:w-1/2 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-plane icon text-[var(--azul-oscuro)]"></i>
                <select class="flex-grow outline-none bg-transparent text-gray-700 py-2 px-2 rounded focus:bg-gray-100 whitespace-nowrap"
                        id="aerolinea" name="aerolinea" aria-label="Seleccione su aerolinea *" required>
                    <option class="whitespace-nowrap" value="" disabled ${empty aerolineaId ? "selected" : ""}>
                        Seleccione una aerolínea *
                    </option>
                    <c:forEach var="a" items="${aerolineas}">
                        <option value="${a.nickname}" ${a.nickname eq aerolineaId ? "selected" : ""}>
                                ${a.nombre}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <% System.out.println("rutaid " + request.getAttribute("rutaId")); %>

            <div class="flex w-full md:w-1/2 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
                <i class="fa fa-route icon text-[var(--azul-oscuro)]"></i>
                <select class="flex-grow outline-none bg-transparent text-gray-700 py-2 px-2 rounded focus:bg-gray-100"
                        id="ruta" name="ruta" aria-label="Seleccione una ruta de vuelo *" required>
                    <option value="" disabled ${empty rutaId ? "selected" : ""}>
                        Seleccione una ruta de vuelo *
                    </option>
                    <%
                        if(request.getAttribute("rutas") != null){
                            for (Object r : (java.util.List) request.getAttribute("rutas")) {
                                System.out.println("ruta: " + r);
                            }
                        }else{
                            System.out.println("rutas es null");
                        }
                    %>
                    <c:forEach var="r" items="${rutas}">
                    <option value="${r.nombre}" ${r.nombre eq rutaId ? "selected" : ""}>
                            ${r.nombre}
                    </option>
                    </c:forEach>
                </select>
            </div>

            <div>
                <label for="fecha" class="block text-sm font-medium text-gray-700">Fecha (Opcional)</label>
                <input type="date" name="fecha"
                       class="mt-1 block w-full md:w-1/2 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"/>
            </div>

            <p id="error-msg"
               class="hidden text-red-600 text-base text-center transition-all duration-300 text-base transform origin-top -translate-y-1">
            </p>

            <button type="submit"
                    class="hover:bg-[var(--azul-claro)] w-full md:w-1/2 text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)] mt-2">
                <i class="fa fa-search mr-2"></i> Ver Vuelos
            </button>

        </form>
        <div id="vuelosContainer" class="flex w-full md:w-1/2 items-center border-b border-gray-300 mt-3 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)] ${empty vuelos ? "hidden" : ""}">
            <i class="fa fa-route icon text-[var(--azul-oscuro)]"></i>
            <select class="flex-grow outline-none bg-transparent text-gray-700 py-2 px-2 rounded focus:bg-gray-100"
                    id="vuelos" name="vuelos" aria-label="Seleccione un vuelo" required>
                <option value="" disabled selected>Seleccione un vuelo para ver su información </option>

                <c:forEach var="vuelo" items="${vuelos}">
                    <option value="${vuelo.nombre}">${vuelo.nombre}</option>
                </c:forEach>
            </select>

        </div>
        <div class="mt-2">

            <button type="submit" onclick="window.location.href='${pageContext.request.contextPath}/vuelo/consulta?nombre=' + document.getElementById('vuelos').value"
                    class="hover:bg-[var(--azul-claro)] w-full md:w-1/2 text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)] mt-2 ${empty vuelos ? "hidden" : ""}">
                <i class="fa fa-info mr-2"></i> Ver información del vuelo
            </button>
        </div>
    </section>

</body>

<script>
    const form = document.getElementById('form');
    const errorMsg = document.getElementById('error-msg');
    const aerolineaSelect = document.getElementById('aerolinea');

    aerolineaSelect.addEventListener('change', function(e) {
        const aerolinea = e.target.value;
        if (aerolinea) {
            window.location.href = '${pageContext.request.contextPath}/vuelo/buscar?aerolinea=' + aerolinea;
        }
    });

    form.addEventListener('submit', function(event) {
        const ruta = document.getElementById('ruta').value;
        const fecha = document.querySelector('input[name="fecha"]').value;
        const aerolinea = document.getElementById('aerolinea').value;

        if (!aerolinea || !ruta) {
            event.preventDefault();
            alert("Debes completar todos los campos antes de buscar vuelos.");
            return;
        }

        window.location.href = '${pageContext.request.contextPath}/vuelo/buscar?aerolinea='
            + aerolinea + '&ruta=' + ruta + '&fecha=' + fecha;
    });
</script>
</html>
