package com.unab.tesisegac.tesisuno.ActividadesConsumidor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.unab.tesisegac.tesisuno.R;

import java.util.Calendar;

public class CrearDemandaUno extends AppCompatActivity implements View.OnClickListener {
    Spinner cantidades;
    private int dia;
    private int mes;
    private int ano;
    Button siguiente;
    EditText fecha, producto, cantidad, variedad;
    String estadoCosecha;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_demanda_uno);

        //Menu
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //INICIALIZACIÓN
        cantidades=findViewById(R.id.cdCantidades);
        producto=findViewById(R.id.cdProducto);
        variedad=findViewById(R.id.cdVariedad);
        cantidad=findViewById(R.id.cdCantidadCosecha);
        siguiente=findViewById(R.id.cdSiguiente);


        //Spinner Cantidades
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.cantidades,
                android.R.layout.simple_spinner_item);
        cantidades.setAdapter(adapter);

        //Siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente= new Intent(CrearDemandaUno.this, CrearDemandaDos.class);
                Bundle myBundle=new Bundle();
                myBundle.putString("producto", producto.getText().toString());
                myBundle.putString("variedad", variedad.getText().toString());
                myBundle.putString("cantidad", cantidad.getText().toString());
                myBundle.putString("medida", cantidades.getSelectedItem().toString());
                siguiente.putExtras(myBundle);
                startActivity(siguiente);

            }
        });
    }
    @Override
    public void onClick(View v) {

    }
}
