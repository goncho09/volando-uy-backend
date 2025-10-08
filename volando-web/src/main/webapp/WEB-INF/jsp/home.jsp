<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
</head>

<body>
    <jsp:include page="header/header.jsp" />

<main class=" container my-5 mb-4">
    <div class="row">
        <aside class="col-md-3 mb-4" style="position: sticky; top: 20px;  align-self: start;">
            <div class="card shadow">
                <div class="card-header fw-bold">CATEGORÍAS</div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="categoria" items="${categorias}">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/categoria?nombre=${categoria.nombre}" class="text-decoration-none">${categoria.nombre}</a></li>
                    </c:forEach>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">Nacionales</a></li>--%>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">Internacionales</a></li>--%>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">Europa</a></li>--%>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">América</a></li>--%>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">Exclusivos</a></li>--%>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">Temporada</a></li>--%>
<%--                    <li class="list-group-item"><a href="#" class="text-decoration-none">Cortos</a></li>--%>
                </ul>
            </div>
        </aside>

        <div class="col-md-8 mx-auto">
            <c:forEach var="ruta" items="${rutas}">
                <div class="card mb-5 border-0" style="max-width: 800px;">
                    <div class="row g-0 align-items-stretch">
                        <div class="col-md-5">
                            <img src="assets/vuelo1.jpg" alt="Vuelo 1" class="img-fluid h-100"
                                 style="object-fit: cover; height: 200px;">
                        </div>
                        <div class="col-md-7 p-4 d-flex flex-column justify-content-center">
                            <h5 class="fw-bold">${ruta.nombre}</h5>
                            <p>${ruta.descripcionCorta}</p>
                            <button class="btn btn-outline-dark mt-2 align-self-start"
                                    onclick="window.location.href='${pageContext.request.contextPath}/rutaDeVuelo?nombre=${ruta.nombre}'">Ver Ruta de Vuelo</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>

</body>

</html>