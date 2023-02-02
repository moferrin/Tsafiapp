package com.espe.tsafiapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.espe.tsafiapp.data.Rutas;
import com.espe.tsafiapp.data.RutasContract.RutasEntry;
import com.espe.tsafiapp.data.Traducciones;
import com.espe.tsafiapp.data.TraduccionesContract.TraduccionesEntry;

public class TraduccionesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Traducciones.db";

    public TraduccionesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("laptm","llega al constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TraduccionesEntry.TABLE_NAME+ " ("
                + TraduccionesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TraduccionesEntry.ID + " TEXT NOT NULL,"
                + TraduccionesEntry.LENGUA_GRAB + " TEXT,"
                + TraduccionesEntry.LENGUA_MADRE + " TEXT ,"
                + TraduccionesEntry.LENGUA_SEGUNDARIA + " TEXT ,"
                + TraduccionesEntry.CIUDAD + " TEXT ,"
                + TraduccionesEntry.NOTA+ " TEXT ,"
                + TraduccionesEntry.APELLIDO_NOMBRE + " TEXT,"
                + TraduccionesEntry.EDAD + " TEXT,"
                + TraduccionesEntry.GENERO + " TEXT,"
                + TraduccionesEntry.FECHA_CREACION + " TEXT, "
                + "UNIQUE (" + TraduccionesEntry.ID + "))");

        db.execSQL("CREATE TABLE " + RutasEntry.TABLE_NAME+ " ("
                + RutasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RutasEntry.ID + " TEXT NOT NULL,"
                + RutasEntry.RUTA + " TEXT,"
                + RutasEntry.FECHA_CREACION + " TEXT ,"
                + RutasEntry.ESTADO + " TEXT ,"
                + "UNIQUE (" + RutasEntry.ID + "))");
    }

    public Cursor getAllTraducciones() {
        Log.d("laptm","getAllTraducciones");
        return getReadableDatabase()
                .query(
                        TraduccionesEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAllRutas() {
        Log.d("laptm","getAllRutas");
        return getReadableDatabase()
                .query(
                        RutasEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }


    public long saveTraduccion (Traducciones traducciones){
        Log.d("laptm","saveTraduccion");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                TraduccionesEntry.TABLE_NAME,
                null,
                traducciones.toContentValues());

    }

    public long saveRuta (Rutas rutas){
        Log.d("laptm","saveRuta");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                RutasEntry.TABLE_NAME,
                null,
                rutas.toContentValues());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d("laptm","se debio actuaizar");
        db.execSQL("DROP TABLE IF EXISTS " + TraduccionesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RutasEntry.TABLE_NAME);
        onCreate(db);
    }



    public int updateTraduccion(Traducciones traducciones, String traduccionId) {
        return getWritableDatabase().update(
                TraduccionesEntry.TABLE_NAME,
                traducciones.toContentValues(),
                TraduccionesEntry.ID + " LIKE ?",
                new String[]{traduccionId}
        );
    }

    public int updateRuta(Rutas rutas, String rutaId) {
        return getWritableDatabase().update(
                RutasEntry.TABLE_NAME,
                rutas.toContentValues(),
                RutasEntry.ID + " LIKE ?",
                new String[]{rutaId}
        );
    }

/*
    public long mockLawyer(Traducciones traducciones) {
        Log.d("laptm","mockLawyer()");
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(
                TraduccionesEntry.TABLE_NAME,
                null,
                traducciones.toContentValues());
    }


        public void mockData(SQLiteDatabase db) {
            //db.execSQL("DROP TABLE IF EXISTS " + TraduccionesEntry.TABLE_NAME);
            mockLawyer(new Traducciones("espa desde helpero", "madre",
                "secund", "EL carmen","nota","apellidonombre","edad",
                "geneno"));
    }*/

}