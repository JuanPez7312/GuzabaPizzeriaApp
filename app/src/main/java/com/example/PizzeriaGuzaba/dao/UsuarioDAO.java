package com.example.ejemplofirebase.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ejemplofirebase.database.DataBaseHelper;
import com.example.ejemplofirebase.model.Producto;
import com.example.ejemplofirebase.model.Usuario;

import java.util.ArrayList;
import java.util.List;

//El usuario Dao es que usa la base de datos

public class UsuarioDAO {

    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void abrir() {
        db = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        dbHelper.close();
    }

    //Insert
    public long insert(String nombre, String descripcion, double precio, String tamanio) {

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("tamanio", tamanio);


        return db.insert("usuarios", null, values);
    }

    //Get
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM productos", null);

        //Iteramos con el cursor para obtener los datos
        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setId(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setDescripcion(cursor.getString(2));
                producto.setPrecio(cursor.getFloat(3));
                producto.setTamanio(cursor.getString(4));
                productos.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close(); //Cerramos el cursor
        return productos;
    }

    //Update
    public void actualizar(int id, String nombre, String descripcion, String tamanio, double precio) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("tamanio", tamanio);
        values.put("precio", precio);
        db.update("productos", values, "id = ?", new String[]{String.valueOf(id)});
    }

    //Delete
    public void eliminar(int id) {
        db.delete("productos", "id = ?", new String[]{String.valueOf(id)});
    }

}
