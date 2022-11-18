package com.espe.tsafiapp

import android.Manifest
import net.gotev.uploadservice.HttpUploadRequest.setMethod
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest.addFileToUpload
import net.gotev.uploadservice.UploadRequest.startUpload
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.media.MediaPlayer
import android.os.Bundle
import com.espe.tsafiapp.R
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/*
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;
*/   class GrabandoAudio : AppCompatActivity(), OnCompletionListener {
    private var btnGrabar: Button? = null
    var fechaActual: String? = null
    var archivo: File? = null
    private var grabacion: MediaRecorder? = null
    var player: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grabando_audio)
        btnGrabar = findViewById<View>(R.id.btnGrabarOn) as Button
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@GrabandoAudio, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000)
        }
        val todayDate = Date()
        val sdf = SimpleDateFormat("yyyyMMdd-HHmmss")
        fechaActual = sdf.format(todayDate)

        //Toast.makeText(getApplicationContext(),fechaActual.toString(),Toast.LENGTH_SHORT).show();
/*
        Toast.makeText(getApplicationContext(),Environment.getDataDirectory()
                .getAbsolutePath().toString(),Toast.LENGTH_SHORT).show();
*/
        //Toast.makeText(getApplicationContext(),getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(),Toast.LENGTH_SHORT).show();
    }

    private fun crearDirectorio(): File? {
        val path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        var patchFecha: File? = null
        if (!File(path, fechaActual).exists()) {
            patchFecha = File(path, fechaActual)
            patchFecha.mkdir()
        }
        return patchFecha
    }

    private fun testeo(f: File?) {
        Log.d("laptm", "cheeee")

        //String pat = getPat
        try {
            MultipartUploadRequest(this, "http://10.241.7.120:3000/upload")
                    .setMethod("POST")
                    .addFileToUpload(
                            "" + f!!.absolutePath.toString(),
                            "myFile"
                    ).startUpload()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d("laptm", e.toString())
        }
        try {
            /*new MultipartUploadRequest(this, "http://localhost")
                    .setMethod("POST")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .addFileToUpload(setMethod("POST")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .addFileToUpload(
                    f
                    f.getPath(),f.getName()
                    ).startUpload();
*/
            //filenameGaleria = getFilename();
            /*
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadId, "localhost")
                    .addFileToUpload(archivo.getPath(), "picture")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .addParameter("filename", archivo.getName())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(UploadInfo uploadInfo) {}
                        @Override
                        public void onError(UploadInfo uploadInfo, Exception e) {}
                        @Override
                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                            //ELiminar imagen
                            File eliminar = new File(archivo.getPath());
                            if (eliminar.exists()) {
                                if (eliminar.delete()) {
                                    System.out.println("“archivo eliminado:”" + archivo.getPath());
                                } else {
                                    System.out.println("“archivo no eliminado”" + archivo.getPath());
                                }
                            }
                            Toast.makeText(getApplicationContext(),"Imagen subida exitosamente.",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(UploadInfo uploadInfo) {}
                    })
                    .startUpload()
                    ;*/
        } catch (exc: Exception) {
            println(exc.message + " " + exc.localizedMessage)
            Log.d("laptm", exc.toString())
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    fun grabar(v: View?) {
        if (grabacion == null) {
            grabacion = MediaRecorder()
            grabacion!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            grabacion!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            grabacion!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            val path = crearDirectorio()
            try {
                archivo = File.createTempFile("temporal", ".3gp", path)
            } catch (e: IOException) {
                Log.e("archivo", "crear archivo failed")
            }
            grabacion!!.setOutputFile(archivo!!.absolutePath)
            try {
                grabacion!!.prepare()
            } catch (e: IOException) {
                Log.e("prepare", "prepare() failed")
            }
            grabacion!!.start()
            Toast.makeText(applicationContext, "Grabando...", Toast.LENGTH_SHORT).show()
            btnGrabar!!.setBackgroundResource(R.drawable.icon_grab_on)
        } else {
            btnGrabar!!.setBackgroundResource(R.drawable.icon_grab_off)
            grabacion!!.stop()
            grabacion!!.release()
            player = MediaPlayer()
            player!!.setOnCompletionListener(this)
            try {
                player!!.setDataSource(archivo!!.absolutePath)
                testeo(archivo)
            } catch (e: IOException) {
                Log.e("archivo", "no se encontro archivo")
            }
            try {
                player!!.prepare()
            } catch (e: IOException) {
                Log.e("media", "no se prepraro el reproductor")
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer) {
        /*b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        tv1.setText("Listo");*/
    }

    fun reproducir(view: View?) {
        player!!.start()
        /*MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            //mediaPlayer.setDataSource(archivoSalida);
            //mediaPlayer.prepare();
        }catch (IOException e){

        }*/
        //mediaPlayer.start();
        Toast.makeText(applicationContext, "Reproduciendo...", Toast.LENGTH_SHORT).show()
    }
}