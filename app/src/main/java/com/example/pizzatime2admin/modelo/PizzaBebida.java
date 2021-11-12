package com.example.pizzatime2admin.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PizzaBebida {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("precio")
    @Expose
    private double precio;

    @SerializedName("tipo")
    @Expose
    private int tipo;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getTipo() {
        return tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}