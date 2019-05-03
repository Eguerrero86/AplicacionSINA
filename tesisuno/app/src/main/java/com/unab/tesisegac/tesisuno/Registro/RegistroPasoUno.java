package com.unab.tesisegac.tesisuno.Registro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.unab.tesisegac.tesisuno.R;

public class RegistroPasoUno extends AppCompatActivity {
    EditText nombre, cedula, telefono, apellido;
    Spinner sexo;
    Button siguiente;
    String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paso_uno);

        //MENU
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //INICIALIZACIÓN
        apellido = findViewById(R.id.reApellido);
        nombre = findViewById(R.id.reNombre);
        cedula = findViewById(R.id.reCedula);
        sexo = findViewById(R.id.reSexo);
        telefono = findViewById(R.id.reTelefono);
        siguiente = findViewById(R.id.reSiguiente1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexos, android.R.layout.simple_spinner_item);
        sexo.setAdapter(adapter);

        //Bunble
        Bundle bundleRecivido= this.getIntent().getExtras();
        if(bundleRecivido!=null){
           tipoUsuario=bundleRecivido.getString("tipoUsuario");

        }

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().isEmpty() && !apellido.getText().toString().isEmpty() &&
                        !cedula.getText().toString().isEmpty() && !sexo.getSelectedItem().toString().isEmpty()
                        && !telefono.getText().toString().isEmpty()) {


                    Intent paso2 = new Intent(RegistroPasoUno.this, RegistroPasoDos.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putString("nombre", nombre.getText().toString());
                    myBundle.putString("apellido", apellido.getText().toString());
                    myBundle.putString("cedula", cedula.getText().toString());
                    myBundle.putString("telefono", telefono.getText().toString());
                    myBundle.putString("sexo", sexo.getSelectedItem().toString());
                    myBundle.putString("tipoUsuario", tipoUsuario);
                    paso2.putExtras(myBundle);
                    startActivity(paso2);
                } else {
                    Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
