<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Volando.uy | Ver paquete</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
</head>
<body>

<jsp:include page="../components/header.jsp"/>


<main id="main-content"
      class="flex flex-col items-center md:items-start md:flex-row max-w-7xl md:mx-auto px-4 sm:px-6 lg:px-8 justify-center  mt-5">

    <!-- Sidebar -->
    <jsp:include page="../components/miPerfil.jsp"/>

    <section class="flex flex-col items-center w-full max-w-3xl bg-white p-6 rounded-lg shadow-lg mt-5 md:mt-0 md:ml-5">
        <h2 class="text-2xl font-bold mb-6 text-center text-[#12445d]">${paquete.nombre}</h2>

        <div class="flex flex-col items-center w-full bg-white rounded-lg shadow-md overflow-hidden border border-gray-200 hover:shadow-lg transition-shadow duration-300">
            <img src="${pageContext.request.contextPath}/assets/packageDefault.png"
                 alt="Esto es una imagen de paquete"
                 class="w-1/2 h-40 object-contain">
            <h6 class="font-bold text-[#0c2636] mb-2">${paquete.nombre}</h6>
            <c:choose>
                <c:when test="${paquete.descuento > 0}">
                    <p class="text-gray-600 text-sm mb-0">
                        Costo (USD):
                        <b class="text-[#960018] text-sm">
                                ${paquete.costo}
                        </b>
                        <span class="line-through text-gray-400 ml-2">
                                ${paquete.costo / (1 - (paquete.descuento / 100))}
                        </span>
                    </p>
                    <p class="mb-4"><b class="text-[#960018] text-sm mb-4">Ahorra un ${paquete.descuento}%</b></p>
                </c:when>
                <c:otherwise>
                    <p class="text-gray-600 text-sm mb-4">Costo (USD): ${paquete.costo}</p>
                </c:otherwise>
            </c:choose>
            <p class="text-gray-600 text-sm mb-4">Validez: ${paquete.validezDias} días</p>
            <h3 class="text-lg font-semibold mb-4 text-[#12445d]">Rutas incluidas:</h3>
            <div>
                <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3 w-full p-2">
                    <c:forEach var="ruta" items="${paquete.rutaEnPaquete}">
                        <div class="p-4 flex flex-col space-y-2 rounded-2xl shadow-md  hover:scale-110 transition-scale duration-300 bg-gray-300 border border-gray-100 text-center cursor-pointer"
                        onclick="window.location.href='${pageContext.request.contextPath}/ruta-de-vuelo/buscar?nombre=${ruta.rutaDeVuelo}'">
                            <img src="${ruta.rutaDeVuelo.urlImagen}" alt="Imagen de ${ruta.rutaDeVuelo}"
                                 class="w-full h-32 object-contain mb-2 rounded-lg"/>
                            <h3>${ruta.rutaDeVuelo}</h3>
                            <p class="text-sm text-gray-600">Tipo de asiento: ${ruta.tipoAsiento}</p>
                            <p class="text-sm text-gray-600">Cantidad de pasajes: ${ruta.cantidad}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
</main>
</body>
</html>
