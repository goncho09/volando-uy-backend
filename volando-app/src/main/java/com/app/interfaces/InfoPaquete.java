package com.app.interfaces;

import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRuta;
import com.app.utils.AuxiliarFunctions;

import javax.swing.*;
import java.awt.event.*;

public class InfoPaquete extends JFrame {
    private AuxiliarFunctions a;

    private JLabel nombre;
    private JLabel descripcion;
    private JLabel validezDias;
    private JLabel descuento;
    private JLabel costo;
    private JLabel cantidadRutas;
    private JComboBox JComboBoxRutasVuelo;
    private JPanel dataPaquetePanel;
    private JButton ButtonVerRutaDeVuelo;
    private JPanel dataPaqueteDisplay;

    public InfoPaquete(DtPaquete paquete, AuxiliarFunctions auxiliar) {
            setTitle("Datos del paquete: " + paquete.getNombre());
            setResizable(false);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setSize(500, 300);
            setLocationRelativeTo(null);
            setVisible(true);
            add(dataPaquetePanel);

            this.a = auxiliar;

            nombre.setText(paquete.getNombre());
            descripcion.setText(paquete.getDescripcion());
            validezDias.setText(Integer.toString(paquete.getValidezDias()));
            descuento.setText(Float.toString(paquete.getDescuento()));
            costo.setText(Float.toString(paquete.getCosto()));
            cantidadRutas.setText(Integer.toString(paquete.getRutaEnPaquete().size()));

            JComboBoxRutasVuelo.setModel(a.getComboRutaPaquete());
            a.cargarRutasPaquete(paquete);


        ButtonVerRutaDeVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JComboBoxRutasVuelo.getSelectedItem() == null || JComboBoxRutasVuelo.getSelectedItem().toString().equals("N/A")){
                    new VentanaMensaje("Debe seleccionar una ruta de vuelo para ver su información");
                    return;
                }
                try{
                    DtRuta ruta = (DtRuta) JComboBoxRutasVuelo.getSelectedItem();
                    InfoRutaDeVuelo ventanaRuta = new InfoRutaDeVuelo(ruta, a);
                    setEnabled(false);

                    ventanaRuta.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e){setEnabled(true);
                        };
                    });
                } catch (Exception ex) {
                    new VentanaMensaje(ex.getMessage());
                }

            }
        });
    }
}
