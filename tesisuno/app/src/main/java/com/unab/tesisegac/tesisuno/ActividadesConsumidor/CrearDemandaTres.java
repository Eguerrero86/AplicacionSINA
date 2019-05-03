package com.unab.tesisegac.tesisuno.ActividadesConsumidor;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unab.tesisegac.tesisuno.ActividadeAgricultor.CrearOfertaDos;
import com.unab.tesisegac.tesisuno.ActividadeAgricultor.CrearOfertaTres;
import com.unab.tesisegac.tesisuno.Actividades.Inicio;
import com.unab.tesisegac.tesisuno.R;

import java.util.HashMap;
import java.util.Map;

public class CrearDemandaTres extends AppCompatActivity {
    Button galeria, atras, finalizar;
    private static final int GALERY_INTENT = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int idDemanda, contadorImagenes, permissionCheck;
    ImageView img1, img2, img3, img4, sina;
    String demanda;
    StorageReference myStorage;
    DatabaseReference myDataBase;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_demanda_tres);

        //Menu
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
        //Inicialización
        sina = findViewById(R.id.imagenSina);
        img1 = findViewById(R.id.cdImg1);
        img2 = findViewById(R.id.cdImg2);
        img3 = findViewById(R.id.cdImg3);
        img4 = findViewById(R.id.cdImg4);
        galeria = findViewById(R.id.cdGaleria);
        atras = findViewById(R.id.cdAtras3);
        finalizar = findViewById(R.id.cdFinalizar);
        permissionCheck = ContextCompat.checkSelfPermission(CrearDemandaTres.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        myStorage = FirebaseStorage.getInstance().getReference();
        myDataBase = FirebaseDatabase.getInstance().getReference();
        contadorImagenes = 0;
        progressDialog = new ProgressDialog(this);

        //Recive el Bundle
        Bundle bundleRecivido = getIntent().getExtras();
        if (bundleRecivido != null) {
            idDemanda = bundleRecivido.getInt("idDemanda");
            demanda = Integer.toString(idDemanda);
        }
        //Onclick
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeria = new Intent(Intent.ACTION_PICK);
                galeria.setType("image/*");
                startActivityForResult(galeria, GALERY_INTENT);
            }
        });
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(CrearDemandaTres.this, CrearDemandaDos.class);
                startActivity(atras);

            }
        });
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalizar = new Intent(CrearDemandaTres.this, Inicio.class);
                Toast.makeText(getApplicationContext(), "Se guardó correctamente", Toast.LENGTH_LONG).show();
                startActivity(finalizar);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERY_INTENT && resultCode == RESULT_OK) {
            progressDialog.setTitle("Subiendo");
            progressDialog.setMessage("Subiendo imagen");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Uri uri = data.getData();
            String contador = Integer.toString(contadorImagenes);
            StorageReference filepad = myStorage.child("demandas").child(demanda).child(contador);
            filepad.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    contadorImagenes = contadorImagenes + 1;
                    if (checkPermissionREAD_EXTERNAL_STORAGE(CrearDemandaTres.this)) {
                        checkPermissionREAD_EXTERNAL_STORAGE(CrearDemandaTres.this);
                        Uri descargaFoto = data.getData();
                        traerImagenes(descargaFoto);
                        crearNodoEnBaseDeDatos(contador, descargaFoto);
                    }

                }
            });

        } else {
            Log.d("requescode", "------------------NO estamos en el if-------------------------");
        }
    }

    public void crearNodoEnBaseDeDatos(String contador, Uri linkFoto) {
        Map<String, Object> datosImagen = new HashMap<>();
        datosImagen.put("nombre", contador);
        datosImagen.put("url", linkFoto.toString());
        myDataBase.child("demandas").child(demanda).push().setValue(datosImagen);


    }

    public void traerImagenes(Uri descargaFoto) {
        switch (contadorImagenes) {
            case 1:
                Glide.with(CrearDemandaTres.this)
                        .load(descargaFoto)
                        .fitCenter()
                        .centerCrop()
                        .into(img1);
                break;
            case 2:
                Glide.with(CrearDemandaTres.this)
                        .load(descargaFoto)
                        .fitCenter()
                        .centerCrop()
                        .into(img2);
                break;
            case 3:
                Glide.with(CrearDemandaTres.this)
                        .load(descargaFoto)
                        .fitCenter()
                        .centerCrop()
                        .into(img3);
                break;
            case 4:
                Glide.with(CrearDemandaTres.this)
                        .load(descargaFoto)
                        .fitCenter()
                        .centerCrop()
                        .into(img4);
                break;
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
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
        AlertDialog alert = alertBuilder.create();
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
                    Toast.makeText(CrearDemandaTres.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}
