package com.unab.tesisegac.tesisuno.Objetos;


public class MensajeRecibir extends Mensaje {
    private Long hora;

    public MensajeRecibir() {
    }

    public MensajeRecibir(Long hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String urlFoto, String mensaje, String nombre, String foto, String cedulaProductor, String uid, String type_mensaje, Long hora) {
        super(urlFoto, mensaje, nombre, foto, cedulaProductor, uid, type_mensaje);
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String nombre, String foto, String cedulaProductor, String uid, String type_mensaje, Long hora) {
        super(mensaje, nombre, foto, cedulaProductor, uid, type_mensaje);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
