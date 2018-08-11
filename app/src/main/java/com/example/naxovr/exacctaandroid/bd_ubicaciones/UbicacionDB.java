package com.example.naxovr.exacctaandroid.bd_ubicaciones;

import android.provider.BaseColumns;

public class UbicacionDB {

    private UbicacionDB() {}

//    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "ubicacionesDB";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_LONGITUD = "longitud";
        public static final String COLUMNA_LATITUD = "latitud";
        public static final String COLUMNA_FECHA = "fecha";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UbicacionDB.TABLE_NAME + " (" +
                        UbicacionDB.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UbicacionDB.COLUMNA_LONGITUD + " TEXT," +
                        UbicacionDB.COLUMNA_LONGITUD + " TEXT," +
                        UbicacionDB.COLUMNA_FECHA + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + UbicacionDB.TABLE_NAME;
    }

