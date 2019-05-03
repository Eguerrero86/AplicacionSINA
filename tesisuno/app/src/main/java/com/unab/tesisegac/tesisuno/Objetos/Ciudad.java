package com.unab.tesisegac.tesisuno.Objetos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Ciudad implements Serializable {
    @SerializedName("id_ciudad")
    @Expose
    private Integer idCiudad;
    @SerializedName("nombre_ciudad")
    @Expose
    private String nombreCiudad;
    @SerializedName("lat_ciudad")
    @Expose
    private String latCiudad;
    @SerializedName("long_ciudad")
    @Expose
    private String longCiudad;
    @SerializedName("departamento_ciudad")
    @Expose
    private String departamentoCiudad;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getLatCiudad() {
        return latCiudad;
    }

    public void setLatCiudad(String latCiudad) {
        this.latCiudad = latCiudad;
    }

    public String getLongCiudad() {
        return longCiudad;
    }

    public void setLongCiudad(String longCiudad) {
        this.longCiudad = longCiudad;
    }

    public String getDepartamentoCiudad() {
        return departamentoCiudad;
    }

    public void setDepartamentoCiudad(String departamentoCiudad) {
        this.departamentoCiudad = departamentoCiudad;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}