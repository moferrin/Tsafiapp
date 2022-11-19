package com.espe.tsafiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.espe.tsafiapp.fragments.InicioFragment;
import com.espe.tsafiapp.interfaces.IComunicaFragments;

public class MainActivity extends AppCompatActivity implements IComunicaFragments, InicioFragment.OnFragmentInteractionListener {


    Fragment fragmentInicio;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("laptm","nueva instancia inicio fgrament");
        super.onCreate(savedInstanceState);

        if(instance == null){
            instance = this;
        }



        setContentView(R.layout.activity_main);

        fragmentInicio = new InicioFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentInicio).commit();
    }

    public void onFragmentInteraction(Uri uri){

    }

    public static MainActivity getInstance(){
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasNetwork(){
        return instance.isNetworkConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    @Override
    public void irGrabar() {
        Intent cardGrabar = new Intent(this, Grabacion.class);
        startActivity(cardGrabar);
    }

    @Override
    public void irCorregir() {
        Intent cardCorregir = new Intent(this, Corregir.class);
        String path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
        cardCorregir.putExtra("path",path);
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