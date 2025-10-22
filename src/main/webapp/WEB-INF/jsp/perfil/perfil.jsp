<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.app.enums.TipoDocumento" %>

<c:set var="usuario" value="${sessionScope.usuario}"/>
<c:set var="usuarioTipo" value="${sessionScope.usuarioTipo}"/>
<c:set var="cliente" value="${requestScope.cliente}"/>
<c:set var="aerolinea" value="${requestScope.aerolinea}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="defaultImgPath" value="${contextPath}/assets/userDefault.png"/>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil</title>

    <!-- Librerías Header -->
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.10/dist/full.min.css" rel="stylesheet" type="text/css"/>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${contextPath}/assets/globals.css"/>

</head>

<body>
<jsp:include page="../components/header.jsp"/>

<main class="container-fluid my-3 px-3">
    <div class="row">

        <jsp:include page="../components/miPerfil.jsp"/>

        <div class="col-12 col-md-9">
            <!-- Error global si hay -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger mb-4">${error}</div>
            </c:if>

            <!-- Mensaje de éxito si hay -->
            <c:if test="${not empty success}">
                <div class="alert alert-success mb-4">${success}</div>
            </c:if>

            <!-- Foto fija (siempre visible) -->
            <div class="card mb-5 border-0">
                <div class="row g-0">
                    <div class="col-12 col-md-5 d-flex align-items-center justify-content-center mb-3 mb-md-0">
                        <img id="previewImagen" src="${empty usuarioImagen || usuarioImagen == defaultImgPath ? defaultImgPath : usuarioImagen}" alt="Foto de perfil" class="img-fluid rounded-circle" style="max-width: 200px; height: auto; object-fit: cover;" onerror="handleImageError(this);">
                    </div>
                    <div class="col-12 col-md-7 p-4">
                        <c:choose>
                            <c:when test="${usuario != null}">
                                <h5 class="fw-bold">Perfil - Usuario</h5>
                                <h3 class="h4 h-md-3">
                                    <strong>
                                        <c:choose>
                                            <c:when test="${usuarioTipo == 'cliente'}">
                                                <c:out value="${usuario.nombre}"/> <c:out value="${usuario.apellido}"/>
                                            </c:when>
                                            <c:when test="${usuarioTipo == 'aerolinea'}">
                                            <c:out value="${usuario.nombre}"/>
                                        </c:when>
                                            <c:otherwise>
                                                N/A
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </h3>
                                <p class="mb-0" style="opacity: 0.5;"><c:out value="${not empty usuario.nickname ? usuario.nickname : 'N/A'}"/> / <c:out value="${not empty usuario.email ? usuario.email : 'N/A'}"/></p>
                            </c:when>
                            <c:otherwise>
                                <h5 class="fw-bold">Perfil - Visitante</h5>
                                <h3 class="h4 h-md-3"><strong>Inicia sesión</strong></h3>
                                <p class="mb-0" style="opacity: 0.5;">Accede a tu perfil completo</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <!-- Card con botones cuadrados pequeños arriba y contenido toggleable abajo -->
            <div class="card mb-5 border-0">
                <div class="card-body p-3 p-md-4">
                    <!-- Botones cuadrados pequeños, horizontales en desktop, stack en mobile -->
                    <div class="d-flex flex-wrap gap-1 mb-3">
                        <button class="btn btn-sm square-btn btn-primary active" onclick="mostrarSeccion('perfil', this)" title="Perfil" aria-label="Ver Perfil">Perfil</button>
                        <c:if test="${usuarioTipo == 'cliente'}">
                            <button class="btn btn-sm square-btn btn-secondary" onclick="mostrarSeccion('reservas', this)" title="Reservas de Vuelo" aria-label="Ver Reservas">Reservas</button>
                            <button class="btn btn-sm square-btn btn-secondary" onclick="mostrarSeccion('paquetes', this)" title="Paquetes" aria-label="Ver Paquetes">Paquetes</button>
                        </c:if>
                        <c:if test="${usuarioTipo == 'aerolinea'}">
                            <button class="btn btn-sm square-btn btn-secondary" onclick="mostrarSeccion('rutas', this)" title="Rutas De Vuelo" aria-label="Ver Rutas">Rutas</button>
                        </c:if>
                    </div>

                    <!-- Contenido toggleable: Detalles o tablas, abajo de botones -->
                    <div id="contenido-perfil" class="seccion-activa">
                        <c:choose>
                            <c:when test="${usuario != null}">
                                <form id="formPerfil" action="${contextPath}/modificar-perfil" method="post" enctype="multipart/form-data">
                                    <div class="p-3 p-md-4" style="border: 2px solid black;">
                                        <!-- Nickname (no editable) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center">
                                            <p class="mb-0 fw-bold">Nickname: <span class="text-muted"><c:out value="${not empty usuario.nickname ? usuario.nickname : 'N/A'}"/></span></p>
                                        </div>

                                        <!-- Nombre (editable para ambos) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="nombre">
                                            <p class="mb-0 fw-bold">Nombre: <span id="span-nombre" class="text-muted"><c:out value="${not empty usuario.nombre ? usuario.nombre : 'N/A'}"/></span></p>
                                            <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('nombre')" title="Editar Nombre" aria-label="Editar Nombre"></i>
                                            <input type="text" name="nombre" id="input-nombre" class="form-control d-none" value="${not empty usuario.nombre ? usuario.nombre : ''}" required>
                                        </div>

                                        <!-- Email (no editable) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center">
                                            <p class="mb-0 fw-bold">Email: <span class="text-muted"><c:out value="${not empty usuario.email ? usuario.email : 'N/A'}"/></span></p>
                                        </div>

                                        <!-- Campos específicos para Cliente -->
                                        <c:if test="${usuarioTipo == 'cliente' and not empty cliente}">
                                            <!-- Apellido -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="apellido">
                                                <p class="mb-0 fw-bold">Apellido: <span id="span-apellido" class="text-muted"><c:out value="${not empty cliente.apellido ? cliente.apellido : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('apellido')" title="Editar Apellido" aria-label="Editar Apellido"></i>
                                                <input type="text" name="apellido" id="input-apellido" class="form-control d-none" value="${not empty cliente.apellido ? cliente.apellido : ''}" required>
                                            </div>

                                            <!-- Fecha Nacimiento -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="fechaNacimiento">
                                                <p class="mb-0 fw-bold">Fecha Nacimiento: <span id="span-fechaNacimiento" class="text-muted"><c:out value="${not empty cliente.fechaNacimiento ? cliente.fechaNacimiento : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('fechaNacimiento')" title="Editar Fecha Nacimiento" aria-label="Editar Fecha Nacimiento"></i>
                                                <input type="date" name="fechaNacimiento" id="input-fechaNacimiento" class="form-control d-none" value="${not empty cliente.fechaNacimiento ? cliente.fechaNacimiento : ''}" required>
                                            </div>

                                            <!-- Nacionalidad -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="nacionalidad">
                                                <p class="mb-0 fw-bold">Nacionalidad: <span id="span-nacionalidad" class="text-muted"><c:out value="${not empty cliente.nacionalidad ? cliente.nacionalidad : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('nacionalidad')" title="Editar Nacionalidad" aria-label="Editar Nacionalidad"></i>
                                                <input type="text" name="nacionalidad" id="input-nacionalidad" class="form-control d-none" value="${not empty cliente.nacionalidad ? cliente.nacionalidad : ''}" required>
                                            </div>

                                            <!-- Tipo Documento -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="tipoDocumento">
                                                <p class="mb-0 fw-bold">Tipo Documento: <span id="span-tipoDocumento" class="text-muted"><c:out value="${not empty cliente.tipoDocumento ? cliente.tipoDocumento : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('tipoDocumento')" title="Editar Tipo Documento" aria-label="Editar Tipo Documento"></i>
                                                <select name="tipoDocumento" id="input-tipoDocumento" class="form-control d-none" required>
                                                    <c:forEach var="td" items="${TipoDocumento.values()}">
                                                        <option value="${td}" ${cliente.tipoDocumento == td ? 'selected' : ''}>${td}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <!-- Número Documento -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="numeroDocumento">
                                                <p class="mb-0 fw-bold">Número Documento: <span id="span-numeroDocumento" class="text-muted"><c:out value="${not empty cliente.numeroDocumento ? cliente.numeroDocumento : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('numeroDocumento')" title="Editar Número Documento" aria-label="Editar Número Documento"></i>
                                                <input type="number" name="numeroDocumento" id="input-numeroDocumento" class="form-control d-none" value="${not empty cliente.numeroDocumento ? cliente.numeroDocumento : ''}" required>
                                            </div>
                                        </c:if>

                                        <!-- Campos específicos para Aerolínea -->
                                        <c:if test="${usuarioTipo == 'aerolinea' and not empty aerolinea}">
                                            <!-- Descripción -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="descripcion">
                                                <p class="mb-0 fw-bold">Descripción: <span id="span-descripcion" class="text-muted"><c:out value="${not empty aerolinea.descripcion ? aerolinea.descripcion : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('descripcion')" title="Editar Descripción" aria-label="Editar Descripción"></i>
                                                <textarea name="descripcion" id="input-descripcion" class="form-control d-none" rows="3">${not empty aerolinea.descripcion ? aerolinea.descripcion : ''}</textarea>
                                            </div>

                                            <!-- Link Web -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="linkWeb">
                                                <p class="mb-0 fw-bold">Link Web: <span id="span-linkWeb" class="text-muted"><c:out value="${not empty aerolinea.linkWeb ? aerolinea.linkWeb : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('linkWeb')" title="Editar Link Web" aria-label="Editar Link Web"></i>
                                                <input type="url" name="linkWeb" id="input-linkWeb" class="form-control d-none" value="${not empty aerolinea.linkWeb ? aerolinea.linkWeb : ''}">
                                            </div>
                                        </c:if>

                                        <!-- Imagen (editable para ambos) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="imagen">
                                            <div class="d-flex align-items-center">
                                                <p class="mb-0 fw-bold me-2">Imagen: </p>
                                                <img id="preview-imagen-pequena" src="${empty sessionScope.usuarioImagen || sessionScope.usuarioImagen == defaultImgPath ? defaultImgPath : sessionScope.usuarioImagen}" alt="Preview pequeña" style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%;" onerror="handleImageError(this);">
                                            </div>
                                            <i id="icono-imagen" class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('imagen')" title="Cambiar Imagen" aria-label="Cambiar Imagen"></i>
                                            <input type="file" name="imagen" id="input-imagen" class="form-control d-none" accept="image/*" onchange="previewImagen(this)">
                                        </div>

                                        <!-- Botón Guardar (oculto inicialmente) -->
                                        <div class="d-none" id="boton-guardar">
                                            <button type="submit" class="btn btn-success">Guardar Cambios</button>
                                            <button type="button" class="btn btn-secondary ms-2" onclick="cancelarEdicion()">Cancelar</button>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="p-3 p-md-4 text-center">
                                    <p class="alert alert-warning">Debes iniciar sesión para ver tu perfil completo. <a href="${contextPath}/login" class="alert-link">Iniciar sesión</a></p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- [Resto de secciones sin cambios - agregar aquí si es necesario, e.g., reservas y paquetes] -->
                    <!-- Ejemplo para secciones ocultas (agregar si faltan): -->
                    <!--
                    <div id="contenido-reservas" class="seccion-oculta">
                        <p>Contenido de Reservas aquí (datos: ${not empty reservas ? fn:length(reservas) : 0})</p>
                    </div>
                    <div id="contenido-paquetes" class="seccion-oculta">
                        <p>Contenido de Paquetes aquí (datos: ${not empty paquetes ? fn:length(paquetes) : 0})</p>
                    </div>
                    -->
                </div>
            </div>
        </div>
    </div>
