package com.espe.tsafiapp.data;

import android.provider.BaseColumns;

public class RutasContract {

    private RutasContract () {};

    public static abstract class RutasEntry implements BaseColumns {
        public static final String TABLE_NAME = "Archivos";
        public static final String ID = "id";
        public static final String RUTA = "ruta";
        public static final String FECHA_CREACION = "fechaCreacion";
        public static final String ESTADO = "estado";
    }
}
