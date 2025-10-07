package com.app.interfaces;

import com.app.datatypes.DtPasajero;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AgregarPasajero extends JFrame{

    private int contador;
    private int cantidad;
    private List<DtPasajero> listPasajes = new ArrayList<>();

    private JPanel panelPasajes;
    private JTextField nombrePasajero;
    private JTextField apellidoPasajero;
    private JButton confirmarPasajeButton;

    public List<DtPasajero> getPasajes(){
        return listPasajes;
    }

    public AgregarPasajero(int n) {
        cantidad = n;
        contador = 0;

        setTitle("Cargando datos..");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(275, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        add(panelPasajes);

        confirmarPasajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombrePasajero.getText().trim();
                String apellido = apellidoPasajero.getText().trim();

                if(nombre.isEmpty() || apellido.isEmpty()) {
                    new VentanaMensaje("No puedes dejar campos vacíos..");
                    return;
                }

                // Crear el objeto pasaje (podés usar tu DtPasaje o Pasaje)
                DtPasajero p = new DtPasajero(nombre, apellido);
                if(listPasajes.contains(p)){
                    new VentanaMensaje("No puedes crear 2 pasajes iguales");
                    dispose();
                    return;
                }
                listPasajes.add(p);
                contador++;

                if(contador == cantidad){
                    dispose();
                }else{
                    nombrePasajero.setText("");
                    apellidoPasajero.setText("");
                    new VentanaMensaje("Pasaje creado con éxito");
                }
            }
        });
    }
}
