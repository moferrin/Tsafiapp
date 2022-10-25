package com.espe.tsafiapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espe.tsafiapp.Grabacion;
import com.espe.tsafiapp.R;
import com.espe.tsafiapp.TraduccionesDbHelper;
import com.espe.tsafiapp.VolleySingleton;
import com.espe.tsafiapp.data.TraduccionesContract;
import com.espe.tsafiapp.interfaces.IComunicaFragments;
import com.espe.tsafiapp.red.ManagerDatos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    private TraduccionesDbHelper mTraduccionesDbHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Activity actividad;
    CardView cardGrabar, cardCorregir, cardTraducir, cardVerificar_1, cardVerificar_2, cardCompartir;
    IComunicaFragments interfaceComunicaFragments;

    private final String URL_SAVE_NAME = "http://192.168.8.101:3000/grabacion";

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
        Log.d("laptm","nueva instancia inicio fgrament");
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("laptm","oncreate inicio fgrament");
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

        mTraduccionesDbHelper = new TraduccionesDbHelper(getContext());
        sincronizarDatos();

        return vista;
    }

    private void sincronizarDatos() {
        new TraduccionesLoadTask().execute();
    }


    private class TraduccionesLoadTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            /*mTraduccionesDbHelper.saveTraduccion(new Traducciones("espa desde inicio fragment", "madre",
                    "secund", "EL carmen","nota","apellidonombre","edad",
                    "geneno"));*/
            return mTraduccionesDbHelper.getAllTraducciones();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            int a = cursor.getCount();
            if (cursor != null && a > 0) {
                Log.d("laptm",String.valueOf(a)+"aqui debera decir tamaño");
                /*ManagerDatos manager = new ManagerDatos();
                manager.enviarDatos(cursor);*/
                enviarDatos(cursor);
                //mLawyersAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
    @SuppressLint("Range")
    public void enviarDatos(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {
                //calling the method to save the unsynced name to MySQL
                saveName(
                        cursor.getString(cursor.getColumnIndex(TraduccionesContract.TraduccionesEntry.ID))
                );
            } while (cursor.moveToNext());
        }
    }

    private void saveName(
            final String id_persona) {
        Log.d("laptm",id_persona);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String obj1 = new JSONObject(response).getString("status");
                            Log.d("success","respuesta per_enc");
                            if ("persona_enc_c".equals(obj1)) {
                                Log.d("success","envia per_enc");
                                // actualizando el estado en sqlite
                                //db.actualizarPersonaEncuestadaBDD(id_persona,MainActivity3.NAME_SYNCED_WITH_SERVER);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("laptm",error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lenguaGrab",id_persona );
                params.put("lenguaMadre",id_persona );
                params.put("lenguaSecundaria",id_persona );
                params.put("ciudad",id_persona );
                params.put("nota",id_persona );
                params.put("apellidoNombre",id_persona );
                params.put("edad",id_persona );
                params.put("genero",id_persona );
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void eventosMenu(){
        cardGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //interfaceComunicaFragments.irGrabar();
                Intent intent = new Intent(getActivity(), Grabacion.class);
                lanzarVista.launch(intent);

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
    ActivityResultLauncher<Intent> lanzarVista = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("laptm","se fuee, llamar a sincronizar ");
                    sincronizarDatos();
                    if(result.getResultCode() == Activity.RESULT_OK){
                    }
                }
    });



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