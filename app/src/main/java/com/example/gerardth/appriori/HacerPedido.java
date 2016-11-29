package com.example.gerardth.appriori;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerardth.appriori.objects.Menu;
import com.example.gerardth.appriori.objects.Pedido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class HacerPedido extends AppCompatActivity {

    public Spinner spinnerSopa, spinnerEntrada, spinnerProteina, spinnerJugo;
    String sopa, entrada, proteina, jugo, id;
    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        menu = new Menu();
        Intent i = getIntent();
        id = i.getStringExtra("restaurante");

        mDatabase = reference.getReference("restaurantes/" + id);
        mDatabase.child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    menu = snapshot.getValue(Menu.class);

                    spinnerSopa.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, menu.sopa));
                    spinnerJugo.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, menu.jugo));
                    spinnerEntrada.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, menu.entrada));
                    spinnerProteina.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, menu.proteina));
                }
                else {}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spinnerSopa = (Spinner)findViewById(R.id.spinnerSopa);
        spinnerEntrada = (Spinner)findViewById(R.id.spinnerEntrada);
        spinnerProteina = (Spinner)findViewById(R.id.spinnerProteina);
        spinnerJugo = (Spinner)findViewById(R.id.spinnerJugo);


        spinnerSopa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sopa = spinnerSopa.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sopa = "sin sopa";
            }
        });


        spinnerEntrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entrada = spinnerEntrada.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                entrada = "sin entrada";
            }
        });


        spinnerProteina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proteina = spinnerProteina.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                proteina = "sin proteina";
            }
        });

        spinnerJugo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jugo = spinnerJugo.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jugo = "sin jugo";
            }
        });
    }

    public void hacerPedido(View v){
        if(sopa.equals("sin sopa") && entrada.equals("sin entrada") && proteina.equals("sin proteina") && jugo.equals("sin jugo")){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
            //showDialog("Valores vacíos", "Ingrese todos los valores correctamente.", MainActivity.class);
        }
        else {

            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Pedido pedido = new Pedido(sopa, entrada, proteina, jugo);
            crearPedido(id, pedido);
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
    }

    public void crearPedido(String id, Pedido pedido){
        mDatabase = reference.getReference("restaurantes").child(id).child("pedido").child(user.getUid());

        mDatabase.setValue(pedido);
    }

}