</main>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    let editando = false;
    let valoresOriginales = {};
    let imagenSeleccionada = false;
    const contextPath = '${contextPath}';
    const defaultImgPath = contextPath + '/assets/userDefault.png';

    // Función para manejar errores de imagen (evita loops)
    function handleImageError(img) {
        console.error('Imagen falló - path:', img.src);
        img.src = defaultImgPath;
        img.onerror = null;  // Previene loops infinitos
    }

    // Función para toggle secciones y botones
    function mostrarSeccion(seccion, button) {
        const secciones = document.querySelectorAll('[id^="contenido-"]');
        secciones.forEach(s => {
            s.classList.add('seccion-oculta');
            s.classList.remove('seccion-activa');
        });
        const elemSeccion = document.getElementById('contenido-' + seccion);
        if (elemSeccion) {
            elemSeccion.classList.remove('seccion-oculta');
            elemSeccion.classList.add('seccion-activa');
        }
        const botones = document.querySelectorAll('.square-btn');
        botones.forEach(b => {
            b.classList.remove('btn-primary', 'active');
            b.classList.add('btn-secondary');
        });
        if (button) {
            button.classList.remove('btn-secondary');
            button.classList.add('btn-primary', 'active');
        }

        // Debug para reservas/paquetes (con chequeos)
        <c:if test="${not empty reservas}">
        if (seccion === 'reservas') {
            console.log('Sección Reservas cargada. Datos:', ${fn:length(reservas)});
        }
            </c:if>
            <c:if test="${not empty paquetes}">
        else if (seccion === 'paquetes') {
            console.log('Sección Paquetes cargada. Datos:', ${fn:length(paquetes)});
        }
        </c:if>
    }

    // Toggle inicial: Perfil visible
    document.addEventListener('DOMContentLoaded', function() {
        const seccionPerfil = document.getElementById('contenido-perfil');
        if (seccionPerfil) {
            seccionPerfil.classList.remove('seccion-oculta');
            seccionPerfil.classList.add('seccion-activa');
        }
        // Guarda valores originales
        document.querySelectorAll('.editable-field [id^="input-"]').forEach(input => {
            const field = input.id.split('-')[1];
            valoresOriginales[field] = input.value || '';
        });
        console.log('Valores originales guardados:', valoresOriginales);
        // Debug inicial datos
        <c:if test="${not empty reservas}">
        console.log('Reservas inicial:', ${fn:length(reservas)});
        </c:if>
        <c:if test="${not empty paquetes}">
        console.log('Paquetes inicial:', ${fn:length(paquetes)});
        </c:if>
        // Fallback global para imgs
        document.querySelectorAll('img').forEach(img => {
            if (!img.onerror) {
                img.onerror = function() { handleImageError(this); };
            }
        });
        // Bind preview para imagen
        document.getElementById('input-imagen').addEventListener('change', previewImagen);
    });

    // Toggle edición para campo específico
    function toggleEdit(campo) {
        const span = document.getElementById('span-' + campo);
        const input = document.getElementById('input-' + campo);
        let icono = input ? input.previousElementSibling : document.getElementById('icono-imagen');

        if (input) {
            if (campo === 'imagen') {
                // Special case for file: trigger click
                input.click();
                console.log('Clicked file input for imagen');
                return;  // No toggle, solo trigger
            } else if (span) {
                if (input.classList.contains('d-none')) {
                    // Activar para otros campos
                    span.classList.add('d-none');
                    input.classList.remove('d-none');
                    input.focus();
                    if (icono) {
                        icono.classList.remove('fa-pencil-alt', 'text-success');
                        icono.classList.add('fa-save', 'text-primary');
                    }
                    editando = true;
                    document.getElementById('boton-guardar').classList.remove('d-none');
                    console.log('Editando campo:', campo);
                } else {
                    // Desactivar para otros campos
                    span.textContent = input.value || 'N/A';
                    input.classList.add('d-none');
                    span.classList.remove('d-none');
                    if (icono) {
                        icono.classList.remove('fa-save', 'text-primary');
                        icono.classList.add('fa-pencil-alt', 'text-success');
                    }
                    if (!document.querySelector('.editable-field input:not(.d-none), .editable-field textarea:not(.d-none), .editable-field select:not(.d-none)') && !imagenSeleccionada) {
                        editando = false;
                        document.getElementById('boton-guardar').classList.add('d-none');
                    }
                }
            }
        }
    }

    // Preview para imagen
    function previewImagen(input) {
        console.log('Imagen seleccionada:', input.files[0]?.name || 'Ninguna');
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('previewImagen').src = e.target.result;
                document.getElementById('preview-imagen-pequena').src = e.target.result;
                console.log('Preview actualizado');
            };
            reader.readAsDataURL(input.files[0]);
            imagenSeleccionada = true;
            // Cambia icono a check verde
            const iconoImagen = document.getElementById('icono-imagen');
            if (iconoImagen) {
                iconoImagen.classList.remove('fa-pencil-alt', 'text-success');
                iconoImagen.classList.add('fa-check', 'text-success');
            }
            editando = true;
            document.getElementById('boton-guardar').classList.remove('d-none');
        } else {
            imagenSeleccionada = false;
            const iconoImagen = document.getElementById('icono-imagen');
            if (iconoImagen) {
                iconoImagen.classList.remove('fa-check', 'text-success');
                iconoImagen.classList.add('fa-pencil-alt', 'text-success');
            }
            if (!editando) document.getElementById('boton-guardar').classList.add('d-none');
        }
    }

    // Cancelar edición
    function cancelarEdicion() {
        document.querySelectorAll('.editable-field input:not(.d-none), .editable-field textarea:not(.d-none), .editable-field select:not(.d-none)').forEach(el => {
            const field = el.id.split('-')[1];
            el.value = valoresOriginales[field] || '';
            el.classList.add('d-none');
            const span = document.getElementById('span-' + field);
            if (span) {
                span.textContent = valoresOriginales[field] || 'N/A';
                span.classList.remove('d-none');
            }
        });
        document.querySelectorAll('.fas.fa-save').forEach(icon => {
            icon.classList.remove('fa-save', 'text-primary');
            icon.classList.add('fa-pencil-alt', 'text-success');
        });
        // Reset imagen
        document.getElementById('input-imagen').value = '';
        document.getElementById('previewImagen').src = '${empty sessionScope.usuarioImagen || sessionScope.usuarioImagen == defaultImgPath ? defaultImgPath : sessionScope.usuarioImagen}';
        document.getElementById('preview-imagen-pequena').src = '${empty sessionScope.usuarioImagen || sessionScope.usuarioImagen == defaultImgPath ? defaultImgPath : sessionScope.usuarioImagen}';
        const iconoImagen = document.getElementById('icono-imagen');
        if (iconoImagen) {
            iconoImagen.classList.remove('fa-check', 'text-success');
            iconoImagen.classList.add('fa-pencil-alt', 'text-success');
        }
        imagenSeleccionada = false;
        editando = false;
        document.getElementById('boton-guardar').classList.add('d-none');
        console.log('Edición cancelada');
    }

    // Validar antes de submit
    document.getElementById('formPerfil').addEventListener('submit', function(e) {
        if (!editando && !imagenSeleccionada) {
            e.preventDefault();
            alert('No hay cambios para guardar.');
            return;
        }
        const nombre = document.getElementById('input-nombre').value.trim();
        if (!nombre) {
            e.preventDefault();
            alert('El nombre es requerido.');
            return;
        }
        <c:if test="${usuarioTipo == 'cliente'}">
        const apellido = document.getElementById('input-apellido').value.trim();
        if (!apellido) {
            e.preventDefault();
            alert('El apellido es requerido.');
            return;
        }
        const fechaNac = document.getElementById('input-fechaNacimiento').value;
        if (!fechaNac) {
            e.preventDefault();
            alert('La fecha de nacimiento es requerida.');
            return;
        }
        </c:if>
        console.log('Form submit - datos OK');
    });
</script>

<style>
    .seccion-oculta { display: none; }
    .seccion-activa { display: block; }
    .square-btn {
        width: 70px;
        height: 35px;
        font-size: 0.8rem;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .btn.active { box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25); }
    .cursor-pointer { cursor: pointer; }
    .editable-field { position: relative; }
    @media (max-width: 768px) {
        .square-btn { width: 60px; height: 30px; font-size: 0.75rem; }
        .d-flex.flex-wrap { gap: 0.5rem; }
        .table th, .table td { padding: 0.5rem; font-size: 0.85rem; }
        .card-body { padding: 1rem; }
    }
</style>

</body>
</html>