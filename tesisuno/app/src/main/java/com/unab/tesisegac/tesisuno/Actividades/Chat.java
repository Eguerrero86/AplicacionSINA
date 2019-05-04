package com.unab.tesisegac.tesisuno.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.unab.tesisegac.tesisuno.Adaptadores.AdaptadorChat;
import com.unab.tesisegac.tesisuno.Objetos.ChatClase;
import com.unab.tesisegac.tesisuno.Objetos.Consumidor;
import com.unab.tesisegac.tesisuno.Objetos.GlobalUsuario;
import com.unab.tesisegac.tesisuno.Objetos.Productor;
import com.unab.tesisegac.tesisuno.Objetos.Usuario;
import com.unab.tesisegac.tesisuno.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Chat extends AppCompatActivity {
    List<ChatClase> chats=new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    GlobalUsuario sesion;
    AdaptadorChat adapter;
    Productor productor;
    Consumidor consumidor;
    String nombreTomado = "Nombre Ejemplo";
    Usuario usuarioConverzacion;
    List<Consumidor> consumidores;
    List<Productor> productores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //InicializaciÃƒÂ³n
        productor = new Productor();
        consumidor = new Consumidor();
        usuarioConverzacion=new Usuario();
        recyclerView = findViewById(R.id.recyclerChats);
        sesion = (GlobalUsuario) getApplicationContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        String id = Integer.toString(sesion.getIdUsuario());
        databaseReference = database.getReference().child("chats").child(id);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        adapter = new AdaptadorChat(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        consumidores=new ArrayList<>();
        productores=new ArrayList<>();
        llenarlista();


        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversacion = new Intent(Chat.this, Conversacion.class);
                int posicion=recyclerView.getChildAdapterPosition(v);
                Bundle myBundle=new Bundle();
                if (sesion.getTipoUsuario().equals("Productor")){
                    myBundle.putSerializable("receptor",consumidores.get(recyclerView.getChildAdapterPosition(v)));
                }else if(sesion.getTipoUsuario().equals("Consumidor")){
                    myBundle.putSerializable("receptor",productores.get(recyclerView.getChildAdapterPosition(v)));
                }
                conversacion.putExtras(myBundle);
                startActivity(conversacion);

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

    private void abrirConverzacion(View v) {

    }

    private void llenarlista() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idChat = snapshot.getKey();
                    if (sesion.getTipoUsuario().equals("Productor")) {
                        storageReference.child("Consumidor").child(idChat).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                traerUsuario(idChat, uri);
                            }
                        });
                    } else if (sesion.getTipoUsuario().equals("Consumidor")) {
                        storageReference.child("Productor").child(idChat).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                traerUsuario(idChat, uri);
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error al sacar imagenes", "no se econtraron imagenes de la oferta");
            }

        });
    }

    public void traerUsuario(String id, Uri uri) {
        String url = "";
        OkHttpClient client = new OkHttpClient();
        if (sesion.getTipoUsuario().equals("Productor")) {
            url = "http://"+getString(R.string.ip)+"/api/consumidores/"+ id +"";
        } else if (sesion.getTipoUsuario().equals("Consumidor")) {
            url = "http://"+getString(R.string.ip)+"/api/productores/" + id + "";
        }
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

                    Chat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sesion.getTipoUsuario().equals("Productor")) {
                                final Gson gson = new Gson();
                                consumidor = gson.fromJson(myResponse, Consumidor.class);
                                nombreTomado = "" + consumidor.getNombreConsumidor() + " " + consumidor.getApellidoConsumidor();
                                consumidores.add(consumidor);
                                chats.add(new ChatClase(uri, nombreTomado, usuarioConverzacion));
                                adapter.addChat(chats);
                                recyclerView.setAdapter(adapter);
                            } else if (sesion.getTipoUsuario().equals("Consumidor")) {
                                final Gson gson = new Gson();
                                productor = gson.fromJson(myResponse, Productor.class);
                                nombreTomado = "" + productor.getNombreProductor() + " " + productor.getApellidoProductor();
                                productores.add(productor);
                                chats.add(new ChatClase(uri, nombreTomado, usuarioConverzacion));
                                adapter.addChat(chats);
                                recyclerView.setAdapter(adapter);
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
