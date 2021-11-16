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

public class ActualizarBorrar extends AppCompatActivity {

    private int posTipo;
    private boolean borrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_borrar);

        final EditText editId = findViewById(R.id.editId_actborrar);
        final EditText editNombre = findViewById(R.id.editNombre_actborrar);
        final EditText editPrecio = findViewById(R.id.editprecio_actborrar);
        final Spinner spinTipo = findViewById(R.id.spinTipo_actborrar);

        final Button btnActualizar = findViewById(R.id.btnActualizar);
        final Button btnBorrar = findViewById(R.id.btnBorrar);

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

        Intent intentMain = getIntent();
        if (intentMain != null && intentMain.hasExtra(Intent.EXTRA_TEXT)){
            Bundle bundle = intentMain.getBundleExtra(Intent.EXTRA_TEXT);
            PizzaBebida pb = bundle.getParcelable(MainActivity.PIZZABEBIDA);

            editId.setText(String.valueOf(pb.getId()));
            editNombre.setText(pb.getNombre());
            editPrecio.setText(String.valueOf(pb.getPrecio()));
            spinTipo.setSelection(pb.getTipo());
        }

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PizzaBebida pb = new PizzaBebida(editNombre.getText().toString(), Double.parseDouble(editPrecio.getText().toString()), posTipo);
                pb.setId(Integer.parseInt(editId.getText().toString()));

                borrar = false;
                actualizarOBorrar(pb);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PizzaBebida pb = new PizzaBebida(editNombre.getText().toString(), Double.parseDouble(editPrecio.getText().toString()), posTipo);
                pb.setId(Integer.parseInt(editId.getText().toString()));

                borrar = true;
                actualizarOBorrar(pb);
            }
        });

    } //End onCreate

    private void actualizarOBorrar(PizzaBebida pb){
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.PIZZAACTUALIZAR, pb);
        bundle.putBoolean(MainActivity.BORRAR, borrar);

        Intent intentActualizar = new Intent(ActualizarBorrar.this, MainActivity.class);
        intentActualizar.putExtra(Intent.EXTRA_SUBJECT, bundle);

        setResult(RESULT_OK, intentActualizar);
        finish();
    }
}