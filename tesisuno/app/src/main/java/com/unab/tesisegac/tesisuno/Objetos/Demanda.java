package com.unab.tesisegac.tesisuno.Objetos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Demanda implements Serializable {
        @SerializedName("id_demanda")
        @Expose
        private Integer idDemanda;
        @SerializedName("nombre_producto")
        @Expose
        private String nombreProducto;
        @SerializedName("medida_producto")
        @Expose
        private String medidaProducto;
        @SerializedName("cantidad_producto")
        @Expose
        private Integer cantidadProducto;
        @SerializedName("variedad_producto")
        @Expose
        private String variedadProducto;
        @SerializedName("descripcion_demanda")
        @Expose
        private String descripcionDemanda;
        @SerializedName("direccion_demanda")
        @Expose
        private String direccionDemanda;
        @SerializedName("estado_demanda")
        @Expose
        private String estadoDemanda;
        @SerializedName("consumidor")
        @Expose
        private Consumidor consumidor;
        @SerializedName("ciudad")
        @Expose
        private Ciudad ciudad;
        @SerializedName("create_at")
        @Expose
        private String createAt;

    public String getDireccionDemanda() {
        return direccionDemanda;
    }

    public void setDireccionDemanda(String direccionDemanda) {
        this.direccionDemanda = direccionDemanda;
    }

    public Integer getIdDemanda() {
            return idDemanda;
        }

        public void setIdDemanda(Integer idDemanda) {
            this.idDemanda = idDemanda;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }

        public void setNombreProducto(String nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        public String getMedidaProducto() {
            return medidaProducto;
        }

        public void setMedidaProducto(String medidaProducto) {
            this.medidaProducto = medidaProducto;
        }

        public Integer getCantidadProducto() {
            return cantidadProducto;
        }

        public void setCantidadProducto(Integer cantidadProducto) {
            this.cantidadProducto = cantidadProducto;
        }
        
        public String getVariedadProducto() {
            return variedadProducto;
        }

        public void setVariedadProducto(String variedadProducto) {
            this.variedadProducto = variedadProducto;
        }

        public String getDescripcionDemanda() {
            return descripcionDemanda;
        }

        public void setDescripcionDemanda(String descripcionDemanda) {
            this.descripcionDemanda = descripcionDemanda;
        }
        
        public String getEstadoDemanda() {
            return estadoDemanda;
        }

        public void setEstadoOferta(String estadoDemanda) {
            this.estadoDemanda = estadoDemanda;
        }

        public Consumidor getConsumidor() {
            return consumidor;
        }

        public void setConsumidor(Consumidor consumidor) {
            this.consumidor = consumidor;
        }

        public Ciudad getCiudad() {
            return ciudad;
        }

        public void setCiudad(Ciudad ciudad) {
            this.ciudad = ciudad;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }


}