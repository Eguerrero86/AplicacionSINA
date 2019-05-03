package com.unab.tesisegac.tesisuno.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.unab.tesisegac.tesisuno.ActividadesConsumidor.VerOfertas;
import com.unab.tesisegac.tesisuno.Adaptadores.AdaptadorTarjeta;
import com.unab.tesisegac.tesisuno.Objetos.Demanda;
import com.unab.tesisegac.tesisuno.Objetos.ImagenFireBase;
import com.unab.tesisegac.tesisuno.Objetos.Oferta;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOfertasPerfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOfertasPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOfertasPerfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<Tarjeta> tarjetas;
    List<String> imagenesOfertas;
    List<Oferta> ofertas;
    RecyclerView recyclerView;
    int contador = 0;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int permissionCheck;
    FirebaseStorage storage;
    StorageReference myStorage;
    DatabaseReference myDataBase;
    AdaptadorTarjeta adaptaer;
    public FragmentOfertasPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOfertasPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOfertasPerfil newInstance(String param1, String param2) {
        FragmentOfertasPerfil fragment = new FragmentOfertasPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_ofertas_perfil, container, false);
        //Inicialización
        tarjetas = new ArrayList<>();
        ofertas = new ArrayList<Oferta>();
        imagenesOfertas = new ArrayList<String>();
        recyclerView = vista.findViewById(R.id.recycleOfertasFragment);
        myDataBase = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        storage = FirebaseStorage.getInstance();
        myStorage=storage.getReference();
        adaptaer = new AdaptadorTarjeta(getActivity());
        traerOfertas();


        adaptaer.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "selecciona:" + tarjetas.
                        get(recyclerView.getChildAdapterPosition(v)).getProducto(), Toast.LENGTH_SHORT).show();
                abrirDialogDescripcion(v);
            }
        });
        return vista;
    }
    private void traerOfertas() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+getString(R.string.ip)+"/api/ofertas";
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

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Gson gson = new Gson();
                            final Type tipoLista = new TypeToken<List<Oferta>>() {
                            }.getType();
                            ofertas = gson.fromJson(myResponse, tipoLista);
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

        for (int i = 0; i < ofertas.size(); i++) {
            traerImagenes(ofertas.get(i).getIdOferta(), i);
        }
    }

    private void traerImagenes(int idOferta, int i) {

        if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
            checkPermissionREAD_EXTERNAL_STORAGE(getActivity());
            String id = Integer.toString(idOferta);
            myDataBase.child("ofertas").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()==0){
                        imagenesOfertas.add("https://firebasestorage.googleapis.com/v0/b/imagenestesis-dc1cf.appspot.com/o/predetarminada%2Fdefault.jpg?alt=media&token=666a8413-a84f-4f80-85bb-a7c0b36d1bfe");

                    }else {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ImagenFireBase imagen = snapshot.getValue(ImagenFireBase.class);
                            imagenesOfertas.add(imagen.getUrl());

                        }
                    }
                    String cantidadProducto = Integer.toString(ofertas.get(i).getCantidadProducto());
                    tarjetas.add(new Tarjeta(imagenesOfertas, ofertas.get(i).getNombreProducto(), cantidadProducto));
                    adaptaer.addTarjeta(tarjetas);
                    recyclerView.setAdapter(adaptaer);
                    imagenesOfertas = new ArrayList<String>();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error al sacar imagenes", "no se econtraron imagenes de la oferta");
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
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void abrirDialogDescripcion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_descripcion, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView producto = view.findViewById(R.id.productoDialog);
        TextView cantidad = view.findViewById(R.id.cantidadDialog);
        TextView nombre = view.findViewById(R.id.nombreDialog);
        TextView ciudad = view.findViewById(R.id.ciudadDialog);
        TextView direccion = view.findViewById(R.id.direccionDialog);
        TextView descripcion = view.findViewById(R.id.descripcionDialog);
        CircularImageView imagen = view.findViewById(R.id.imagenDialog);
        Button enviarMensaje=view.findViewById(R.id.enviarMensajeDialog);
        int y = recyclerView.getChildAdapterPosition(v);
        String idProducor = Integer.toString(ofertas.get(y).getProductor().getCedulaProductor());
        producto.setText(ofertas.get(y).getNombreProducto());
        cantidad.setText(ofertas.get(y).getCantidadProducto().toString());
        nombre.setText(ofertas.get(y).getProductor().getNombreProductor()+" "+ofertas.get(y).getProductor().getApellidoProductor());
        ciudad.setText(ofertas.get(y).getCiudad().getNombreCiudad());
        direccion.setText(ofertas.get(y).getLugarOferta());
        descripcion.setText(ofertas.get(y).getDescripcionProducto());
        myStorage.child("/Productor").child("/" + idProducor).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(getActivity())
                        .load(uri)
                        .fitCenter()
                        .centerCrop()
                        .into(imagen);
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
                Intent conversar= new Intent(getActivity(), Conversacion.class);
                Bundle myBundle=new Bundle();
                myBundle.putSerializable("receptor", ofertas.get(y).getProductor());
                conversar.putExtras(myBundle);
                startActivity(conversar);
            }
        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
