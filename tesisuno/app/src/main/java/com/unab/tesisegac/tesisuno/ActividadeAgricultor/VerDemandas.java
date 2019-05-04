package com.unab.tesisegac.tesisuno.ActividadeAgricultor;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.unab.tesisegac.tesisuno.Actividades.Conversacion;
import com.unab.tesisegac.tesisuno.Adaptadores.AdaptadorTarjeta;
import com.unab.tesisegac.tesisuno.Objetos.Demanda;
import com.unab.tesisegac.tesisuno.Objetos.ImagenFireBase;
import com.unab.tesisegac.tesisuno.Objetos.Tarjeta;
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

public class VerDemandas extends AppCompatActivity {
    ArrayList<Tarjeta> tarjetas;
    List<String> imagenesDemanda;
    List<Demanda> demandas;
    RecyclerView recyclerView;
    int contador = 0;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int permissionCheck, idAnterior;
    FirebaseStorage storage;
    StorageReference myStorage;
    DatabaseReference myDataBase;
    AdaptadorTarjeta adaptaer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demandas);
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //InicializaciÃ³n
        tarjetas = new ArrayList<>();
        demandas = new ArrayList<Demanda>();
        imagenesDemanda = new ArrayList<String>();
        recyclerView = findViewById(R.id.recyclerDemandas);
        myDataBase = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        traerDemandas();
        adaptaer = new AdaptadorTarjeta(getApplicationContext());
        storage= FirebaseStorage.getInstance();
        myStorage=storage.getReference();

        adaptaer.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogDescripcion(v);
            }
        });

    }

    private void traerDemandas() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+getString(R.string.ip)+"/api/demandas";
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

                    VerDemandas.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Gson gson = new Gson();
                            final Type tipoLista = new TypeToken<List<Demanda>>() {
                            }.getType();
                            demandas = gson.fromJson(myResponse, tipoLista);
                            llenarTarjetas();
                        }
                    });
                } else {
                    Log.d("Aceptado", "-----------------Faltan campos por llenar------------------");
                }
            }
        });
    }

    public void llenarTarjetas() {
        for (int i = 0; i < demandas.size(); i++) {
            traerImagenes(demandas.get(i).getIdDemanda(), i);
        }
    }

    private void traerImagenes(int idDemanda, int i) {
        if (checkPermissionREAD_EXTERNAL_STORAGE(VerDemandas.this)) {
            checkPermissionREAD_EXTERNAL_STORAGE(VerDemandas.this);
            String id = Integer.toString(idDemanda);

            myDataBase.child("demandas").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()==0){
                        imagenesDemanda.add("https://firebasestorage.googleapis.com/v0/b/imagenestesis-dc1cf.appspot.com/o/predetarminada%2Fdefault.jpg?alt=media&token=666a8413-a84f-4f80-85bb-a7c0b36d1bfe");

                    }else {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ImagenFireBase imagen = snapshot.getValue(ImagenFireBase.class);
                            imagenesDemanda.add(imagen.getUrl());

                        }
                    }
                    String cantidadProducto = Integer.toString(demandas.get(i).getCantidadProducto());
                    tarjetas.add(new Tarjeta(imagenesDemanda, demandas.get(i).getNombreProducto(), cantidadProducto));
                    imagenesDemanda = new ArrayList<String>();
                    adaptaer.addTarjeta(tarjetas);
                    recyclerView.setAdapter(adaptaer);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error al sacar imagenes", "no se econtraron imagenes de la demanda");
                }

            });
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        android.support.v7.app.AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(VerDemandas.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
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

    private void abrirDialogDescripcion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_descripcion, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView producto = view.findViewById(R.id.productoDialog);
        TextView cantidad = view.findViewById(R.id.cantidadDialog);
        TextView direccion= view.findViewById(R.id.direccionDialog);
        TextView descripccion= view.findViewById(R.id.descripcionDialog);
        TextView unidad= view.findViewById(R.id.unidadDialog);
        CircularImageView imagenPublicacion=view.findViewById(R.id.imagenDialog);
        TextView nombre=view.findViewById(R.id.nombreDialog);
        TextView ciudad=view.findViewById(R.id.ciudadDialog);
        Button enviarMensaje= view.findViewById(R.id.enviarMensajeDialog);
        int y=recyclerView.getChildAdapterPosition(v);
        producto.setText(demandas.get(y).getNombreProducto());
        direccion.setText(demandas.get(y).getDireccionDemanda());
        cantidad.setText(demandas.get(y).getCantidadProducto().toString());
        descripccion.setText(demandas.get(y).getDescripcionDemanda());
        unidad.setText(demandas.get(y).getMedidaProducto());
        nombre.setText(demandas.get(y).getConsumidor().getNombreConsumidor()+" "+demandas.get(y).getConsumidor().getApellidoConsumidor());
        ciudad.setText(demandas.get(y).getCiudad().getNombreCiudad());
        unidad.setText(demandas.get(y).getMedidaProducto());
        String idConsumidor= Integer.toString(demandas.get(y).getConsumidor().getCedulaConsumidor());
        myStorage.child("/Consumidor").child("/"+idConsumidor).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(VerDemandas.this)
                        .load(uri)
                        .fitCenter()
                        .centerCrop()
                        .into(imagenPublicacion);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        enviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversar= new Intent(VerDemandas.this, Conversacion.class);
                Bundle myBundle=new Bundle();
                myBundle.putSerializable("receptor", demandas.get(y).getConsumidor());
                conversar.putExtras(myBundle);
                startActivity(conversar);
                finish();
            }
        });

    }

}
