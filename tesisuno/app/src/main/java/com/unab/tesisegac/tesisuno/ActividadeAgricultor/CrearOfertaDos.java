package com.unab.tesisegac.tesisuno.ActividadeAgricultor;

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

public class CrearOfertaDos extends AppCompatActivity {
    EditText direccion, presio, descripcion, ciudad;
    String producto, variedad, cantidad, unidad, estado, fecha;
    Button ubicacion, atras, guardar;
    Oferta oferta;
    GlobalUsuario sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta_dos);
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //INICIALIZACIÓN
        direccion = findViewById(R.id.coDireccion);
        presio = findViewById(R.id.coPresio);
        descripcion = findViewById(R.id.coObservaciones);
        ubicacion = findViewById(R.id.coUbicacion);
        atras = findViewById(R.id.coAtras);
        guardar = findViewById(R.id.coGuardarOferta);
        ciudad=findViewById(R.id.coCiudad);
        sesion=(GlobalUsuario)getApplicationContext();

        Bundle bundleRecivido = getIntent().getExtras();
        if (bundleRecivido != null) {
            producto = bundleRecivido.getString("producto");
            variedad = bundleRecivido.getString("variedad");
            cantidad = bundleRecivido.getString("cantidad");
            unidad = bundleRecivido.getString("unidad");
            estado = bundleRecivido.getString("estado");
            fecha = bundleRecivido.getString("fecha");
            unidad = bundleRecivido.getString("medida");

        }
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(CrearOfertaDos.this, CrearOfertaUno.class);
                startActivity(atras);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (producto != "" && cantidad != "" && fecha != "" && variedad != "" && unidad != "" && estado != "" &&
                        !direccion.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty()
                        && !presio.getText().toString().isEmpty()) {
                    String json = " {\n" +
                            "        \"nombre_producto\": \"" + producto + "\",\n" +
                            "        \"unidad_medida_producto\": \"" + unidad + "\",\n" +
                            "        \"cantidad_producto\": " + cantidad + ",\n" +
                            "        \"precio_producto\": "+ presio.getText().toString() +",\n" +
                            "        \"variedad_producto\": \"" + variedad + "\",\n" +
                            "        \"descripcion_producto\": \"" + descripcion.getText().toString() + "\",\n" +
                            "        \"lugar_oferta\": \"" + direccion.getText().toString() + "\",\n" +
                            "        \"estado_oferta\": \"" + estado + "\",\n" +
                            "        \"fecha_recoleccion_oferta\": \"" + fecha + "\",\n" +
                            "        \"productor\": "+sesion.getIdUsuario()+",\n" +
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
        String url = "http://"+getString(R.string.ip)+"/api/ofertas";
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
                    CrearOfertaDos.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Aceptado", "-----------------Estamos Readys-------------------" +myResponse);
                            Gson gson = new GsonBuilder().create();
                            oferta= gson.fromJson(myResponse, Oferta.class);
                            Log.d("Aceptado", "-----------------Campos de la oferta creada-------------------" +
                                    oferta.getIdOferta()+" nombre"+oferta.getNombreProducto() );
                          Intent tres = new Intent(CrearOfertaDos.this, CrearOfertaTres.class);
                          Bundle bundle=new Bundle();
                          bundle.putInt("idOferta", oferta.getIdOferta());
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
