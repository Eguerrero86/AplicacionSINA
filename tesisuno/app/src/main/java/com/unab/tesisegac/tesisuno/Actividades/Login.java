package com.unab.tesisegac.tesisuno.Actividades;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unab.tesisegac.tesisuno.ActividadeAgricultor.VerDemandas;
import com.unab.tesisegac.tesisuno.Objetos.Consumidor;
import com.unab.tesisegac.tesisuno.Objetos.Demanda;
import com.unab.tesisegac.tesisuno.Objetos.GlobalUsuario;
import com.unab.tesisegac.tesisuno.Objetos.Productor;
import com.unab.tesisegac.tesisuno.R;
import com.unab.tesisegac.tesisuno.Registro.RegistroPasoUno;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    TextView tituloLogin, registro;
    String tipoUsuario;
    EditText correo, contrasena;
    private Typeface tipo;
    Button login;
    Consumidor consumidor;
    Productor productor;
    GlobalUsuario sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //INICIALIZACIÓN
        ConstraintLayout layout_login = findViewById(R.id.layout_login);
        login = findViewById(R.id.logEntrar);
        registro = findViewById(R.id.txt_registro);
        correo = findViewById(R.id.logCorreo);
        contrasena = findViewById(R.id.logContrasena);
        consumidor = new Consumidor();
        productor = new Productor();
        sesion = (GlobalUsuario) getApplicationContext();

        //BRACKGROUND
        AnimationDrawable animationDrawable = (AnimationDrawable) layout_login.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //RECIBE TIPO USUARIO
        Bundle bundleRecivido = getIntent().getExtras();
        tipoUsuario = bundleRecivido.getString("tipoUsuario");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tipoUsuario.equals("Consumidor")) {
                    verificacionConsumidor(correo.getText().toString(), contrasena.getText().toString());


                } else if (tipoUsuario.equals("Productor")) {
                    verificacionProductor(correo.getText().toString(), contrasena.getText().toString());


                }


            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registro = new Intent(Login.this, RegistroPasoUno.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("tipoUsuario", tipoUsuario);
                registro.putExtras(myBundle);
                startActivity(registro);
            }
        });
    }

    public void verificacionConsumidor(String correoC, String contrasenaC) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+getString(R.string.ip)+"/api/consumidores/buscar/" + correoC + "/" + contrasenaC + "";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Error", e.getMessage());
                Log.d("Fallido", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Login.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Gson gson = new Gson();

                            consumidor = gson.fromJson(myResponse, Consumidor.class);
                            if (consumidor != null) {
                                sesion.setIdUsuario(consumidor.getCedulaConsumidor());
                                sesion.setNombre(consumidor.getNombreConsumidor());
                                sesion.setApellido(consumidor.getApellidoConsumidor());
                                sesion.setCorreo(consumidor.getCorreoConsumidor());
                                sesion.setTelefono(consumidor.getTelefonoConsumidor());
                                sesion.setTipoUsuario(tipoUsuario);
                                Intent inicial = new Intent(Login.this, Inicio.class);
                                startActivity(inicial);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Los campos ingresados son incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Log.d("Aceptado", "-----------------Faltan campos por llenar------------------");
                }
            }
        });
    }

    public void verificacionProductor(String correoP, String contrasenaP) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+getString(R.string.ip)+"/api/productores/buscar/" + correoP + "/" + contrasenaP + "";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Error", e.getMessage());
                Log.d("Fallido", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Login.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Gson gson = new Gson();

                                productor = gson.fromJson(myResponse, Productor.class);
                            if (productor != null) {
                                sesion.setIdUsuario(productor.getCedulaProductor());
                                sesion.setNombre(productor.getNombreProductor());
                                sesion.setApellido(productor.getApellidoProductor());
                                sesion.setCorreo(productor.getCorreoProductor());
                                sesion.setTelefono(productor.getTelefonoProductor());
                                sesion.setTipoUsuario(tipoUsuario);
                                Intent inicial = new Intent(Login.this, Inicio.class);
                                startActivity(inicial);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Los campos ingresados son incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Log.d("Aceptado", "-----------------Faltan campos por llenar------------------");
                }
            }
        });

    }
}
