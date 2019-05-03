package com.unab.tesisegac.tesisuno.Actividades;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.unab.tesisegac.tesisuno.Fragments.FragmentDemandasPerfil;
import com.unab.tesisegac.tesisuno.Fragments.FragmentInformacionPerfil;
import com.unab.tesisegac.tesisuno.Fragments.FragmentOfertasPerfil;
import com.unab.tesisegac.tesisuno.Objetos.GlobalUsuario;
import com.unab.tesisegac.tesisuno.R;

public class Perfil extends AppCompatActivity
        implements FragmentInformacionPerfil.OnFragmentInteractionListener, FragmentDemandasPerfil.OnFragmentInteractionListener,
        FragmentOfertasPerfil.OnFragmentInteractionListener {

    TextView nombre, correo, telefono, departamento;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int permissionCheck;
    StorageReference myStorage;
    CircularImageView imagenPerfil;

    FragmentInformacionPerfil informacionPerfil;
    FragmentOfertasPerfil ofertasPerfil;
    FragmentDemandasPerfil demangasPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Inicialización
        informacionPerfil = new FragmentInformacionPerfil();
        ofertasPerfil = new FragmentOfertasPerfil();
        demangasPerfil = new FragmentDemandasPerfil();
        nombre = findViewById(R.id.perfil_nombreUsuario);
        correo = findViewById(R.id.perfil_correo);
        telefono = findViewById(R.id.perfil_telefono);
        departamento = findViewById(R.id.perfil_departamento);
        myStorage = FirebaseStorage.getInstance().getReference();
        imagenPerfil = (CircularImageView)findViewById(R.id.perfil_imgPerfil);
        permissionCheck = ContextCompat.checkSelfPermission(Perfil.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragmensPerfil, ofertasPerfil).commit();
        GlobalUsuario sesion= (GlobalUsuario) getApplicationContext();
        nombre.setText(sesion.getNombre()+" "+sesion.getApellido());
        correo.setText(sesion.getCorreo());
        telefono.setText(sesion.getTelefono());
        departamento.setText("Santander");
        cargarFoto(sesion);


    }

    public void cargarFoto(GlobalUsuario sesion) {

        if (checkPermissionREAD_EXTERNAL_STORAGE(Perfil.this)) {
            checkPermissionREAD_EXTERNAL_STORAGE(Perfil.this);
            Log.d("TipoUsuario", sesion.getTipoUsuario());
            myStorage.child("/"+sesion.getTipoUsuario()).child( "/"+ sesion.getIdUsuario()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    traerImagenes(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });


        }
    }
    public void traerImagenes(Uri descargaFoto) {

        Glide.with(Perfil.this)
                .load(descargaFoto)
                .fitCenter()
                .centerCrop()
                .into(imagenPerfil);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnDemandasPerfil:
                transaction.replace(R.id.contenedorFragmensPerfil, demangasPerfil);
                break;
            case R.id.btnInformacionPerfil:
                transaction.replace(R.id.contenedorFragmensPerfil, informacionPerfil);
                break;
            case R.id.btnOfertasPerfil:
                transaction.replace(R.id.contenedorFragmensPerfil, ofertasPerfil);
                break;
        }
        transaction.commit();
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
                    Toast.makeText(Perfil.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}
