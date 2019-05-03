package com.unab.tesisegac.tesisuno.Objetos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Productor implements Serializable {
    @SerializedName("cedula_productor")
    private Integer cedulaProductor;
    @SerializedName("nombre_productor")
    private String nombreProductor;
    @SerializedName("apellido_productor")
    private String apellidoProductor;
    @SerializedName("telefono_productor")
    private String telefonoProductor;
    @SerializedName("correo_productor")
    private String correoProductor;
    @SerializedName("contrasenia_productor")
    private String contraseniaProductor;
    @SerializedName("sexo_productor")
    private String sexoProductor;


    public Integer getCedulaProductor() {
        return cedulaProductor;
    }

    public void setCedulaProductor(Integer cedulaProductor) {
        this.cedulaProductor = cedulaProductor;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }

    public String getApellidoProductor() {
        return apellidoProductor;
    }

    public void setApellidoProductor(String apellidoProductor) {
        this.apellidoProductor = apellidoProductor;
    }

    public String getTelefonoProductor() {
        return telefonoProductor;
    }

    public void setTelefonoProductor(String telefonoProductor) {
        this.telefonoProductor = telefonoProductor;
    }

    public String getCorreoProductor() {
        return correoProductor;
    }

    public void setCorreoProductor(String correoProductor) {
        this.correoProductor = correoProductor;
    }

    public String getContraseniaProductor() {
        return contraseniaProductor;
    }

    public void setContraseniaProductor(String contraseniaProductor) {
        this.contraseniaProductor = contraseniaProductor;
    }

    public String getSexoProductor() {
        return sexoProductor;
    }

    public void setSexoProductor(String sexoProductor) {
        this.sexoProductor = sexoProductor;
    }

}
