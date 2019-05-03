package com.unab.tesisegac.tesisuno.ActividadeAgricultor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.unab.tesisegac.tesisuno.R;

import java.util.Calendar;

public class CrearOfertaUno extends AppCompatActivity implements  View.OnClickListener {
    Spinner cantidades;
    private int dia;
    private int mes;
    private int ano;
    Button  siguiente;
    EditText fecha, producto, cantidad, variedad;
    Switch estado;
    String estadoCosecha;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta_uno);
        //Menu
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //INICIALIZACIÓN

        cantidades=findViewById(R.id.coCantidades);
        fecha=findViewById(R.id.coFechaCosecha);
        producto=findViewById(R.id.coProducto);
        variedad=findViewById(R.id.coVariedad);
        cantidad=findViewById(R.id.coCantidadCosecha);
        siguiente=findViewById(R.id.coSiguiente);
        estado=findViewById(R.id.coEstado);
        estadoCosecha="1";
        //CALENDAR
        calendar=Calendar.getInstance();
        dia=calendar.get(Calendar.DAY_OF_MONTH);
        mes=calendar.get(Calendar.MONTH);
        ano=calendar.get(Calendar.YEAR);
        mostrarFecha();
        //Switch


        //Spinner Cantidades
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.cantidades,
                android.R.layout.simple_spinner_item);
        cantidades.setAdapter(adapter);
        //Llamado a calendar
        fecha.setOnClickListener(this);
        //Siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente= new Intent(CrearOfertaUno.this, CrearOfertaDos.class);
                Bundle myBundle=new Bundle();
                myBundle.putString("producto", producto.getText().toString());
                myBundle.putString("variedad", variedad.getText().toString());
                myBundle.putString("cantidad", cantidad.getText().toString());
                myBundle.putString("fecha", fecha.getText().toString());
                myBundle.putString("medida", cantidades.getSelectedItem().toString());
                myBundle.putString("estado", estadoCosecha);
                siguiente.putExtras(myBundle);
                startActivity(siguiente);

            }
        });
    }
    //DATE PIKER FECHA
    public void mostrarFecha(){
        fecha.setText(dia+"/"+(mes+1)+"/"+ano);
    }
    @Override
    public void onClick(View v) {
        if(v==fecha){
            final Calendar calendar= Calendar.getInstance();
            dia=calendar.get(Calendar.DAY_OF_MONTH);
            mes=calendar.get(Calendar.MONTH);
            ano=calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                }
            }
                    ,dia, mes, ano);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
    }
    public void onSwitchClicked(View v){

        if (estado.isChecked()){
            estadoCosecha="2";
            fecha.setFocusable(false);
            fecha.setEnabled(false);
        }else{
            estadoCosecha="1";
            fecha.setFocusable(true);
            fecha.setEnabled(true);

        }
    }
}
