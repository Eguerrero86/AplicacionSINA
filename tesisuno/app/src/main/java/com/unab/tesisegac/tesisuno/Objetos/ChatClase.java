package com.unab.tesisegac.tesisuno.Objetos;

import android.net.Uri;

public class ChatClase {
    private Uri imagen;
    private String nombre;
    private Usuario usuario;

    public ChatClase(Uri imagen, String nombre, Usuario usuario) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public ChatClase(Uri imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
