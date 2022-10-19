package com.espe.tsafiapp.data;

import com.espe.tsafiapp.data.TraduccionesContract.TraduccionesEntry;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.UUID;


public class Traducciones {
    private String id;
    private String lenguaGrab;
    private String lenguaMadre;
    private String lenguaSecundaria;
    private String ciudad;
    private String nota;
    private String apellidoNombre;
    private String edad;
    private String genero;

    public Traducciones( String lenguaGrab, String lenguaMadre, String lenguaSecundaria, String ciudad, String nota, String apellidoNombre, String edad, String genero) {
        this.id = UUID.randomUUID().toString();
        this.lenguaGrab = lenguaGrab;
        this.lenguaMadre = lenguaMadre;
        this.lenguaSecundaria = lenguaSecundaria;
        this.ciudad = ciudad;
        this.nota = nota;
        this.apellidoNombre = apellidoNombre;
        this.edad = edad;
        this.genero = genero;
    }

    @SuppressLint("Range")
    public Traducciones(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.ID));
        lenguaGrab = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.LENGUA_GRAB));
        lenguaMadre = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.LENGUA_MADRE));
        lenguaSecundaria = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.LENGUA_SEGUNDARIA));
        ciudad = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.CIUDAD));
        nota = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.NOTA));
        apellidoNombre = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.APELLIDO_NOMBRE));
        edad = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.EDAD));
        genero = cursor.getString(cursor.getColumnIndex(TraduccionesEntry.GENERO));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLenguaGrab() {
        return lenguaGrab;
    }

    public void setLenguaGrab(String lenguaGrab) {
        this.lenguaGrab = lenguaGrab;
    }

    public String getLenguaMadre() {
        return lenguaMadre;
    }

    public void setLenguaMadre(String lenguaMadre) {
        this.lenguaMadre = lenguaMadre;
    }

    public String getLenguaSecundaria() {
        return lenguaSecundaria;
    }

    public void setLenguaSecundaria(String lenguaSecundaria) {
        this.lenguaSecundaria = lenguaSecundaria;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public void setApellidoNombre(String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TraduccionesEntry.ID, id);
        values.put(TraduccionesEntry.LENGUA_GRAB, lenguaGrab);
        values.put(TraduccionesEntry.LENGUA_MADRE, lenguaMadre);
        values.put(TraduccionesEntry.LENGUA_SEGUNDARIA, lenguaSecundaria);
        values.put(TraduccionesEntry.CIUDAD, ciudad);
        values.put(TraduccionesEntry.NOTA, nota);
        values.put(TraduccionesEntry.APELLIDO_NOMBRE, apellidoNombre);
        values.put(TraduccionesEntry.EDAD, edad);
        values.put(TraduccionesEntry.GENERO, genero);
        return values;
    }
}