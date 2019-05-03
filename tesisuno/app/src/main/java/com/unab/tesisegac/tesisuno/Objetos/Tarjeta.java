package com.unab.tesisegac.tesisuno.Objetos;

import java.util.List;

public class Tarjeta {
    private List<String> imagenes;
    private String producto;
    private String cantidad;

    public Tarjeta(List<String> imagenes, String producto, String cantidad) {
        this.imagenes=imagenes;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


}
