package com.espe.tsafiapp;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.espe.tsafiapp.data.TraduccionesContract;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioArchivos extends IntentService {
    /**
     * @deprecated
     */
    public ServicioArchivos() {
        super("SERVICIO_ARCHIVOS");
    }
    boolean bandera = true;

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("Range")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Context context = getApplicationContext();
        TraduccionesDbHelper aa = new TraduccionesDbHelper(context);


        int limite = intent.getIntExtra("limite", 0);

        int contador = 1;

        while (contador <= 3) {
            try {
                Thread.sleep(1000);

                Intent i = new Intent("broadcast");
                i.putExtra("contador",contador);
                LocalBroadcastManager.getInstance(this).sendBroadcast(i);
                contador++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //subir archivos a la carpeta
        Cursor cursor = aa.getAllTraducciones();
        int a = cursor.getCount();
        if (cursor != null && a > 0) {
            Log.d("laptm",String.valueOf(a)+"aqui debera decir tamaño");
            if (cursor.moveToFirst()) {
                do {
                    Log.d("ROGER",cursor.getString(cursor.getColumnIndex(TraduccionesContract.TraduccionesEntry.ID)));
                    Log.d("ROGER",cursor.getString(cursor.getColumnIndex(TraduccionesContract.TraduccionesEntry.APELLIDO_NOMBRE)));
                    //calling the method to save the unsynced name to MySQL


                } while (cursor.moveToNext());
            }
        }

        File[] listaArchivos = darArchivos();

        if (listaArchivos!= null) {
            for (File file : listaArchivos) {
                if (file.isFile()) {
                    try {
                        subirArchivo(file);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public File[] darArchivos() {
        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.espe.tsafiapp/20230129-150813");
        return path.listFiles();
    }

    public void subirArchivo(File f) throws Exception {
        //progressDialog.show();
        bandera = true;
        while (bandera) {

        String filePath = f.getAbsolutePath().toString();
        String name = f.getName();

        File file = f;

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<ServerResponse> call= getResponse.uploadFile(fileToUpload, filename);



            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse serverResponse = response.body();
                    Log.d("laptm2", "cheeee7");
                    if (serverResponse != null) {
                        if (response.code()==200) {
                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("laptm2", "if verdadero"+serverResponse.toString());
                            bandera = false;
                        } else {
                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("laptm2", "else del success"+serverResponse.getMessage());
                            bandera = false;
                        }
                    } else {
                        assert serverResponse != null;
                        Log.d("laptm2", "no hay respuesta"+serverResponse.toString());
                    }
                    //progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                    Log.d("laptm2",t.getMessage()+t.toString());

                    //bandera = true;
                    //progressDialog.dismiss();
                }
            });
            Thread.sleep(5000);
        }
/*
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.d("laptm2", "cheeee6");
                ServerResponse serverResponse = response.body();
                Log.d("laptm2", "cheeee7");
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.d("Response", "no hay respuesta"+serverResponse.toString());
                }
                //progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d("Laptm2",call.isCanceled()+" "+t.getMessage());
                Call<ServerResponse> aa = call.clone();
                try {
                    Thread.sleep(10000);
                    aa.;
                    Log.d("Laptm2","se ejecuto");
                } catch (Exception e) {
                    Log.d("Laptm2","exepcion de execute "+e.toString());
                    e.printStackTrace();
                }
                //progressDialog.dismiss();

            }

        });

        */
    }
}
