package com.unab.tesisegac.tesisuno.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unab.tesisegac.tesisuno.Objetos.Demanda;
import com.unab.tesisegac.tesisuno.Objetos.Oferta;
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

public class OpcionesPlan extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Demanda demanda;
    List<Oferta> ofertas;
    String tipoDePlan;
    TextView productores, precio, cantidad, ciudad;
    Button anterior, siguiente;
    int opcion=0, latitud, longitud;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_plan);
        //Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Bundle Recivido
        Bundle bundleRecivido = getIntent().getExtras();
        demanda = (Demanda) bundleRecivido.getSerializable("seleccion");
        tipoDePlan= bundleRecivido.getString("tipoDePlan");

        //INICIALIZACIÓN
        ofertas = new ArrayList<Oferta>();
        productores=findViewById(R.id.opProductores);
        precio=findViewById(R.id.opPrecio);
        cantidad=findViewById(R.id.opCantidad);
        ciudad=findViewById(R.id.opCiudad);
        anterior=findViewById(R.id.opAnterior);
        siguiente=findViewById(R.id.opSiguiente);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);


        switch (tipoDePlan){
            case "agrupacion":
                    Log.d("seleccion", "---------------agrupación");
                break;
            case "ubicacion":
                Log.d("seleccion", "---------------ubicacion");
                break;
            case "cantidad":
               traerOfertasExacta();
                break;
            case "precio":
                Log.d("seleccion", "---------------precio");
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, latitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
    }

    public void traerOfertasExacta() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://" + getString(R.string.ip) + "/api/ofertas/demanda/exacto/" + demanda.getIdDemanda() + "";
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

                    OpcionesPlan.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Gson gson = new Gson();
                            final Type tipoLista = new TypeToken<List<Oferta>>() {
                            }.getType();
                            ofertas = gson.fromJson(myResponse, tipoLista);
                            configurarVista();
                        }
                    });
                } else {
                    Log.d("Aceptado", "-----------------Faltan campos por llenar------------------");
                }
            }
        });

    }
         public void configurarVista(){
                Log.d("tamaño lista", ""+ofertas.size()+" "+ofertas.get(0).getNombreProducto());
              cantidad.setText(ofertas.get(opcion).getCantidadProducto().toString());
              precio.setText(ofertas.get(opcion).getPrecioProducto().toString());
              productores.setText("1 Productor");
              ciudad.setText(ofertas.get(opcion).getCiudad().getNombreCiudad());
              longitud=ofertas.get(opcion).getCiudad().getLongCiudad();
              latitud=ofertas.get(opcion).getCiudad().getLatCiudad();
             mapFragment.getMapAsync(this);
         }
}
