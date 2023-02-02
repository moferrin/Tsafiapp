package com.espe.tsafiapp.data;

import com.espe.tsafiapp.data.RutasContract.RutasEntry;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Rutas {
    private String id;
    private String ruta;
    private String fechaCreacion;
    private String estado;

    public Rutas(String ruta, String fechaCreacion, String estado){
        this.id = this.id = UUID.randomUUID().toString();
        this.ruta = ruta;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    @SuppressLint("Range")
    public Rutas(Cursor cursor){
        this.id = cursor.getString(cursor.getColumnIndex(RutasEntry.ID));
        this.ruta = cursor.getString(cursor.getColumnIndex(RutasEntry.RUTA));
        this.fechaCreacion = cursor.getString(cursor.getColumnIndex(RutasEntry.FECHA_CREACION));
        this.estado = cursor.getString(cursor.getColumnIndex(RutasEntry.ESTADO));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(RutasEntry.ID, this.id);
        values.put(RutasEntry.RUTA, this.ruta);
        values.put(RutasEntry.FECHA_CREACION, this.fechaCreacion);
        values.put(RutasEntry.ESTADO, this.estado);
        return values;
    }
}
