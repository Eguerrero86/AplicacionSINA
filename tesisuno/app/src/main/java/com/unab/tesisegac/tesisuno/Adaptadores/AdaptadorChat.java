package com.unab.tesisegac.tesisuno.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.unab.tesisegac.tesisuno.Actividades.Conversacion;
import com.unab.tesisegac.tesisuno.Objetos.ChatClase;
import com.unab.tesisegac.tesisuno.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ViewHolderChat> implements View.OnClickListener  {
    List<ChatClase> chats=new ArrayList<>();
    private View.OnClickListener listener;
    Context contex;
    public void addChat(List<ChatClase> chatsRecividos) {
        chats=chatsRecividos;
    }

    public AdaptadorChat(Context contex) {
        this.contex = contex;
    }
    @NonNull
    @Override
    public ViewHolderChat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(contex).inflate(R.layout.layout_converzacion, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolderChat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChat holder, int i) {
        holder.nombre.setText(chats.get(i).getNombre());
        Glide.with(contex)
                .load(chats.get(i).getImagen())
                .fitCenter()
                .centerCrop()
                .into(holder.imagen);

    }

    @Override
    public int getItemCount() {

        return chats.size();
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

    public class ViewHolderChat extends RecyclerView.ViewHolder {
        TextView nombre, ultimoMensaje;
        CircularImageView imagen;
        public ViewHolderChat(@NonNull View itemView) {
            super(itemView);
            nombre=  itemView.findViewById(R.id.chatNombre);
            imagen= itemView.findViewById(R.id.chatImagen);

        }
    }
}
