package com.espe.tsafiapp.corregir;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import com.espe.tsafiapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class VideoPlayer_Corregir_Activity extends AppCompatActivity {

    Button btn_rep_vid_act, btn_guardar_nvideo, btn_regrabar, btn_rep_nvideo;
    String pathVideo;

    File archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_corregir);

        btn_rep_vid_act = findViewById(R.id.btn_rep_vid_act);
        btn_guardar_nvideo = findViewById(R.id.btn_guardar_nvideo);
        btn_regrabar = findViewById(R.id.btn_regrabar);
        btn_rep_nvideo = findViewById(R.id.btn_rep_video);

        pathVideo = getIntent().getStringExtra("VIDEO");

        setEventos();

    }

   void setEventos(){
        btn_rep_vid_act.setOnClickListener(v -> reproducirVideo(pathVideo));
        btn_rep_nvideo.setOnClickListener(v -> reproducirVideo(pathVideo));
        btn_regrabar.setOnClickListener(v -> regrabar());
        /*stopBtn2.setOnClickListener(v -> stopPlay2());
        btnGrabar.setOnClickListener(v -> grabar());
        btnGuardarInf.setOnClickListener(v -> guardarInf());*/
   }



   public void reproducirVideo(String pathVideo) {
       try {
       Intent intent = new Intent();
       intent.setAction(android.content.Intent.ACTION_VIEW);
       intent.setDataAndType(Uri.parse(pathVideo),"video/*");
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
       } catch (Exception e) {
           Log.d("laptm2",e.toString());
       }
   };

   public void regrabar(){
       lanzarVideo.launch(new Intent(MediaStore.ACTION_VIDEO_CAPTURE));

   }

    ActivityResultLauncher<Intent> lanzarVideo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){

                try {
                    archivo = new File(pathVideo);
                    String auxFolfer = archivo.getParent();
                    String auxName = archivo.getName().split("\\.")[0];

                    ///File videoFile = File.createTempFile(fechaActual,".mp4", storageDirectory);
                    File videoFile = new File(auxFolfer+"/"+auxName+"_rpk.mp4");
                    if(videoFile.exists()){
                        videoFile.delete();
                    }
                    videoFile.createNewFile();
                    //Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.espe.tsafiapp
                    Uri uriVideo = result.getData().getData();


                    InputStream inputStream = getContentResolver().openInputStream(uriVideo);
                    FileOutputStream fileOutputStream = new FileOutputStream(videoFile);
                    byte[] buf = new byte[1024];
                    int len;

                    //copio el buffer
                    while ((len = inputStream.read(buf)) > 0) {
                        fileOutputStream.write(buf, 0, len);
                    }
                    fileOutputStream.close();
                    inputStream.close();

                    //testeo(videoFile);

                } catch (Exception e) {
                    Log.d("laptm2", e.toString());
                    e.printStackTrace();
                }
            }
        }
    });







}