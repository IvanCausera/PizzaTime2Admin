package com.example.pizzatime2admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pizzatime2admin.modelo.PizzaBebida;

public class Insertar extends AppCompatActivity {

    private int posTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        final EditText editNombre = findViewById(R.id.editNombre_insertar);
        final EditText editPrecio = findViewById(R.id.editprecio_insertar);
        final Spinner spinTipo = findViewById(R.id.spinTipo_insertar);

        final Button btnInsertar = findViewById(R.id.btnInsertar);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.tipos, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTipo.setAdapter(spinAdapter);

        spinTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posTipo = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                PizzaBebida pb = new PizzaBebida(editNombre.getText().toString(), Double.parseDouble(editPrecio.getText().toString()), posTipo);

                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.PIZZAINSERTAR, pb);

                intent.putExtra(Intent.EXTRA_TEXT, bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}