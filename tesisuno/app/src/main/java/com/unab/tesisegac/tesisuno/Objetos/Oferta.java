package com.unab.tesisegac.tesisuno.Objetos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Oferta implements Serializable {
        @SerializedName("id_oferta")
        @Expose
        private Integer idOferta;
        @SerializedName("nombre_producto")
        @Expose
        private String nombreProducto;
        @SerializedName("unidad_medida_producto")
        @Expose
        private String unidadMedidaProducto;
        @SerializedName("cantidad_producto")
        @Expose
        private Integer cantidadProducto;
        @SerializedName("precio_producto")
        @Expose
        private Integer precioProducto;
        @SerializedName("variedad_producto")
        @Expose
        private String variedadProducto;
        @SerializedName("descripcion_producto")
        @Expose
        private String descripcionProducto;
        @SerializedName("lugar_oferta")
        @Expose
        private String lugarOferta;
        @SerializedName("estado_oferta")
        @Expose
        private String estadoOferta;
        @SerializedName("fecha_recoleccion_oferta")
        @Expose
        private String fechaRecoleccionOferta;
        @SerializedName("productor")
        @Expose
        private Productor productor;
        @SerializedName("ciudad")
        @Expose
        private Ciudad ciudad;
        @SerializedName("create_at")
        @Expose
        private String createAt;


        public Integer getIdOferta() {
            return idOferta;
        }

        public void setIdOferta(Integer idOferta) {
            this.idOferta = idOferta;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }

        public void setNombreProducto(String nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        public String getUnidadMedidaProducto() {
            return unidadMedidaProducto;
        }

        public void setUnidadMedidaProducto(String unidadMedidaProducto) {
            this.unidadMedidaProducto = unidadMedidaProducto;
        }

        public Integer getCantidadProducto() {
            return cantidadProducto;
        }

        public void setCantidadProducto(Integer cantidadProducto) {
            this.cantidadProducto = cantidadProducto;
        }

        public Integer getPrecioProducto() {
            return precioProducto;
        }

        public void setPrecioProducto(Integer precioProducto) {
            this.precioProducto = precioProducto;
        }

        public String getVariedadProducto() {
            return variedadProducto;
        }

        public void setVariedadProducto(String variedadProducto) {
            this.variedadProducto = variedadProducto;
        }

        public String getDescripcionProducto() {
            return descripcionProducto;
        }

        public void setDescripcionProducto(String descripcionProducto) {
            this.descripcionProducto = descripcionProducto;
        }

        public String getLugarOferta() {
            return lugarOferta;
        }

        public void setLugarOferta(String lugarOferta) {
            this.lugarOferta = lugarOferta;
        }

        public String getEstadoOferta() {
            return estadoOferta;
        }

        public void setEstadoOferta(String estadoOferta) {
            this.estadoOferta = estadoOferta;
        }

        public String getFechaRecoleccionOferta() {
            return fechaRecoleccionOferta;
        }

        public void setFechaRecoleccionOferta(String fechaRecoleccionOferta) {
            this.fechaRecoleccionOferta = fechaRecoleccionOferta;
        }

        public Productor getProductor() {
            return productor;
        }

        public void setProductor(Productor productor) {
            this.productor = productor;
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