package com.espe.tsafiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.espe.tsafiapp.corregir.AdapterArchivosCorregir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Corregir extends AppCompatActivity {


    RecyclerView recyclerView;
    File[] filesAndFolders;
    Button btnAgregarTxt;
    String patch;

    AdapterArchivosCorregir adapterActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corregir);

        recyclerView = findViewById(R.id.recycler_view);
        TextView noFilesText = findViewById(R.id.nofiles_textview);

        patch = getIntent().getStringExtra("path");

        File root = new File(patch);
        filesAndFolders = root.listFiles();

        btnAgregarTxt = findViewById(R.id.btn_agregar_txt);
        btnAgregarTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearArchivo();
            }
        });

        if(filesAndFolders == null || filesAndFolders.length==0){
            noFilesText.setVisibility(View.VISIBLE);
            btnAgregarTxt.setEnabled(false);
            return;
        }


        noFilesText.setVisibility(View.INVISIBLE);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterActual = new AdapterArchivosCorregir(this, filesAndFolders);
        recyclerView.setAdapter(adapterActual);
    }

    public void crearArchivo(){
        try {
            new File(adapterActual.getPatch(),"aaaaa.txt").createNewFile();
            File root = new File(adapterActual.getPatch());
            filesAndFolders = root.listFiles();

            recyclerView.setAdapter(new AdapterArchivosCorregir(this, filesAndFolders));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}