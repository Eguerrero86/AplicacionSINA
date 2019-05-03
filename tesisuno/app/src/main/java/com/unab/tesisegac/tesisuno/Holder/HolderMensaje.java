package com.unab.tesisegac.tesisuno.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.unab.tesisegac.tesisuno.R;

public class HolderMensaje extends RecyclerView.ViewHolder {
    private CircularImageView foto;
    private TextView nombre;
    private TextView hora;
    private TextView mensaje;
    private ImageView imagenEnviada;

    public HolderMensaje(@NonNull View itemView) {
        super(itemView);
        foto = itemView.findViewById(R.id.menImagen);
        nombre = itemView.findViewById(R.id.menNombre);
        hora = itemView.findViewById(R.id.menHora);
        mensaje = itemView.findViewById(R.id.menMensaje);
        imagenEnviada=itemView.findViewById(R.id.menFoto);
    }

    public CircularImageView getFoto() {
        return foto;
    }

    public ImageView getImagenEnviada() {
        return imagenEnviada;
    }

    public void setImagenEnviada(ImageView imagenEnviada) {
        this.imagenEnviada = imagenEnviada;
    }

    public void setFoto(CircularImageView foto) {
        this.foto = foto;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }
}
