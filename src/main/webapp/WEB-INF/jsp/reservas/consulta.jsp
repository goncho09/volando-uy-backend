<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<%--<c:set var="tipoUsuario" value="${sessionScope.usuarioTipo}"/>--%>

<main class="flex flex-col items-center md:items-start md:flex-row max-w-7xl md:mx-auto px-4 sm:px-6 lg:px-8 justify-center mt-5">

    <c:if test="${empty reserva}">
        <div class="mb-4 p-4 rounded-lg text-red-600">
            <p class="text-center">Error al buscar reserva.</p>
        </div>
    </c:if>

<%--    <c:if test="${not empty reserva}">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${tipoUsuario == 'cliente'}">--%>
<%--                <div class="flex flex-col">--%>
<%--                    <p>Vuelo: ${reserva.vuelo}</p>--%>
<%--                    <p>Tipo asiento: ${reserva.tipoAsiento}</p>--%>
<%--                    <p>Precio: ${reserva.costo}</p>--%>
<%--                    <p>Fecha: ${reserva.fecha}</p>--%>
<%--                    <p>Cantidad de pasajes: ${reserva.cantPasajes}</p>--%>
<%--                    <p>Costo equipaje extra: ${reserva.equipajeExtra}</p>--%>
<%--                </div>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <p>dsa</p>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </c:if>--%>
    <h1>dsadas</h1>
</main>
</body>
</html>
