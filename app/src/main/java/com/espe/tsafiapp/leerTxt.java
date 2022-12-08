package com.espe.tsafiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class leerTxt extends AppCompatActivity {
    private EditText et2;
    String pathTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_txt);
        pathTxt = getIntent().getStringExtra("FILE");
        et2=findViewById(R.id.et2);
        recuperar();
    }


    public void grabar(View v) {
        File aux = new File(pathTxt);
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(new FileOutputStream(aux));
            archivo.write(et2.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (Exception e) {
        }
        Toast t = Toast.makeText(this, "Los datos fueron grabados",
                Toast.LENGTH_SHORT);
        t.show();
        et2.setText("");
    }


    public void recuperar() {
        File aux = new File(pathTxt);
            try {
                InputStreamReader archivo = new InputStreamReader(new FileInputStream(aux));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                et2.setText(todo);
            } catch (Exception e) {
            }
    }
}