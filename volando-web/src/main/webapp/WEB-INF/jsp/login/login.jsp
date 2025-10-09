<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy | Iniciar sesión</title>

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
</head>

<body>
<div class=" h-screen flex justify-center items-center bg-[var(--azul-oscuro)]">
    <form id="login-form" method="POST" action="${pageContext.request.contextPath}/login"
          class="flex flex-col items-center gap-4 py-6  rounded-lg shadow-lg bg-white w-[85%] md:w-128 ">
        <a href="${pageContext.request.contextPath}/home" class="space-x-1 text-left w-full px-6">
            <i class="fa fa-arrow-left text-[var(--azul-oscuro)]"></i>
            <span class="decoration-[var(--azul-claro)] underline-offset-5 hover:underline">Volver al inicio</span>
        </a>
        <h1 class="text-center text-3xl font-semibold text-[var(--azul-oscuro)]">Iniciar sesión</h1>
        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-user icon text-[var(--azul-oscuro)]"></i>
            <input type="text" name="name" placeholder="Ingrese su nickname" required
                   class="flex-grow outline-none">
        </div>
        <div
                class="flex w-[90%] md:w-2/3 items-center border-b border-gray-300 py-2 space-x-3 focus-within:border-[var(--azul-oscuro)]">
            <i class="fa fa-lock icon text-[var(--azul-oscuro)]"></i>
            <input type="password" name="password" placeholder="Ingrese su contraseña" required
                   class="flex-grow outline-none">
        </div>

        <c:if test="${not empty error}">
            <div class="text-red-600 text-base my-1">
                    ${error}
            </div>
        </c:if>

        <button type="submit"
                class="hover:bg-[var(--azul-claro)] w-2/3 text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)]">Iniciar
            sesión</button>

        <div class="flex items-center space-x-1">
            <p class="text-center">¿No tienes una cuenta? </p>
            <a href="${pageContext.request.contextPath}/register" class="">
                <p class="hover:text-[var(--azul-claro)] hover:underline m-0">Regístrate</p>
            </a>
        </div>
    </form>
</div>
</body>

</html>