<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy | Ver vuelo</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css">
</head>

<body>

<% System.out.println("llega1"); %>

 <jsp:include page="../components/header.jsp" />

 <main class="flex flex-col items-center md:items-start md:flex-row max-w-7xl md:mx-auto px-4 sm:px-6 lg:px-8 justify-center  mt-5">
     <!-- Sidebar -->
     <jsp:include page="../components/miPerfil.jsp"/>



     <section class="bg-white w-full ml-8  rounded-xl shadow-2xl overflow-hidden">
         <div class="flex flex-col lg:flex-row">
<%--             <div class="lg:w-2/5">--%>
<%--                 <img src="${vuelo.urlImage}"--%>
<%--                      alt="Imagen del vuelo"--%>
<%--                      class="w-full h-64 lg:h-full object-cover">--%>
<%--             </div>--%>

             <div class="lg:w-3/5 p-6 lg:p-8">
                 <h5 class="text-xl font-bold text-[#0c2636] mb-2 flex items-center">
                     <i class="fas fa-plane-departure mr-2 text-[#1d6e86]"></i>
                     Detalles del vuelo
                 </h5>

                 <h2 class="text-2xl font-bold text-[#12445d] mb-4">${vuelo.nombre}</h2>

                 <div class="grid grid-cols-1 gap-4 mb-4">
                     <div class="flex items-center">
                         <i class="fas fa-calendar-alt text-[#1d6e86] mr-2"></i>
                         <span class="font-semibold text-[#0c2636]">Fecha:</span>
                         <span class="ml-2 text-gray-700">${vuelo.fecha}</span>
                     </div>
                     <div class="flex items-center">
                         <i class="fas fa-clock text-[#1d6e86] mr-2"></i>
                         <span class="font-semibold text-[#0c2636]">Duración:</span>
                         <span class="ml-2 text-gray-700">${vuelo.duracion} hrs</span>
                     </div>
                     <div class="flex items-center">
                         <i class="fas fa-users text-[#1d6e86] mr-2"></i>
                         <span class="font-semibold text-[#0c2636]">Capacidad Turista:</span>
                         <span class="ml-2 text-gray-700">${vuelo.maxTuristas}</span>
                     </div>
                     <div class="flex items-center">
                         <i class="fas fa-briefcase text-[#1d6e86] mr-2"></i>
                         <span class="font-semibold text-[#0c2636]">Capacidad Ejecutiva:</span>
                         <span class="ml-2 text-gray-700">${vuelo.maxEjecutivos}</span>
                     </div>
                     <div class="flex items-center">
                         <i class="fas fa-ticket-alt text-[#1d6e86] mr-2"></i>
                         <span class="font-semibold text-[#0c2636]">Cantidad reservas:</span>
                         <span class="ml-2 text-gray-700">${vuelo.cantReservas}</span>
                     </div>
                     <button onclick="window.location.href='${pageContext.request.contextPath}/reservas'" type="submit" class="hover:bg-[var(--azul-claro)] w-full text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)]">Ver Reservas</button>

                     <button onclick="window.location.href='${pageContext.request.contextPath}/ruta-de-vuelo?nombre=${ruta.nombre}'" type="submit" class="hover:bg-[var(--azul-claro)] w-full text-white py-2 rounded-lg duration-400 bg-[var(--azul-oscuro)]">Ver Ruta de Vuelo</button>
                 </div>>
             </div>
         </div>
     </section>


 </main>
</body>
</html>