<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil</title>

    <!-- Librerías Header -->
    <script src="https://cdn.tailwindcss.com"></script>  <!-- Corregí a Tailwind CDN directo; ajusta si usas build. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.10/dist/full.min.css" rel="stylesheet" type="text/css" />  <!-- Versión estable. -->

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/scripts/header.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>

    <!-- Estilos y scripts propios (descomentados si los necesitas) -->
    <%-- <link rel="stylesheet" href="styles/consultaUsuario.css"> --%>
    <%-- <script src="scripts/cambio.js" defer></script> --%>
</head>

<body>
<jsp:include page="../components/header.jsp"/>

<main class="container my-5">
    <div class="row">
        <aside class="col-md-3 mb-4">
            <div class="card mb-3 shadow">
                <p class="card-header fw-bold">MI PERFIL</p>
                <div class="card-body p-3">
                    <ul class="list-unstyled mb-0">
                        <li><a href="#" class="text-decoration-none">Nueva Ruta</a></li>
                        <li><a href="#" class="text-decoration-none">Nuevo Vuelo</a></li>
                    </ul>
                </div>
            </div>

            <div class="card shadow">
                <div class="card-header fw-bold">CATEGORÍAS</div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a href="#" class="text-decoration-none">Nacionales</a></li>
                    <li class="list-group-item"><a href="#" class="text-decoration-none">Internacionales</a></li>
                    <li class="list-group-item"><a href="#" class="text-decoration-none">Europa</a></li>
                    <li class="list-group-item"><a href="#" class="text-decoration-none">América</a></li>
                    <li class="list-group-item"><a href="#" class="text-decoration-none">Exclusivos</a></li>
                    <li class="list-group-item"><a href="#" class="text-decoration-none">Temporada</a></li>
                    <li class="list-group-item"><a href="#" class="text-decoration-none">Cortos</a></li>
                </ul>
            </div>
        </aside>

        <c:choose>
            <c:when test="${not empty usuario}">
                <div id="clientes" class="col-md-9">
                    <div class="card mb-5 border-0">
                        <div class="row g-0">
                            <div class="col-md-5">
                                <img src="${usuario.urlImage}" alt="Foto de perfil" class="img-fluid">  <!-- Dinámico! -->
                            </div>
                            <div class="col-md-7 p-4">
                                <h5 class="fw-bold">Perfil - Usuario</h5>
                                <h3><strong><c:out value="${usuario.nombre}"/></strong></h3>  <!-- Dinámico -->
                                <p style="opacity: 0.5;"><c:out value="${usuario.nickname}"/> / <c:out value="${usuario.email}"/></p>
                            </div>
                        </div>
                    </div>

                    <div class="card mb-5 border-0">
                        <div class="row g-0">
                            <div class="col-md-12 p-4">
                                <button class="btn btn-primary me-2">Perfil</button>
                                <button class="btn btn-secondary me-2">Reservas de Vuelo</button>
                                <button class="btn btn-secondary">Paquetes</button>
                            </div>
                            <div class="col-md-12 p-4" style="border: 2px solid black;">
                                <p style="opacity: 0.5;">Nickname: <c:out value="${usuario.nickname}"/></p>
                                <p>Nombre: <c:out value="${usuario.nombre}"/></p>
                                <p style="opacity: 0.5;">Email: <c:out value="${usuario.email}"/></p>
                                <p>Descripción: <c:out value="${usuario.descripcion}"/></p>  <!-- Asume campo; ajusta -->
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Fallback si no hay usuario -->
                <div class="col-md-9">
                    <p class="alert alert-warning">Error: No se pudo cargar el perfil. <c:out value="${error}"/></p>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Sección para Aerolínea (muestra si el usuario es de tipo aerolínea; ajusta lógica) -->
        <c:if test="${usuario.tipo == 'aerolinea'}">  <!-- Asume campo 'tipo' en DtUsuario -->
            <div id="aerolineas" class="col-md-9">
                <div class="card mb-5 border-0">
                    <div class="row g-0">
                        <div class="col-md-5">
                            <img src="${pageContext.request.contextPath}/assets/logo.jpg" alt="Logo Aerolínea" class="img-fluid">
                        </div>
                        <div class="col-md-7 p-4">
                            <h5 class="fw-bold">Perfil - Aerolínea</h5>
                            <h3><strong><c:out value="${usuario.nombre}"/></strong></h3>
                            <p style="opacity: 0.5;"><c:out value="${usuario.nickname}"/> / <c:out value="${usuario.email}"/></p>
                        </div>
                    </div>
                </div>

                <div class="card mb-5 border-0">
                    <div class="row g-0">
                        <div class="col-md-12 p-4">
                            <button class="btn btn-primary me-2">Perfil</button>
                            <button class="btn btn-secondary">Rutas De Vuelo</button>
                        </div>
                        <div class="col-md-12 p-4" style="border: 2px solid black;">
                            <p style="opacity: 0.5;">Nickname: <c:out value="${usuario.nickname}"/></p>
                            <p>Nombre: <c:out value="${usuario.nombre}"/></p>
                            <p style="opacity: 0.5;">Email: <c:out value="${usuario.email}"/></p>
                            <p>Descripción: <c:out value="${usuario.descripcion}"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</main>

<button id="cambio" class="btn btn-info">Cambiar</button>  <!-- Agrega JS si togglea secciones -->
<footer class="bg-dark text-white text-center py-3 mt-4">
    &copy; 2025 Volando UY. Todos los derechos reservados.
</footer>

<!-- Bootstrap JS (agregado para botones) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.getElementById('cambio').addEventListener('click', () => {
        document.getElementById('clientes').style.display = 'none';
        document.getElementById('aerolineas').style.display = 'block';  // O viceversa.
    });
</script>
</body>
</html>
