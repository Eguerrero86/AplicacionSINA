package com.unab.tesisegac.tesisuno.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.unab.tesisegac.tesisuno.Adaptadores.AdaptadorConversacion;
import com.unab.tesisegac.tesisuno.Objetos.Consumidor;
import com.unab.tesisegac.tesisuno.Objetos.GlobalUsuario;
import com.unab.tesisegac.tesisuno.Objetos.MensajeEnviar;
import com.unab.tesisegac.tesisuno.Objetos.MensajeRecibir;
import com.unab.tesisegac.tesisuno.Objetos.Productor;
import com.unab.tesisegac.tesisuno.Objetos.Usuario;
import com.unab.tesisegac.tesisuno.R;

public class Conversacion extends AppCompatActivity {
    CircularImageView imagenChat, enviar;
    ImageButton galeria;
    EditText mensaje;
    TextView nombre;
    GlobalUsuario sesion;
    RecyclerView rvConverzacion;
    private AdaptadorConversacion adapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceReceptor;
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageReference storageReferenceReceptor;
    Usuario usuario;
    final static int PHOTO_SEND=1;
    int posicion=0;
    String idReceptor, cedulaProductor, uid;
    Consumidor consumidor;
    Productor productor;
    Uri imagenMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        //INICIALIZACIÓN
        imagenChat = findViewById(R.id.conImagen);
        enviar = findViewById(R.id.conEnviar);
        mensaje = findViewById(R.id.conMensaje);
        nombre = findViewById(R.id.conNombre);
        usuario=new Usuario();
        sesion= (GlobalUsuario) getApplicationContext();
        galeria = findViewById(R.id.conGaleria);
        rvConverzacion = findViewById(R.id.recycler_converzaciones);
        adapter = new AdaptadorConversacion(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvConverzacion.setLayoutManager(layoutManager);
        rvConverzacion.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        String idUsuario=Integer.toString(sesion.getIdUsuario());
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        Bundle bundleRecivido= getIntent().getExtras();
        if(sesion.getTipoUsuario().equals("Productor")){
            if (bundleRecivido!=null){
                consumidor= (Consumidor) bundleRecivido.getSerializable("receptor");
                idReceptor=Integer.toString(consumidor.getCedulaConsumidor());
                usuario.setCedula(consumidor.getCedulaConsumidor());
                usuario.setNombre(consumidor.getNombreConsumidor());
                usuario.setApellido(consumidor.getApellidoConsumidor());
                cedulaProductor=Integer.toString(sesion.getIdUsuario());
                uid=idReceptor;
                usuario.setTipoUsuario("Consumidor");
            }
        }else if(sesion.getTipoUsuario().equals("Consumidor")){
                productor= (Productor) bundleRecivido.getSerializable("receptor");
                idReceptor=Integer.toString(productor.getCedulaProductor());
                usuario.setCedula(productor.getCedulaProductor());
                usuario.setNombre(productor.getNombreProductor());
                usuario.setApellido(productor.getApellidoProductor());
                cedulaProductor=idReceptor;
                uid=Integer.toString(sesion.getIdUsuario());
                usuario.setTipoUsuario("Productor");
        }


        databaseReference = database.getReference("chats").child(idUsuario).child(idReceptor);
        databaseReferenceReceptor=database.getReference("chats").child(idReceptor).child(idUsuario);
        cargarImagenReceptor();




        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensajeEnviado= mensaje.getText().toString();
                if (!mensajeEnviado.equals("")){
                    databaseReference.push().setValue(new MensajeEnviar( mensaje.getText().toString(), sesion.getNombre()+" "+sesion.getApellido(), imagenMensaje.toString(),cedulaProductor, uid,   "1", ServerValue.TIMESTAMP));
                    databaseReferenceReceptor.push().setValue(new MensajeEnviar( mensaje.getText().toString(), sesion.getNombre()+" "+sesion.getApellido(), imagenMensaje.toString(),cedulaProductor, uid,  "1", ServerValue.TIMESTAMP));
                    mensaje.setText("");
                }


            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeria=new Intent(Intent.ACTION_GET_CONTENT);
                galeria.setType("image/jpeg");
                galeria.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(galeria, "selecciona una imagen"), 1);
            }
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrolBar();
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensajeRecibir mensaje = dataSnapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(mensaje);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cargarImagenReceptor() {
        nombre.setText(usuario.getNombre()+" "+usuario.getApellido());
        Log.d("idusuario", ""+usuario.getCedula());
        storageReference.child("/"+sesion.getTipoUsuario()).child("/"+sesion.getIdUsuario()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("imagen usuario", uri.toString());
                    imagenMensaje=uri;
            }
        });
        storageReference.child("/"+usuario.getTipoUsuario()).child("/"+usuario.getCedula()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Conversacion.this)
                        .load(uri)
                        .fitCenter()
                        .centerCrop()
                        .into(imagenChat);
            }
        });
    }

    private void setScrolBar() {
        rvConverzacion.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PHOTO_SEND && resultCode==RESULT_OK){
                Uri u= data.getData();
                String idUsuario=Integer.toString(sesion.getIdUsuario());
                String idReceptor=sesion.getTelefono();
                storageReference=storage.getReference().child("chats").child(idUsuario).child(idReceptor);
                storageReferenceReceptor=storage.getReference().child("chats").child(idReceptor).child(idUsuario);
                final StorageReference fotoSubida= storageReference.child(u.getLastPathSegment());
                fotoSubida.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      Uri u=data.getData();
                      MensajeEnviar m=new MensajeEnviar(u.toString(),"Foto enviada",sesion.getNombre()+" "+sesion.getApellido(),imagenMensaje.toString(),cedulaProductor, uid,"2",  ServerValue.TIMESTAMP);
                      databaseReference.push().setValue(m);
                      databaseReferenceReceptor.push().setValue(m);
                    }
                });
            }
    }
}
