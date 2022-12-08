package com.espe.tsafiapp.grabaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.espe.tsafiapp.CapturarFoto;
import com.espe.tsafiapp.Grabacion;
import com.espe.tsafiapp.GrabandoAudio;
import com.espe.tsafiapp.GrabandoVideo;
import com.espe.tsafiapp.R;

public class opcionesGrabacion extends AppCompatActivity {
    Button btnIrGrabandoAudio;
    Button btnIrGrabandoVideo;
    Button btnIrCapturaFoto;
    public static String FECHA_ACTUAL = "fechaActual";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_grabacion);

        btnIrGrabandoAudio=(Button)findViewById(R.id.btnGrabandoAudio);
        btnIrGrabandoVideo=(Button)findViewById(R.id.btnGrabandoVideo);
        btnIrCapturaFoto=(Button)findViewById(R.id.btnCapturarFoto);

        Intent intent = getIntent();

        String fechaActual = intent.getStringExtra(Grabacion.FECHA_ACTUAL);

        Log.d("laptm",fechaActual);

        btnIrGrabandoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(opcionesGrabacion.this, GrabandoAudio.class);
                i.putExtra(FECHA_ACTUAL,fechaActual);
                startActivity(i);
            }
        });

        btnIrGrabandoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(opcionesGrabacion.this, GrabandoVideo.class);
                i.putExtra(FECHA_ACTUAL,fechaActual);
                startActivity(i);
            }
        });

        btnIrCapturaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(opcionesGrabacion.this, CapturarFoto.class);
                i.putExtra(FECHA_ACTUAL,fechaActual);
                startActivity(i);
            }
        });
    }
}