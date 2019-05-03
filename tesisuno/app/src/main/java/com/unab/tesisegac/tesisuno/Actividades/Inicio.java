package com.unab.tesisegac.tesisuno.Actividades;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unab.tesisegac.tesisuno.ActividadeAgricultor.CrearOfertaUno;
import com.unab.tesisegac.tesisuno.ActividadeAgricultor.VerDemandas;
import com.unab.tesisegac.tesisuno.ActividadesConsumidor.CrearDemandaUno;
import com.unab.tesisegac.tesisuno.Objetos.GlobalUsuario;
import com.unab.tesisegac.tesisuno.R;
import com.unab.tesisegac.tesisuno.ActividadesConsumidor.VerOfertas;


public class Inicio extends AppCompatActivity {
    LinearLayout perfil, crearOferta, demandas, chat, plan;
    ImageView imgPerfil, imgCrearOferta, imgDemandas, imgChat, imgPlan;
    ImageView sina;
    TextView crear, ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //MENU
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        sina = (ImageView) findViewById(R.id.imagenSina);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(400, 0, 0, 0);
        sina.setLayoutParams(params);
        //Background
        ConstraintLayout layout_inicio = findViewById(R.id.layout_inicio);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout_inicio.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        //INICIALIZACIÓN
        perfil = findViewById(R.id.ly_perfil);
        crearOferta = findViewById(R.id.ly_crear_oferta);
        demandas = findViewById(R.id.ly_demandas);
        chat = findViewById(R.id.ly_chats);
        imgChat = findViewById(R.id.imgChat);
        imgCrearOferta = findViewById(R.id.imgCrearOferta);
        imgDemandas = findViewById(R.id.imgDemandas);
        imgPerfil = findViewById(R.id.imgPerfil);
        crear = findViewById(R.id.inicio_crear);
        ver = findViewById(R.id.inicio_ver);
        imgPlan=findViewById(R.id.imgPlan);
        plan=findViewById(R.id.ly_plan);
        GlobalUsuario sesion=(GlobalUsuario) getApplicationContext();
        if (sesion.getTipoUsuario().equals("Productor")) {
            Log.e("información usuario", "se entró como productor");
            imgCrearOferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent crearOferta = new Intent(Inicio.this, CrearOfertaUno.class);
                    startActivity(crearOferta);
                }
            });
            crearOferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent crearOferta = new Intent(Inicio.this, CrearOfertaUno.class);
                    startActivity(crearOferta);
                }
            });
            imgDemandas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent demandas = new Intent(Inicio.this, VerDemandas.class);
                    startActivity(demandas);
                }
            });
            demandas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent demandas = new Intent(Inicio.this, VerDemandas.class);
                    startActivity(demandas);
                }
            });

        } else if (sesion.getTipoUsuario().equals("Consumidor")) {

            Log.e("información usuario", "se entró como consumidor");
            imgCrearOferta.setImageResource(R.drawable.ic_tienda);
            crear.setText("Crear Demanda");
            imgDemandas.setImageResource(R.drawable.ic_oferta);
            ver.setText("Ver Ofertas");
            imgCrearOferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent crearOferta = new Intent(Inicio.this, CrearDemandaUno.class);
                    startActivity(crearOferta);
                }
            });
            crearOferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent crearOferta = new Intent(Inicio.this, CrearDemandaUno.class);
                    startActivity(crearOferta);
                }
            });
            imgDemandas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent demandas = new Intent(Inicio.this, VerOfertas.class);
                    startActivity(demandas);
                }
            });
            demandas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent demandas = new Intent(Inicio.this, VerOfertas.class);
                    startActivity(demandas);
                }
            });

        }

//INTENT
        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plan=new Intent(Inicio.this, PlanDeCompra.class);
                startActivity(plan);
            }
        });
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plan=new Intent(Inicio.this, PlanDeCompra.class);
                startActivity(plan);
            }
        });
        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(Inicio.this, Perfil.class);
                startActivity(perfil);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(Inicio.this, Perfil.class);
                startActivity(perfil);
            }
        });
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chats = new Intent(Inicio.this, Chat.class);
                startActivity(chats);

            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chats = new Intent(Inicio.this, Chat.class);
                startActivity(chats);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.editar:
                Toast.makeText(getApplicationContext(), "Editar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cerrar_sesion:
                Toast.makeText(getApplicationContext(), "cerrar sesion", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
