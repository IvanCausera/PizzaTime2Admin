package com.example.pizzatime2admin.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PizzaBebida implements Parcelable {
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

    public PizzaBebida (){
        super();
    }

    public PizzaBebida(String nombre, double precio, int tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    protected PizzaBebida(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        precio = in.readDouble();
        tipo = in.readInt();
    }

    public static final Creator<PizzaBebida> CREATOR = new Creator<PizzaBebida>() {
        @Override
        public PizzaBebida createFromParcel(Parcel in) {
            return new PizzaBebida(in);
        }

        @Override
        public PizzaBebida[] newArray(int size) {
            return new PizzaBebida[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeDouble(precio);
        parcel.writeInt(tipo);
    }
}