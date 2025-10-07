package com.app.interfaces;

import com.app.clases.Categoria;
import com.app.datatypes.DtRuta;
import com.app.datatypes.DtVuelo;
import com.app.enums.TipoImagen;
import com.app.utils.AuxiliarFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class InfoRutaDeVuelo extends JFrame{
    private AuxiliarFunctions a;

    private JPanel panelGeneralRuta;
    private JLabel nombre;
    private JLabel descripcion;
    private JLabel duracion;
    private JLabel costoTurista;
    private JLabel costoEjecutivo;
    private JLabel costoEquipaje;
    private JLabel fechaAlta;
    private JLabel ciudadOrigen;
    private JLabel ciudadDestino;
    private JPanel dataRutaPanel;
    private JPanel categoriasPanel;
    private JComboBox JComboBoxVuelos;
    private JButton ButtonVerVuelo;
    private JPanel imagenRutaPanel;
    private JLabel estado;
    private ImageIcon imagen;

    public InfoRutaDeVuelo(DtRuta ruta, AuxiliarFunctions auxiliar){
        setContentPane(panelGeneralRuta);
        setTitle("Datos de la ruta: " + ruta.getNombre());
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        a = auxiliar;

        JComboBoxVuelos.setModel(a.getComboVueloRutaDeVueloModel());
        a.cargarVuelosComboBoxRuta(ruta.getNombre());

        nombre.setText(ruta.getNombre());
        descripcion.setText(ruta.getDescripcion());
        duracion.setText(ruta.getDuracion().toString());
        costoTurista.setText(Float.toString(ruta.getCostoTurista()));
        costoEjecutivo.setText(Float.toString(ruta.getCostoEjecutivo()));
        costoEquipaje.setText(Float.toString(ruta.getEquipajeExtra()));
        fechaAlta.setText(ruta.getFechaAlta().toString());
        ciudadOrigen.setText(ruta.getCiudadOrigen().getNombre());
        ciudadDestino.setText(ruta.getCiudadDestino().getNombre());
        estado.setText(ruta.getEstado().toString());


        categoriasPanel.setLayout(new GridLayout(0, 2, 10, 5));

        //new VentanaMensaje(ruta.getUrlImagen());

        try {
            Path userImg = AuxiliarFunctions.getImagePath(ruta.getUrlImagen(), TipoImagen.RUTA);
            if(!Files.exists(userImg)) {
                throw new Exception("No se encontró la imagen");
            }
            imagen = new ImageIcon(userImg.toAbsolutePath().toString());
        } catch (Exception e) {
            imagen = new ImageIcon(getClass().getResource("/pictures/rutas/default.png"));
        }

        AuxiliarFunctions.mostrarFoto(imagenRutaPanel, imagen, 175, 175, TipoImagen.RUTA);

        if (ruta.getCategorias().isEmpty()){
            categoriasPanel.add(new JLabel("No hay categorías."));
        }else{
            for (Categoria cat : ruta.getCategorias()) {
                JLabel label = new JLabel(cat.getNombre());
                categoriasPanel.add(label);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        ButtonVerVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JComboBoxVuelos.getSelectedItem() == null || JComboBoxVuelos.getSelectedItem().toString().equals("N/A")){
                    new VentanaMensaje("Seleccione un vuelo para poder verlo.");
                    return;
                }

                DtVuelo v =  (DtVuelo) JComboBoxVuelos.getSelectedItem();
                InfoVuelo ventanaVuelo = new InfoVuelo(v.getDatos(), a);
                setEnabled(false);

                ventanaVuelo.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e){setEnabled(true);
                            };
                        });

            }
        });
    }
}
