package com.app.enums;

public enum TipoImagen {
    USUARIO("users"),
    VUELO("vuelos"),
    RUTA("rutas");

    private final String folder;

    TipoImagen(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}

