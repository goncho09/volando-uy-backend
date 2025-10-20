<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Reserva - Volando.uy</title>

    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="../header/header.js" defer></script>
    <script src="reserva.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
</head>

<body>
<jsp:include page="../components/header.jsp"/>

<main class="container my-5">
    <div class="row">
        <aside class="col-md-3 mb-4">
            <jsp:include page="../components/miPerfil.jsp"/>
        </aside>

        <div class="col-md-9">
            <div class="card mb-4 border-0 shadow">
                <div class="card-header fw-bold">
                    <h4 class="mb-0">Crear Nueva Reserva</h4>
                </div>
                <div class="card-body p-4">
                    <form id="form-reserva" method="POST" action="${pageContext.request.contextPath}/reservas/crear">

                        <!-- Selección de vuelo -->
                        <div class="mb-4">
                            <h5 class="mb-3">Selecciona tu Vuelo</h5>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Aerolínea</label>
                                    <select class="form-select" name="aerolinea"
                                        onchange="window.location.href='?aerolinea=' + this.value;">
                                        <option value="" disabled ${empty aerolineaId ? "selected" : ""}>Seleccione una aerolínea *</option>
                                        <c:forEach var="a" items="${aerolineas}">
                                            <option value="${a.nickname}" ${a.nickname eq aerolineaId ? "selected" : ""}>${a.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Ruta</label>
                                    <select class="form-select" name="ruta"
                                        ${empty rutas ? "disabled" : ""}
                                        onchange="window.location.href='?aerolinea=${aerolineaId}&ruta=' + this.value;">
                                        <option value="" disabled ${empty rutaId ? "selected" : ""}>Seleccione una ruta de vuelo *</option>
                                        <c:forEach var="r" items="${rutas}">
                                            <option value="${r.nombre}" ${r.nombre eq rutaId ? "selected" : ""}>${r.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Vuelo</label>
                                    <select class="form-select" name="vuelo" ${empty vuelos ? "disabled" : ""}>
                                        <option value="" disabled ${empty vueloId ? "selected" : ""}>Seleccione un vuelo *</option>
                                        <c:forEach var="v" items="${vuelos}">
                                            <option value="${v.nombre}">${v.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>


                        <!-- Datos de los pasajeros -->
                        <div class="mb-4">
                            <h5 class="mb-3">Datos de los Pasajeros</h5>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="cantidad-pasajes" class="form-label">Cantidad de pasajes</label>
                                    <input type="number" class="form-control" id="cantidad-pasajes" name="cantidad-pasajes" min="1" value="1">
                                </div>
                                <div class="col-md-6">
                                    <label for="tipo-asiento" class="form-label">Tipo de asiento</label>
                                    <select class="form-select" id="tipo-asiento" name="tipo-asiento">
                                        <option value="turista">
                                            Turista - USD ${precioTurista}
                                        </option>
                                        <option value="ejecutivo">
                                            Ejecutivo - USD ${precioEjecutivo}
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="equipaje-extra" class="form-label">Unidades de equipaje extra</label>
                                <input type="number" class="form-control" id="equipaje-extra" name="equipaje-extra" min="0" max="5" value="0" required>
                                <div class="form-text">Costo por unidad: ${precioEquipaje}</div>
                            </div>

                            <div id="nombres-pasajeros" class="mt-3">

                            </div>
                        </div>

                        <!-- Método de pago -->
                        <div class="mb-4">
                            <h5 class="mb-3">Método de Pago</h5>

                            <div class="form-check mb-2">
                                <input class="form-check-input" type="radio" name="metodo-pago" id="pago-general" value="pago-general" checked required>
                                <label class="form-check-label" for="pago-general">Pago general</label>
                            </div>

                            <div class="form-check mb-2">
                                <input class="form-check-input" type="radio" name="metodo-pago" id="pago-paquete" value="pago-paquete" required>
                                <label class="form-check-label" for="pago-paquete">Pago con paquete</label>
                            </div>

                            <div id="selector-paquete" class="mt-3 d-none">
                                <label for="paquete" class="form-label fw-bold">Selecciona un paquete</label>
                                <select class="form-select" id="paquete" name="paquete">
                                    <option value="" disabled ${empty paqueteId ? "selected" : ""}>Seleccione un paquete *</option>
                                    <c:forEach var="p" items="${paquetes}">
                                        <option value="${p.nombre}">${p.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <!-- Botón confirmar -->
                        <div class="d-flex justify-content-between">
                            <button type="button" class="btn btn-outline-secondary" onclick="window.location.href='../consulta-vuelo/consulta-vuelo.html'">← Volver a Consultar Vuelos</button>
                            <button type="submit" class="btn btn-primary" style="background-color: var(--azul-oscuro); border-color: var(--azul-oscuro);">Confirmar Reserva</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const pagoGeneral = document.getElementById("pago-general");
    const pagoPaquete = document.getElementById("pago-paquete");
    const selectorPaquete = document.getElementById("selector-paquete");

    const togglePaquete = () => {
        if (pagoPaquete.checked) selectorPaquete.classList.remove("d-none");
        else selectorPaquete.classList.add("d-none");
    };

    pagoGeneral.addEventListener("change", togglePaquete);
    pagoPaquete.addEventListener("change", togglePaquete);

    togglePaquete();
});
</script>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const cantidadPasajesSelect = document.getElementById("cantidad-pasajes");
    const pasajerosDiv = document.getElementById("nombres-pasajeros");

    const generarCamposPasajeros = () => {
        const cantidad = parseInt(cantidadPasajesSelect.value);
        pasajerosDiv.innerHTML = ""; // Limpiar los campos previos

        for (let i = 1; i <= cantidad; i++) {
            const divPasajero = document.createElement("div");
            divPasajero.classList.add("pasajero", "mb-3");

            divPasajero.innerHTML = `
                <label class="form-label">Pasajero ${i}</label>
                <div class="row">
                    <div class="col-md-6">
                        <input type="text" class="form-control" name="nombrePasajero" placeholder="Nombre" required>
                    </div>
                    <div class="col-md-6">
                        <input type="text" class="form-control" name="apellidoPasajero" placeholder="Apellido" required>
                    </div>
                </div>
            `;
            pasajerosDiv.appendChild(divPasajero);
        }
    };

    // Generar campos al cargar la página
    generarCamposPasajeros();

    // Generar campos cada vez que cambie la cantidad de pasajes
    cantidadPasajesSelect.addEventListener("change", generarCamposPasajeros);
});
</script>

</body>
</html>
