package com.app.interfaces;

import com.app.datatypes.*;
import com.app.enums.TipoImagen;
import com.app.utils.AuxiliarFunctions;

import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class InfoUsuario extends JFrame {

    private AuxiliarFunctions a;

    private JPanel PanelGlobal;
    private JLabel nicknameConsultaUsuario;
    private JLabel nombreConsultaUsuario;
    private JLabel emailConsultaUsuario;
    private JPanel PanelAerolinea;
    private JButton cancelarButton;
    private JLabel apellidoConsultaUsuario;
    private JLabel fechaNacConsultaUsuario;
    private JLabel tipoDocConsultaUsuario;
    private JLabel numDocumentoConsultaCliente;
    private JLabel nacionalidadConsultaUsuario;
    private JLabel descripcionAerolineaConsultaUsuario;
    private JLabel sitioAerolineaConsultaUsuario;
    private JPanel PanelUsuario;
    private JPanel PanelCliente;
    private JComboBox JComboBoxRutasDeVuelo;
    private JButton verRuta;
    private JComboBox JComboBoxReservas;
    private JComboBox JComboBoxPaquetes;
    private JButton verReserva;
    private JButton verPaquete;
    private JPanel imagenPanel;
    private ImageIcon profileImage;

    public InfoUsuario(DtUsuario usuario, AuxiliarFunctions auxiliar) {
        setContentPane(PanelGlobal);
        setTitle("Datos de Usuario: " + usuario.getNickname());
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.a = auxiliar;

        cancelarButton.addActionListener(e -> dispose());

        nicknameConsultaUsuario.setText(usuario.getNickname());
        nombreConsultaUsuario.setText(usuario.getNombre());
        emailConsultaUsuario.setText(usuario.getEmail());

        try {
            Path userImg = AuxiliarFunctions.getImagePath(usuario.getUrlImage(), TipoImagen.USUARIO);
            if(!Files.exists(userImg)) {
                throw new Exception("No se encontro el imagen");
            }
            profileImage = new ImageIcon(userImg.toAbsolutePath().toString());
        } catch (Exception e) {
            profileImage = new ImageIcon(getClass().getResource("/pictures/users/default.png"));
        }

        AuxiliarFunctions.mostrarFoto(imagenPanel, profileImage, 175, 175, TipoImagen.USUARIO);


        if (usuario instanceof DtCliente) {
            DtCliente cliente = (DtCliente) usuario;
            PanelCliente.setVisible(true);
            PanelAerolinea.setVisible(false);
            apellidoConsultaUsuario.setText(cliente.getApellido());
            nacionalidadConsultaUsuario.setText(cliente.getNacionalidad());
            tipoDocConsultaUsuario.setText(cliente.getTipoDocumento().toString());
            numDocumentoConsultaCliente.setText(String.valueOf(cliente.getNumeroDocumento()));
            fechaNacConsultaUsuario.setText(cliente.getFechaNacimiento().toString());

            JComboBoxReservas.setModel(a.getComboReservaVueloClienteModel());
            a.cargarDatosReservaComboBox(cliente);

            JComboBoxPaquetes.setModel(a.getComboPaqueteClienteModel());
            a.cargarPaqueteClienteComboBox(cliente);

        } else if (usuario instanceof DtAerolinea) {
            DtAerolinea aerolinea = (DtAerolinea) usuario;
            PanelAerolinea.setVisible(true);
            PanelCliente.setVisible(false);

            descripcionAerolineaConsultaUsuario.setText(aerolinea.getDescripcion());
            sitioAerolineaConsultaUsuario.setText(aerolinea.getLinkWeb());

            JComboBoxRutasDeVuelo.setModel(a.getComboRutaDeVueloAerolineaModel());
            a.cargarRutasDeVueloComboBoxAerolinea(aerolinea);
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        verReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JComboBoxReservas.getSelectedItem() == null || JComboBoxReservas.getSelectedItem().toString().equals("N/A")){
                    new VentanaMensaje("Seleccione una reserva");
                    return;
                }
                DtReserva r = (DtReserva) JComboBoxReservas.getSelectedItem();
                InfoReserva ventanaReserva = new InfoReserva(r, a);
                setEnabled(false);

                ventanaReserva.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        setEnabled(true);
                    };
                });
            }
        });

        verPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JComboBoxPaquetes.getSelectedItem() == null || JComboBoxPaquetes.getSelectedItem().toString().equals("N/A")){
                    new VentanaMensaje("Seleccione un paquete");
                    return;
                }
                DtPaquete p = (DtPaquete) JComboBoxPaquetes.getSelectedItem();
                InfoPaquete ventanaPaquete = new InfoPaquete(p, a);
                setEnabled(false);

                ventanaPaquete.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        setEnabled(true);
                    };
                });
            }
        });
        verRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JComboBoxRutasDeVuelo.getSelectedItem() == null || JComboBoxRutasDeVuelo.getSelectedItem().toString().equals("N/A")){
                    new VentanaMensaje("Seleccione una ruta");
                    return;
                }
                DtRuta ruta =  (DtRuta) JComboBoxRutasDeVuelo.getSelectedItem();
                InfoRutaDeVuelo ventanaRuta = new InfoRutaDeVuelo(ruta, a);
                setEnabled(false);

                ventanaRuta.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        setEnabled(true);
                    }
                });
            }
        });
    }


    private void createUIComponents() {
    }
}
