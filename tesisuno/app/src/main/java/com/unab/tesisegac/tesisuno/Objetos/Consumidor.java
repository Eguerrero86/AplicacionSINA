package com.unab.tesisegac.tesisuno.Objetos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

    public class Consumidor implements Serializable{

        @SerializedName("cedula_consumidor")
        @Expose
        private Integer cedulaConsumidor;
        @SerializedName("nombre_consumidor")
        @Expose
        private String nombreConsumidor;
        @SerializedName("apellido_consumidor")
        @Expose
        private String apellidoConsumidor;
        @SerializedName("telefono_consumidor")
        private String telefonoConsumidor;
        @SerializedName("correo_consumidor")
        @Expose
        private String correoConsumidor;
        @SerializedName("contrasenia_consumidor")
        @Expose
        private String contraseniaConsumidor;
        @SerializedName("sexo_consumidor")
        @Expose
        private String sexoConsumidor;


        public Integer getCedulaConsumidor() {
            return cedulaConsumidor;
        }

        public void setCedulaConsumidor(Integer cedulaConsumidor) {
            this.cedulaConsumidor = cedulaConsumidor;
        }

        public String getNombreConsumidor() {
            return nombreConsumidor;
        }

        public void setNombreConsumidor(String nombreConsumidor) {
            this.nombreConsumidor = nombreConsumidor;
        }

        public String getApellidoConsumidor() {
            return apellidoConsumidor;
        }

        public void setApellidoConsumidor(String apellidoConsumidor) {
            this.apellidoConsumidor = apellidoConsumidor;
        }

        public String getTelefonoConsumidor() {
            return telefonoConsumidor;
        }

        public void setTelefonoConsumidor(String telefonoConsumidor) {
            this.telefonoConsumidor = telefonoConsumidor;
        }

        public String getCorreoConsumidor() {
            return correoConsumidor;
        }

        public void setCorreoConsumidor(String correoConsumidor) {
            this.correoConsumidor = correoConsumidor;
        }

        public String getContraseniaConsumidor() {
            return contraseniaConsumidor;
        }

        public void setContraseniaConsumidor(String contraseniaConsumidor) {
            this.contraseniaConsumidor = contraseniaConsumidor;
        }

        public String getSexoConsumidor() {
            return sexoConsumidor;
        }

        public void setSexoConsumidor(String sexoConsumidor) {
            this.sexoConsumidor = sexoConsumidor;
        }

    }