package com.unab.tesisegac.tesisuno.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.unab.tesisegac.tesisuno.Holder.HolderMensaje;
import com.unab.tesisegac.tesisuno.Objetos.MensajeRecibir;
import com.unab.tesisegac.tesisuno.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdaptadorConversacion extends RecyclerView.Adapter<HolderMensaje> {
    List<MensajeRecibir> listMensajes = new ArrayList<>();
    Context contex;

    public AdaptadorConversacion(Context contex) {
        this.contex = contex;
    }

    public void addMensaje(MensajeRecibir mensaje) {
        listMensajes.add(mensaje);
        notifyItemInserted(listMensajes.size());
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(contex).inflate(R.layout.layout_mensaje, parent, false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holderMensaje, int i) {
        holderMensaje.getNombre().setText(listMensajes.get(i).getNombre());
        holderMensaje.getMensaje().setText(listMensajes.get(i).getMensaje());
        Glide.with(contex)
                .load(listMensajes.get(i).getFoto())
                .fitCenter()
                .centerInside()
                .into(holderMensaje.getFoto());

        if (listMensajes.get(i).getType_mensaje().equals("2")) {
            holderMensaje.getFoto().setVisibility(View.VISIBLE);
            holderMensaje.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(contex)
                    .load(listMensajes.get(i).getUrlFoto())
                    .fitCenter()
                    .centerInside()
                    .into(holderMensaje.getImagenEnviada());
        } else if (listMensajes.get(i).getType_mensaje().equals("1")) {
            holderMensaje.getFoto().setVisibility(View.VISIBLE);
            holderMensaje.getMensaje().setVisibility(View.VISIBLE);

        }

        Long codigo=listMensajes.get(i).getHora();
        Date date=new Date(codigo);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
        holderMensaje.getHora().setText(simpleDateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return listMensajes.size();
    }
}
