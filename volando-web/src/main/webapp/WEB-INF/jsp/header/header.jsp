<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <script src="${pageContext.request.contextPath}/scripts/header.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
</head>

<body>
<header id="header" class="flex flex-col px-4 py-2 text-white w-[100vw] bg-[var(--azul-oscuro)]">
<div class="flex items-center flex-col justify-between p-2 space-y-2 w-full header-top md:flex-row md:space-y-0"
     style="background-color: var(--azul-oscuro);">
    <a href="${pageContext.request.contextPath}/home" class="text-3xl font-bold uppercase">Volando.uy</a>

    <div class="flex flex-col items-center space-x-3 md:flex-row" id="user-info">
        <a class="flex items-center" href="${pageContext.request.contextPath}/perfil">
            <img src="${pageContext.request.contextPath}/assets/user-image.png" id="user-image" class="w-12 h-12 rounded-full" />
            <p class="m-0" id="nickname">Nickname</p>
        </a>
        <p class="decoration-[var(--celeste-claro)] cursor-pointer underline-offset-5 m-0 hover:underline" onclick="cerrarSesion()">Cerrar sesión</p>
    </div>

    <div class="flex space-x-2" id="sesion">
        <a href="${pageContext.request.contextPath}/signin">
            <p class="decoration-[var(--celeste-claro)] underline-offset-5 m-0 hover:underline">Iniciar sesión</p>
        </a>
        <div class="border-l border-1 h-6"></div>
        <a href="${pageContext.request.contextPath}/signup">
            <p class="decoration-[var(--celeste-claro)] underline-offset-5 m-0 hover:underline">Registrarme</p>
        </a>
    </div>
</div>

<!-- Barra de búsqueda -->
<div class="flex items-center justify-center w-full !space-x-2 self-center text-white border-b border-white p-2
            focus-within:border-[var(--celeste-claro)] duration-200 ease-in md:w-1/2">
    <button><i class="fa-solid fa-magnifying-glass text-xl"></i></button>
    <input type="text" placeholder="Buscar origen, destino, paquete, aerolínea..."
           class="outline-none border-0 bg-transparent w-[95%] text-lg" />
</div>

<!-- Navbar -->
<nav class="navbar shadow-sm flex items-center justify-center daisy">
    <!-- Navbar Start (Hamburguesa solo en móvil) -->
    <div class="navbar-start !w-full flex md:!hidden">
        <div class="dropdown">
            <div tabindex="0" role="button" class="flex">
                <i class="fa-solid fa-bars text-xl text-center"></i>
            </div>
            <ul tabindex="0"
                class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow rounded-box !w-72 bg-[var(--azul-oscuro)] text-white">

                <!-- Vuelos -->
                <li>
                    <details>
                        <summary>Vuelos</summary>
                        <ul class="p-2 bg-[var(--azul-oscuro)]">
                            <li>
                                <a href="${pageContext.request.contextPath}/vuelo/crear">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Crear vuelo</p>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/vuelo">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Ver vuelos</p>
                                </a>
                            </li>
                        </ul>
                    </details>
                </li>

                <!-- Paquetes -->
                <li>
                    <details>
                        <summary>Paquetes</summary>
                        <ul class="p-2 bg-[var(--azul-oscuro)]">
                            <li>
                                <a href="${pageContext.request.contextPath}/paquete/crear">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Crear paquete</p>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/paquete">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Ver paquetes</p>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/ruta-en-paquete">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Agregar ruta a paquete</p>
                                </a>
                            </li>
                        </ul>
                    </details>
                </li>

                <!-- Rutas de vuelo -->
                <li>
                    <details>
                        <summary>Rutas de vuelo</summary>
                        <ul class="p-2 bg-[var(--azul-oscuro)]">
                            <li>
                                <a href="${pageContext.request.contextPath}/ruta-de-vuelo/crear">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Crear ruta</p>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/ruta-de-vuelo">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Ver rutas</p>
                                </a>
                            </li>
                        </ul>
                    </details>
                </li>

                <!-- Reservas -->
                <li>
                    <details>
                        <summary>Reservas</summary>
                        <ul class="p-2 bg-[var(--azul-oscuro)]">
                            <li>
                                <a href="${pageContext.request.contextPath}/reservas/crear">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Crear reserva</p>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/reservas">
                                    <p
                                            class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                        Ver reservas</p>
                                </a>
                            </li>
                        </ul>
                    </details>
                </li>

            </ul>
        </div>
    </div>

    <!-- Navbar Center (solo escritorio) -->
    <div class="flex w-full items-center  z-10 justify-center hidden md:flex" id="navbar-center">
        <ul class="menu menu-horizontal flex m-0 text-white">

            <!-- Vuelos -->
            <li>
                <details>
                    <summary>Vuelos</summary>
                    <ul class="p-2 w-32 bg-[var(--azul-oscuro)]">
                        <li>
                            <a href="${pageContext.request.contextPath}/vuelo/crear">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Crear vuelo</p>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/vuelo">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Ver vuelos</p>
                            </a>
                        </li>
                    </ul>
                </details>
            </li>

            <!-- Paquetes -->
            <li>
                <details>
                    <summary>Paquetes</summary>
                    <ul class="p-2 w-32 bg-[var(--azul-oscuro)]">
                        <li>
                            <a href="${pageContext.request.contextPath}/paquete/crear">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Crear paquete</p>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/paquete">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Ver paquetes</p>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/ruta-en-paquete">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Agregar ruta a paquete</p>
                            </a>
                        </li>
                    </ul>
                </details>
            </li>

            <!-- Rutas de vuelo -->
            <li>
                <details>
                    <summary>Rutas de vuelo</summary>
                    <ul class="p-2 w-32 bg-[var(--azul-oscuro)]">
                        <li>
                            <a href="${pageContext.request.contextPath}/ruta-de-vuelo/crear">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Crear ruta</p>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/ruta-de-vuelo">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Ver rutas</p>
                            </a>
                        </li>
                    </ul>
                </details>
            </li>

            <!-- Reservas -->
            <li>
                <details>
                    <summary>Reservas</summary>
                    <ul class="p-2 w-32 bg-[var(--azul-oscuro)]">
                        <li>
                            <a href="${pageContext.request.contextPath}/reservas/crear">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Crear reserva</p>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/reservas">
                                <p class="m-0 decoration-[var(--celeste-claro)] underline-offset-5 hover:underline">
                                    Ver reservas</p>
                            </a>
                        </li>
                    </ul>
                </details>
            </li>

        </ul>
    </div>
</nav>
</header>
</body>

</html>