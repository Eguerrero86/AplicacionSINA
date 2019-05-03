package com.unab.tesisegac.tesisuno.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unab.tesisegac.tesisuno.ActividadesConsumidor.VerOfertas;
import com.unab.tesisegac.tesisuno.Objetos.Demanda;
import com.unab.tesisegac.tesisuno.Objetos.Oferta;
import com.unab.tesisegac.tesisuno.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SeleccionTipoPlan extends AppCompatActivity {
    Button agrupacion, ubicacion, cantidad, precio;
    Demanda demanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_tipo_plan);
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Bundle Recivido
        Bundle bundleRecivido=getIntent().getExtras();
        demanda=(Demanda)bundleRecivido.getSerializable("seleccion");
        //Inicialización
        agrupacion= findViewById(R.id.busAgrupacion);
        ubicacion=findViewById(R.id.busUbicacion);
        cantidad=findViewById(R.id.busCantidad);
        precio=findViewById(R.id.busPrecio);

        agrupacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verOpciones=new Intent(SeleccionTipoPlan.this, OpcionesPlan.class );
                Bundle seleccion= new Bundle();
                seleccion.putSerializable("seleccion", demanda);
                seleccion.putString("tipoDePlan", "agrupacion");
                verOpciones.putExtras(seleccion);
                startActivity(verOpciones);
            }
        });
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verOpciones=new Intent(SeleccionTipoPlan.this, OpcionesPlan.class );
                Bundle seleccion= new Bundle();
                seleccion.putSerializable("seleccion", demanda);
                seleccion.putString("tipoDePlan", "ubicacion");
                verOpciones.putExtras(seleccion);
                startActivity(verOpciones);
            }
        });
        cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verOpciones=new Intent(SeleccionTipoPlan.this, OpcionesPlan.class );
                Bundle seleccion= new Bundle();
                seleccion.putSerializable("seleccion", demanda);
                seleccion.putString("tipoDePlan", "cantidad");
                verOpciones.putExtras(seleccion);
                startActivity(verOpciones);
            }
        });
        precio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verOpciones=new Intent(SeleccionTipoPlan.this, OpcionesPlan.class );
                Bundle seleccion= new Bundle();
                seleccion.putSerializable("seleccion", demanda);
                seleccion.putString("tipoDePlan", "precio");
                verOpciones.putExtras(seleccion);
                startActivity(verOpciones);
            }
        });
    }

}
