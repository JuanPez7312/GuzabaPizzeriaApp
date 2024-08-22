package com.example.ejemplofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplofirebase.adapter.UsuarioListAdapter;
import com.example.ejemplofirebase.dao.UsuarioDAO;
import com.example.ejemplofirebase.model.Producto;
import com.example.ejemplofirebase.model.Usuario;

import java.util.List;

public class AgregarActivity extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private ListView listView;
    private EditText etlNombre, etlDescripcion, etlPrecio, etlTamanio;
    private Button btnAgregar, btnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar);

        usuarioDAO = new UsuarioDAO(this);
        usuarioDAO.abrir();

        //linkeamos los valores con la interfaz
        etlNombre = findViewById(R.id.etlNombre);
        etlDescripcion = findViewById(R.id.etlDescripcion);
        etlPrecio = findViewById(R.id.etlPrecio);
        etlTamanio = findViewById(R.id.etlTamanio);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVolver = findViewById(R.id.btnVolver);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto();
            }
        });

        actualizarListaProductos();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Ciere de la conexión con la base de datos al destruir la actividad
        usuarioDAO.cerrar();
    }

    private void actualizarListaProductos() {
        mostrarProductos();
    }

    private void mostrarProductos() {
        List<Producto> productos = usuarioDAO.obtenerTodos();
        StringBuilder stringBuilder = new StringBuilder();

        for (Producto producto : productos) {
            stringBuilder.append("ID: ").append(producto.getId()).append(", Nombre: ").append(producto.getNombre().toString())
                    .append("Precio: ").append((int) producto.getPrecio()).append(producto.getDescripcion().
                            toString()).append(producto.getTamanio().toString()).append("\n");
        }

        UsuarioListAdapter adapter = new UsuarioListAdapter(this, productos);
        listView.setAdapter(adapter);
        adapter.setOnEditClickListener(new UsuarioListAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                Producto producto = productos.get(position);
                etlNombre.setText(producto.getNombre());
                etlDescripcion.setText(producto.getDescripcion());
                etlTamanio.setText(producto.getTamanio());
                etlPrecio.setText(String.valueOf(producto.getPrecio()));

                int idProducto = producto.getId();
                btnAgregar.setText("Guardar");
                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        actualizarProducto(idProducto);
                    }
                });

            }
        });
    }

    private void agregarProducto() {
        String nombre = etlNombre.getText().toString().trim();
        String descripcion = etlDescripcion.getText().toString().trim();
        String precio = etlPrecio.getText().toString().trim();
        String tamanio = etlTamanio.getText().toString().trim();

        if (!nombre.isEmpty() && !descripcion.isEmpty() && precio.isEmpty() && tamanio.isEmpty()) {
            long resultado = usuarioDAO.insert(nombre, descripcion, Double.parseDouble(precio), tamanio);
            if (resultado != -1) {
                Toast.makeText(AgregarActivity.this, "Producto agregado exitosamente", Toast.LENGTH_SHORT).show();

                etlNombre.setText("");
                etlDescripcion.setText("");
                etlPrecio.setText("");
                etlTamanio.setText("");

                Intent intent = new Intent(AgregarActivity.this, InicioActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(AgregarActivity.this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AgregarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarProducto(int idProducto) {
        String nombre = etlNombre.getText().toString().trim();
        String descripcion = etlDescripcion.getText().toString().trim();
        String precio = etlPrecio.getText().toString().trim();
        String tamanio = etlTamanio.getText().toString().trim();
        if (!nombre.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty() && !tamanio.isEmpty()) {
            usuarioDAO.actualizar(idProducto, nombre, descripcion, tamanio, Double.parseDouble(precio));
            Toast.makeText(AgregarActivity.this, "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show();
            etlNombre.setText("");
            etlDescripcion.setText("");
            etlTamanio.setText("");
            etlPrecio.setText("");
            btnAgregar.setText("Agregar");
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarProducto();
                }
            });
        } else {
            Toast.makeText(AgregarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }

        actualizarListaProductos();
    }
}