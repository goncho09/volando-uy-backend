<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>


    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <script src="${pageContext.request.contextPath}/scripts/header.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
    <!-- Librerias Header -->

</head>

<body>

<jsp:include page="header/header.jsp"/>

<div role="status" id="spinner" class="w-full h-[80vh] flex justify-center items-center bg-white">
    <svg aria-hidden="true" class="w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
        <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
    </svg>
    <span class="sr-only">Loading...</span>
</div>

<main id="main-content" class="container my-5 mb-4 hidden">
    <div class="row">
        <!-- Sidebar -->
        <aside class="col-md-3 mb-4" style="position: sticky; top: 20px;">
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

        <!-- Vuelos -->
        <div class="col-md-8 mx-auto">
            <c:forEach var="ruta" items="${rutas}">
                <div class="card mb-5 border-0" style="max-width: 800px;">
                    <div class="row g-0 align-items-stretch">
                        <div class="col-md-5">
                            <img src="${pageContext.request.contextPath}/assets/vuelo1.jpg" alt="Vuelo" class="img-fluid h-100"
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
    <script>
        window.onload = ()=>{
            const spinner = document.getElementById('spinner');
            const mainContent = document.getElementById('main-content');
            spinner.classList.add('hidden');
            mainContent.classList.remove('hidden');
        }
    </script>
</body>
</html>
