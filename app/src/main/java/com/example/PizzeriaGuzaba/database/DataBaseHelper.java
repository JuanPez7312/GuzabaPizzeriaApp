package com.example.ejemplofirebase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USERS = "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, etlDescripcion TEXT, etlPrecio LONG, etlTamanio TEXT)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(sqLiteDatabase);
    }

}
