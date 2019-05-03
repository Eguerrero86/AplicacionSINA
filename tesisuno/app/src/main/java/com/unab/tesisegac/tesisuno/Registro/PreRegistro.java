package com.unab.tesisegac.tesisuno.Registro;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.unab.tesisegac.tesisuno.Actividades.Login;
import com.unab.tesisegac.tesisuno.R;

public class PreRegistro extends AppCompatActivity {
    ImageButton agricultor, consumidor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_registro);

        //Inicialización
        ConstraintLayout layout_pre_registro= findViewById(R.id.layout_pre_registro);
        agricultor=findViewById(R.id.reImgAgricultor);
        consumidor=findViewById(R.id.reImgProductor);

        //BRACKGROUND
        AnimationDrawable animationDrawable= (AnimationDrawable)  layout_pre_registro.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        agricultor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agri= new Intent(PreRegistro.this, Login.class);
                Bundle myBundle= new Bundle();
                myBundle.putString("tipoUsuario", "Productor");
                agri.putExtras(myBundle);
                startActivity(agri);
            }
        });
       consumidor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent cons= new Intent(PreRegistro.this, Login.class);
               Bundle myBundle=new Bundle();
               myBundle.putString("tipoUsuario", "Consumidor");
               cons.putExtras(myBundle);
               startActivity(cons);
           }
       });
    }
}
