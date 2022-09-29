package com.espe.tsafiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.espe.tsafiapp.fragments.InicioFragment;
import com.espe.tsafiapp.interfaces.IComunicaFragments;

public class MainActivity extends AppCompatActivity implements IComunicaFragments, InicioFragment.OnFragmentInteractionListener {


    Fragment fragmentInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentInicio = new InicioFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentInicio).commit();
    }

    public void onFragmentInteraction(Uri uri){

    }

    @Override
    public void irGrabar() {
        Intent cardGrabar = new Intent(this, Grabacion.class);
        startActivity(cardGrabar);
    }

    @Override
    public void irCorregir() {
        Intent cardCorregir = new Intent(this, Corregir.class);
        startActivity(cardCorregir);
    }


    @Override
    public void irTraducir() {
        Intent cardTraducir = new Intent(this, Traducir.class);
        startActivity(cardTraducir);
    }

    @Override
    public void irVerificar_1() {
        Intent cardVerificar_1 = new Intent(this, Verificar_1.class);
        startActivity(cardVerificar_1);
    }

    @Override
    public void irVerificar_2() {
        Intent cardVerificar_2 = new Intent(this, Verificar_2.class);
        startActivity(cardVerificar_2);
    }

    public void irOpcionesGrabacion(){

    }
    @Override
    public void irCompartir() {

    }
}