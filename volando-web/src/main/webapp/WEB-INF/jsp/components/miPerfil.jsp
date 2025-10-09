<%--
  Created by IntelliJ IDEA.
  User: Zar
  Date: 9/10/2025
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="w-72 bg-white rounded-2xl shadow-xl overflow-hidden border border-blue-100 transform transition-all duration-300 hover:shadow-2xl">
    <!-- Sección de Perfil -->
    <div class="p-5 bg-gradient-to-br from-blue-50 to-indigo-50 border-b border-blue-100">
        <div class="bg-white rounded-xl p-4 shadow-sm border border-blue-200">
            <p class="font-bold text-blue-800 text-lg mb-3 flex items-center">
                <i class="fas fa-user-circle mr-2 text-blue-500"></i>
                MI PERFIL
            </p>
            <div class="space-y-2">
                <a href="crear.html" class="flex items-center py-2 px-3 rounded-lg hover:bg-blue-50 transition-all duration-200 text-blue-700 group">
                    <i class="fas fa-plus-circle mr-2 text-blue-500 group-hover:text-blue-600"></i>
                    Nueva Ruta
                </a>
                <a href="../vuelo/crear.html" class="flex items-center py-2 px-3 rounded-lg hover:bg-blue-50 transition-all duration-200 text-blue-700 group">
                    <i class="fas fa-plane mr-2 text-blue-500 group-hover:text-blue-600"></i>
                    Nuevo Vuelo
                </a>
            </div>
        </div>
    </div>

    <!-- Sección de Categorías -->
    <div class="p-5">
        <h2 class="text-lg font-semibold text-blue-900 mb-4 flex items-center">
            <i class="fas fa-tags mr-2 text-blue-500"></i>
            CATEGORÍAS
        </h2>
        <div class="h-px bg-gradient-to-r from-transparent via-blue-300 to-transparent mb-4"></div>
        <ul class="space-y-2">
            <!-- Ejemplo de categorías (simulando el bucle) -->
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-blue-700 hover:bg-blue-50 transition-all duration-200 hover:translate-x-1">
                    <i class="fas fa-chevron-right mr-2 text-blue-400 text-xs group-hover:text-blue-600"></i>
                    Vuelos Nacionales
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-blue-700 hover:bg-blue-50 transition-all duration-200 hover:translate-x-1">
                    <i class="fas fa-chevron-right mr-2 text-blue-400 text-xs group-hover:text-blue-600"></i>
                    Vuelos Internacionales
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-blue-700 hover:bg-blue-50 transition-all duration-200 hover:translate-x-1">
                    <i class="fas fa-chevron-right mr-2 text-blue-400 text-xs group-hover:text-blue-600"></i>
                    Rutas Terrestres
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-blue-700 hover:bg-blue-50 transition-all duration-200 hover:translate-x-1">
                    <i class="fas fa-chevron-right mr-2 text-blue-400 text-xs group-hover:text-blue-600"></i>
                    Destinos Populares
                </a>
            </li>
        </ul>
    </div>

    <!-- Pie decorativo -->
    <div class="bg-gradient-to-r from-blue-500 to-blue-600 h-1 w-full"></div>
</aside>
