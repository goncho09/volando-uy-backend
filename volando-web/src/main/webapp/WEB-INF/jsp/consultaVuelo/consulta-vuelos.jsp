<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consulta de Vuelo - Volando.uy</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/consultaVuelo/header.js" defer></script>
    <script src="${pageContext.request.contextPath}/consultaVuelo/vuelo.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/globals.css">
</head>

<body>
    <header id="header" class="flex flex-col px-4 py-2 text-white w-[100vw] bg-[var(--azul-oscuro)]"></header>

    <main class="container my-5">
        <div class="row">
            <aside class="col-md-3 mb-4">
                <div class="card mb-3 shadow">
                    <p class="card-header fw-bold">MI PERFIL</p>
                    <div class="card-body p-3">
                        <ul class="list-unstyled mb-0">
                            <li><a href="${pageContext.request.contextPath}/perfil/perfil.html" class="text-decoration-none">Mi Perfil</a></li>
                            <li><a href="${pageContext.request.contextPath}/reservas/reservas.html" class="text-decoration-none">Reservar Vuelo</a></li>
                            <li><a href="${pageContext.request.contextPath}/paquete/paquete.html" class="text-decoration-none">Comprar Paquete</a></li>
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

            <div class="col-md-9">
                <!-- Búsqueda -->
                <div class="card mb-4 border-0 shadow">
                    <div class="card-header fw-bold">
                        <h4 class="mb-0">Consulta de Vuelo</h4>
                    </div>
                    <div class="card-body p-4">
                        <div class="row g-3 align-items-end">
                            <div class="col-md-4">
                                <label class="form-label fw-bold">Aerolínea</label>
                                <select class="form-select" id="select-aerolinea">
                                    <option value="">Selecciona una aerolínea</option>
                                    <c:forEach var="aerolinea" items="${aerolineas}">
                                        <option value="${aerolinea.nickname}">${aerolinea.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                           <div class="col-md-4">
                               <label class="form-label fw-bold">Ruta</label>
                               <select class="form-select" id="select-ruta" ${empty rutasAerolinea ? 'disabled' : ''}>
                                   <option value="">${empty rutasAerolinea ? 'Primero selecciona aerolínea' : 'Selecciona una ruta'}</option>
                                   <c:if test="${not empty rutasAerolinea}">
                                       <c:forEach var="ruta" items="${rutasAerolinea}">
                                           <option value="${ruta.nombre}">${ruta.nombre}</option>
                                       </c:forEach>
                                   </c:if>
                               </select>
                           </div>
                            <div class="col-md-4">
                                <button class="btn btn-primary w-100" id="btn-buscar" disabled;">
                                    <i class="fas fa-search me-2"></i>Buscar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="mensaje-inicial" class="card border-0 shadow">
                    <div class="card-body text-center py-5">
                        <i class="fas fa-plane fa-3x text-muted mb-3"></i>
                        <h5 class="text-muted">Consulta de Vuelos</h5>
                        <p class="text-muted">Selecciona una aerolínea y una ruta para buscar vuelos disponibles</p>
                    </div>
                </div>

                <div id="lista-vuelos-container" class="d-none">
                    <div class="card border-0 shadow">
                        <div class="card-header fw-bold">
                            <h5 class="mb-0" id="titulo-lista-vuelos">Vuelos Disponibles</h5>
                        </div>
                        <div class="card-body p-0">
                            <div id="lista-vuelos">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Detalles del vuelo -->
                <div id="detalle-vuelo-container" class="d-none">
                    <div class="d-flex gap-2 mb-3">
                        <button type="button" class="btn btn-outline-secondary" id="btn-volver-lista">
                            ← Volver a la lista
                        </button>
                    </div>

                    <div class="card mb-5 border-0 shadow">
                        <div class="row g-0">
                            <div class="col-md-5">
                                <img src="" alt="Vuelo" id="vuelo-imagen"
                                    class="img-fluid h-100 rounded-start mx-auto d-block"
                                    style="height:100%; object-fit:cover;">
                            </div>
                            <div class="col-md-7 p-4">
                                <h4 class="fw-bold mb-3" id="vuelo-nombre">Vuelo</h4>

                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Aerolínea:</strong> <span id="vuelo-aerolinea"></span></p>
                                        <p><strong>Ruta:</strong> <span id="vuelo-ruta"></span></p>
                                        <p><strong>Fecha:</strong> <span id="vuelo-fecha"></span></p>
                                        <p><strong>Duración:</strong> <span id="vuelo-duracion"></span></p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Asientos turista:</strong> <span id="vuelo-turista"></span></p>
                                        <p><strong>Asientos ejecutivo:</strong> <span id="vuelo-ejecutivo"></span></p>
                                        <p><strong>Fecha de alta:</strong> <span id="vuelo-fecha-alta"></span></p>
                                        <p><strong>Hora:</strong> <span id="vuelo-hora"></span></p>
                                    </div>
                                </div>

                                <!-- Si el usuario tiene reserva -->
                                <div id="info-reserva" class="mt-3 p-3 bg-light rounded d-none">
             <p class="mb-2 text-center">Ya tienes una reserva para este vuelo, <span id="nombre-usuario"></span></p>
    <button type="button" class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/reservas/consultar-reservas.html'" style="background-color: var(--azul-oscuro); border-color: var(--azul-oscuro);">Ver reserva</button>
</div>
                            </div>
                        </div>
                    </div>

                    <div class="card mb-4 shadow">
                        <div class="card-header fw-bold">INFORMACIÓN DE LA RUTA</div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>Origen:</strong> <span id="ruta-origen"></span></p>
                                    <p><strong>Destino:</strong> <span id="ruta-destino"></span></p>
                                    <p><strong>Hora de salida:</strong> <span id="ruta-hora"></span></p>
                                </div>
                                <div class="col-md-6">
                                    <p><strong>Costo turista:</strong> <span id="ruta-costo-turista"></span></p>
                                    <p><strong>Costo ejecutivo:</strong> <span id="ruta-costo-ejecutivo"></span></p>
                                    <p><strong>Equipaje extra:</strong> <span id="ruta-equipaje"></span></p>
                                </div>
                            </div>
<button type="button" class="btn btn-primary mt-2" onclick="window.location.href='${pageContext.request.contextPath}/ruta-de-vuelo/ruta-de-vuelo.html'" style="background-color: var(--azul-oscuro); border-color: var(--azul-oscuro);">Ver detalles de la ruta</button>                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
<script>
// Función para recargar la página con la aerolínea seleccionada
function cargarRutas(aerolineaId) {
    console.log('Seleccionada aerolínea:', aerolineaId);

    if (aerolineaId) {
        // Recargar la página con el parámetro de aerolínea seleccionada
        window.location.href = '${pageContext.request.contextPath}/consulta-vuelos?aerolineaSeleccionada=' + aerolineaId;
    } else {
        // Si no hay aerolínea seleccionada, recargar sin parámetros
        window.location.href = '${pageContext.request.contextPath}/consulta-vuelos';
    }
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    const selectAerolinea = document.getElementById('select-aerolinea');
    const selectRuta = document.getElementById('select-ruta');
    const btnBuscar = document.getElementById('btn-buscar');

    if (selectAerolinea) {
        // Establecer la aerolínea seleccionada si viene del servidor
        const aerolineaSeleccionada = '${aerolineaSeleccionada}';
        if (aerolineaSeleccionada) {
            selectAerolinea.value = aerolineaSeleccionada;
        }

        // Evento para cambiar aerolínea
        selectAerolinea.addEventListener('change', function() {
            cargarRutas(this.value);
        });
    }

    // Habilitar/deshabilitar búsqueda según selección de ruta
    if (selectRuta && btnBuscar) {
        selectRuta.addEventListener('change', function() {
            btnBuscar.disabled = !this.value;
        });

        // Si ya hay rutas cargadas, habilitar el select de rutas
        const hayRutas = ${not empty rutasAerolinea};
        if (hayRutas) {
            selectRuta.disabled = false;
        }
    }

    // Botón buscar
    if (btnBuscar) {
        btnBuscar.addEventListener('click', function() {
            const rutaSeleccionada = selectRuta.value;
            if (rutaSeleccionada) {
                // Aquí puedes implementar la búsqueda de vuelos
                alert('Buscar vuelos para ruta: ' + rutaSeleccionada);
                // mostrarListaVuelosConDatosPrueba();
            }
        });
    }
});
</script>
</body>

</html>