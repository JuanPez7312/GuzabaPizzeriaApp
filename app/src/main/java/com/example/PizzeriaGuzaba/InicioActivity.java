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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplofirebase.adapter.UsuarioListAdapter;
import com.example.ejemplofirebase.dao.UsuarioDAO;
import com.example.ejemplofirebase.model.Producto;
import com.example.ejemplofirebase.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private FloatingActionButton btnagregar;
    private Button btnBuscarId;
    private Button btnPerfil;
    private Button btnClientes;
    private ListView listView;
    private UsuarioDAO usuarioDAO;
    private EditText etlNombre, etlDescripcion, etlPrecio, etlTamanio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        //Linkeamos con los valores de la interfaz
        btnagregar = findViewById(R.id.btnAgregar);
        btnBuscarId = findViewById(R.id.btnBuscarId);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnClientes = findViewById(R.id.btnClientes);
        listView = findViewById(R.id.listView);

        ;

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, AgregarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, ClientesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnBuscarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, BuscarId.class);
                startActivity(intent);
                finish();
            }
        });


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
                Toast.makeText(InicioActivity.this, "Producto agregado exitosamente", Toast.LENGTH_SHORT).show();
                etlNombre.setText("");
                etlDescripcion.setText("");
                etlPrecio.setText("");
                etlTamanio.setText("");
            } else {
                Toast.makeText(InicioActivity.this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(InicioActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
}