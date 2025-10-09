<%--
  Created by IntelliJ IDEA.
  User: Zar
  Date: 9/10/2025
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="w-72 bg-white rounded-xl shadow-2xl overflow-hidden transform transition-all duration-300 hover:shadow-2xl">
    <!-- Sección de Perfil -->
    <div class="p-5 bg-gradient-to-br from-[#f8fdff] to-[#e8f7ff] border-b border-[#2bc8c8]/20">
        <div class="bg-white rounded-lg p-4 shadow-sm border border-[#269fb8]/20">
            <p class="font-bold text-[#0c2636] text-lg mb-3 flex items-center">
                <i class="fas fa-user-circle mr-2 text-[#1d6e86]"></i>
                MI PERFIL
            </p>
            <div class="space-y-2">
                <a href="crear.html" class="flex items-center py-2 px-3 rounded-lg hover:bg-[#e8f7ff] transition-all duration-200 text-[#12445d] group border border-transparent hover:border-[#2bc8c8]/30">
                    <i class="fas fa-route mr-2 text-[#1d6e86] group-hover:text-[#2bc8c8]"></i>
                    Nueva Ruta
                </a>
                <a href="../vuelo/crear.html" class="flex items-center py-2 px-3 rounded-lg hover:bg-[#e8f7ff] transition-all duration-200 text-[#12445d] group border border-transparent hover:border-[#2bc8c8]/30">
                    <i class="fas fa-plane mr-2 text-[#1d6e86] group-hover:text-[#2bc8c8]"></i>
                    Nuevo Vuelo
                </a>
            </div>
        </div>
    </div>

    <!-- Sección de Categorías -->
    <div class="p-5">
        <h2 class="text-lg font-semibold text-[#0c2636] mb-4 flex items-center">
            <i class="fas fa-tags mr-2 text-[#1d6e86]"></i>
            CATEGORÍAS
        </h2>
        <div class="h-px bg-gradient-to-r from-transparent via-[#269fb8] to-transparent mb-4"></div>
        <ul class="space-y-2">
            <!-- Ejemplo de categorías (simulando el bucle) -->
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-[#12445d] hover:bg-[#e8f7ff] transition-all duration-200 hover:translate-x-1 border border-transparent hover:border-[#2bc8c8]/20">
                    <i class="fas fa-chevron-right mr-2 text-[#1d6e86] text-xs group-hover:text-[#2bc8c8]"></i>
                    Vuelos Nacionales
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-[#12445d] hover:bg-[#e8f7ff] transition-all duration-200 hover:translate-x-1 border border-transparent hover:border-[#2bc8c8]/20">
                    <i class="fas fa-chevron-right mr-2 text-[#1d6e86] text-xs group-hover:text-[#2bc8c8]"></i>
                    Vuelos Internacionales
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-[#12445d] hover:bg-[#e8f7ff] transition-all duration-200 hover:translate-x-1 border border-transparent hover:border-[#2bc8c8]/20">
                    <i class="fas fa-chevron-right mr-2 text-[#1d6e86] text-xs group-hover:text-[#2bc8c8]"></i>
                    Rutas Terrestres
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-[#12445d] hover:bg-[#e8f7ff] transition-all duration-200 hover:translate-x-1 border border-transparent hover:border-[#2bc8c8]/20">
                    <i class="fas fa-chevron-right mr-2 text-[#1d6e86] text-xs group-hover:text-[#2bc8c8]"></i>
                    Destinos Populares
                </a>
            </li>
            <li class="group">
                <a href="#" class="flex items-center py-2 px-3 rounded-lg text-[#12445d] hover:bg-[#e8f7ff] transition-all duration-200 hover:translate-x-1 border border-transparent hover:border-[#2bc8c8]/20">
                    <i class="fas fa-chevron-right mr-2 text-[#1d6e86] text-xs group-hover:text-[#2bc8c8]"></i>
                    Ofertas Especiales
                </a>
            </li>
        </ul>
    </div>

    <!-- Pie decorativo -->
    <div class="bg-gradient-to-r from-[#1d6e86] to-[#2bc8c8] h-1.5 w-full"></div>
</aside>