<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.app.datatypes.DtUsuario" %>
<%@ page import="com.app.enums.TipoDocumento" %>

<c:set var="usuario" value="${sessionScope.usuario}"/>
<c:set var="usuarioTipo" value="${sessionScope.usuarioTipo}"/>

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

    <script src="${pageContext.request.contextPath}/scripts/header.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/globals.css"/>
</head>

<body>
<jsp:include page="../components/header.jsp"/>

<main class="container-fluid my-3 px-3">
    <div class="row">
        <aside class="col-12 col-md-3 mb-4">
            <div class="card mb-3 shadow">
                <p class="card-header fw-bold">MI PERFIL</p>
                <div class="card-body p-3">
                    <ul class="list-unstyled mb-0">
                        <li><a href="#" class="text-decoration-none">Nueva Ruta</a></li>
                        <li><a href="#" class="text-decoration-none">Nuevo Vuelo</a></li>
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
                        <img id="previewImagen" src="${not empty sessionScope.usuarioImagen ? sessionScope.usuarioImagen : pageContext.request.contextPath}/assets/userDefault.png"
                             alt="Foto de perfil" class="img-fluid rounded-circle" style="max-width: 200px; height: auto; object-fit: cover;">
                    </div>
                    <div class="col-12 col-md-7 p-4">
                        <c:choose>
                            <c:when test="${usuario != null}">
                                <h5 class="fw-bold">Perfil - Usuario</h5>
                                <h3 class="h4 h-md-3"><strong><c:out value="${not empty usuario.nombre ? usuario.nombre : 'N/A'}"/></strong></h3>
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
                        <button class="btn btn-sm square-btn btn-primary active" onclick="mostrarSeccion('perfil')" title="Perfil">Perfil</button>
                        <c:if test="${usuarioTipo == 'cliente'}">
                            <button class="btn btn-sm square-btn btn-secondary" onclick="mostrarSeccion('reservas')" title="Reservas de Vuelo">Reservas</button>
                            <button class="btn btn-sm square-btn btn-secondary" onclick="mostrarSeccion('paquetes')" title="Paquetes">Paquetes</button>
                        </c:if>
                        <c:if test="${usuarioTipo == 'aerolinea'}">
                            <button class="btn btn-sm square-btn btn-secondary" onclick="mostrarSeccion('rutas')" title="Rutas De Vuelo">Rutas</button>
                        </c:if>
                    </div>

                    <!-- Contenido toggleable: Detalles o tablas, abajo de botones -->
                    <div id="contenido-perfil" class="seccion-activa">
                        <c:choose>
                            <c:when test="${usuario != null}">
                                <form id="formPerfil" action="${pageContext.request.contextPath}/modificar-perfil" method="post" enctype="multipart/form-data">
                                    <div class="p-3 p-md-4" style="border: 2px solid black;">
                                        <!-- Nickname (no editable) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center">
                                            <p class="mb-0 fw-bold">Nickname: <span class="text-muted"><c:out value="${not empty usuario.nickname ? usuario.nickname : 'N/A'}"/></span></p>
                                        </div>

                                        <!-- Nombre (editable para ambos) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="nombre">
                                            <p class="mb-0 fw-bold">Nombre: <span id="span-nombre" class="text-muted"><c:out value="${not empty usuario.nombre ? usuario.nombre : 'N/A'}"/></span></p>
                                            <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('nombre')" title="Editar Nombre"></i>
                                            <input type="text" name="nombre" id="input-nombre" class="form-control d-none" value="${not empty usuario.nombre ? usuario.nombre : ''}" required>
                                        </div>

                                        <!-- Email (no editable) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center">
                                            <p class="mb-0 fw-bold">Email: <span class="text-muted"><c:out value="${not empty usuario.email ? usuario.email : 'N/A'}"/></span></p>
                                        </div>

                                        <!-- Campos específicos para Cliente -->
                                        <c:if test="${usuarioTipo == 'cliente'}">
                                            <!-- Apellido -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="apellido">
                                                <p class="mb-0 fw-bold">Apellido: <span id="span-apellido" class="text-muted"><c:out value="${not empty usuario.apellido ? usuario.apellido : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('apellido')" title="Editar Apellido"></i>
                                                <input type="text" name="apellido" id="input-apellido" class="form-control d-none" value="${not empty usuario.apellido ? usuario.apellido : ''}" required>
                                            </div>

                                            <!-- Fecha Nacimiento -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="fechaNacimiento">
                                                <p class="mb-0 fw-bold">Fecha Nacimiento: <span id="span-fechaNacimiento" class="text-muted"><c:out value="${not empty usuario.fechaNacimiento ? usuario.fechaNacimiento : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('fechaNacimiento')" title="Editar Fecha Nacimiento"></i>
                                                <input type="date" name="fechaNacimiento" id="input-fechaNacimiento" class="form-control d-none" value="${not empty usuario.fechaNacimiento ? usuario.fechaNacimiento : ''}" required>
                                            </div>

                                            <!-- Nacionalidad -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="nacionalidad">
                                                <p class="mb-0 fw-bold">Nacionalidad: <span id="span-nacionalidad" class="text-muted"><c:out value="${not empty usuario.nacionalidad ? usuario.nacionalidad : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('nacionalidad')" title="Editar Nacionalidad"></i>
                                                <input type="text" name="nacionalidad" id="input-nacionalidad" class="form-control d-none" value="${not empty usuario.nacionalidad ? usuario.nacionalidad : ''}" required>
                                            </div>

                                            <!-- Tipo Documento -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="tipoDocumento">
                                                <p class="mb-0 fw-bold">Tipo Documento: <span id="span-tipoDocumento" class="text-muted"><c:out value="${not empty usuario.tipoDocumento ? usuario.tipoDocumento : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('tipoDocumento')" title="Editar Tipo Documento"></i>
                                                <select name="tipoDocumento" id="input-tipoDocumento" class="form-control d-none" required>
                                                    <c:forEach var="td" items="${TipoDocumento.values()}">
                                                        <option value="${td}" ${usuario.tipoDocumento == td ? 'selected' : ''}>${td}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <!-- Número Documento -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="numeroDocumento">
                                                <p class="mb-0 fw-bold">Número Documento: <span id="span-numeroDocumento" class="text-muted"><c:out value="${not empty usuario.numeroDocumento ? usuario.numeroDocumento : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('numeroDocumento')" title="Editar Número Documento"></i>
                                                <input type="number" name="numeroDocumento" id="input-numeroDocumento" class="form-control d-none" value="${not empty usuario.numeroDocumento ? usuario.numeroDocumento : ''}" required>
                                            </div>
                                        </c:if>

                                        <!-- Campos específicos para Aerolínea -->
                                        <c:if test="${usuarioTipo == 'aerolinea'}">
                                            <!-- Descripción -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="descripcion">
                                                <p class="mb-0 fw-bold">Descripción: <span id="span-descripcion" class="text-muted"><c:out value="${not empty usuario.descripcion ? usuario.descripcion : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('descripcion')" title="Editar Descripción"></i>
                                                <textarea name="descripcion" id="input-descripcion" class="form-control d-none" rows="3">${not empty usuario.descripcion ? usuario.descripcion : ''}</textarea>
                                            </div>

                                            <!-- Link Web -->
                                            <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="linkWeb">
                                                <p class="mb-0 fw-bold">Link Web: <span id="span-linkWeb" class="text-muted"><c:out value="${not empty usuario.linkWeb ? usuario.linkWeb : 'N/A'}"/></span></p>
                                                <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('linkWeb')" title="Editar Link Web"></i>
                                                <input type="url" name="linkWeb" id="input-linkWeb" class="form-control d-none" value="${not empty usuario.linkWeb ? usuario.linkWeb : ''}">
                                            </div>
                                        </c:if>

                                        <!-- Imagen (editable para ambos) -->
                                        <div class="mb-3 d-flex justify-content-between align-items-center editable-field" data-field="imagen">
                                            <div class="d-flex align-items-center">
                                                <p class="mb-0 fw-bold me-2">Imagen: </p>
                                                <img id="preview-imagen-pequena" src="${not empty sessionScope.usuarioImagen ? sessionScope.usuarioImagen : pageContext.request.contextPath}/assets/userDefault.png" alt="Preview" style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%;">
                                            </div>
                                            <i class="fas fa-pencil-alt text-success cursor-pointer" onclick="toggleEdit('imagen')" title="Cambiar Imagen"></i>
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
                                    <p class="alert alert-warning">Debes iniciar sesión para ver tu perfil completo. <a href="${pageContext.request.contextPath}/login" class="alert-link">Iniciar sesión</a></p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Resto de secciones (reservas, paquetes, rutas) sin cambios -->
                    <!-- [Omitido por brevedad; copia del JSP anterior] -->
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="bg-dark text-white text-center py-3 mt-4">
    &copy; 2025 Volando UY. Todos los derechos reservados.
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    let editando = false;
    let valoresOriginales = {};  // Para reset en cancelar

    // [Función mostrarSeccion sin cambios]

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
            valoresOriginales[field] = input.value;
        });
    });

    // Toggle edición para campo específico
    function toggleEdit(campo) {
        const span = document.getElementById('span-' + campo);
        const input = document.getElementById('input-' + campo);
        const icono = input.previousElementSibling;  // Lápiz

        if (span && input) {
            if (input.classList.contains('d-none')) {
                // Activar
                span.classList.add('d-none');
                input.classList.remove('d-none');
                input.focus();
                icono.classList.remove('fa-pencil-alt', 'text-success');
                icono.classList.add('fa-save', 'text-primary');
                editando = true;
                document.getElementById('boton-guardar').classList.remove('d-none');
            } else {
                // Desactivar (guarda local)
                span.textContent = input.value || 'N/A';
                input.classList.add('d-none');
                span.classList.remove('d-none');
                icono.classList.remove('fa-save', 'text-primary');
                icono.classList.add('fa-pencil-alt', 'text-success');
                if (!document.querySelector('.editable-field input:not(.d-none), .editable-field textarea:not(.d-none), .editable-field select:not(.d-none)')) {
                    editando = false;
                    document.getElementById('boton-guardar').classList.add('d-none');
                }
            }
        }
    }

    // Preview para imagen
    function previewImagen(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('previewImagen').src = e.target.result;
                document.getElementById('preview-imagen-pequena').src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
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
        document.getElementById('input-imagen').value = '';
        document.getElementById('previewImagen').src = '${not empty sessionScope.usuarioImagen ? sessionScope.usuarioImagen : pageContext.request.contextPath}/assets/userDefault.png';
        document.getElementById('preview-imagen-pequena').src = '${not empty sessionScope.usuarioImagen ? sessionScope.usuarioImagen : pageContext.request.contextPath}/assets/userDefault.png';
        editando = false;
        document.getElementById('boton-guardar').classList.add('d-none');
    }

    // Validar antes de submit
    document.getElementById('formPerfil').addEventListener('submit', function(e) {
        if (!editando) {
            e.preventDefault();
            alert('No hay cambios para guardar.');
            return;
        }
        // Valida campos requeridos (ajusta según tipo)
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
        </c:if>
    });
</script>

<style>
    /* [Estilos sin cambios] */
</style>

</body>
</html>