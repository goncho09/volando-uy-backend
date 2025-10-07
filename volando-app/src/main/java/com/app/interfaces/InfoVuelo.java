package com.app.interfaces;

import com.app.datatypes.DtVuelo;
import com.app.datatypes.DtReserva;
import com.app.enums.TipoImagen;
import com.app.utils.AuxiliarFunctions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class InfoVuelo extends JFrame {

    private AuxiliarFunctions a;

    private JPanel dataVueloPanel;
    private JButton consultarReservasButton;
    private JLabel nombreVuelo;
    private JLabel fechaDespegue;
    private JLabel flyDuration;
    private JLabel cantMaxTuristas;
    private JLabel cantMaxEjecutivos;
    private JLabel fechaAlta;
    private JLabel cantReservas;
    private JLabel capacidadRestante;
    private JPanel dataVueloDisplay;
    private JComboBox JComboBoxReservas;
    private JPanel imagenVueloPanel;
    private ImageIcon imagen;

    public InfoVuelo(DtVuelo dataVuelo, AuxiliarFunctions auxiliar) {
        setContentPane(dataVueloPanel);
        setTitle("Datos del Vuelo: " + dataVuelo.getNombre());
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        a = auxiliar;
        int cantidadReservas = dataVuelo.getCantReservas();
        int capacidadMaxima = dataVuelo.getMaxEjecutivos() + dataVuelo.getMaxTuristas();

        nombreVuelo.setText(dataVuelo.getNombre());
        fechaDespegue.setText(dataVuelo.getFecha().toString());
        flyDuration.setText(dataVuelo.getDuracion().toString());
        cantMaxTuristas.setText(Integer.toString(dataVuelo.getMaxTuristas()));
        cantMaxEjecutivos.setText(Integer.toString(dataVuelo.getMaxEjecutivos()));
        fechaAlta.setText(dataVuelo.getFechaAlta().toString());
        cantReservas.setText(Integer.toString(cantidadReservas));
        capacidadRestante.setText(Integer.toString(capacidadMaxima - cantidadReservas));

        JComboBoxReservas.setModel(a.getComboReservaVueloModel());

        //new VentanaMensaje(dataVuelo.getUrlImage());

        try {
            Path userImg = AuxiliarFunctions.getImagePath(dataVuelo.getUrlImage(), TipoImagen.VUELO);
            if(!Files.exists(userImg)) {
                throw new Exception("No se encontró la imagen");
            }
            imagen = new ImageIcon(userImg.toAbsolutePath().toString());
        } catch (Exception e) {
            imagen = new ImageIcon(getClass().getResource("/pictures/vuelos/default.jpg"));
        }

        AuxiliarFunctions.mostrarFoto(imagenVueloPanel, imagen, 175, 175, TipoImagen.VUELO);
        a.cargarDatosReservaComboBox(dataVuelo);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        consultarReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JComboBoxReservas.getSelectedItem() == null || JComboBoxReservas.getSelectedItem().toString().equals("N/A")){
                    new VentanaMensaje("Seleccione una reserva.");
                    return;
                }

                DtReserva reserva = (DtReserva) JComboBoxReservas.getSelectedItem();
                InfoReserva ventanaReserva = new InfoReserva(reserva, a);
                setEnabled(false);

                ventanaReserva.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        setEnabled(true);
                    }
                });
            }
        });
    }

}


