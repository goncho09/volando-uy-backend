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
        <!-- Carta de Paquete -->
        <div class="bg-white rounded-2xl shadow-xl overflow-hidden card-hover border border-gray-100">
            <!-- Encabezado con imagen y título -->
            <div class="relative">
                <div class="h-48 bg-gradient-to-r from-[#0c2636] to-[#12445d] flex items-center justify-center">
                    <div class="text-center text-white px-4">
                        <h1 class="text-3xl font-bold mb-2">Paquete de Viaje</h1>
                        <p class="text-lg opacity-90">Disfruta de una experiencia única con nuestras rutas seleccionadas</p>
                    </div>
                </div>
            </div>

            <!-- Contenido principal -->
            <div class="p-6 md:p-8">
                <!-- Información general del paquete -->
                <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                    <!-- Columna 1: Detalles principales -->
                    <div class="md:col-span-2">
                        <div class="mb-6">
                            <h2 class="text-xl font-bold text-[#12445d] mb-4 flex items-center">
                                <i class="fas fa-info-circle mr-2"></i> Información del Paquete
                            </h2>
                            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                <div class="bg-gray-50 p-4 rounded-lg">
                                    <p class="text-sm text-gray-500">Nombre</p>
                                    <p class="font-semibold text-[#0c2636]">${paquete.nombre}</p>
                                </div>
                                <div class="bg-gray-50 p-4 rounded-lg">
                                    <p class="text-sm text-gray-500">Días de Validez</p>
                                    <p class="font-semibold text-[#0c2636]">${paquete.validezDias} días</p>
                                </div>
                                <c:choose>
                                    <c:when test="${paquete.descuento > 0}">
                                        <div class="bg-gray-50 p-4 rounded-lg">
                                            <p class="text-sm text-gray-500">Costo Original</p>
                                            <p class="font-semibold text-gray-400 line-through">$${paquete.costo} USD</p>
                                        </div>
                                        <div class="bg-gray-50 p-4 rounded-lg">
                                            <p class="text-sm text-gray-500">Precio con Descuento</p>
                                            <p class="font-semibold text-[#960018] text-lg">$${paquete.costo - (paquete.costo * (paquete.descuento / 100))} (-${paquete.descuento}%)</p>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="bg-gray-50 p-4 rounded-lg col-span-2">
                                            <p class="text-sm text-gray-500">Costo</p>
                                            <p class="font-semibold text-gray-400">$${paquete.costo} USD</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div>
                            <h2 class="text-xl font-bold text-[#12445d] mb-4 flex items-center">
                                <i class="fas fa-map-marked-alt mr-2"></i> Descripción
                            </h2>
                            <p class="text-gray-600 bg-gray-50 p-4 rounded-lg">
                                ${paquete.descripcion}
                            </p>
                        </div>
                    </div>

                    <!-- Columna 2: Resumen de precios -->
                    <div class="bg-[#f8fafc] border border-gray-200 rounded-xl p-5 h-fit">
                        <h3 class="text-lg font-bold text-[#12445d] mb-4 text-center">Resumen de Precios</h3>
                        <c:choose>
                            <c:when test="${paquete.descuento > 0}">
                                <div class="space-y-3 mb-4">
                                    <div class="flex justify-between">
                                        <span class="text-gray-600">Precio base:</span>
                                        <span class="font-medium">$${paquete.costo}</span>
                                    </div>
                                    <div class="flex justify-between text-[#960018]">
                                        <span>Descuento:</span>
                                        <span class="font-medium">-$${paquete.costo * (paquete.descuento / 100)}</span>
                                    </div>
                                    <div class="border-t border-gray-300 pt-2 flex justify-between font-bold text-lg">
                                        <span class="text-[#12445d]">Total:</span>
                                        <span class="text-[#960018]">$${paquete.costo - (paquete.costo * (paquete.descuento / 100))} USD</span>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="space-y-3 mb-4">
                                    <div class="flex justify-between">
                                        <span class="text-gray-600">Precio:</span>
                                        <span class="font-medium">$${paquete.costo}</span>
                                    </div>
                                    <div class="flex justify-between">
                                    </div>
                                    <div class="border-t border-gray-300 pt-2 flex justify-between font-bold text-base">
                                        <span class="text-[#12445d]">Total:</span>
                                        <span>$${paquete.costo} USD</span>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <div class="text-center mt-6">
                            <button class="bg-[#12445d] hover:bg-[#0c2636] text-white font-bold py-3 px-6 rounded-lg w-full transition-colors duration-300 flex items-center justify-center">
                                <i class="fas fa-shopping-cart mr-2"></i> Reservar Ahora
                            </button>
                            <p class="text-xs text-gray-500 mt-2">Pago seguro • Cancelación flexible</p>
                        </div>
                    </div>
                </div>

                <!-- Rutas incluidas -->
                <div class="mt-8">
                    <h2 class="text-2xl font-bold mb-6 text-[#12445d] text-center flex items-center justify-center">
                        <i class="fas fa-route mr-3"></i> Rutas Incluidas en el Paquete
                    </h2>

                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
                        <!-- Ruta 1 -->
                        <div class="bg-white border border-gray-200 rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-all duration-300 card-hover cursor-pointer"
                             onclick="window.location.href='#'">
                            <div class="h-40 bg-gradient-to-br from-blue-100 to-cyan-100 flex items-center justify-center">
                                <i class="fas fa-plane-departure text-4xl text-[#12445d]"></i>
                            </div>
                            <div class="p-4">
                                <h3 class="font-bold text-[#0c2636] mb-2">Madrid - París</h3>
                                <div class="flex items-center text-sm text-gray-600 mb-2">
                                    <i class="fas fa-chair mr-2"></i>
                                    <span>Clase Ejecutiva</span>
                                </div>
                                <div class="flex items-center text-sm text-gray-600">
                                    <i class="fas fa-ticket-alt mr-2"></i>
                                    <span>2 pasajes incluidos</span>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
</body>
</html>
