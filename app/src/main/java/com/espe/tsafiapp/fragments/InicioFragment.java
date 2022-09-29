package com.espe.tsafiapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.espe.tsafiapp.Grabacion;
import com.espe.tsafiapp.R;
import com.espe.tsafiapp.interfaces.IComunicaFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Activity actividad;
    CardView cardGrabar, cardCorregir, cardTraducir, cardVerificar_1, cardVerificar_2, cardCompartir;
    IComunicaFragments interfaceComunicaFragments;
    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        cardGrabar=vista.findViewById(R.id.cardGrabar);
        cardCorregir=vista.findViewById(R.id.cardCorregir);
        cardTraducir=vista.findViewById(R.id.cardTraducir);
        cardVerificar_1=vista.findViewById(R.id.cardVerificar_1);
        cardVerificar_2=vista.findViewById(R.id.cardVerificar_2);
        cardCompartir=vista.findViewById(R.id.cardCompartir);

        eventosMenu();

        return vista;
    }
    private void eventosMenu(){
        cardGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.irGrabar();
            }
        });

        cardCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.irCorregir();
            }
        });

        cardTraducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.irTraducir();
            }
        });

        cardVerificar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.irVerificar_1();
            }
        });

        cardVerificar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.irVerificar_2();
            }
        });

        cardCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.irCompartir();
            }
        });
    }

    public interface OnFragmentInteractionListener {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            actividad = (Activity) context;
            interfaceComunicaFragments = (IComunicaFragments) actividad;
        }
    }
}