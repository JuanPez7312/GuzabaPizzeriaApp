package com.example.ejemplofirebase.model;

public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private String tamanio;
    private double precio;

    public Producto(){}

    public Producto(int id, String nombre, String descripcion, String tamanio, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tamanio = tamanio;
        this.precio = precio;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
