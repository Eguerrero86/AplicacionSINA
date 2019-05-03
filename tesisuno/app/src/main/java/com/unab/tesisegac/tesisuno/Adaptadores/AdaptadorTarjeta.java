package com.unab.tesisegac.tesisuno.Adaptadores;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unab.tesisegac.tesisuno.Objetos.Tarjeta;
import com.unab.tesisegac.tesisuno.R;

import java.util.ArrayList;

public class AdaptadorTarjeta extends RecyclerView.Adapter<AdaptadorTarjeta.ViewHolderTarjeta> implements View.OnClickListener {
    ArrayList<Tarjeta> tarjetas;
    private View.OnClickListener listener;
    Context contex;
    public AdaptadorTarjeta(Context contex) {
        this.contex = contex;
    }
    public void addTarjeta(ArrayList<Tarjeta> tarjetas) {

        this.tarjetas = tarjetas;
    }

    @NonNull
    @Override
    public ViewHolderTarjeta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_tarjeta, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolderTarjeta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTarjeta holder, int i) {
        Log.d("creación tarjets", "creación"+i+" "+tarjetas.get(i).getProducto());
        holder.producto.setText(tarjetas.get(i).getProducto());
        holder.cantidad.setText(tarjetas.get(i).getCantidad());
        Glide.with(contex)
                .load(tarjetas.get(i).getImagenes().get(0))
                .fitCenter()
                .centerCrop()
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {

        return tarjetas.size();
    }
    public void setOnclickListener(View.OnClickListener listener){

        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderTarjeta extends RecyclerView.ViewHolder {
        TextView producto, cantidad;
        ImageView imagen;
        public ViewHolderTarjeta(@NonNull View itemView) {
            super(itemView);
            producto=  itemView.findViewById(R.id.tarjetaProucto);
            cantidad=  itemView.findViewById(R.id.tarjetaCantidad);
            imagen= itemView.findViewById(R.id.tarjetaImagen);

        }
    }
}
