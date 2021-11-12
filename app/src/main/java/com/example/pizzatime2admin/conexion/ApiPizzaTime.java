package com.example.pizzatime2admin.conexion;

import com.example.pizzatime2admin.modelo.PizzaBebida;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiPizzaTime {
    @GET("pizzaBebida")
    Call<ArrayList<PizzaBebida>> getPizzaBebida();

    @FormUrlEncoded
    @POST("pizzaBebida")
    Call<PizzaBebida> postPizzaBebida(
            @Field("nombre") String nombre,
            @Field("precio") double precio,
            @Field("tipo") int tipo
    );

    @PUT("pizzaBebida/{id}")
    Call<PizzaBebida> putPizzaBebida(
            @Path("id") int id,
            @Body PizzaBebida pizzaBebida
    );

    @DELETE("pizzaBebida/{id}")
    Call<PizzaBebida> deletePizzaBebida(@Path("id") int id);
}
