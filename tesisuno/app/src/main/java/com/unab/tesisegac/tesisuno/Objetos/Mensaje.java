package com.unab.tesisegac.tesisuno.Objetos;


public class Mensaje {
    private String urlFoto;
    private String mensaje;
    private String nombre;
    private String foto;
    private String cedulaProductor;
    private String uid;
    private String type_mensaje;


    public Mensaje(String urlFoto, String mensaje, String nombre, String foto, String cedulaProductor, String uid, String type_mensaje) {
        this.urlFoto = urlFoto;
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.foto = foto;
        this.cedulaProductor = cedulaProductor;
        this.uid = uid;
        this.type_mensaje = type_mensaje;
    }

    public Mensaje(String mensaje, String nombre, String foto, String cedulaProductor, String uid, String type_mensaje) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.foto = foto;
        this.cedulaProductor = cedulaProductor;
        this.uid = uid;
        this.type_mensaje = type_mensaje;
    }

    public String getCedulaProductor() {
        return cedulaProductor;
    }

    public void setCedulaProductor(String cedulaProductor) {
        this.cedulaProductor = cedulaProductor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public Mensaje() {
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
