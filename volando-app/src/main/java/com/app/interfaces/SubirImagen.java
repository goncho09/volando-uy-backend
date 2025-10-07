package com.app.interfaces;

import com.app.enums.TipoImagen;
import com.app.utils.AuxiliarFunctions;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SubirImagen extends JFrame {
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JPanel imagePanel;
    private JPanel panelGlobal;
    private ImageIcon profileImage;
    private File selectedFile;
    private TipoImagen imgType;

    public SubirImagen(TipoImagen tipo) {
        setContentPane(panelGlobal);
        setTitle("Subir imagen");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imgType = tipo;

        try{
            if (!abrirFileChooser()) {
                selectedFile = null;
                dispose();
                return;
            }
        }catch(Exception e){
            selectedFile = null;
            dispose();
            return;
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFile = null;
                dispose();
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private boolean abrirFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imágenes (JPG, JPEG, PNG)", "jpg", "jpeg", "png"
        );
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);

        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();

            String name = selectedFile.getName().toLowerCase();
            if (!(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"))) {
                new VentanaMensaje("Debes seleccionar un archivo válido (jpg, jpeg o png)");
                return false;
            }

            profileImage = new ImageIcon(selectedFile.getAbsolutePath());
            Image imgRaw = profileImage.getImage();
            Image imgScaled = imgRaw.getScaledInstance(175, 175, Image.SCALE_SMOOTH);
            profileImage = new ImageIcon(imgScaled);

            if(imgType == TipoImagen.USUARIO){
                profileImage = AuxiliarFunctions.createRoundImageIcon(profileImage);
            }

            JLabel imgLabel = new JLabel(profileImage);

            imagePanel.setLayout(new FlowLayout());
            imagePanel.removeAll();
            imagePanel.add(imgLabel);
            imagePanel.revalidate();
            imagePanel.repaint();

            return true;
        }

        return false;
    }

    public File getImagen(){
        return selectedFile;
    }
}