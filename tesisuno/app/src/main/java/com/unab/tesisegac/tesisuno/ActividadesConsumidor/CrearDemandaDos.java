package com.unab.tesisegac.tesisuno.ActividadesConsumidor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unab.tesisegac.tesisuno.ActividadeAgricultor.CrearOfertaDos;
import com.unab.tesisegac.tesisuno.ActividadeAgricultor.CrearOfertaTres;
import com.unab.tesisegac.tesisuno.Objetos.Demanda;
import com.unab.tesisegac.tesisuno.Objetos.GlobalUsuario;
import com.unab.tesisegac.tesisuno.Objetos.Oferta;
import com.unab.tesisegac.tesisuno.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CrearDemandaDos extends AppCompatActivity {
    EditText direccion, ciudad, descripcion;
    String producto, variedad, cantidad, unidad;
    Button ubicacion, atras, guardar;
    Demanda demanda;
    GlobalUsuario sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_demanda_dos);
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //INICIALIZACIÓN
        direccion = findViewById(R.id.cdDireccion);
        ciudad = findViewById(R.id.cdCiudad);
        descripcion = findViewById(R.id.cdObservaciones);
        ubicacion = findViewById(R.id.cdUbicacion);
        atras = findViewById(R.id.cdAtras);
        guardar = findViewById(R.id.cdGuardarOferta);
        sesion= (GlobalUsuario) getApplicationContext();
        Bundle bundleRecivido = getIntent().getExtras();
        if (bundleRecivido != null) {
            producto = bundleRecivido.getString("producto");
            variedad = bundleRecivido.getString("variedad");
            cantidad = bundleRecivido.getString("cantidad");
            unidad = bundleRecivido.getString("medida");


        }
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(CrearDemandaDos.this, CrearDemandaUno.class);
                startActivity(atras);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (producto != "" && cantidad != "" && variedad != "" && unidad != "" &&
                        !direccion.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty()
                        && !ciudad.getText().toString().isEmpty()) {
                    String json = " {\n" +
                            "        \"nombre_producto\": \"" + producto + "\",\n" +
                            "        \"medida_producto\": \"" + unidad + "\",\n" +
                            "        \"cantidad_producto\": " + cantidad + ",\n" +
                            "        \"variedad_producto\": \"" + variedad + "\",\n" +
                            "        \"descripcion_demanda\": \"" + descripcion.getText().toString() + "\",\n" +
                            "        \"direccion_demanda\": \"" + direccion.getText().toString() + "\",\n" +
                            "        \"estado_demanda\": \" Publicado \",\n" +
                            "        \"consumidor\": "+sesion.getIdUsuario()+",\n" +
                            "        \"ciudad\": "+ciudad.getText().toString()+"\n" +
                            "    }";
                    registro(json);
                }
            }
        });
    }

    //APLICATION/JSON
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    //REGISTRO
    public void registro(String json) {
        Log.d("clic", "-------------CLIC-------------");
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+getString(R.string.ip)+"/api/demandas";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Error", e.getMessage());
                Log.d("Fallido", "--------------No se logró nada-----------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    CrearDemandaDos.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Aceptado", "-----------------Estamos Readys-------------------" + myResponse);
                            Gson gson = new GsonBuilder().create();
                            demanda = gson.fromJson(myResponse, Demanda.class);
                            Log.d("Aceptado", "-----------------Campos de la oferta creada-------------------" +
                                    demanda.getIdDemanda() + " nombre" + demanda.getNombreProducto());
                            Intent tres = new Intent(CrearDemandaDos.this, CrearDemandaTres.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("idDemanda", demanda.getIdDemanda());
                            tres.putExtras(bundle);
                            startActivity(tres);
                        }
                    });
                } else {
                    Log.d("Aceptado", "-----------------Faltan campos por llenar------------------");
                }
            }

        });


    }
}


