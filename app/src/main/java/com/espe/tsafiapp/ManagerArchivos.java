package com.espe.tsafiapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerArchivos {

    public static void enviarArchivo(File f, Context context){
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), f);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploaded_file", f.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), f.getName());

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call= getResponse.uploadFile(fileToUpload, filename);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();

                if (serverResponse != null) {

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
/*
    public static void testeo (File f) {


        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();

                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.d("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d("Laptm2",call.isCanceled()+" "+t.getMessage());
                Call<ServerResponse> aa = call.clone();
                try {
                    aa.execute();
                } catch (Exception e) {
                    Log.d("Laptm2",e.toString());
                    e.printStackTrace();
                }
                //progressDialog.dismiss();

            }

        });

    }
*/
}
