package com.espe.tsafiapp.red;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espe.tsafiapp.VolleySingleton;
import com.espe.tsafiapp.data.TraduccionesContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManagerDatos {

    private String URL_SAVE_NAME = "http://192.188.58.82:3000/persona_enc";

    public ManagerDatos() {
    }

    @SuppressLint("Range")
    public void enviarDatos(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {
                //calling the method to save the unsynced name to MySQL
                saveName(
                        cursor.getString(cursor.getColumnIndex(TraduccionesContract.TraduccionesEntry.LENGUA_GRAB))
                );
            } while (cursor.moveToNext());
        }
    }

    private void saveName(
            final String id_persona) {

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

                                // enviando la transmisión para actualizar la lista
                                //context.sendBroadcast(new Intent(MainActivity3.DATA_SAVED_BROADCAST));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo_persona",id_persona );
                return params;
            }
        };

        //VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
