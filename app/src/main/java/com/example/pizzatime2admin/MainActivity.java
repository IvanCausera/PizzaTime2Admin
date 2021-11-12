package com.example.pizzatime2admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.pizzatime2admin.conexion.ApiPizzaTime;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PizzaBebidaAdapter pizzaBebidaAdapter;
    private int posSelected;
    private ApiPizzaTime api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recViewPizzaBebida_main);
        Button btnChange = findViewById(R.id.btnChange_main);
    }
}