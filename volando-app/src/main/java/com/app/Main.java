package com.app;

import com.app.clases.*;
import com.app.datatypes.*;
import com.app.enums.*;
import com.app.interfaces.*;
import com.app.utils.AuxiliarFunctions;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    //Declaración de variables "importantes"
    private ISistema s;
    private AuxiliarFunctions auxiliar;
    private List<JCheckBox> checkboxes;

    // Para gestión imagenes
    private File imagenTemporalUsuario;
    private File imagenTemporalVuelo;
    private File imagenTemporalRuta;

    //Declaració de JavaSwing
    private JPanel menuPrincipal;
    private JTabbedPane gestionUsuarios;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JComboBox JComboBoxRutaVueloAltaVuelo;
    private JTextField nombreAltaVuelo;
    private JSpinner JSpinnerTuristasAltaVuelo;
    private JSpinner JSpinnerEjecutivosAltaVuelo;
    private JSpinner JSpinnerDiaAltaVuelo;
    private JSpinner JSpinnerAñoAltaVuelo;
    private JSpinner JSpinnerMesAltaVuelo;
    private JComboBox JComboBoxAerolineaAltaVuelo;
    private JPanel formVuelo;
    private JPanel fechaField;
    private JButton crearVueloButton;
    private JPanel vueloPanel;
    private JComboBox JComboBoxConsultaVueloAerolinea;
    private JComboBox JComboBoxConsultaVueloRutaDeVuelo;
    private JComboBox JComboBoxConsultaVueloVuelo;
    private JButton consultarVueloButton;
    private JTextField descripcionAltaPaquete;
    private JButton CANCELARButton;
    private JButton CONFIRMARButton;
    private JButton consultarUsuarioButton;
    private JTextField webAltaCiudad;
    private JButton btnConfirmarAltaCiudad;
    private JTextField nombreAltaCiudad;
    private JTextField paisAltaCiudad;
    private JTextField aeropuertoAltaCiudad;
    private JTextField descripcionAltaCiudad;
    private JPanel JPanelRegistrarCliente;
    private JPanel JPanelRegistrarAerolinea;
    private JTextField nicknameRegistrarCliente;
    private JTextField nombreRegistrarCliente;
    private JTextField correoElectronicoRegistrarCliente;
    private JTextField apellidoRegistrarCliente;
    private JTextField nacionalidadRegistrarCliente;
    private JComboBox tipoDocumentoRegistrarCliente;
    private JTextField documentoRegistrarCliente;
    private JButton confirmarAltaCliente;
    private JButton cancelarAltaCliente;
    private JTextField nicknameRegistrarAerolinea;
    private JTextField nombreRegistrarAerolinea;
    private JTextField correoRegistrarAerolinea;
    private JTextField sitioWebRegistrarAerolinea;
    private JTextField descripcionRegistrarAerolinea;
    private JButton cancelarAltaAerolinea;
    private JButton confirmarAltaAerolinea;
    private JTabbedPane tabbedPane4;
    private JComboBox JComboBoxCiudadDestino;
    private JSpinner SpinnerCostoTurista;
    private JSpinner SpínnerCostoEjecutivo;
    private JSpinner SpinnerCostoEquipaje;
    private JComboBox userType;
    private JPanel reservaPanel;
    private JComboBox JComboBoxseleccionarAerolineaReserva;
    private JComboBox JComboBoxrutaDeVueloReserva;
    private JComboBox JComboBoxvueloReserva;
    private JButton JButtonVerVueloReservaButton;
    private JComboBox JComboBoxSeleccionarClienteReserva;
    private JComboBox JComboBoxtipoAsientoReserva;
    private JSpinner JSpinnerCantPasajesReserva;
    private JSpinner JSpinnerCantEquipajeExtraReserva;
    private JButton hacerReservaButton;
    private JSpinner JSpinnerPeriodoAltaPaquete;
    private JSpinner JSpinnerDescuentoAltaPaquete;
    private JTextField nombreAltaPaquete;
    private JComboBox JComboBoxPaqueteComprarPaquete;
    private JComboBox JComboBoxClienteCompraPaquete;
    private JButton ButtonConfirmarCompraPaquete;
    private JButton CANCELARButton1;
    private JPanel JPanelModificarAerolinea;
    private JButton JButtonModificarAerolinea;
    private JTextField nicknameModificarAerolinea;
    private JPanel JPanelModificarCliente;
    private JTextField nicknameModificarCliente;
    private JComboBox JComboBoxSeleccionarUsuarioModificar;
    private JTextField nombreClienteModificar;
    private JTextField correoClienteModificar;
    private JTextField apellidoClienteModificar;
    private JSpinner fechaDiaClienteModificar;
    private JSpinner fechaMesClienteModificar;
    private JSpinner fechaAnioClienteModificar;
    private JTextField nacionalidadClienteModificar;
    private JComboBox tipoDocumentoClienteModificar;
    private JComboBox JComboBoxSeleccionarUsuarioConsultar;
    private JSpinner JSpinnerDuracionAltaVueloHora;
    private JSpinner JSpinnerDuracionAltaVueloMinuto;
    private JTextField nombreAltaCategoría;
    private JButton confirmarAltaCategoria;
    private JSpinner fechaDiaRegistrarCliente;
    private JSpinner fechaMesRegistrarCliente;
    private JSpinner fechaAnioRegistrarCliente;
    private JButton crearRutaDeVuelo;
    private JTextField nombreAltaRutaDeVuelo;
    private JTextField descripcionAltaRutaDeVuelo;
    private JComboBox JComboBoxCiudadOrigen;
    private JSpinner SpinnerMinutoAltaRutaDeVuelo;
    private JSpinner SpinnerHoraAltaRutaDeVuelo;
    private JComboBox JComboBoxAerolineaAltaRutaVuelo;
    private JComboBox JComboBoxPaqueteConsultaPaqueteRutaVuelo;
    private JButton JButtonConsultarPaquete;
    private JPanel JPanelCategorias;
    private JSpinner JSpinnerCostoAltaPaquete;
    private JComboBox JComboBoxAerolineaConsultaRuta;
    private JComboBox JComboBoxRutaVueloAgregarRuta;
    private JButton ButtonConfirmarAgregarRutaPaquete;
    private JSpinner JSpinnerCantidadAgregarRuta;
    private JComboBox JComboBoxTipoAsientoAgregarRutaPaquete;
    private JComboBox JComboBoxPaqueteAgregarRuta;
    private JComboBox JComboBoxAerolineaAgregarRuta;
    private JComboBox JComboBoxRutaVueloConsultaRuta;
    private JButton ButtonConsultarRutaVuelo;
    private JTextField nombreModificarAerolinea;
    private JTextField correoModificarAerolinea;
    private JTextField linkWebModificarAerolinea;
    private JTextField descripcionModificarAerolinea;
    private JButton confirmarModificarCliente;
    private JTextField numeroDocumentoModificarCliente;
    private JButton CancelarAltaCategori;
    private JPasswordField registrarContrasenaCliente;
    private JPanel clienteSubirImagePanel;
    private JPasswordField registrarContrasenaCliente2;
    private JButton subirImagenButton;
    private JLabel selectedFileClienteRegistrar;
    private JPanel modificarClienteImagenPanel;
    private JButton modificarClienteImagen;
    private JButton subirImagenImagenAerolineaButton;
    private JPanel aerolineaSubirImagenPanel;
    private JPasswordField registrarAerolineaContrasena;
    private JPasswordField registrarAerolineaContrasena2;
    private JLabel selectedFileAerolineaRegistrar;
    private JPanel modificarAerolineaImagenPanel;
    private JButton modificarAerolineaImagenButton;
    private JButton button1;
    private JLabel selectedFileVueloCrear;
    private JButton button2;
    private JLabel selectedFileRutaCrear;
    private JComboBox seleccionarAerolineaAceptarRechazarRuta;
    private JComboBox seleccionarRutaAceptarRechazarRuta;
    private JButton aprobarButtonAceptarRechazarRuta;
    private JButton rechazarButtonAceptarRechazarRuta;
    private JLabel estadoRutaText;
    private JTextField descripcionCortaAltaRutaDeVuelo;


    public Main() {
        
        //Inicializar Sistema
        s = Factory.getSistema();

        //Inicializar Auxiliar
        this.auxiliar = new AuxiliarFunctions(s);
        this.checkboxes = new ArrayList<>();

        //Inicializar carpetas
        AuxiliarFunctions.initFolders();

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf: " + ex);
            throw new RuntimeException();
        }

        // Configuración del JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Admin Dashboard");
        add(menuPrincipal);
        setVisible(true);

        SwingUtilities.updateComponentTreeUI(this);

        //Settear swing elements visibles false
        JPanelRegistrarAerolinea.setVisible(false);
        JPanelModificarAerolinea.setVisible(false);
        JPanelModificarCliente.setVisible(false);

        //Settear swing elements isEditable false
        nicknameModificarAerolinea.setEditable(false);
        nicknameModificarCliente.setEditable(false);
        correoModificarAerolinea.setEditable(false);
        correoClienteModificar.setEditable(false);

        //Settear swing elements isEnabled false
        JComboBoxrutaDeVueloReserva.setEnabled(false);
        JComboBoxvueloReserva.setEnabled(false);
        JComboBoxSeleccionarClienteReserva.setEnabled(false);
        JComboBoxtipoAsientoReserva.setEnabled(false);
        JButtonVerVueloReservaButton.setEnabled(false);
        aprobarButtonAceptarRechazarRuta.setEnabled(false);
        rechazarButtonAceptarRechazarRuta.setEnabled(false);

        //Settear modelos JComboBox
        JComboBoxSeleccionarUsuarioModificar.setModel(auxiliar.getComboUserModel());
        JComboBoxSeleccionarUsuarioConsultar.setModel(auxiliar.getComboUserModel());

        JComboBoxClienteCompraPaquete.setModel(auxiliar.getComboClienteModel());
        JComboBoxSeleccionarClienteReserva.setModel(auxiliar.getComboClienteModel());

        JComboBoxAerolineaAltaVuelo.setModel(auxiliar.getComboAerolineaModel());
        JComboBoxAerolineaAltaRutaVuelo.setModel(auxiliar.getComboAerolineaModel());
        JComboBoxAerolineaConsultaRuta.setModel(auxiliar.getComboAerolineaModel());
        JComboBoxseleccionarAerolineaReserva.setModel(auxiliar.getComboAerolineaModel());
        JComboBoxAerolineaAgregarRuta.setModel(auxiliar.getComboAerolineaModel());
        JComboBoxConsultaVueloAerolinea.setModel(auxiliar.getComboAerolineaModel());
        JComboBoxAerolineaConsultaRuta.setModel(auxiliar.getComboAerolineaModel());
        seleccionarAerolineaAceptarRechazarRuta.setModel(auxiliar.getComboAerolineaModel());

        JComboBoxRutaVueloAltaVuelo.setModel(auxiliar.getComboRutaDeVueloModel());

        JComboBoxCiudadOrigen.setModel(auxiliar.getComboCiudadOrigenModel());

        JComboBoxCiudadDestino.setModel(auxiliar.getComboCiudadDestinoModel());

        JComboBoxPaqueteConsultaPaqueteRutaVuelo.setModel(auxiliar.getComboPaqueteModel());

        JComboBoxPaqueteComprarPaquete.setModel(auxiliar.getComboPaquetesConRutasModel());

        JComboBoxPaqueteAgregarRuta.setModel(auxiliar.getComboPaqueteNoCompradoModel());

        JComboBoxRutaVueloAgregarRuta.setModel(auxiliar.getComboRutaDeVueloAerolineaModel());
        JComboBoxRutaVueloConsultaRuta.setModel(auxiliar.getComboRutaDeVueloAerolineaModel());
        JComboBoxrutaDeVueloReserva.setModel(auxiliar.getComboRutaDeVueloAerolineaModel());
        JComboBoxConsultaVueloRutaDeVuelo.setModel(auxiliar.getComboRutaDeVueloAerolineaModel());
        JComboBoxRutaVueloAltaVuelo.setModel(auxiliar.getComboRutaDeVueloAerolineaModel());
        seleccionarRutaAceptarRechazarRuta.setModel(auxiliar.getComboRutaDeVueloAerolineaModel());

        JComboBoxvueloReserva.setModel(auxiliar.getComboVueloRutaDeVueloModel());
        JComboBoxConsultaVueloVuelo.setModel(auxiliar.getComboVueloRutaDeVueloModel());

        JPanelCategorias.setLayout(new GridLayout(0, 2, 5, 5));

        // Es para mostrar las categorias en alta ruta vuelo
        cargarCategorias();


        // Configurar JSpinner
        JSpinnerDiaAltaVuelo.setModel(new SpinnerNumberModel(1, 1, 31, 1));

        JSpinnerMesAltaVuelo.setModel(new SpinnerNumberModel(1, 1, 12, 1));

        JSpinnerAñoAltaVuelo.setModel(new SpinnerNumberModel(2025, 2025, 2030, 1));

        JSpinnerEjecutivosAltaVuelo.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinnerTuristasAltaVuelo.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinnerCantPasajesReserva.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinnerCantEquipajeExtraReserva.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        SpinnerCostoTurista.setModel(new SpinnerNumberModel(1, 1, 1_000_000, 1));
        SpínnerCostoEjecutivo.setModel(new SpinnerNumberModel(1, 1, 1_000_000, 1));
        SpinnerCostoEquipaje.setModel(new SpinnerNumberModel(1, 1, 1_000_000, 1));

        JSpinnerCantidadAgregarRuta.setModel(new SpinnerNumberModel(1, 1, 1_000_000, 1));

        SpinnerHoraAltaRutaDeVuelo.setModel(new SpinnerNumberModel(0, 0, 24, 1));
        JSpinnerDuracionAltaVueloHora.setModel(new SpinnerNumberModel(0, 0, 24, 1));

        SpinnerMinutoAltaRutaDeVuelo.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        JSpinnerDuracionAltaVueloMinuto.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        JSpinnerDescuentoAltaPaquete.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        JSpinnerCostoAltaPaquete.setModel(new SpinnerNumberModel(1.0, 1.0, 1_000_000.0, 1.0));
        JSpinnerPeriodoAltaPaquete.setModel(new SpinnerNumberModel(1, 1, 1_000_000, 1));


        //¡Cargar datitos!
        auxiliar.cargarTodosLosDatos();

        JComboBoxSeleccionarUsuarioModificar.setSelectedIndex(-1);
        JComboBoxSeleccionarUsuarioConsultar.setSelectedIndex(-1);
        JComboBoxAerolineaAltaVuelo.setSelectedIndex(-1);
        JComboBoxRutaVueloAltaVuelo.setSelectedIndex(-1);
        JComboBoxCiudadOrigen.setSelectedIndex(-1);
        JComboBoxCiudadDestino.setSelectedIndex(-1);
        JComboBoxAerolineaAltaRutaVuelo.setSelectedIndex(-1);
        JComboBoxPaqueteConsultaPaqueteRutaVuelo.setSelectedIndex(-1);
        JComboBoxseleccionarAerolineaReserva.setSelectedIndex(-1);
        JComboBoxrutaDeVueloReserva.setSelectedIndex(-1);
        JComboBoxvueloReserva.setSelectedIndex(-1);
        JComboBoxSeleccionarClienteReserva.setSelectedIndex(-1);
        JComboBoxtipoAsientoReserva.setSelectedIndex(-1);
        JComboBoxPaqueteComprarPaquete.setSelectedIndex(-1);
        JComboBoxConsultaVueloAerolinea.setSelectedIndex(-1);
        JComboBoxAerolineaConsultaRuta.setSelectedIndex(-1);
        JComboBoxPaqueteAgregarRuta.setSelectedIndex(-1);
        JComboBoxAerolineaAgregarRuta.setSelectedIndex(-1);
        JComboBoxRutaVueloAgregarRuta.setSelectedIndex(-1);



        consultarVueloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxConsultaVueloAerolinea, JComboBoxConsultaVueloRutaDeVuelo, JComboBoxConsultaVueloVuelo)){
                    new VentanaMensaje("Faltan argumentos");
                    return;
                }

                String aerolinea = JComboBoxConsultaVueloAerolinea.getSelectedItem().toString();
                String rutaDeVuelo = JComboBoxConsultaVueloRutaDeVuelo.getSelectedItem().toString();
                String vueloStr = JComboBoxConsultaVueloVuelo.getSelectedItem().toString();
                DtVuelo dtVuelo = (DtVuelo) JComboBoxConsultaVueloVuelo.getSelectedItem();

                System.out.println("Aerolinea: " + aerolinea);
                System.out.println("Ruta de Vuelo: " + rutaDeVuelo);
                System.out.println("Vuelo: " + vueloStr);

                InfoVuelo vuelo = new InfoVuelo(dtVuelo, auxiliar);
                setEnabled(false);

                vuelo.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        setEnabled(true);
                    };
                });

            }
        });

        btnConfirmarAltaCiudad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (auxiliar.estanVaciosJTextField(nombreAltaCiudad, paisAltaCiudad, aeropuertoAltaCiudad, descripcionAltaCiudad)) {
                        new VentanaMensaje("Faltan argumentos");
                        return;
                    }
                    try {
                        s.altaCiudad(new DtCiudad(nombreAltaCiudad.getText(), paisAltaCiudad.getText(), aeropuertoAltaCiudad.getText(), descripcionAltaCiudad.getText(), webAltaCiudad.getText(), LocalDate.now()));
                    } catch (Exception ex) {
                        new VentanaMensaje("Ha fallado el alta ciudad");
                        return;
                    }
                    new VentanaMensaje("Ciudad registrada exitosamente: " + nombreAltaCiudad.getText());

                    auxiliar.cargarCiudadesComboBox();

                } catch (IllegalArgumentException ex) {
                    new VentanaMensaje("Error " + ex.getMessage()); //de validacion
            } catch (Exception ex) {
                new VentanaMensaje("Error al registrar ciudad: " + ex.getMessage());
                }
            }
        });


        hacerReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxseleccionarAerolineaReserva, JComboBoxrutaDeVueloReserva, JComboBoxvueloReserva, JComboBoxSeleccionarClienteReserva, JComboBoxtipoAsientoReserva)){
                    new VentanaMensaje("Faltan argumentos");
                    return;
                }
                // Valores que maneja la reserva
                String aerolinea = JComboBoxseleccionarAerolineaReserva.getSelectedItem().toString();
                String rutaDeVuelo = JComboBoxrutaDeVueloReserva.getSelectedItem().toString();
                DtVuelo vuelo = (DtVuelo) JComboBoxvueloReserva.getSelectedItem();
                DtCliente cliente = (DtCliente) JComboBoxSeleccionarClienteReserva.getSelectedItem();
                TipoAsiento tipoAsiento = JComboBoxtipoAsientoReserva.getSelectedItem().toString() == "Turista" ? TipoAsiento.TURISTA : TipoAsiento.EJECUTIVO;

                int pasajes = (Integer) JSpinnerCantPasajesReserva.getValue();
                int equipajeExtra = (Integer) JSpinnerCantEquipajeExtraReserva.getValue();

                LocalDate fecha = LocalDate.now();

                if(pasajes <= 0){
                    new VentanaMensaje("Pasajes de reservas debe ser mayor a 0");
                    return;
                }

                AgregarPasajero ventanaPasajes = new AgregarPasajero(pasajes);
                setEnabled(false);

                ventanaPasajes.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        List<DtPasajero> listaPasajes = ventanaPasajes.getPasajes();
                        if(listaPasajes.size() == pasajes){
                            DtReserva reserva = new DtReserva(
                                    fecha,
                                    tipoAsiento,
                                    pasajes,
                                    equipajeExtra,
                                    listaPasajes
                            );
                            try{
                                s.altaReserva(reserva, cliente, vuelo);
                                new VentanaMensaje("Reserva realizada exitosamente");
                            } catch (Exception ex) {
                                new VentanaMensaje(ex.getMessage());
                            }
                        } else {
                            new VentanaMensaje("La cantidad de pasajes no concuerda..");
                        }
                    }
                });

            }
        });
        userType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        userType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String tipoUsuario = userType.getSelectedItem().toString();
                    if(tipoUsuario.equals("Cliente")){
                        JPanelRegistrarAerolinea.setVisible(false);
                        JPanelRegistrarCliente.setVisible(true);
                    }else{
                        JPanelRegistrarAerolinea.setVisible(true);
                        JPanelRegistrarCliente.setVisible(false);
                    }
                }
            }
        });

        //REGISTRAR USUARIO
        confirmarAltaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String tipoUsuario = userType.getSelectedItem().toString();
                    if (tipoUsuario.equals("Cliente")) {
                        if (auxiliar.estanVaciosJTextField(nicknameRegistrarCliente, nombreRegistrarCliente, correoElectronicoRegistrarCliente, registrarContrasenaCliente, registrarContrasenaCliente2, apellidoRegistrarCliente, nacionalidadRegistrarCliente, documentoRegistrarCliente) || auxiliar.estanVaciosJComboBox(tipoDocumentoRegistrarCliente)){
                            new VentanaMensaje("Faltan argumentos");
                            return;
                        }

                        auxiliar.validarCorreo(correoElectronicoRegistrarCliente.getText());
                        auxiliar.validarDocumento(documentoRegistrarCliente.getText());

                        // Crear DtCliente
                        int dia = (Integer)fechaDiaRegistrarCliente.getValue();
                        int mes = (Integer)fechaMesRegistrarCliente.getValue();
                        int anio = (Integer)fechaAnioRegistrarCliente.getValue();
                        LocalDate fecha;

                        try{
                            fecha = LocalDate.of(anio,mes,dia);
                            if(!auxiliar.esFechaValida(fecha)){
                                new VentanaMensaje("Debes ingresar una fecha válida..");
                                return;
                            }
                        }catch (Exception ex){
                            new VentanaMensaje("Debes ingresar una fecha válida..");
                            return;
                        }

                        TipoDocumento documentoT = tipoDocumentoRegistrarCliente.getSelectedItem().toString().equals("Cedula") ?
                                TipoDocumento.CEDULA : TipoDocumento.PASAPORTE;

                        if(!registrarContrasenaCliente.getText().equals(registrarContrasenaCliente2.getText())){
                            new VentanaMensaje("Las contraseñas no coinciden.");
                            return;
                        }

                        String contrasena = registrarContrasenaCliente.getText();

                        if(imagenTemporalUsuario == null){
                            new VentanaMensaje("Debes ingresar una imagen.");
                            return;
                        }

                        File imagenGuardada = AuxiliarFunctions.guardarImagen(imagenTemporalUsuario, TipoImagen.USUARIO);
                        imagenTemporalUsuario = null;

                        if(imagenGuardada == null){
                            new VentanaMensaje("Ocurrió un error al guardar la imagen");
                            return;
                        }

                        String urlImage = imagenGuardada.getName();

                        DtCliente cliente = new DtCliente(
                                nicknameRegistrarCliente.getText(),
                                nombreRegistrarCliente.getText(),
                                correoElectronicoRegistrarCliente.getText(),
                                contrasena,
                                urlImage,
                                apellidoRegistrarCliente.getText(),
                                fecha,
                                nacionalidadRegistrarCliente.getText(),
                                documentoT,
                                Integer.parseInt(documentoRegistrarCliente.getText())
                        );
                        try {
                            s.registrarCliente(cliente);
                            new VentanaMensaje("Cliente creado correctamente.");
                            auxiliar.cargarUsuariosComboBox(); //Cada vez que se agregue un usuario, actualice todos los datos
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            AuxiliarFunctions.borrarImagen(urlImage, TipoImagen.USUARIO);
                            selectedFileClienteRegistrar.setText("No se ha seleccionado archivo.");
                            s.cancelarAltaUsuario();
                            new VentanaMensaje(ex.getMessage());
                        }
                        auxiliar.cargarUsuariosComboBox();
                        imagenTemporalUsuario = null;
                        selectedFileClienteRegistrar.setText("No se ha seleccionado archivo");

                    }
                } catch (Exception ex) {
                        ex.printStackTrace();
                        new VentanaMensaje(ex.getMessage());
                    }
                }
            });

        //CONSULTAR USUARIO
        consultarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try {
                if (JComboBoxSeleccionarUsuarioConsultar.getSelectedItem() == null) {
                    new VentanaMensaje("Seleccione un usuario");
                    return;
                }
                String nickname = JComboBoxSeleccionarUsuarioConsultar.getSelectedItem().toString();

                s.elegirUsuario(nickname);
                DtUsuario dtUsuario = s.getUsuarioSeleccionado();

                if (dtUsuario == null) {
                    new VentanaMensaje("Usuario no encontrado");
                    return;
                }

                InfoUsuario ventanaUsuario = new InfoUsuario(dtUsuario, auxiliar);
                setEnabled(false);

                ventanaUsuario.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                    };
                });

            }catch (Exception ex) {
                new VentanaMensaje("Error al consultar: " + ex.getMessage());
                ex.printStackTrace();
            }
            }

        });
        JComboBoxSeleccionarUsuarioModificar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    DtUsuario user = (DtUsuario)JComboBoxSeleccionarUsuarioModificar.getSelectedItem();
                    if(user instanceof DtCliente){ // Pregunta si mi usuario es un cliente
                        DtCliente cliente = (DtCliente) user;
                        LocalDate fechaCliente = cliente.getFechaNacimiento();
                        int tipo = cliente.getTipoDocumento() == TipoDocumento.CEDULA ? 0 : 1;

                        JPanelModificarCliente.setVisible(true);
                        JPanelModificarAerolinea.setVisible(false);
                        nicknameModificarCliente.setText(cliente.getNickname());
                        nombreClienteModificar.setText(cliente.getNombre());
                        apellidoClienteModificar.setText(cliente.getApellido());
                        correoClienteModificar.setText(cliente.getEmail());
                        fechaDiaClienteModificar.setValue(fechaCliente.getDayOfMonth());
                        fechaMesClienteModificar.setValue(fechaCliente.getMonthValue());
                        fechaAnioClienteModificar.setValue(fechaCliente.getYear());
                        nacionalidadClienteModificar.setText(cliente.getNacionalidad());
                        tipoDocumentoClienteModificar.setSelectedIndex(tipo);
                        numeroDocumentoModificarCliente.setText(String.valueOf(cliente.getNumeroDocumento()));
                        ImageIcon profileImage;

                        try {
                            Path userImg = AuxiliarFunctions.getImagePath(cliente.getUrlImage(), TipoImagen.USUARIO);
                            if(!Files.exists(userImg)) {
                                throw new Exception("No se encontro el imagen");
                            }
                            profileImage = new ImageIcon(userImg.toAbsolutePath().toString());
                        } catch (Exception err) {
                            profileImage = new ImageIcon(getClass().getResource("/pictures/users/default.png"));
                        }

                        AuxiliarFunctions.mostrarFoto(modificarClienteImagenPanel, profileImage, 100, 100, TipoImagen.USUARIO);

                    }else if(user instanceof DtAerolinea){
                        DtAerolinea aerolinea = (DtAerolinea) user;
                        JPanelModificarCliente.setVisible(false);
                        JPanelModificarAerolinea.setVisible(true);
                        nicknameModificarAerolinea.setText(aerolinea.getNickname());
                        nombreModificarAerolinea.setText(aerolinea.getNombre());
                        correoModificarAerolinea.setText(aerolinea.getEmail());
                        linkWebModificarAerolinea.setText(aerolinea.getLinkWeb());
                        descripcionModificarAerolinea.setText(aerolinea.getDescripcion());
                        ImageIcon profileImage;

                        try {
                            Path userImg = AuxiliarFunctions.getImagePath(aerolinea.getUrlImage(), TipoImagen.USUARIO);
                            if(!Files.exists(userImg)) {
                                throw new Exception("No se encontro el imagen");
                            }
                            profileImage = new ImageIcon(userImg.toAbsolutePath().toString());
                        } catch (Exception err) {
                            profileImage = new ImageIcon(getClass().getResource("/pictures/users/default.png"));
                        }

                        AuxiliarFunctions.mostrarFoto(modificarAerolineaImagenPanel, profileImage, 100, 100, TipoImagen.USUARIO);
                    }else{
                        JPanelModificarAerolinea.setVisible(false);
                        JPanelModificarCliente.setVisible(false);
                    }
                }
            }
        });
        crearVueloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(auxiliar.estanVaciosJComboBox(JComboBoxAerolineaAltaVuelo,JComboBoxRutaVueloAltaVuelo) || auxiliar.estanVaciosJTextField(nombreAltaVuelo)){
                        new VentanaMensaje("Faltan argumentos");
                        return;
                    }

                    String aerolinea = JComboBoxAerolineaAltaVuelo.getSelectedItem().toString();
                    String ruta = JComboBoxRutaVueloAltaVuelo.getSelectedItem().toString();
                    String nombre = nombreAltaVuelo.getText();

                    auxiliar.validarNombreVuelo(nombre);

                    LocalDate fecha = LocalDate.of((Integer)JSpinnerAñoAltaVuelo.getValue(), (Integer)JSpinnerMesAltaVuelo.getValue(), (Integer)JSpinnerDiaAltaVuelo.getValue());
                    LocalTime hora = LocalTime.of((Integer) JSpinnerDuracionAltaVueloHora.getValue(), (Integer) JSpinnerDuracionAltaVueloMinuto.getValue());

                    if(fecha.isBefore(LocalDate.now())){
                        new VentanaMensaje("La fecha debe ser para el futuro");
                        return;
                    }

                    if(imagenTemporalVuelo == null){
                        new VentanaMensaje("Debes ingresar una imagen.");
                        return;
                    }

                    File imagenGuardada = AuxiliarFunctions.guardarImagen(imagenTemporalVuelo, TipoImagen.VUELO);
                    imagenTemporalVuelo = null;

                    if(imagenGuardada == null){
                        new VentanaMensaje("Ocurrió un error al guardar la imagen");
                        return;
                    }

                    String urlImage = imagenGuardada.getName();

                    DtVuelo dtVuelo = new DtVuelo(nombre, fecha, hora, (Integer)JSpinnerTuristasAltaVuelo.getValue(), (Integer)JSpinnerEjecutivosAltaVuelo.getValue(), urlImage, LocalDate.now(), null, 0);

                    try {
                        s.seleccionarAerolineaParaVuelo(aerolinea);
                        s.seleccionarRuta(ruta);
                        s.ingresarDatosVuelo(dtVuelo);
                        s.confirmarAltaVuelo();
                        s.consultarVuelo(dtVuelo.getNombre());
                        new VentanaMensaje("Vuelo creado correctamente");
                    } catch (IllegalArgumentException ex) {
                        AuxiliarFunctions.borrarImagen(urlImage, TipoImagen.VUELO);
                        new VentanaMensaje("Error: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                    auxiliar.limpiarJTextField(nombreAltaVuelo);
                    auxiliar.cargarAerolineasComboBox();
                    imagenTemporalVuelo = null;
                    selectedFileVueloCrear.setText("No se ha seleccionado archivo");

                } catch (Exception ex) {
                    new VentanaMensaje(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });


        confirmarAltaCategoria.addActionListener(e -> {
            try{
                if(auxiliar.estanVaciosJTextField(nombreAltaCategoría)){
                    new VentanaMensaje("Ingrese un nombre de categoría");
                    return;
                }
                s.altaCategoria(new DtCategoria(nombreAltaCategoría.getText()));
                new VentanaMensaje("Categoria creada correctamente");
                nombreAltaCategoría.setText("");
                cargarCategorias();
            } catch (Exception ex) {
                ex.printStackTrace();
                new VentanaMensaje(ex.getMessage());
            }
        });
        confirmarAltaAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(auxiliar.estanVaciosJComboBox(userType)){
                        new VentanaMensaje("Seleccione un tipo de usuario");
                        return;
                    }
                    String tipoUsuario = userType.getSelectedItem().toString();
                    if (tipoUsuario.equals("Aerolinea")) {
                        if (auxiliar.estanVaciosJTextField(nicknameRegistrarAerolinea, nombreRegistrarAerolinea, correoRegistrarAerolinea, registrarAerolineaContrasena, registrarAerolineaContrasena2, descripcionRegistrarAerolinea)) {
                            new VentanaMensaje("Faltan argumentos");
                            return;
                        }
                        auxiliar.validarCorreo(correoRegistrarAerolinea.getText());

                        if(!registrarAerolineaContrasena.getText().equals(registrarAerolineaContrasena2.getText())){
                            new VentanaMensaje("Las contraseñas no coinciden.");
                            return;
                        }

                        String contrasena = registrarAerolineaContrasena.getText();

                        if(imagenTemporalUsuario == null){
                            new VentanaMensaje("Debes ingresar una imagen.");
                            return;
                        }

                        File imagenGuardada = AuxiliarFunctions.guardarImagen(imagenTemporalUsuario, TipoImagen.USUARIO);
                        imagenTemporalUsuario = null;

                        if(imagenGuardada == null){
                            new VentanaMensaje("Ocurrió un error al guardar la imagen");
                            return;
                        }

                        String urlImage = imagenGuardada.getName();

                        DtAerolinea aerolinea = new DtAerolinea(
                                nicknameRegistrarAerolinea.getText(),
                                nombreRegistrarAerolinea.getText(),
                                correoRegistrarAerolinea.getText(),
                                contrasena,
                                urlImage,
                                descripcionRegistrarAerolinea.getText(),
                                sitioWebRegistrarAerolinea.getText()
                        );
                        try{
                            s.registrarAerolinea(aerolinea);
                            new VentanaMensaje("Aerolínea creada correctamente.");
                        }catch (Exception ex){
                            ex.printStackTrace();
                            s.cancelarAltaUsuario();
                            AuxiliarFunctions.borrarImagen(urlImage, TipoImagen.USUARIO);
                            selectedFileAerolineaRegistrar.setText("No se ha seleccionado archivo.");
                            new VentanaMensaje(ex.getMessage());
                        }
                    }
                    auxiliar.cargarUsuariosComboBox();
                    auxiliar.cargarAerolineasComboBox();
                    imagenTemporalUsuario = null;
                    selectedFileAerolineaRegistrar.setText("No se ha seleccionado archivo");
            }catch (Exception ex) {
                    ex.printStackTrace();
                    new VentanaMensaje(ex.getMessage());
                }
            }
        });
        cancelarAltaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //new dialogMessage(ex.getMessage());
            }
        });
        crearRutaDeVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (auxiliar.estanVaciosJTextField(nombreAltaRutaDeVuelo, descripcionAltaRutaDeVuelo, descripcionCortaAltaRutaDeVuelo) || auxiliar.estanVaciosJComboBox(JComboBoxAerolineaAltaRutaVuelo, JComboBoxCiudadOrigen, JComboBoxCiudadDestino)) {
                        new VentanaMensaje("Faltan argumentos");
                        return;
                    }

                    if (JComboBoxCiudadOrigen.getSelectedItem().toString().equals(JComboBoxCiudadDestino.getSelectedItem().toString())) {
                        new VentanaMensaje("La ciudad de origen no puede ser igual a la ciudad de destino");
                        return;
                    }

                    Integer hora = (Integer) SpinnerHoraAltaRutaDeVuelo.getValue();
                    Integer minuto = (Integer) SpinnerMinutoAltaRutaDeVuelo.getValue();

                    LocalTime horaRuta = LocalTime.of(hora, minuto);
                    Integer costoTurista = (Integer) SpinnerCostoTurista.getValue();
                    Integer costoEjecutivo = (Integer) SpínnerCostoEjecutivo.getValue();
                    Integer costoEquipaje = (Integer) SpinnerCostoEquipaje.getValue();
                    List<String> nombresCategorias = new ArrayList<String>();
                    List<Categoria> categorias = s.getCategorias();

                    DtCiudad ciudadOrigen = (DtCiudad) JComboBoxCiudadOrigen.getSelectedItem();
                    DtCiudad ciudadDestino = (DtCiudad) JComboBoxCiudadDestino.getSelectedItem();

                    for (int i = 0; i < categorias.size(); i++) {
                        if (checkboxes.get(i).isSelected()) {
                            nombresCategorias.add(categorias.get(i).getNombre());
                        }
                    }

                    String urlImage;

                    if(imagenTemporalRuta == null){
                        int opcion = JOptionPane.showConfirmDialog(
                                null,
                                "¿Quieres crear la ruta de vuelo sin una imagen?",
                                "Confirmación",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (opcion == JOptionPane.NO_OPTION) {
                            return;
                        }else{
                            urlImage = null;
                        }
                    }else{
                        File imagenGuardada = AuxiliarFunctions.guardarImagen(imagenTemporalRuta, TipoImagen.RUTA);
                        imagenTemporalRuta = null;

                        if(imagenGuardada == null){
                            new VentanaMensaje("Ocurrió un error al guardar la imagen");
                            return;
                        }

                        urlImage = imagenGuardada.getName();
                    }

                    DtRuta ruta = new DtRuta(nombreAltaRutaDeVuelo.getText(),
                            descripcionAltaRutaDeVuelo.getText(),
                            descripcionCortaAltaRutaDeVuelo.getText(),
                            horaRuta,
                            costoTurista,
                            costoEjecutivo,
                            costoEquipaje,
                            LocalDate.now(),
                            urlImage,
                            s.getCategoriasPorNombre(nombresCategorias),
                            s.buscarCiudad(ciudadOrigen.getNombre(), ciudadOrigen.getPais()),
                            s.buscarCiudad(ciudadDestino.getNombre(), ciudadDestino.getPais()));

                        s.altaRutaDeVuelo(JComboBoxAerolineaAltaRutaVuelo.getSelectedItem().toString(), ruta);
                        auxiliar.cargarRutasDeVueloComboBox();
                        imagenTemporalRuta = null;
                        selectedFileRutaCrear.setText("No se ha seleccionado Archivo");
                        new VentanaMensaje("Ruta de vuelo creada correctamente.");

                        auxiliar.limpiarJTextField(nombreAltaRutaDeVuelo, descripcionAltaRutaDeVuelo);
                        for (int i = 0; i < categorias.size(); i++) {
                            if (checkboxes.get(i).isSelected()) {
                                checkboxes.get(i).setSelected(false);
                            }
                        }

                } catch (Exception ex) {
                    new VentanaMensaje(ex.getMessage());
                }
            }
        });
        JButtonConsultarPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (auxiliar.estanVaciosJComboBox(JComboBoxPaqueteConsultaPaqueteRutaVuelo)) {
                    new VentanaMensaje("Falta ingresar algún dato.");
                    return;
                }
                    try{

                        JFrame paquete = new InfoPaquete(s.buscarPaquete((DtPaquete) JComboBoxPaqueteConsultaPaqueteRutaVuelo.getSelectedItem()).getDatos(), auxiliar);

                        setEnabled(false);

                        paquete.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e){
                                setEnabled(true);
                            };
                        });
                    } catch (Exception ex){
                        new VentanaMensaje(ex.getMessage());
                    }
            }
        });
        CONFIRMARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJTextField(nombreAltaPaquete,descripcionAltaPaquete)){
                    new VentanaMensaje("Falta ingresar campos.");
                    return;
                }
                try {
                    Integer periodo = (Integer) JSpinnerPeriodoAltaPaquete.getValue();
                    Integer descuento = (Integer) JSpinnerDescuentoAltaPaquete.getValue();
                    float costo = ((Double) JSpinnerCostoAltaPaquete.getValue()).floatValue();

                    if(periodo < 1){
                        new VentanaMensaje("El período debe ser 1 o más.");
                        return;
                    }

                    if(costo < 1){
                        new VentanaMensaje("El costo debe ser 1 o más.");
                        return;
                    }

                    s.altaPaquete(new DtPaquete(nombreAltaPaquete.getText(),descripcionAltaPaquete.getText(),periodo,descuento,costo, new ArrayList<>()));
                    new VentanaMensaje("Paquete creado correctamente.");
                    auxiliar.cargarPaqueteComboBox();
                    auxiliar.cargarPaqueteNoCompradoComboBox();
                    auxiliar.limpiarJTextField(nombreAltaPaquete,descripcionAltaPaquete);
                    JSpinnerPeriodoAltaPaquete.setValue(1);
                    JSpinnerDescuentoAltaPaquete.setValue(0);
                    JSpinnerCostoAltaPaquete.setValue(1.0);
                } catch (Exception ex){
                    new VentanaMensaje(ex.getMessage());
                }
            }
        });

        ButtonConfirmarAgregarRutaPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxPaqueteAgregarRuta,JComboBoxAerolineaAgregarRuta,JComboBoxRutaVueloAgregarRuta,JComboBoxTipoAsientoAgregarRutaPaquete)){
                    new VentanaMensaje("Falta ingresar campos de texto");
                    return;
                }

                try{
                    Integer cantidad = (Integer) JSpinnerCantidadAgregarRuta.getValue();
                    int res = s.agregarRutaAPaquete((DtPaquete)JComboBoxPaqueteAgregarRuta.getSelectedItem(),(DtRuta)JComboBoxRutaVueloAgregarRuta.getSelectedItem(),cantidad, TipoAsiento.valueOf(JComboBoxTipoAsientoAgregarRutaPaquete.getSelectedItem().toString()));
                    if (res != 0 ) new VentanaMensaje("La ruta ya existía en el paquete, cantidad modificada a: " + res);
                    else new VentanaMensaje("Ruta de vuelo agregada a paquete correctamente.");
                    auxiliar.cargarPaquetesConRutasComboBox();
                } catch (Exception ex) {
                    new VentanaMensaje(ex.getMessage());
                }
            }
        });

        JComboBoxAerolineaAgregarRuta.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    DtAerolinea a = (DtAerolinea) JComboBoxAerolineaAgregarRuta.getSelectedItem();
                    if(a == null || a.toString().equals("N/A")){
                        new VentanaMensaje("Ha ocurrido un error..");
                        return;
                    }
                    auxiliar.cargarRutasDeVueloComboBoxAerolinea(a);
                }
            }
        });

        JComboBoxAerolineaConsultaRuta.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    DtAerolinea a = (DtAerolinea) JComboBoxAerolineaConsultaRuta.getSelectedItem();
                    if(a == null || a.toString().equals("N/A")){
                        new VentanaMensaje("Ha ocurrido un error..");
                        return;
                    }
                    auxiliar.cargarRutasDeVueloComboBoxAerolinea(a);
                }
            }
        });

        ButtonConsultarRutaVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxAerolineaConsultaRuta,JComboBoxRutaVueloConsultaRuta)){
                    new VentanaMensaje("Seleccione todos los campos");
                    return;
                }
                try{
                    String nombreRuta = JComboBoxRutaVueloConsultaRuta.getSelectedItem().toString();
                    InfoRutaDeVuelo ventanaRuta = new InfoRutaDeVuelo(s.consultarRuta(nombreRuta), auxiliar);
                    setEnabled(false);

                    ventanaRuta.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e){
                            setEnabled(true);
                        };
                    });

                } catch (Exception ex) {
                    new VentanaMensaje(ex.getMessage());
                }


            }
        });
        // MODIFICAR CLIENTE
        confirmarModificarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxSeleccionarUsuarioModificar) || auxiliar.estanVaciosJTextField(nombreClienteModificar, apellidoClienteModificar, correoClienteModificar, nacionalidadClienteModificar, numeroDocumentoModificarCliente)){
                    new VentanaMensaje("Los campos no pueden quedar vacíos..");
                    return;
                }

                int dia = (Integer)fechaDiaClienteModificar.getValue();
                int mes = (Integer)fechaMesClienteModificar.getValue();
                int anio = (Integer)fechaAnioClienteModificar.getValue();
                LocalDate fecha = null;

                try{
                    fecha = LocalDate.of(anio,mes,dia);
                    if(!auxiliar.esFechaValida(fecha)){
                        new VentanaMensaje("Debes ingresar una fecha válida..");
                        return;
                    }
                }catch (Exception ex){
                    new VentanaMensaje("Debes ingresar una fecha válida..");
                    return;
                }


                TipoDocumento tipoDocumento = tipoDocumentoClienteModificar.getSelectedItem().toString().equals("Cedula") ? TipoDocumento.CEDULA : TipoDocumento.PASAPORTE;

                DtCliente cliente = new DtCliente(
                        nicknameModificarCliente.getText(),
                        nombreClienteModificar.getText(),
                        correoClienteModificar.getText(),
                        apellidoClienteModificar.getText(),
                        fecha,
                        nacionalidadClienteModificar.getText(),
                        tipoDocumento,
                        Integer.parseInt(numeroDocumentoModificarCliente.getText())
                );
                try{
                    s.modificarCliente(cliente);
                    auxiliar.cargarUsuariosComboBox(cliente); // Funcion para actualizar valores.
                    new VentanaMensaje("Cliente actualizado correctamente.");
                }catch(Exception ex){
                    new VentanaMensaje(ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });
        JButtonModificarAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJTextField(nombreModificarAerolinea, correoModificarAerolinea, descripcionModificarAerolinea)){
                    new VentanaMensaje("No puedes dejar los campos vacios..");
                    return;
                }

                String link = auxiliar.estanVaciosJTextField(linkWebModificarAerolinea) ? "" : linkWebModificarAerolinea.getText();

                String urlImage = "/src/main/pictures/users/default.png";

                DtAerolinea aerolinea = new DtAerolinea(
                        nicknameModificarAerolinea.getText(),
                        nombreModificarAerolinea.getText(),
                        correoModificarAerolinea.getText(),
                        urlImage,
                        descripcionModificarAerolinea.getText(),
                        link
                );

                try{
                    s.modificarAerolinea(aerolinea);
                    auxiliar.cargarUsuariosComboBox(aerolinea);
                    new VentanaMensaje("Aerolinea actualizado correctamente.");
                } catch (Exception ex) {
                    new VentanaMensaje(ex.getMessage());
                }
            }
        });
        JComboBoxseleccionarAerolineaReserva.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                DtAerolinea aerolinea = (DtAerolinea)JComboBoxseleccionarAerolineaReserva.getSelectedItem();
                if(aerolinea == null){
                    new VentanaMensaje("Ha ocurrido un error..");
                    return;
                }

                //List<DtRuta> rutas = aerolinea.listarRutasDeVuelo();

                //System.out.println("Aerolinea seleccionada: " + aerolinea.getNickname());
                //System.out.println("---- Rutas asociadas ----");
                //for(DtRuta r : rutas){
                    //   System.out.println(r.getNombre());
                //}
                //System.out.println("-------------------------");

                auxiliar.cargarRutasDeVueloComboBoxAerolinea(aerolinea);
                JComboBoxrutaDeVueloReserva.setEnabled(true);
                }
            }
        });
        JComboBoxrutaDeVueloReserva.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    DtRuta ruta = (DtRuta)JComboBoxrutaDeVueloReserva.getSelectedItem();
                    if(ruta == null){
                        new VentanaMensaje("Ha ocurrido un error..");
                        return;
                    }

                    auxiliar.cargarVuelosComboBoxRuta(ruta.getNombre());
                    JComboBoxvueloReserva.setEnabled(true);
                }
            }
        });
        JButtonVerVueloReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxvueloReserva)){
                    new VentanaMensaje("Ha ocurrido un error..");
                    return;
                }

                DtVuelo vuelo = (DtVuelo) JComboBoxvueloReserva.getSelectedItem();
                if(vuelo == null){
                    new VentanaMensaje("Ha ocurrido un error..");
                    return;
                }
                InfoVuelo ventanaVuelo = new InfoVuelo(vuelo, auxiliar);
                setEnabled(false);

                ventanaVuelo.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        setEnabled(true);
                    };
                });
            }
        });
        JComboBoxvueloReserva.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    JButtonVerVueloReservaButton.setEnabled(!JComboBoxvueloReserva.getSelectedItem().toString().equals("N/A"));
                    JComboBoxSeleccionarClienteReserva.setEnabled(true);
                    JComboBoxtipoAsientoReserva.setEnabled(true);
                }
            }
        });
        JComboBoxConsultaVueloAerolinea.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    DtAerolinea aerolinea = (DtAerolinea) JComboBoxConsultaVueloAerolinea.getSelectedItem();
                    auxiliar.cargarRutasDeVueloComboBoxAerolinea(aerolinea);

                    if (aerolinea == null) {
                        new VentanaMensaje("Ha ocurrido un error..");
                        return;
                    }
                }
            }
        });

        JComboBoxConsultaVueloRutaDeVuelo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    DtRuta ruta = (DtRuta) JComboBoxConsultaVueloRutaDeVuelo.getSelectedItem();
                    auxiliar.cargarVuelosComboBoxRuta(ruta.getNombre());

                    if (ruta == null) {
                        new VentanaMensaje("Ha ocurrido un error..");
                        return;
                    }
                }
            }
        });

        CancelarAltaCategori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombreAltaCategoría.setText("");
            }
        });

        ButtonConfirmarCompraPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auxiliar.estanVaciosJComboBox(JComboBoxPaqueteComprarPaquete,JComboBoxClienteCompraPaquete)){
                    new VentanaMensaje("Complete todos los campos.");
                    return;
                }

                try {
                    s.compraPaquete((DtPaquete) JComboBoxPaqueteComprarPaquete.getSelectedItem(),(DtCliente) JComboBoxClienteCompraPaquete.getSelectedItem());
                    new VentanaMensaje("Compra de paquete realizada exitosamente.");
                } catch (Exception ex){
                    new VentanaMensaje(ex.getMessage());
                }
            }
        });

        JComboBoxAerolineaAltaVuelo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    DtAerolinea a = (DtAerolinea) JComboBoxAerolineaAltaVuelo.getSelectedItem();
                    if(a == null || a.toString().equals("N/A")){
                        new VentanaMensaje("Ha ocurrido un error..");
                        return;
                    }
                    auxiliar.cargarRutasDeVueloComboBoxAerolinea(a);
                }
            }
        });
        subirImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTemporalUsuario = null;
                SubirImagen ventanaImagen = new SubirImagen(TipoImagen.USUARIO);
                setEnabled(false);

                ventanaImagen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        imagenTemporalUsuario = ventanaImagen.getImagen();
                        if(imagenTemporalUsuario != null){
                            selectedFileClienteRegistrar.setText(imagenTemporalUsuario.getName());
                        }else{
                            selectedFileClienteRegistrar.setText("No se ha seleccionado archivo.");
                        }
                    };
                });
            }
        });
        modificarClienteImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTemporalUsuario = null;
                SubirImagen ventanaImagen = new SubirImagen(TipoImagen.USUARIO);
                setEnabled(false);

                ventanaImagen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        imagenTemporalUsuario = ventanaImagen.getImagen();

                        DtCliente cliente = (DtCliente)JComboBoxSeleccionarUsuarioModificar.getSelectedItem();
                        if(imagenTemporalUsuario != null){
                            try{
                                AuxiliarFunctions.borrarImagen(cliente.getUrlImage(), TipoImagen.USUARIO);
                                File imagen = AuxiliarFunctions.guardarImagen(imagenTemporalUsuario, TipoImagen.USUARIO);
                                s.modificarClienteImagen(cliente, imagen.getName());
                                auxiliar.cargarUsuariosComboBox(cliente);
                                new VentanaMensaje("Imagen actualizada correctamente.");
                            } catch (Exception ex) {
                                new VentanaMensaje(ex.getMessage());
                                return;
                            }
                        }else{
                            new VentanaMensaje("Se ha cancelado la operación");
                            return;
                        }
                    };
                });
            }
        });
        subirImagenImagenAerolineaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTemporalUsuario = null;
                SubirImagen ventanaImagen = new SubirImagen(TipoImagen.USUARIO);
                setEnabled(false);

                ventanaImagen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        imagenTemporalUsuario = ventanaImagen.getImagen();
                        if(imagenTemporalUsuario != null){
                            selectedFileAerolineaRegistrar.setText(imagenTemporalUsuario.getName());
                        }else{
                            selectedFileAerolineaRegistrar.setText("No se ha seleccionado archivo.");
                        }
                    };
                });
            }
        });
        modificarAerolineaImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTemporalUsuario = null;
                SubirImagen ventanaImagen = new SubirImagen(TipoImagen.USUARIO);
                setEnabled(false);

                ventanaImagen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        imagenTemporalUsuario = ventanaImagen.getImagen();

                        DtAerolinea aerolinea = (DtAerolinea) JComboBoxSeleccionarUsuarioModificar.getSelectedItem();
                        if(imagenTemporalUsuario != null){
                            try{
                                AuxiliarFunctions.borrarImagen(aerolinea.getUrlImage(), TipoImagen.USUARIO);
                                File imagen = AuxiliarFunctions.guardarImagen(imagenTemporalUsuario, TipoImagen.USUARIO);
                                s.modificarAerolineaImagen(aerolinea, imagen.getName());
                                auxiliar.cargarUsuariosComboBox(aerolinea);
                                new VentanaMensaje("Imagen actualizada correctamente.");
                            } catch (Exception ex) {
                                new VentanaMensaje(ex.getMessage());
                                return;
                            }
                        }else{
                            new VentanaMensaje("Se ha cancelado la operación");
                            return;
                        }
                    };
                });
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTemporalVuelo = null;
                SubirImagen ventanaImagen = new SubirImagen(TipoImagen.VUELO);
                setEnabled(false);

                ventanaImagen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        imagenTemporalVuelo = ventanaImagen.getImagen();
                        if(imagenTemporalVuelo != null){
                            selectedFileVueloCrear.setText(imagenTemporalVuelo.getName());
                        }else{
                            selectedFileVueloCrear.setText("No se ha seleccionado archivo.");
                        }
                    };
                });
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagenTemporalRuta = null;
                SubirImagen ventanaImagen = new SubirImagen(TipoImagen.RUTA);
                setEnabled(false);

                ventanaImagen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                        imagenTemporalRuta = ventanaImagen.getImagen();
                        if(imagenTemporalRuta != null){
                            selectedFileRutaCrear.setText(imagenTemporalRuta.getName());
                        }else{
                            selectedFileRutaCrear.setText("No se ha seleccionado archivo.");
                        }
                    };
                });
            }
        });
        seleccionarRutaAceptarRechazarRuta.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    DtRuta ruta = (DtRuta)seleccionarRutaAceptarRechazarRuta.getSelectedItem();
                    if(ruta != null && !ruta.toString().equals("N/A") && ruta.getEstado().toString().equals("INGRESADA")){
                        aprobarButtonAceptarRechazarRuta.setEnabled(true);
                        rechazarButtonAceptarRechazarRuta.setEnabled(true);
                        estadoRutaText.setText(ruta.getEstado().toString());
                    }else{
                        aprobarButtonAceptarRechazarRuta.setEnabled(false);
                        rechazarButtonAceptarRechazarRuta.setEnabled(false);
                        if(ruta != null && !ruta.toString().equals("N/A")){
                            estadoRutaText.setText(ruta.getEstado().toString());
                        }else{
                            estadoRutaText.setText("No se ha seleccionado una ruta.");
                        }
                    }
                }
            }
        });
        aprobarButtonAceptarRechazarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DtRuta ruta = (DtRuta)seleccionarRutaAceptarRechazarRuta.getSelectedItem();
                if(ruta != null && !ruta.toString().equals("N/A")){
                    try{
                        s.actualizarEstadoRuta(ruta, EstadoRuta.APROBADA);
                        auxiliar.cargarRutasDeVueloComboBoxAerolinea((DtAerolinea)seleccionarAerolineaAceptarRechazarRuta.getSelectedItem(), ruta);
                    } catch (Exception ex) {
                        new VentanaMensaje(ex.getMessage());
                    }

                }else{
                    new VentanaMensaje("Debe seleccionar una ruta para realizar esta acción");
                }
            }
        });
        rechazarButtonAceptarRechazarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DtRuta ruta = (DtRuta)seleccionarRutaAceptarRechazarRuta.getSelectedItem();
                if(ruta != null && !ruta.toString().equals("N/A")){
                    try{
                        s.actualizarEstadoRuta(ruta, EstadoRuta.RECHAZADA);
                        auxiliar.cargarRutasDeVueloComboBoxAerolinea((DtAerolinea)seleccionarAerolineaAceptarRechazarRuta.getSelectedItem(), ruta);
                    } catch (Exception ex) {
                        new VentanaMensaje(ex.getMessage());
                    }

                }else{
                    new VentanaMensaje("Debe seleccionar una ruta para realizar esta acción");
                }
            }
        });
    }

    public void cargarCategorias(){
        JPanelCategorias.removeAll();
        checkboxes.clear();
        for (Categoria c : s.getCategorias()) {
            JCheckBox check = new JCheckBox(c.getNombre());
            JPanelCategorias.add(check);
            this.checkboxes.add(check);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}