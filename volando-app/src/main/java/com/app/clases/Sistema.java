package com.app.clases;

import com.app.DAOs.*;
import com.app.datatypes.*;
import com.app.enums.EstadoRuta;
import com.app.enums.MetodoPago;
import com.app.enums.TipoAsiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Sistema implements ISistema {
    private static Sistema instancia;

    private EntityManagerFactory emf;
    private EntityManager em;
    private UserDao userDao;
    private RutaDeVueloDao rutaDeVueloDao;
    private CategoriaDao categoriaDao;
    private CiudadDao ciudadDao;
    private PaqueteDao paqueteDao;
    private VueloDao vueloDao;
    private ReservaDao reservaDao;
    private PasajerosDao pasajeroDao;
    private CompraPaqueteDao compraDao;

    private Map<String, Categoria> categorias;
    private Map<String, Ciudad> ciudades;
    private Map<String, Usuario> usuarios;
    private Map<String, Paquete> paquetes;
    private Map<String, Vuelo> vuelos;
    private Map<String, RutaDeVuelo> rutasDeVuelo;

    private List<CompraPaquete> compraPaquetes;
    private List<Reserva> reservas;
    private List<DtPasajero> pasajes;

    private List<Vuelo> consultaVuelos = new ArrayList<>();
    private List<Usuario> consultaUsuarios = new ArrayList<>();
    private List<CompraPaquete> compras = new ArrayList<>();

    private Usuario usuarioSeleccionado; // Guarda selección actual
    private Paquete paqueteSeleccionado;
    private DtCliente clienteTemporal;
    private DtAerolinea aerolineaTemporal;
    private Aerolinea aerolineaTemporalClase;
    private DtVuelo vueloTemporal;
    private RutaDeVuelo rutaTemporal;
    private Aerolinea aerolineaTemp;
    private Cliente clienteTemp;
    private DtCompraPaquete compraTemp;
    private DtPaquete paqueteTemp;


    private Sistema() {
        //Inicializar JPA
        this.emf = Persistence.createEntityManagerFactory("pepitoElLaburador");
        this.em = emf.createEntityManager(); //Inicializamos el "controlador" global de la BD
        this.userDao = new UserDao(em); // Inicializamos "controlador" de usuarios
        this.rutaDeVueloDao = new RutaDeVueloDao(em);
        this.categoriaDao = new CategoriaDao(em);
        this.ciudadDao = new CiudadDao(em);
        this.paqueteDao = new PaqueteDao(em);
        this.vueloDao = new VueloDao(em);
        this.reservaDao = new ReservaDao(em);
        this.pasajeroDao = new PasajerosDao(em);
        this.compraDao = new CompraPaqueteDao(em);

        this.categorias = categoriaDao.obtenerCategorias();
        this.ciudades = ciudadDao.obtenerCiudades();
        this.usuarios = userDao.obtenerUsuarios();
        this.paquetes = paqueteDao.obtenerPaquetes();
        this.rutasDeVuelo = rutaDeVueloDao.obtenerRutasDeVuelo();
        this.vuelos = vueloDao.obtenerVuelos();
        this.reservas = reservaDao.listar();
        this.pasajes = pasajeroDao.listar();
        this.compras = compraDao.listar();
    }

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public UserDao getUserDao() {
        return this.userDao;
    }

    public RutaDeVueloDao getRutaDeVueloDao() {
        return this.rutaDeVueloDao;
    }

    public CategoriaDao getCategoriaDao() {
        return this.categoriaDao;
    }

    public CiudadDao getCiudadDao() {
        return this.ciudadDao;
    }

    public PaqueteDao getPaqueteDao() {
        return this.paqueteDao;
    }

    public VueloDao getVueloDao() {
        return this.vueloDao;
    }

    public List<DtAerolinea> listarAerolineas() {
        List<DtAerolinea> nuevaLista = new ArrayList<>();
        for (Aerolinea a : this.getAerolineas()) {
            nuevaLista.add(a.getDatos());
        }
        return nuevaLista;
    }

    public List<DtRuta> listarRutasDeVuelo(DtAerolinea aerolinea) {
        List<DtRuta> listaRutas = new ArrayList<>();
        Aerolinea a = (Aerolinea) this.usuarios.get(aerolinea.getNickname());
        if (a == null) {
            return listaRutas;
        }
        List<RutaDeVuelo> rutasAerolinea = a.getRutasDeVuelo();
        for (RutaDeVuelo r : rutasAerolinea) {
            listaRutas.add(r.getDatos());
        }
        return listaRutas;
    }

    public List<DtReserva> listarReservas(DtCliente cliente, DtVuelo vuelo) {
        List<DtReserva> listaReservas = new ArrayList<>();
        Cliente c = (Cliente) this.usuarios.get(cliente.getNickname());
        if (c == null) {
            return listaReservas;
        }
        for (Reserva r : c.getReservas()) {
            if (r.getVuelo().getNombre().equals(vuelo.getNombre())) {
                listaReservas.add(r.getDatos());
            }
        }
        return listaReservas;
    }

    public boolean aerolineaTieneRuta(DtAerolinea aerolinea, String nombre) {
        Aerolinea a = (Aerolinea) this.usuarios.get(aerolinea.getNickname());
        if (a == null) {
            return false;
        }
        for (RutaDeVuelo r : a.getRutasDeVuelo()) {
            if (r.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public List<DtReserva> listarReservas(DtAerolinea aerolinea) {
        List<DtReserva> listaReservas = new ArrayList<>();
        Aerolinea a = (Aerolinea) this.usuarios.get(aerolinea.getNickname());
        if (a == null) {
            return listaReservas;
        }
        for (Reserva r : this.reservas) {
            if (this.aerolineaTieneRuta(aerolinea, r.getVuelo().getRutaDeVuelo().getNombre())) {
                listaReservas.add(r.getDatos());
            }
        }
        return listaReservas;
    }

    public DtCliente getCliente(String nickname) {
        Usuario u = this.usuarios.get(nickname);
        if (u == null) {
            throw new IllegalArgumentException("No existe un usuario con ese nickname.");
        }
        if (u instanceof Cliente c) {
            return c.getDatos();
        } else {
            throw new IllegalArgumentException("El usuario no es un cliente.");
        }
    }

    public DtRuta consultarRuta(String nombre) {
        if (!this.rutasDeVuelo.containsKey(nombre)) throw new IllegalArgumentException("No existe esta ruta");
        return (this.rutasDeVuelo.get(nombre).getDatos());
    }

    public DtVuelo consultarVuelo(String nombre) {
        for (Vuelo v : consultaVuelos) {
            if (v.getNombre().equals(nombre)) {
                return v.getDatos();
            }
        }
        return null;
    }

    public boolean existeRuta(String nombre) {
        for (RutaDeVuelo r : this.getRutasDeVuelo()) {
            if (r.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void altaRutaDeVuelo(String nombreAerolinea, DtRuta datosRuta) {
        Aerolinea aerolinea = (Aerolinea) userDao.buscar(nombreAerolinea);

        if (aerolinea == null) throw new IllegalArgumentException("Aerolinea no existe");

        if (this.rutasDeVuelo.containsKey(datosRuta.getNombre()))
            throw new IllegalArgumentException("Ya existe esa ruta de vuelo.");

        RutaDeVuelo nuevaRuta = new RutaDeVuelo(datosRuta);

        if (aerolinea.getRutasDeVuelo().contains(nuevaRuta))
            throw new IllegalArgumentException("Ya existe esa ruta de vuelo en esa aerolinea");

        this.rutasDeVuelo.put(nuevaRuta.getNombre(), nuevaRuta); // Guardas la ruta en el sistema
        this.rutaDeVueloDao.guardar(nuevaRuta); // Persistimos la nuevaRuta
        this.userDao.addRutaDeVuelo(aerolinea, nuevaRuta); // La agregamos a su aerolinea
    }

    public boolean validarUsuario(String nickname, String password) {
        Usuario u = this.usuarios.get(nickname);
        if (u == null) return false;
        return u.getPassword().equals(password);
    }


    public List<DtUsuario> listarUsuarios() {
        List<DtUsuario> usuarios = new ArrayList<>();
        for (Usuario u : this.getUsuarios()) {
            usuarios.add(u.getDatos());
        }
        return usuarios;
    }

    public List<DtCiudad> listarCiudades() {
        List<DtCiudad> listaCiudades = new ArrayList<>();
        for (Ciudad c : this.getCiudades()) {
            listaCiudades.add(c.getDatos());
        }
        return listaCiudades;
    }

    public void elegirUsuario(String nickname) {
        Usuario u = this.usuarios.get(nickname);
        if (u == null) {
            throw new IllegalArgumentException("No existe un usuario con ese nickname.");
        }
        this.usuarioSeleccionado = u;
    }

    public void borrarUsuarioSeleccionado() {
        if (this.usuarioSeleccionado == null) throw new IllegalArgumentException("No hay un usuario seleccionado.");
        this.usuarioSeleccionado = null;
    }

    public DtUsuario getUsuarioSeleccionado() {
        if (usuarioSeleccionado == null) return null;

        if (usuarioSeleccionado instanceof Cliente) {
            Cliente c = (Cliente) usuarioSeleccionado;
            return c.getDatos();
        } else if (usuarioSeleccionado instanceof Aerolinea) {
            Aerolinea a = (Aerolinea) usuarioSeleccionado;
            return a.getDatos();
        }
        return new DtUsuario(
                usuarioSeleccionado.getNickname(),
                usuarioSeleccionado.getNombre(),
                usuarioSeleccionado.getEmail(),
                usuarioSeleccionado.getUrlImage()
        );
    }

    public List<DtReserva> mostrarReservas() {
        if (usuarioSeleccionado instanceof Cliente) {
            Cliente c = (Cliente) usuarioSeleccionado;
            List<DtReserva> dtReservas = new ArrayList<>();

            for (DtReserva r : c.getDtReservas()) {
                dtReservas.add(new DtReserva(
                        r.getFecha(),
                        r.getTipoAsiento(),
                        r.getCantPasajes(),
                        r.getEquipajeExtra(),
                        r.getCosto(),
                        r.getPasajeros(),
                        c, // Cliente
                        r.getVuelo(),
                        r.getPaquete(),
                        r.getMetodoPago()
                ));
            }
            return dtReservas;
        }
        return new ArrayList<>();
    }

    public List<DtPaquete> mostrarPaquetes() {
        List<DtPaquete> paquetes = new ArrayList<>();

        if (usuarioSeleccionado instanceof Cliente) {
            Cliente cliente = (Cliente) usuarioSeleccionado;
            for (DtPaquete paquete : cliente.getPaquetesAdquiridos()) {
                paquetes.add(new DtPaquete(
                        paquete.getNombre(),
                        paquete.getDescripcion(),
                        paquete.getValidezDias(),
                        paquete.getDescuento(),
                        paquete.getCosto(),
                        paquete.getRutaEnPaquete()
                ));
            }
        }
        return paquetes;
    }

    public List<DtRuta> mostrarRutasDeVuelo() {
        List<DtRuta> rutas = new ArrayList<>();

        if (usuarioSeleccionado instanceof Aerolinea) {
            Aerolinea aerolinea = (Aerolinea) usuarioSeleccionado;

            // Convertir cada RutaDeVuelo a DtRutaDeVuelo
            for (RutaDeVuelo ruta : aerolinea.getRutasDeVuelo()) {
                rutas.add(convertirRutaADtRuta(ruta));
            }
        }
        return rutas;
    }

    private DtRuta convertirRutaADtRuta(RutaDeVuelo ruta) {
        return ruta.getDatos();
    }

    public List<DtPaquete> listarPaquetes() {
        List<DtPaquete> listaPaquetes = new ArrayList<>();
        for (Paquete p : this.getPaquetes()) {
            listaPaquetes.add(p.getDatos());
        }
        return listaPaquetes;
    }

    public List<DtPaquete> listarPaquetes(DtCliente c) {
        Cliente cliente = (Cliente) this.usuarios.get(c.getNickname());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        List<DtPaquete> listaPaquetes = new ArrayList<>();
        for (CompraPaquete cp : cliente.getComprasPaquetes()) {
            listaPaquetes.add(cp.getPaquete().getDatos());
        }
        return listaPaquetes;
    }

    public List<DtPaquete> listarPaquetes(DtAerolinea a) {
        Aerolinea aerolinea = (Aerolinea) this.usuarios.get(a.getNickname());
        if (aerolinea == null) {
            throw new RuntimeException("Aerolinea no encontrada");
        }

        List<DtRuta> rutasAerolinea = a.listarRutasDeVuelo();
        if (rutasAerolinea == null || rutasAerolinea.isEmpty()) {
            return Collections.emptyList();
        }

        // Conjunto de nombres de rutas de la aerolínea para búsqueda rápida
        Set<String> nombresRutas = rutasAerolinea.stream()
                .map(DtRuta::getNombre)
                .collect(Collectors.toSet());

        // Filtrar paquetes que contengan alguna de esas rutas
        return this.listarPaquetes().stream()
                .filter(p -> p.getRutaEnPaquete().stream()
                        .anyMatch(rp -> nombresRutas.contains(rp.getRutaDeVuelo().getNombre())))
                .collect(Collectors.toList());
    }

    public List<DtPaquete> listarPaquetesNoComprados() {
        List<DtPaquete> listaPaquetes = new ArrayList<>();
        for (Paquete p : this.getPaquetes()) {
            boolean esComprado = false;
            for (CompraPaquete cp : this.compras) {
                if (cp.getPaquete().equals(p)) {
                    esComprado = true;
                    break;
                }
            }
            if (!esComprado) listaPaquetes.add(p.getDatos());
        }
        return listaPaquetes;

    }

    public List<DtPaquete> listarPaquetesConRutas() {
        List<DtPaquete> listaPaquetes = new ArrayList<>();
        for (Paquete p : this.getPaquetes()) {
            if (!p.getRutaEnPaquete().isEmpty()) {
                listaPaquetes.add(p.getDatos());
            }
        }
        return listaPaquetes;
    }

    public List<DtCliente> listarClientes() {
        List<DtCliente> listaClientes = new ArrayList<>();
        for (Cliente c : this.getClientes()) {
            listaClientes.add(c.getDatos());
        }
        return listaClientes;
    }

    public List<Ciudad> getCiudades() {
        return new ArrayList<>(this.ciudades.values());
    }

    public List<Paquete> getPaquetes() {
        return new ArrayList<>(this.paquetes.values());
    }

    public List<Vuelo> getVuelos() {
        return new ArrayList<>(this.vuelos.values());
    }

    public List<RutaDeVuelo> getRutasDeVuelo() {
        return new ArrayList<>(this.rutasDeVuelo.values());
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(this.usuarios.values());
    }

    ;

    public List<Cliente> getClientes() {
        List<Usuario> usuarios = this.getUsuarios();
        List<Cliente> clientes = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Cliente) {
                clientes.add((Cliente) u);
            }
        }
        return clientes;
    }

    ;

    public List<Aerolinea> getAerolineas() {
        List<Aerolinea> listaAerolineas = new ArrayList<>();
        List<Usuario> listaUsuarios = this.getUsuarios();
        for (Usuario u : listaUsuarios) {
            if (u instanceof Aerolinea) {
                Aerolinea a = (Aerolinea) u;
                listaAerolineas.add(a);
            }
        }
        return listaAerolineas;
    }

    public DtPaquete getPaquete() {
        return this.paqueteSeleccionado.getDatos();
    }

    public void seleccionarAeroLinea(String nickname) {
        Aerolinea aerolinea = null;
        for (Aerolinea a : this.getAerolineas()) {
            if (a.getNickname().equals(nickname)) {
                aerolinea = a;
                break;
            }
        }
        if (aerolinea == null) {
            throw new IllegalArgumentException("No existe una aerolinea con ese nombre.");
        }
        this.aerolineaTemporalClase = aerolinea;
    }

    public List<DtRuta> listarRutasDeVuelo() {
        List<DtRuta> listaRutas = new ArrayList<>();
        for (RutaDeVuelo r : this.getRutasDeVuelo()) {
            listaRutas.add(r.getDatos());
        }
        return listaRutas;
    }

    public List<DtRuta> listarRutasDeVuelo(DtPaquete p) {
        List<DtRuta> listaRutas = new ArrayList<>();
        List<RutaEnPaquete> rutasDelPaquete = p.getRutaEnPaquete();
        for (RutaEnPaquete r : rutasDelPaquete) {
            listaRutas.add(r.getRutaDeVuelo().getDatos());
        }
        return listaRutas;
    }

    public void seleccionarRutaDeVuelo(String nombre, int cantidad, TipoAsiento tipoAsiento) {
        if (this.aerolineaTemporal == null) {
            throw new IllegalArgumentException("No hay una aerolinea seleccionada.");
        }
        RutaDeVuelo ruta = null;
        for (RutaDeVuelo r : this.aerolineaTemporal.getRutasDeVuelo()) {
            if (r.getNombre().equals(nombre)) {
                ruta = r;
                break;
            }
        }
        if (ruta == null) {
            throw new IllegalArgumentException("No existe una ruta de vuelo con ese nombre para esta aerolinea.");
        }
        if (this.paqueteSeleccionado == null) {
            throw new IllegalArgumentException("No hay un paquete seleccionado.");
        }

        // Verificar si la ruta ya está en el paquete
        for (RutaEnPaquete r : this.paqueteSeleccionado.getRutaEnPaquete()) {
            if (r.getRutaDeVuelo().getNombre().equals(ruta.getNombre())) {
                throw new IllegalArgumentException("La ruta ya está asociada al paquete.");
            }
        }

        RutaEnPaquete tp = new RutaEnPaquete();
        tp.setCantidad(cantidad);
        tp.setTipoAsiento(tipoAsiento);
        this.paqueteSeleccionado.getRutaEnPaquete().add(tp);

    }


    public void altaPaquete(DtPaquete paquete) {
        if (this.paquetes.containsKey(paquete.getNombre())) {
            throw new IllegalArgumentException("Ya existe un paquete con ese nombre.");
        }
        Paquete p = new Paquete(paquete);
        paqueteDao.guardar(p);
        this.paquetes.put(p.getNombre(), p);
    }

    public boolean existePaquete(String nombre) {
        return this.paquetes.containsKey(nombre);
    }

    public boolean existeUsuarioNickname(String nickname) {
        return this.usuarios.containsKey(nickname);
    }

    public boolean existeUsuarioEmail(String email) {
        for (Usuario u : this.usuarios.values()) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void altaCategoria(DtCategoria categoria) {
        if (categoriaDao.buscar(categoria.getNombre()) != null) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        Categoria c = new Categoria(categoria);
        this.categorias.put(c.getNombre(), c);
        categoriaDao.guardar(c);
    }

    public void altaCiudad(DtCiudad ciudad) {
        CiudadId id = new CiudadId(ciudad.getNombre(), ciudad.getPais());
        Ciudad existente = ciudadDao.buscar(id);

        if (existente != null) {
            throw new IllegalArgumentException("Ya existe una ciudad con ese nombre en ese país.");
        }

        // Si no existe, crear
        Ciudad c = new Ciudad(ciudad);
        this.ciudades.put(c.getNombre(), c);
        ciudadDao.guardar(c);
    }


    public List<Categoria> getCategorias() {
        if (categoriaDao.listar().isEmpty()) {
            throw new IllegalArgumentException("No hay categorias.");
        }
        return categoriaDao.listar();
    }

    public void consultaVuelo(DtVuelo vuelo) {
        Vuelo v = this.vuelos.get(vuelo.getNombre());
        if (v == null) {
            throw new IllegalArgumentException("Vuelo no encontrado.");
        }
    }

    public void registrarCliente(DtCliente cliente) {
        if (this.usuarios.containsKey(cliente.getNickname()) || userDao.buscar(cliente.getNickname()) != null) {
            throw new IllegalArgumentException("Este usuario ya existe.");
        }

        System.out.println("pasa");
        for (Usuario existente : this.usuarios.values()) {
            if (existente.getEmail().equals(cliente.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email.");
            }
        }

        confirmarAltaUsuario(cliente);
    }

    ;

    public void modificarCliente(DtCliente cliente) {
        Usuario u = this.usuarios.get(cliente.getNickname());
        if (u == null) {
            throw new IllegalArgumentException("Este usuario no existe.");
        }

        if (u instanceof Cliente c) {
            c.setNombre(cliente.getNombre());
            c.setApellido(cliente.getApellido());
            c.setEmail(cliente.getEmail());
            c.setFechaNacimiento(cliente.getFechaNacimiento());
            c.setNacionalidad(cliente.getNacionalidad());
            c.setTipoDocumento(cliente.getTipoDocumento());
            c.setNumeroDocumento(cliente.getNumeroDocumento());
            userDao.actualizar(c);
        } else {
            throw new IllegalArgumentException("Este usuario es aerolinea.");
        }
    }

    ;


    public DtAerolinea getAerolinea(String nickname) {
        Usuario u = this.usuarios.get(nickname);
        if (u == null) {
            throw new IllegalArgumentException("No existe un usuario con ese nickname.");
        }
        if (u instanceof Aerolinea a) {
            return a.getDatos();
        } else {
            throw new IllegalArgumentException("El usuario no es una aerolínea.");
        }
    }

    public void modificarClienteImagen(DtCliente cliente, String urlImagen) {
        Cliente c = this.buscarCliente(cliente);
        if (c == null) {
            throw new IllegalArgumentException("Este usuario no existe.");
        }
        c.setUrlImage(urlImagen);
        userDao.actualizar(c);
    }

    ;

    public void registrarAerolinea(DtAerolinea aerolinea) {
        if (this.usuarios.containsKey(aerolinea.getNickname())) {
            throw new IllegalArgumentException("Este usuario ya existe.");
        }

        for (Usuario existente : this.usuarios.values()) {
            if (existente.getEmail().equals(aerolinea.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email.");
            }
        }
        this.confirmarAltaUsuario(aerolinea);
    }

    ;

    public void modificarAerolinea(DtAerolinea aerolinea) {
        Usuario u = this.usuarios.get(aerolinea.getNickname());
        if (u == null) {
            throw new IllegalArgumentException("Este usuario no existe.");
        }

        if (u instanceof Aerolinea a) {
            a.setNombre(aerolinea.getNombre());
            a.setEmail(aerolinea.getEmail());
            a.setDescripcion(aerolinea.getDescripcion());
            a.setLinkWeb(aerolinea.getLinkWeb());
            userDao.actualizar(a);
            a.mostrarDatos();
        } else {
            throw new IllegalArgumentException("Este usuario es cliente.");
        }
    }

    ;

    public void modificarAerolineaImagen(DtAerolinea aerolinea, String urlImagen) {
        Aerolinea a = this.buscarAerolinea(aerolinea);
        if (a == null) {
            throw new IllegalArgumentException("Este usuario no existe.");
        }
        a.setUrlImage(urlImagen);
        userDao.actualizar(a);
    }

    ;

    public void confirmarAltaUsuario(DtUsuario usuario) {
        System.out.println("dasdsa");
        if (usuario instanceof DtCliente) {
            Cliente newUser = new Cliente((DtCliente) usuario);
            this.usuarios.put(newUser.getNickname(), newUser);
            userDao.guardar(newUser);
            //newUser.mostrarDatos(); //Debug
        } else if (usuario instanceof DtAerolinea) {
            Aerolinea newUser = new Aerolinea((DtAerolinea) usuario);
            this.usuarios.put(newUser.getNickname(), newUser);
            userDao.guardar(newUser);
            //newUser.mostrarDatos(); //Debug
        }
    }

    public List<Categoria> getCategoriasPorNombre(List<String> nombres) {
        List<Categoria> categoriasSeleccionadas = new ArrayList<>();
        for (String nombreCategoria : nombres) {
            for (Categoria c : this.getCategorias()) {
                if (c.getNombre().equals(nombreCategoria)) {
                    categoriasSeleccionadas.add(c);
                    break; // salimos del loop interno
                }
            }
        }
        return categoriasSeleccionadas;
    }

    public void cancelarAltaUsuario() {
        this.clienteTemporal = null;
        this.aerolineaTemporal = null;
    }

    public Ciudad buscarCiudad(String nombre, String pais) {
        CiudadId ciudadId = new CiudadId(nombre, pais);
        Ciudad c = this.ciudadDao.buscar(ciudadId);
        if (c == null) throw new IllegalArgumentException("Esta ciudad no existe.");
        return c;
    }

    public Ciudad buscarCiudadPorNombreYPais(String nombre, String pais) {
        Ciudad c = this.ciudadDao.buscar(new CiudadId(nombre, pais));
        if (c == null) {
            throw new IllegalArgumentException("No existe una ciudad con ese nombre y país.");
        }
        return c;
    }

    // ---------- ALTA DE VUELO ---------- //
    public void seleccionarAerolineaParaVuelo(String nickname) {
        Aerolinea aerolinea = (Aerolinea) userDao.buscar(nickname);
        if (aerolinea == null) {
            throw new IllegalArgumentException("La aerolínea no existe.");
        }
        this.aerolineaTemp = aerolinea;
    }

    public void seleccionarRuta(String nombre) {
        RutaDeVuelo ruta = null;
        for (RutaDeVuelo r : this.aerolineaTemp.getRutasDeVuelo()) {
            if (r.getNombre().equals(nombre)) {
                ruta = r;
                break;
            }
        }
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no existe en esta aerolínea.");
        }

        if (ruta.getEstado() != EstadoRuta.APROBADA) {
            throw new IllegalArgumentException("La ruta de vuelo no está aprobada.");
        }

        this.rutaTemporal = ruta;
    }

    public boolean existeVuelo(String nombre) {
        return this.vuelos.containsKey(nombre);
    }

    public DtRuta getRutaDeVuelo(String nombre) {
        if (!this.rutasDeVuelo.containsKey(nombre)) throw new IllegalArgumentException("No existe esta ruta");
        return (this.rutasDeVuelo.get(nombre).getDatos());
    }


    public void ingresarDatosVuelo(DtVuelo datosVuelo) {
        if (this.rutaTemporal == null) {
            throw new IllegalArgumentException("Debe seleccionar una ruta primero.");
        }
        if (this.vuelos.containsKey(datosVuelo.getNombre())) {
            throw new IllegalArgumentException("Ya existe un vuelo con ese nombre.");
        }
        this.vueloTemporal = datosVuelo;
    }


    public void confirmarAltaVuelo() {
        if (this.vueloTemporal == null) {
            throw new IllegalArgumentException("Debe seleccionar un vuelo");
        }
        if (this.aerolineaTemp == null) {
            throw new IllegalArgumentException("Debe seleccionar una aerolinea");
        }

        if (this.rutaTemporal == null) {
            throw new IllegalArgumentException("Debe seleccionar una ruta");
        }

        Vuelo nuevo = new Vuelo(this.vueloTemporal);
        nuevo.setRutaDeVuelo(this.rutaTemporal);

        this.vuelos.put(nuevo.getNombre(), nuevo);
        this.consultaVuelos.add(nuevo);
        this.vueloDao.guardar(nuevo);

        this.vueloTemporal = null;
        this.rutaTemporal = null;
        this.aerolineaTemp = null;
    }

    public void cancelarAlta() {
        this.rutaTemporal = null;
        this.aerolineaTemp = null;
        this.vueloTemporal = null;
    }

    public DtVuelo getVuelo(String nombre) {
        for (Vuelo v : this.getVuelos()) {
            if (v.getNombre().trim().equals(nombre.trim())) {
                return v.getDatos();
            }
        }
        throw new IllegalArgumentException("No existe este vuelo");
    }


    // ---------- COMPRA PAQUETE ---------- //
    public Cliente buscarCliente(DtCliente c) {
        Cliente cliente = (Cliente) this.usuarios.get(c.getNickname());
        if (cliente == null) {
            throw new IllegalArgumentException(("El cliente no existe"));
        }
        return cliente;
    }

    public Paquete buscarPaquete(DtPaquete paquete) {
        Paquete p = this.paquetes.get(paquete.getNombre());
        if (p == null) {
            throw new IllegalArgumentException(("El paquete no existe"));
        }
        return p;
    }

    public RutaDeVuelo buscarRutaDeVuelo(DtRuta ruta) {
        RutaDeVuelo r = this.rutasDeVuelo.get(ruta.getNombre());
        if (r == null) {
            throw new IllegalArgumentException(("La ruta de vuelo no existe"));
        }
        return r;
    }

    public void compraPaquete(DtPaquete paquete, DtCliente cliente) {
        Cliente c = buscarCliente(cliente);
        Paquete p = buscarPaquete(paquete);


        for (CompraPaquete cp : c.getComprasPaquetes()) {
            if (cp.getPaquete().equals(p)) {
                throw new IllegalArgumentException("El cliente ya ha comprado este paquete.");
            }
        }

        LocalDate vencimiento = LocalDate.now().plusDays(p.getValidezDias());

        CompraPaquete nuevaCompra = new CompraPaquete(new DtCompraPaquete(LocalDate.now(), vencimiento, p.getCosto(), p, c));

        this.compras.add(nuevaCompra);
        userDao.addCompraPaquete(c, nuevaCompra);
    }

    public int agregarRutaAPaquete(DtPaquete paquete, DtRuta dataRuta, int cantidad, TipoAsiento tipoAsiento) {
        Paquete p = buscarPaquete(paquete);
        RutaDeVuelo ruta = buscarRutaDeVuelo(dataRuta);

        float nuevoCosto = p.getCosto();

        if (tipoAsiento == TipoAsiento.EJECUTIVO) {
            nuevoCosto += ruta.getCostoEjecutivo() * cantidad;
        } else if (tipoAsiento == TipoAsiento.TURISTA) {
            nuevoCosto += ruta.getCostoTurista() * cantidad;
        }

        for (RutaEnPaquete rep : p.getRutaEnPaquete()) {
            if (rep.getRutaDeVuelo().getNombre().equals(ruta.getNombre()) && rep.getTipoAsiento() == tipoAsiento) {
                p.setCosto(nuevoCosto);
                System.out.println(nuevoCosto);
                return paqueteDao.actualizarCantidadRutaEnPaquete(p, rep, cantidad);
            }
        }

        RutaEnPaquete rp = new RutaEnPaquete(cantidad, tipoAsiento, ruta);
        p.setCosto(nuevoCosto);
        System.out.println(nuevoCosto);


        paqueteDao.addRutaEnPaquete(p, rp);

        return 0;
    }

    public List<DtVuelo> getVuelosRutaDeVuelo(DtRuta ruta) {
        List<DtVuelo> vuelos = new ArrayList<>();

        for (Vuelo v : this.getVuelos()) {
            if (v.getRutaDeVuelo().getNombre().equals(ruta.getNombre())) {
                vuelos.add(v.getDatos());
            }
        }
        return vuelos;
    }

    public DtPaquete getPaquete(String nombre) {
        Paquete p = this.paquetes.get(nombre);
        if (p == null) {
            throw new IllegalArgumentException(("El paquete no existe"));
        }
        return p.getDatos();
    }

    public List<DtReserva> listarReservaDeVuelo(String nombreVuelo) {
        List<DtReserva> reservasVuelo = new ArrayList<>();

        for (Reserva r : this.reservas) {
            if (r.getVuelo() != null && r.getVuelo().getNombre().equals(nombreVuelo)) {
                reservasVuelo.add(r.getDatos());
            }
        }
        return reservasVuelo;
    }

    public List<DtVuelo> listarVuelos() {
        List<DtVuelo> vuelos = new ArrayList<>();
        for (Vuelo v : this.getVuelos()) {
            vuelos.add(v.getDatos());
        }
        return vuelos;
    }

    public List<DtVuelo> listarVuelos(String nombreRuta) {
        List<DtVuelo> vuelos = new ArrayList<>();
        for (Vuelo v : this.getVuelos()) {
            if (v.getRutaDeVuelo().getNombre().equals(nombreRuta) && v.getFecha().isAfter(LocalDate.now())) {
                vuelos.add(v.getDatos());
            }
        }
        return vuelos;
    }

    public List<DtVuelo> listarVuelos(String nombreRuta, LocalDate fecha){
            List<DtVuelo> vuelos = new ArrayList<>();
            for (Vuelo v : this.getVuelos()) {
                boolean condicionFecha = fecha == null || v.getFecha().isAfter(fecha);
                if (v.getRutaDeVuelo().getNombre().equals(nombreRuta) && v.getFecha().isAfter(LocalDate.now()) && condicionFecha) {
                    vuelos.add(v.getDatos());
                }
            }
            return vuelos;
    }

    public void crearPaqueteDeRutas(DtPaquete datosPaquete) {
        Paquete paquete = paqueteDao.buscar((datosPaquete.getNombre()));
        if (paquete == null) { //Si el paquete no existe
            this.paqueteTemp = datosPaquete;
        } else {
            throw new IllegalArgumentException("El paquete ya existe.");
        }
    }

    public void confirmarAltaPaquete() {
        if (this.paqueteTemp == null) {
            throw new IllegalArgumentException("Debe ingresar datos del paquete");
        }
        Paquete nuevo = new Paquete(paqueteTemp);

        paqueteDao.guardar(nuevo);

        this.paquetes.put(nuevo.getNombre(), nuevo);
        this.paqueteTemp = null;
    }

    public void cancelarAltaPquete() {
        paqueteTemp = null;
    }

    public Vuelo buscarVuelo(DtVuelo vuelo) {
        Vuelo v = (Vuelo) this.vuelos.get(vuelo.getNombre());
        if (v == null) {
            throw new IllegalArgumentException("El vuelo no existe.");
        }
        return v;
    }

    public void altaReserva(DtReserva reserva, DtCliente cliente, DtVuelo vuelo) {
        Cliente c = buscarCliente(cliente);
        Vuelo v = buscarVuelo(vuelo);
        float costoAsiento = reserva.getTipoAsiento() == TipoAsiento.EJECUTIVO ? v.getRutaDeVuelo().getCostoEjecutivo() : v.getRutaDeVuelo().getCostoTurista();
        int cantPasajes = reserva.getCantPasajes();
        float costoEquipaje = reserva.getEquipajeExtra() * v.getRutaDeVuelo().getEquipajeExtra();
        float costo = (costoAsiento * cantPasajes) + costoEquipaje;

        if(reserva.getMetodoPago() == MetodoPago.PAQUETE){
            int pasajesRestantes = cantPasajes;
            List<DtPaquete> paquetesComprados = listarPaquetes(cliente);

            for (DtPaquete p : paquetesComprados){
                Paquete paq = buscarPaquete(p);
                for (RutaEnPaquete rep : paq.getRutaEnPaquete()){
                    if(rep.getRutaDeVuelo().equals(v.getRutaDeVuelo())) {
                        int descontar = Math.min(pasajesRestantes, rep.getCantidad());
                        rep.setCantidad(rep.getCantidad() - descontar);
                        pasajesRestantes -= descontar;
                        if (pasajesRestantes == 0) break;
                    }
                }
                if(pasajesRestantes == 0) break;
            }
            costo = pasajesRestantes * (reserva.getTipoAsiento() == TipoAsiento.EJECUTIVO ? v.getRutaDeVuelo().getCostoEjecutivo() : v.getRutaDeVuelo().getCostoTurista());
            costo += reserva.getEquipajeExtra() * v.getRutaDeVuelo().getEquipajeExtra();
        }

        if (c.existeVueloReserva(v)) {
            throw new IllegalArgumentException("Ya existe una reserva para este vuelo. Cambie el Cliente, Aerolinea o RutaDeVuelo.");
        }

        if (cantPasajes != reserva.getPasajeros().size()) {
            throw new IllegalArgumentException("La cantidad de pasajeros no coincide");
        }

        DtReserva r = new DtReserva(
                reserva.getFecha(),
                reserva.getTipoAsiento(),
                cantPasajes,
                reserva.getEquipajeExtra(),
                costo,
                reserva.getPasajeros(),
                c,
                v
        );

        Reserva nuevaReserva = new Reserva(r);
        this.reservas.add(nuevaReserva);
        this.reservaDao.guardar(nuevaReserva);
        this.userDao.addReserva(c, nuevaReserva);

        v.setCantReservas(v.getCantReservas() + 1);
        this.vueloDao.actualizar(v);

        for (DtPasajero p : reserva.getPasajeros()) {
            this.pasajeroDao.guardar(p);
        }

    }

    public List<DtReserva> listarReservas() {
        List<DtReserva> listaReservas = new ArrayList<>();
        for (Reserva r : this.reservas) {
            listaReservas.add(r.getDatos());
        }
        return listaReservas;
    }

    ;

    public List<DtReserva> listarReservas(DtVuelo vuelo) {
        String nombre = vuelo.getNombre();
        List<DtReserva> listaReservas = new ArrayList<>();
        for (Reserva r : this.reservas) {
            if (r.getVuelo().getNombre().equals(nombre)) {
                listaReservas.add(r.getDatos());
            }
        }
        return listaReservas;
    }

    ;

    public List<DtReserva> listarReservas(DtCliente cliente) {
        String nickname = cliente.getNickname();
        List<DtReserva> listaReservas = new ArrayList<>();
        for (Reserva r : this.reservas) {
            if (r.getCliente().getNickname().equals(nickname)) {
                listaReservas.add(r.getDatos());
            }
        }
        return listaReservas;
    }

    ;

    public List<DtPasajero> listarPasajeros() {
        return this.pasajes;
    }

    ;

    public List<DtPasajero> listarPasajeros(DtReserva reserva) {
        return reserva.getPasajeros();
    }

    ;

    public Aerolinea buscarAerolinea(DtAerolinea a) {
        Aerolinea aerolinea = (Aerolinea) this.usuarios.get(a.getNickname());
        if (aerolinea == null) {
            throw new IllegalArgumentException(("El cliente no existe"));
        }
        return aerolinea;
    }

    public void actualizarEstadoRuta(DtRuta ruta, EstadoRuta estado) {
        RutaDeVuelo rdv = buscarRutaDeVuelo(ruta);
        rdv.setEstado(estado);
        rutaDeVueloDao.actualizar(rdv);
    }

    ;

    public DtCategoria buscarCategoria(String nombre) {
        DtCategoria categoria = this.categorias.get(nombre).getDatos();
        return categoria;
    }

    ;

    public boolean containsCategoria(DtRuta ruta, String categoria) {
        for (Categoria c : ruta.getCategorias()) {
            if (c.getNombre().equals(categoria)) {
                return true;
            }
        }
        return false;
    }

    ;

    public boolean clienteTienePaquete(String nickname, String nombrePaquete){
        DtCliente c = getCliente(nickname);
        Cliente cliente = buscarCliente(c);
        DtPaquete paquete = getPaquete(nombrePaquete);
        for(CompraPaquete paquetesComprados : cliente.getComprasPaquetes()){
            if(paquetesComprados.getPaquete().getNombre().equals(paquete.getNombre())){
                return true;
            }
        }
        return false;
    };

}

