package com.espe.tsafiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Grabacion extends AppCompatActivity {

    public static String FECHA_ACTUAL = "fechaActual";
    Spinner comboLenguasGrabacion;
    Spinner comboLenguaMadre;
    Spinner comboLenguaSec;
    EditText editCiudad;
    EditText editNota;
    EditText editNombreApellido;
    EditText editEdad;
    RadioButton checkGeneroM;
    RadioButton checkGeneroF;
    Button btnGuardarInf;

    String lengGrab;
    String lengMad;
    String lengSec;
    String ciudad;
    String nota;
    String nombreApe;
    String edad;
    String genero;
    String fechaActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabacion);

        btnGuardarInf=(Button)findViewById(R.id.btnGuardarInf);

        comboLenguasGrabacion = findViewById(R.id.spinnerLenguaGrab);
        comboLenguaMadre = findViewById(R.id.spinnerLenguaMadre);
        comboLenguaSec = findViewById(R.id.spinnerLenguaSec);

        //Lista para elegir Lengua de grabación
        ArrayAdapter<CharSequence> adapterLG = ArrayAdapter.createFromResource(this, R.array.comboLenguaGrabacion, android.R.layout.simple_spinner_item);
        comboLenguasGrabacion.setAdapter(adapterLG);

        //Lista para elegir Lengua Madre
        ArrayAdapter<CharSequence> adapterLM = ArrayAdapter.createFromResource(this, R.array.comboLenguaMadre, android.R.layout.simple_spinner_item);
        comboLenguaMadre.setAdapter(adapterLM);

        //Lista para elegir Lengua Secundaria
        ArrayAdapter<CharSequence> adapterLS = ArrayAdapter.createFromResource(this, R.array.comboLenguaSec, android.R.layout.simple_spinner_item);
        comboLenguaSec.setAdapter(adapterLS);

        editCiudad = findViewById(R.id.editTextTextPersonName4);
        editNota = findViewById(R.id.editTextTextPersonName5);
        editNombreApellido = findViewById(R.id.efde4);
        editEdad = findViewById(R.id.edidfrsosd5);

        checkGeneroM = findViewById(R.id.radioButton4);
        checkGeneroF = findViewById(R.id.radioButton3);

        checkGeneroM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkGeneroM.isChecked()){
                    checkGeneroF.setChecked(false);
                }
            }
        });

        checkGeneroF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkGeneroF.isChecked()){
                    checkGeneroM.setChecked(false);
                }
            }
        });

        Date todayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        fechaActual = sdf.format(todayDate);

        btnGuardarInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                traerDatos();
                Intent i = new Intent(Grabacion.this, opcionesGrabacion.class);
                i.putExtra(FECHA_ACTUAL,fechaActual);
                startActivity(i);
            }
        });
    }

    public void traerDatos (){
        lengGrab = comboLenguasGrabacion.getSelectedItem().toString();
        lengMad = comboLenguaMadre.getSelectedItem().toString();
        lengSec = comboLenguaSec.getSelectedItem().toString();
        ciudad = editCiudad.getText().toString().trim();
        nota = editNota.getText().toString().trim();
        nombreApe = editNombreApellido.getText().toString().trim();
        edad = editEdad.getText().toString().trim();
        genero = checkGeneroM.isChecked() ? "Masculino" : "Femenino";
    }
}