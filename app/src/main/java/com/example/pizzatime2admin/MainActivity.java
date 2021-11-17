package com.example.pizzatime2admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzatime2admin.conexion.ApiPizzaTime;
import com.example.pizzatime2admin.conexion.Cliente;
import com.example.pizzatime2admin.modelo.PizzaBebida;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String PIZZABEBIDA = "pizzabebida";
    public static final String PIZZAINSERTAR = "pizzainsertar";
    public static final String PIZZAACTUALIZAR = "pizzaactualizar";
    public static final String BORRAR = "borrar";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PizzaBebidaAdapter pizzaBebidaAdapter;
    private int posSelected;
    private ApiPizzaTime api;

    private int actualTipe = 0;
    private ArrayList<PizzaBebida> pizzas = new ArrayList<>();
    private ArrayList<PizzaBebida> bebidas = new ArrayList<>();

    ActivityResultLauncher<Intent> resultInsertar = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent datosResult = result.getData();

                        Bundle bundle = datosResult.getBundleExtra(Intent.EXTRA_TEXT);
                        PizzaBebida pb = bundle.getParcelable(PIZZAINSERTAR);

                        insertDatos(pb);

                    }
                }
            });

    ActivityResultLauncher<Intent> resultActualizarBorrar = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent datosResult = result.getData();

                        Bundle bundle = datosResult.getBundleExtra(Intent.EXTRA_SUBJECT);
                        PizzaBebida pb = bundle.getParcelable(PIZZAACTUALIZAR);
                        if (bundle.getBoolean(BORRAR)){
                            borrarDatos(pb.getId());
                        } else {
                            actualizarDatos(pb);
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        Button btnChange = findViewById(R.id.btnChange_main);
        TextView txtLabel = findViewById(R.id.txtLableList_main);

        btnChange.setText(R.string.ver_bebida);
        txtLabel.setText(getString(R.string.pizza) + ":");

        recyclerView = findViewById(R.id.recViewPizzaBebida_main);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, 1));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        pizzaBebidaAdapter = new PizzaBebidaAdapter(this);

        pizzaBebidaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posSelected = recyclerView.getChildAdapterPosition((view));
                PizzaBebida pb = pizzaBebidaAdapter.getList().get(posSelected);

                Bundle bundle = new Bundle();
                bundle.putParcelable(PIZZABEBIDA, pb);

                Intent intentActualizar = new Intent(MainActivity.this, ActualizarBorrar.class);
                intentActualizar.putExtra(Intent.EXTRA_TEXT, bundle);

                resultActualizarBorrar.launch(intentActualizar);
            }
        });
        recyclerView.setAdapter(pizzaBebidaAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInsertar = new Intent(MainActivity.this, Insertar.class);
                resultInsertar.launch(intentInsertar);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actualTipe == 0){
                    actualTipe = 1;
                    btnChange.setText(R.string.ver_pizza);
                    txtLabel.setText(getString(R.string.bebida) + ":");
                    pizzaBebidaAdapter.changeList(bebidas);
                } else {
                    actualTipe = 0;
                    btnChange.setText(R.string.ver_bebida);
                    txtLabel.setText(getString(R.string.pizza) + ":");
                    pizzaBebidaAdapter.changeList(pizzas);
                }
            }
        });

        retrofit = Cliente.getCliente();

        getDatos();
    } //End onCreate

    private void getDatos(){
        api = retrofit.create(ApiPizzaTime.class);
        Call<ArrayList<PizzaBebida>> respuesta = api.getPizzaBebida();
        respuesta.enqueue(new Callback<ArrayList<PizzaBebida>>() {
            @Override
            public void onResponse(Call<ArrayList<PizzaBebida>> call, Response<ArrayList<PizzaBebida>> response) {
                if (response.isSuccessful()){
                    ArrayList<PizzaBebida> listPizzaBebida = response.body();

                    for (int i = 0; i < listPizzaBebida.size(); i++){
                        PizzaBebida pb = listPizzaBebida.get(i);
                        if (pb.getTipo() == 0) pizzas.add(pb);
                        else bebidas.add(pb);
                    }

                    if (actualTipe == 0) pizzaBebidaAdapter.addToList(pizzas);
                    else pizzaBebidaAdapter.addToList(bebidas);

                } else {
                    Toast.makeText(getApplicationContext(), "Fallo en la respuesta", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PizzaBebida>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    private void insertDatos(PizzaBebida pb){
        api = retrofit.create(ApiPizzaTime.class);
        Call<PizzaBebida> pizzaBebida = api.postPizzaBebida(pb.getNombre(), pb.getPrecio(), pb.getTipo());

        pizzaBebida.enqueue(new Callback<PizzaBebida>() {
            @Override
            public void onResponse(Call<PizzaBebida> call, Response<PizzaBebida> response) {
                PizzaBebida pb = response.body();
                if (pb.getTipo() == 0) {
                    pizzas.add(pb);
                    if (actualTipe == 0) pizzaBebidaAdapter.addToList(pb);
                }  else {
                    bebidas.add(pb);
                    if (actualTipe == 1) pizzaBebidaAdapter.addToList(pb);
                }
            }

            @Override
            public void onFailure(Call<PizzaBebida> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo en la respuesta", Toast.LENGTH_LONG).show();
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    private  void actualizarDatos(PizzaBebida pb){
        api = retrofit.create(ApiPizzaTime.class);
        Call<PizzaBebida> pizzaBebida = api.putPizzaBebida(pb.getId(), pb);

        pizzaBebida.enqueue(new Callback<PizzaBebida>() {
            @Override
            public void onResponse(Call<PizzaBebida> call, Response<PizzaBebida> response) {
                PizzaBebida pb = response.body();
                if (pb.getTipo() == 0) {
                    if (actualTipe == 0) { //Update pizza
                        pizzas.set(posSelected, pb);
                        pizzaBebidaAdapter.updateItem(posSelected, pb);
                    } else { //Update bebida to pizza
                        bebidas.remove(posSelected);
                        pizzaBebidaAdapter.deleteItem(posSelected);
                        pizzas.add(pb);
                    }
                }  else {
                    if (actualTipe == 1) { //Update bebida
                        bebidas.set(posSelected, pb);
                        pizzaBebidaAdapter.updateItem(posSelected, pb);
                    } else{ //Update pizza to bebida
                        pizzas.remove(posSelected);
                        pizzaBebidaAdapter.deleteItem(posSelected);
                        bebidas.add(pb);
                    }
                }
            }
            @Override
            public void onFailure(Call<PizzaBebida> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo en la respuesta", Toast.LENGTH_LONG).show();
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    private void borrarDatos(int id){
        api = retrofit.create(ApiPizzaTime.class);
        Call<PizzaBebida> pizzaBebida = api.deletePizzaBebida(id);
        pizzaBebida.enqueue(new Callback<PizzaBebida>() {
            @Override
            public void onResponse(Call<PizzaBebida> call, Response<PizzaBebida> response) {

                if (actualTipe == 0) {
                    pizzas.remove(posSelected);
                }  else {
                    bebidas.remove(posSelected);
                }
                pizzaBebidaAdapter.deleteItem(posSelected);
            }
            @Override
            public void onFailure(Call<PizzaBebida> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo en la respuesta", Toast.LENGTH_LONG).show();
                Log.e("FAIL", t.getMessage());
            }
        });
    }

}