package com.deleondiego.kinalapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    private Long codigoUsuario;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String rol;

    @Column
    private Long estado;

    public Usuario() {
    }

    public Usuario(Long codigoUsuario, String userName, String password, String email, String rol, Long estado) {
        this.codigoUsuario = codigoUsuario;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }
}