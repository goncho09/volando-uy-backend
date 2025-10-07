package com.app.datatypes;

public class DtUsuario {
    protected String nickname;
    protected String nombre;
    protected String email;
    protected String password;
    protected String urlImage;

    public DtUsuario() {}
    public DtUsuario(String nickname,String nombre, String email, String password, String urlImage){
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.urlImage = urlImage;
    }

    public DtUsuario(String nickname,String nombre, String email, String urlImage){
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.password = null;
        this.urlImage = urlImage;
    }

    public DtUsuario(String nickname,String nombre, String email){
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.password = null;
        this.urlImage = null;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {return password;}

    public String getUrlImage() {return urlImage;}

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {this.password = password;}

    public void setUrlImage(String urlImage) {this.urlImage = urlImage;}

    @Override
    public String toString() { return this.nickname; }
}
