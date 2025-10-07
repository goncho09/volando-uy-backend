package com.app.interfaces;

import com.app.datatypes.DtReserva;
import com.app.utils.AuxiliarFunctions;

import javax.swing.*;

public class InfoReserva extends JFrame{

    private AuxiliarFunctions a;

    private JPanel dataReservaPanel;
    private JPanel dataReservaDisplay;
    private JComboBox JComboBoxPasajeros;
    private JLabel clienteLabel;
    private JLabel fechaLabel;
    private JLabel asientosLabel;
    private JLabel tipoAsientoLabel;
    private JLabel equipajeLabel;
    private JLabel costoLabel;

    public InfoReserva(DtReserva dataReserva, AuxiliarFunctions auxiliar){
        setTitle("Reservas del vuelo: " + dataReserva.getVuelo().getNombre());
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        add(dataReservaPanel);

        this.a = auxiliar;

        clienteLabel.setText(dataReserva.getCliente().getNombre());
        fechaLabel.setText(dataReserva.getFecha().toString());
        asientosLabel.setText(Integer.toString(dataReserva.getCantPasajes()));
        tipoAsientoLabel.setText(dataReserva.getTipoAsiento().toString());
        equipajeLabel.setText(Integer.toString(dataReserva.getEquipajeExtra()));
        costoLabel.setText(Float.toString(dataReserva.getCosto()));

        JComboBoxPasajeros.setModel(a.getComboPasajerosReserva());
        a.cargarPasajeros(dataReserva);

    }

}
