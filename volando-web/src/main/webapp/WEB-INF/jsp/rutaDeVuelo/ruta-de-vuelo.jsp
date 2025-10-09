<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rutas de vuelo</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/scripts/header.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>

</head>

<body>
<jsp:include page="../components/header.jsp"/>

<main class="container my-5">

        <div class="row">
            <jsp:include page="../components/miPerfil.jsp"/>

            <div class="col-md-9">
                <div class="card mb-5 border-0">
                    <div class="row g-0">
                        <div class="col-md-5">
                            <img src="${ruta.urlImagen}" alt="Rio de Janeiro" class="img-fluid h-100 rounded-start"
                                style="height:100%; object-fit:cover;">
                        </div>
                        <div class="col-md-7 p-4">
                            <h5 class="fw-bold">Ruta de vuelo</h5>
                            <p><strong>${ruta.nombre}</strong></p>
                            <p>${ruta.descripcion}</p>
                            <p>Origen: ${ruta.ciudadOrigen}</p>
                            <p>Destino: ${ruta.ciudadDestino}</p>
                            <p>Duración: ${ruta.duracion}hrs</p>
                            <p>Costo Turista: $${ruta.costoTurista}</p>
                            <p>Costo Ejecutivo: $${ruta.costoEjecutivo}</p>
                            <p>Costo equipaje extra: $${ruta.equipajeExtra}</p>
                            <p>Fecha de alta: ${ruta.fechaAlta}</p>
                            <p>Estado: ${ruta.estado}</p>
                            <p>Categorias: <c:forEach var="categoria" items="${ruta.categorias}">
                                <a style="color: var(--celeste); text-decoration: underline" href="${pageContext.request.contextPath}/home?categoria=${categoria}">${categoria}</a>
                            </c:forEach></p>
                        </div>
                    </div>
                </div>

                    <section class="vuelos">
                        <h5 class="fw-bold mb-4">VUELOS</h5>
                        <div class="row g-4">
                            <c:choose>
                                <c:when test="${not empty vuelos}">
                                    <c:forEach var="vuelo" items="${vuelos}">
                                        <div class="col-md-4">
                                            <div class="card shadow h-100">
                                                <img src="${vuelo.urlImage}" alt="ImagenVuelo"
                                                     class="card-img-top img-fluid">
                                                <div class="card-body">
                                                    <h6 class="card-title">${vuelo.nombre}</h6>
                                                    <button type="button" class="btn btn-primary"
                                                            onclick="window.location.href='${pageContext.request.contextPath}/vuelo?nombre${vuelo.nombre}'">
                                                        Ver vuelo
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p>Esta ruta no tiene vuelos disponibles</p>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not empty sessionScope.usuario}">
                            <div class="mt-4">
                                <button type="button" class="btn btn-primary"
                                        onclick="window.location.href='../vuelo/crear.html'">Agregar Vuelo
                                </button>
                            </div>
                            </c:if>
                        </div>
                    </section>
            </div>
        </div>
    </main>

</body>

</html>