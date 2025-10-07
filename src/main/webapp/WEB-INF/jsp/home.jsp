<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

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
