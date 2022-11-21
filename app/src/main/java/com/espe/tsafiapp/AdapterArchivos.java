package com.espe.tsafiapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espe.tsafiapp.audioR.MiMusicPlayer;

import java.io.File;
public class AdapterArchivos extends RecyclerView.Adapter<AdapterArchivos.ViewHolder>{

    Context context;
    File[] filesAndFolders;
    public AdapterArchivos(Context context, File[] filesAndFolders){
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File selectedFile = filesAndFolders[position];
        holder.textView.setText(selectedFile.getName());
        if(selectedFile.isDirectory()){
            holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
        }else {
            holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedFile.isDirectory()){
                    Intent cardCorregir = new Intent(context, Corregir.class);
                    String path = selectedFile.getAbsolutePath();
                    cardCorregir.putExtra("path",path);
                    cardCorregir.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(cardCorregir);
                } else {
                    ///MiMusicPlayer.getInstance().reset();
                    //MiMusicPlayer.currentIndex = position;
                    Log.d("laptm","antes de intent");
                    Intent intent = new Intent(context, MusicPlayerActivity.class);
                    intent.putExtra("SONG",selectedFile.getAbsolutePath());
                    Log.d("laptm","antes de startActivity");
                    context.startActivity(intent);

                    /*
                    try {
                        Intent intent2 = new Intent();
                        intent2.setAction(android.content.Intent.ACTION_VIEW);
                        String type = "image";
                        intent2.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()),type);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                    }catch (Exception e){
                        Toast.makeText(context.getApplicationContext(),"Cannot open the file",Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });
    }

    /**
     * Remove all trees from the display
     */


    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.file_name_view);
            imageView = itemView.findViewById(R.id.icon_view);
        }
    }
}
