package com.espe.tsafiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class Corregir extends AppCompatActivity {


    RecyclerView recyclerView;
    File[] filesAndFolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar);

        recyclerView = findViewById(R.id.recycler_view);
        TextView noFilesText = findViewById(R.id.nofiles_textview);

        String patch = getIntent().getStringExtra("path");

        File root = new File(patch);
        filesAndFolders = root.listFiles();

        if(filesAndFolders == null || filesAndFolders.length==0){
            noFilesText.setVisibility(View.VISIBLE);
            return;
        }

        noFilesText.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterArchivos(this,filesAndFolders));
    }
}