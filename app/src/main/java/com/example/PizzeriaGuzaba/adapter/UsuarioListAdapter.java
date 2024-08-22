package com.example.ejemplofirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ejemplofirebase.R;
import com.example.ejemplofirebase.model.Producto;
import com.example.ejemplofirebase.model.Usuario;

import java.util.List;

public class UsuarioListAdapter extends BaseAdapter {

    private List<Producto> productos;
    private LayoutInflater inflater;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public UsuarioListAdapter(Context context, List<Producto> productos){
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Producto getItem(int i) {
        return productos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_usuario, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtId = convertView.findViewById(R.id.etId);
            viewHolder.textNombre = convertView.findViewById(R.id.etNombre);
            viewHolder.txtDescripcion = convertView.findViewById(R.id.etlDescripcion);
            viewHolder.txtDescripcion = convertView.findViewById(R.id.etlDescripcion);
            viewHolder.txtPrecio = convertView.findViewById(R.id.etlPrecio);
            viewHolder.txtTamanio = convertView.findViewById(R.id.etlTamanio);
            viewHolder.btnEditar = convertView.findViewById(R.id.btnEditar);
            viewHolder.btnEliminar = convertView.findViewById(R.id.btnEliminar);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Producto producto = productos.get(position);
        viewHolder.txtId.setText(String.valueOf("ID del producto: "+producto.getId()));
        viewHolder.textNombre.setText("Nombre: "+producto.getNombre());
        viewHolder.txtDescripcion.setText("Descripción: "+producto.getDescripcion());
        viewHolder.txtPrecio.setText("Precio: "+producto.getPrecio());
        viewHolder.txtTamanio.setText("Tamaño: "+producto.getTamanio());


        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClickListener.onEditClick(position);
            }
        });

        ViewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClickListener.onDeleteClick(position);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView txtId;
        TextView textNombre;
        TextView txtDescripcion;
        TextView txtTamanio;
        TextView txtPrecio;
        Button btnEditar;
        static Button btnEliminar;
    }

}
