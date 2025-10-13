<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Reserva - Volando.uy</title>

    <!-- Librerias Header -->
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="../header/header.js" defer></script>
    <script src="reserva.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>

</head>

<body>
    <jsp:include page="../components/header.jsp"/>

    <main class="container my-5">
        <div class="row">
            <!-- Barra lateral -->
            <aside class="col-md-3 mb-4">
                 <jsp:include page="../components/miPerfil.jsp"/>
            </aside>

            <!-- Contenido principal -->
            <div class="col-md-9">
                <div class="card mb-4 border-0 shadow">
                    <div class="card-header fw-bold">
                        <h4 class="mb-0">Crear Nueva Reserva</h4>
                    </div>
                    <div class="card-body p-4">
                        <!-- Selección de vuelo -->
                        <div class="mb-4">
                            <h5 class="mb-3">Selecciona tu Vuelo</h5>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Aerolínea</label>
                                    <select class="form-select" id="aerolinea-select" name="aerolinea" onchange="this.form.submit()">
                                        <option value="">Selecciona una aerolínea</option>
                                        <c:forEach var="a" items="${aerolineas}">
                                            <option value="${a.nickname}" ${a.nickname eq aerolineaId ? "selected" : ""}>
                                                ${a.nombre}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Ruta</label>
                                    <select class="form-select" name="ruta" id="ruta-select" ${empty rutas ? "disabled" : ""} onchange="this.form.submit()">
                                        <option value="">Selecciona una ruta</option>
                                        <c:forEach var="r" items="${rutas}">
                                            <option value="${r.id}" ${r.id eq rutaSeleccionada ? "selected" : ""}>
                                                ${r.origen} → ${r.destino}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Vuelo</label>
                                    <select class="form-select" id="vuelo-select">
                                        <option value="">Selecciona un vuelo</option>
                                        <option value="zl1502001">ZL1502001 - 25/10/2024 12:50</option>
                                        <option value="zl1502002">ZL1502002 - 26/10/2024 18:30</option>
                                        <option value="zl0801001">ZL0801001 - 25/10/2024 08:00</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <!-- Información del vuelo seleccionado -->
                        <div id="info-vuelo" class="card mb-4 bg-light d-none">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-4">
                                        <img src="../assets/vuelo1.jpg" alt="Vuelo seleccionado" class="img-fluid rounded" id="vuelo-imagen">
                                    </div>
                                    <div class="col-md-8">
                                        <h5 id="vuelo-titulo">ZL1502001 - Montevideo a Río de Janeiro</h5>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p><strong>Fecha:</strong> <span id="vuelo-fecha">25/10/2024</span></p>
                                                <p><strong>Hora:</strong> <span id="vuelo-hora">12:50</span></p>
                                                <p><strong>Duración:</strong> <span id="vuelo-duracion">2h 30min</span></p>
                                            </div>
                                            <div class="col-md-6">
                                                <p><strong>Aerolínea:</strong> <span id="vuelo-aerolinea">ZuluFly</span></p>
                                                <p><strong>Asientos disponibles:</strong> <span id="vuelo-asientos">28</span></p>
                                                <p><strong>Estado:</strong> <span id="vuelo-estado">Confirmado</span></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Formulario de reserva -->
                        <form id="form-reserva" method="POST" action="${pageContext.request.contextPath}/Reserva/Crear">
                            <!-- Datos de los pasajeros -->
                            <div class="mb-4">
                                <h5 class="mb-3">Datos de los Pasajeros</h5>

                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <label for="cantidad-pasajes" class="form-label">Cantidad de pasajes</label>
                                        <select class="form-select" id="cantidad-pasajes">
                                            <option value="1">1 pasajero</option>
                                            <option value="2">2 pasajeros</option>
                                            <option value="3">3 pasajeros</option>
                                            <option value="4">4 pasajeros</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="tipo-asiento" class="form-label">Tipo de asiento</label>
                                        <select class="form-select" id="tipo-asiento">
                                            <option value="turista">Turista - USD 75</option>
                                            <option value="ejecutivo">Ejecutivo - USD 190</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="equipaje-extra" class="form-label">Unidades de equipaje extra</label>
                                    <input type="number" class="form-control" id="equipaje-extra" min="0" max="5" value="0">
                                    <div class="form-text">Costo por unidad: USD 30</div>
                                </div>

                                <!-- Nombres de pasajeros -->
                                <div id="nombres-pasajeros" class="mt-3">
                                    <div class="pasajero mb-3">
                                        <label class="form-label">Pasajero 1</label>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" name="nombrePasajero" placeholder="Nombre" required>
                                            </div>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" name="apellidoPasajero" placeholder="Apellido" required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Método de pago -->
                            <div class="mb-4">
                                <h5 class="mb-3">Método de Pago</h5>

                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="radio" name="metodo-pago" id="pago-general" checked>
                                    <label class="form-check-label" for="pago-general">
                                        Pago general
                                    </label>
                                </div>

                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="metodo-pago" id="pago-paquete">
                                    <label class="form-check-label" for="pago-paquete">
                                        Pago con paquete
                                    </label>
                                </div>

                                <!-- Selector de paquete -->
                                <div id="selector-paquete" class="mt-3 d-none">
                                    <label for="paquete" class="form-label">Selecciona un paquete</label>
                                    <select class="form-select" id="paquete">
                                        <option value="">-- Selecciona un paquete --</option>
                                        <option value="paquete1">Paquete Sudamérica (3 rutas disponibles)</option>
                                        <option value="paquete2">Paquete Europa (2 rutas disponibles)</option>
                                        <option value="paquete3">Paquete Nacional (5 rutas disponibles)</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Resumen de costos -->
                            <div class="mb-4 p-3 bg-light rounded">
                                <h5 class="mb-3">Resumen de Costos</h5>
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Pasajes:</strong> <span id="costo-pasajes">USD 75</span></p>
                                        <p><strong>Equipaje extra:</strong> <span id="costo-equipaje">USD 0</span></p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Total:</strong> <span id="total" class="fw-bold">USD 75</span></p>
                                    </div>
                                </div>
                            </div>

                            <!-- Botones de acción -->
                            <div class="d-flex justify-content-between">
                                <button type="button" class="btn btn-outline-secondary" onclick="window.location.href='../consulta-vuelo/consulta-vuelo.html'">
                                    ← Volver a Consultar Vuelos
                                </button>
                                <button type="submit" class="btn btn-primary" style="background-color: var(--azul-oscuro); border-color: var(--azul-oscuro);">
                                    <i class="fas fa-check me-2"></i>Confirmar Reserva
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

</body>

</html>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const selectAerolinea = document.getElementById("aerolinea-select");
    const selectRuta = document.getElementById("ruta-select");
    const selectVuelo = document.getElementById("vuelo-select");


    selectRuta.disabled = true;
    selectVuelo.disabled = true;

    selectAerolinea.addEventListener("change", function() {
        if (selectAerolinea.value) {
            selectRuta.disabled = false;
        } else {
            selectRuta.disabled = true;
            selectVuelo.disabled = true;
        }

        // Limpiar valores previos
        selectRuta.value = "";
        selectVuelo.value = "";
    });

    selectRuta.addEventListener("change", function() {
        if (selectRuta.value) {
            selectVuelo.disabled = false;
        } else {
            selectVuelo.disabled = true;
        }

        selectVuelo.value = "";
    });
});
</script>

