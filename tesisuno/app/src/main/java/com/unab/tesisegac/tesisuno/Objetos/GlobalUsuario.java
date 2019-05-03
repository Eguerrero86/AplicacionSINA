package com.unab.tesisegac.tesisuno.Objetos;

import android.app.Application;

public class GlobalUsuario extends Application {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsnuario) {
        this.idUsuario = idUsnuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private String tipoUsuario;
}
