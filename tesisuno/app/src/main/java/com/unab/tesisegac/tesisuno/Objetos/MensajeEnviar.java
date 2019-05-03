package com.unab.tesisegac.tesisuno.Objetos;

import java.util.Map;

public class MensajeEnviar extends Mensaje {
    private Map hora;

    public MensajeEnviar(Map hora) {
        this.hora = hora;
    }

    public MensajeEnviar() {
    }

    public MensajeEnviar(String urlFoto, String mensaje, String nombre, String foto, String cedulaProductor, String uid, String type_mensaje, Map hora) {
        super(urlFoto, mensaje, nombre, foto, cedulaProductor, uid, type_mensaje);
        this.hora = hora;
    }

    public MensajeEnviar(String mensaje, String nombre, String foto, String cedulaProductor, String uid, String type_mensaje, Map hora) {
        super(mensaje, nombre, foto, cedulaProductor, uid, type_mensaje);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
