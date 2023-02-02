package com.espe.tsafiapp.data;

import android.provider.BaseColumns;

public class TraduccionesContract {

    private TraduccionesContract () {};

    public static abstract class TraduccionesEntry implements BaseColumns {
        public static final String TABLE_NAME ="Traducciones";
        public static final String ID = "id";
        public static final String LENGUA_GRAB = "lenguaGrab";
        public static final String LENGUA_MADRE = "lenguaMadre";
        public static final String LENGUA_SEGUNDARIA = "lenguaSecundaria";
        public static final String CIUDAD = "ciudad";
        public static final String NOTA = "nota";
        public static final String APELLIDO_NOMBRE = "apellidoNombre";
        public static final String EDAD = "edad";
        public static final String GENERO = "genero";
        public static final String FECHA_CREACION = "fechaCreacion";
    }
}
