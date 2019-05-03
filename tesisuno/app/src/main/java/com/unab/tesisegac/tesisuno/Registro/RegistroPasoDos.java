package com.unab.tesisegac.tesisuno.Registro;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unab.tesisegac.tesisuno.Actividades.Login;
import com.unab.tesisegac.tesisuno.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistroPasoDos extends AppCompatActivity {
    EditText correo, contrasena;
    String nombre, cedula, telefono, sexo, ciudad, direccion, departamento, apellido, tipoUsuario;
    Button galeria, atras, registrar;
    StorageReference myStorage;
    private static final int GALERY_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paso_dos);


        //MENU
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //INICIALIZACIÓN
        correo = findViewById(R.id.reCorreo);
        contrasena = findViewById(R.id.reContrasena);
        galeria = findViewById(R.id.reFoto);
        atras = findViewById(R.id.reAtras3);
        registrar = findViewById(R.id.reRegistrar);
        myStorage = FirebaseStorage.getInstance().getReference();
        Bundle bundleRecivido = getIntent().getExtras();
        if (bundleRecivido != null) {
            nombre = bundleRecivido.getString("nombre");
            apellido = bundleRecivido.getString("apellido");
            cedula = bundleRecivido.getString("cedula");
            telefono = bundleRecivido.getString("telefono");
            sexo = bundleRecivido.getString("sexo");
            tipoUsuario = bundleRecivido.getString("tipoUsuario");
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(RegistroPasoDos.this, RegistroPasoUno.class);
                startActivity(atras);
            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galeria = new Intent(Intent.ACTION_PICK);
                galeria.setType("image/*");
                startActivityForResult(galeria, GALERY_INTENT);
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tipoUsuario", tipoUsuario);
                if (tipoUsuario.equals("Productor") ) {
                    Log.d("tipoUsuario", "agicola bien");
                    if (!correo.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty()) {
                        Log.d("tipoUsuario", "armando el json");
                        String json = "  {\n" +
                                "        \"cedula_productor\": "+cedula+",\n" +
                                "        \"nombre_productor\": \""+nombre+"\",\n" +
                                "        \"apellido_productor\": \""+apellido+"\",\n" +
                                "        \"telefono_productor\": "+telefono+",\n" +
                                "        \"correo_productor\": \""+correo.getText().toString()+"\",\n" +
                                "        \"contrasenia_productor\": \""+contrasena.getText().toString()+",\",\n" +
                                "        \"sexo_productor\": \""+sexo+"\"\n" +
                                "    }";
                        registro(json, "productores");
                    } else {
                        Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                    }
                } else if (tipoUsuario.equals("Consumidor")) {
                    if (!correo.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty()) {
                        String json = "  {\n" +
                                "        \"cedula_consumidor\": "+cedula+",\n" +
                                "        \"nombre_consumidor\": \""+nombre+"\",\n" +
                                "        \"apellido_consumidor\": \""+apellido+"\",\n" +
                                "        \"telefono_consumidor\": "+telefono+",\n" +
                                "        \"correo_consumidor\": \""+correo.getText().toString()+"\",\n" +
                                "        \"contrasenia_consumidor\": \""+contrasena.getText().toString()+"\",\n" +
                                "        \"sexo_consumidor\": \""+sexo+"\"\n" +
                                "    }";
                        registro(json, "consumidores");
                    } else {
                        Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    //APLICATION/JSON
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    //REGISTRO
    public void registro(String json, String usuarioTipo) {
        Log.d("tipoUsuario", "intentado el registro");
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+getString(R.string.ip)+"/api/"+usuarioTipo+"";
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

                    RegistroPasoDos.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Aceptado", "-----------------Estamos Readys-------------------" +
                                    myResponse);
                            Intent login = new Intent(RegistroPasoDos.this, Login.class);
                            Bundle myBundle= new Bundle();
                            myBundle.putString("tipoUsuario", tipoUsuario);
                            login.putExtras(myBundle);
                            startActivity(login);
                        }
                    });
                } else {
                    Log.d("Aceptado", "-----------------Faltan campos por llenar------------------");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            StorageReference filepad = myStorage.child(tipoUsuario).child(cedula);
            Log.d("requescode", uri.toString());
            filepad.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Se subió correctamente la foto", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Log.d("requescode", "------------------NO Estamos en el if-------------------------");
        }
    }

}
