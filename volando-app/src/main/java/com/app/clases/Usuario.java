package com.app.clases;

import com.app.datatypes.DtUsuario;
import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Esto indica que es una herencia, por ende tendrá subclases, utilizará el método Join para crear las "subtablas"
@DiscriminatorColumn(name = "userType") // Indica que creará una columna para diferenciar las clases. El valor depende de las subclases.
public abstract class Usuario {
    @Id protected String nickname; // Indica que nickname será la id

    @Column(nullable = false, length = 50) // Indica que será una columna no nula
    protected String nombre;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    protected String urlImage;

    public Usuario() {}
    public Usuario(DtUsuario usuario){
        this.nickname = usuario.getNickname();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.urlImage = usuario.getUrlImage();
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

    public String getPassword() {
        return password;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public DtUsuario getDatos() {
        return new DtUsuario(
                this.nickname,
                this.nombre,
                this.email,
                this.password,
                this.urlImage
        );
    }

    @Override
    public String toString() {
        return this.nickname;
    }
}

