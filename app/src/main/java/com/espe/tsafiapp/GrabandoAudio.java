package com.espe.tsafiapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;
*/

//port net.gotev.uploadservice.MultipartUploadRequest;
/*
import net.gotev.uploadservice.data.UploadNotificationConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;*/
//port net.gotev.uploadservice.UploadNotificationConfig;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GrabandoAudio extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    ProgressDialog progressDialog;
    int serverResponseCode = 0;

    private static final String CHANNEL_ID = "1";
    private Button btnGrabar;

    String fechaActual;
    File archivo;
    private MediaRecorder grabacion;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Uploading...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabando_audio);

        btnGrabar = (Button)findViewById(R.id.btnGrabarOn);

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                 ActivityCompat.requestPermissions(GrabandoAudio.this,
                         new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                                 Manifest.permission.READ_EXTERNAL_STORAGE},
                         1000);
        }

        Intent intent = getIntent();
        fechaActual = intent.getStringExtra(opcionesGrabacion.FECHA_ACTUAL);

    }

    private File crearDirectorio() {
        File path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File patchFecha = null;
        if(!(new File(path, fechaActual).exists())){
            patchFecha = new File(path, fechaActual);
            patchFecha.mkdir();
            return patchFecha;
        } else {
            return new File(path, fechaActual);
        }
    }

    private void testeo (File f) {

        progressDialog.show();


        Log.d("laptm", "cheeee");
        String filePath = f.getAbsolutePath().toString();
        String name = f.getName();

        File file = f;
        Log.d("laptm", "cheeee2");

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Log.d("laptm", "cheeee3");
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Log.d("laptm", "cheeee3_4");
        Call<ServerResponse> call= getResponse.uploadFile(fileToUpload, filename);

        Log.d("laptm", "cheeee4");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.d("laptm", "cheeee6");
                Log.d("laptm", response.toString());
                Log.d("laptm",call.toString());

                ServerResponse serverResponse = response.body();
                Log.d("laptm", "cheeee7");
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

            }
        });

    }

    private void teste2(File f){
        String uploadFilePath = f.getAbsolutePath();
        progressDialog = ProgressDialog.show(GrabandoAudio.this, "", "Uploading file...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("laptm","uploading started.....");
                    }
                });
                uploadFile(uploadFilePath);

            }
        }).start();
    }

    @SuppressLint("LongLogTag")
    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        Log.d("laptm","verificando si esxxites");

        if (!sourceFile.isFile()) {

            progressDialog.dismiss();

            //Log.e("uploadFile", "Source File not exist :"+uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    //messageText.setText("Source File not exist :"+uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        }
        else
        {
            Log.d("laptm","si esxxites");
            try {
                Log.d("laptm","intento");
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL("http://192.168.0.116:3000/upload");
                Log.d("laptm","llega a URL");
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                Log.d("laptm","pasa conn");

                OutputStream oups = conn.getOutputStream();
                Log.d("laptm","pasa oups");
                dos = new DataOutputStream(oups);
                Log.d("laptm","dos");

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                Log.d("laptm","antes de getRsponse");
                serverResponseCode = conn.getResponseCode();
                Log.d("laptm","despues de getRespose");
                String serverResponseMessage = conn.getResponseMessage();

                Log.d("laptm", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                //200
                if(serverResponseCode == serverResponseCode){

                    runOnUiThread(new Runnable() {
                        public void run() {

                       /*String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                               +" http://www.androidexample.com/media/uploads/"
                               +uploadFileName;*/

                            //messageText.setText(msg);
                            Toast.makeText(GrabandoAudio.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                progressDialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        Log.d("laptm","MalformedURLException Exception : check script url.");
                        Toast.makeText(GrabandoAudio.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                progressDialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(GrabandoAudio.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("laptm", "Upload file to server Exception Exception : "
                        + e.getMessage(), e);
            }
            progressDialog.dismiss();
            return serverResponseCode;

        } // End else block
    }



    //@RequiresApi(api = Build.VERSION_CODES.N)
    public void grabar(View v){
        if(grabacion==null) {
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File path = crearDirectorio();
            try {
                archivo = null;
                archivo = File.createTempFile("temporal", ".wav", path);
            } catch (IOException e) {
                Log.e("archivo", "crear archivo failed");
            }
            grabacion.setOutputFile(archivo.getAbsolutePath());
            try {
                grabacion.prepare();
            } catch (IOException e) {
                Log.e("prepare", "prepare() failed");
            }
            grabacion.start();
            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
            btnGrabar.setBackgroundResource(R.drawable.icon_grab_on);
        } else {
            btnGrabar.setBackgroundResource(R.drawable.icon_grab_off);
            grabacion.stop();
            grabacion.release();
            player = new MediaPlayer();
            player.setOnCompletionListener(this);
            try {
                player.setDataSource(archivo.getAbsolutePath());
                //testeo(archivo);
                //teste2(archivo);
            } catch (Exception e) {
                Log.d("archivo", "no se encontro archivo");
                Log.d("laptm", e.toString());
            }

            try {
                player.prepare();
            } catch (IOException e) {
                Log.d("media", "no se prepraro el reproductor");
            }
            grabacion=null;
        }
    }
    public void onCompletion(MediaPlayer mp) {
        /*b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        tv1.setText("Listo");*/
    }
    public void reproducir(View view){
        player.start();
        /*MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            //mediaPlayer.setDataSource(archivoSalida);
            //mediaPlayer.prepare();
        }catch (IOException e){

        }*/
        //mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproduciendo...", Toast.LENGTH_SHORT).show();
    }






}