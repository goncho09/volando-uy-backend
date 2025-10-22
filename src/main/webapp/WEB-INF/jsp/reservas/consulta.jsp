<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>volando.uy | Consulta reserva</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
</head>
<body>

<jsp:include page="../components/header.jsp"/>


<c:set var="tipoUsuario" value="${sessionScope.usuarioTipo}"/>

<main class="flex flex-col items-center  max-w-7xl mx-auto mt-5">

    <section class="flex flex-col items-center bg-white w-full  rounded-xl shadow-2xl">
    <c:if test="${empty reserva}">
        <div class="mb-4 p-4 rounded-lg text-red-600">
            <p class="text-center">Error al buscar reserva.</p>
        </div>
    </c:if>

    <c:if test="${not empty reserva}">
        <h5 class="my-4 text-2xl">
            <i class="fas fa-bookmark mr-2 text-[var(--azul-oscuro)]"></i>
            Detalles de la reserva
        </h5>
      <div class="flex flex-col space-y-4 mb-3">
            <div class="flex items-center space-x-2 cursor-pointer" onclick="window.location.href='${pageContext.request.contextPath}/vuelo/consulta?nombre=${reserva.vuelo}'">
                <i class="fas fa-plane-departure text-[var(--azul-oscuro)]"></i>
                <p class="decoration-[var(--azul-oscuro)] cursor-pointer underline-offset-5 hover:underline ">Vuelo: ${reserva.vuelo}</p>
            </div>

            <div class="flex items-center space-x-2">
                <i class="fas fa-chair text-[var(--azul-oscuro)]"></i>
                <p>Tipo asiento: ${reserva.tipoAsiento}</p>
            </div>

            <div class="flex items-center space-x-2">
                <i class="fas fa-dollar-sign text-[var(--azul-oscuro)]"></i>
                <p>Precio: ${reserva.costo}</p>
            </div>

            <div class="flex items-center space-x-2">
                <i class="fas fa-calendar-alt text-[var(--azul-oscuro)]"></i>
                <p>Fecha: ${reserva.fecha}</p>
            </div>

            <div class="flex items-center space-x-2">
                <i class="fas fa-ticket-alt text-[var(--azul-oscuro)]"></i>
                <p>Cantidad de pasajes: ${reserva.cantPasajes}</p>
            </div>

            <div class="flex items-center space-x-2">
                <i class="fas fa-suitcase-rolling text-[var(--azul-oscuro)]"></i>
                <p>Costo equipaje extra: ${reserva.equipajeExtra}</p>
            </div>

            <c:if test="${tipoUsuario == 'aerolinea'}">
                <div class="flex items-center space-x-2">
                    <i class="fas fa-user text-[var(--azul-oscuro)]"></i>
                    <p>Cliente: ${reserva.cliente}</p>
                </div>
            </c:if>

            <div class="flex items-center space-x-2">
                <i class="fas fa-users text-[var(--azul-oscuro)]"></i>
                <p>Pasajeros:</p>
            </div>

            <c:forEach var="pasajero" items="${reserva.pasajeros}">
                <div class="flex items-center ml-6 space-x-2">
                    <i class="fas fa-user-check text-[var(--azul-oscuro)]"></i>
                    <p>${pasajero}</p>
                </div>
            </c:forEach>
        </div>

    </c:if>
    </section>
</main>
</body>
</html>
