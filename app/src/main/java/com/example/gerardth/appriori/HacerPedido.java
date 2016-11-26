package com.example.gerardth.appriori;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerardth.appriori.MapsActivity;

public class HacerPedido extends AppCompatActivity {

    public Spinner spinnerSopa, spinnerEntrada, spinnerProteina, spinnerJugo;
    String sopa, entrada, proteina, jugo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pedido);

        spinnerSopa = (Spinner)findViewById(R.id.spinnerSopa);
        spinnerEntrada = (Spinner)findViewById(R.id.spinnerEntrada);
        spinnerProteina = (Spinner)findViewById(R.id.spinnerProteina);
        spinnerJugo = (Spinner)findViewById(R.id.spinnerJugo);

        /*sopa = spinnerSopa.getSelectedItem().toString();
        eventos = Ivento.darInstancia().filtrarEventos(filtro);*/

        spinnerSopa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sopa = spinnerSopa.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sopa = "";
            }
        });

        spinnerEntrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entrada = spinnerEntrada.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                entrada = "";
            }
        });

        spinnerProteina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proteina = spinnerProteina.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                proteina = "";
            }
        });

        spinnerJugo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// cuando se cambia el filtro
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jugo = spinnerJugo.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jugo = "";
            }
        });
    }

    public void hacerPedido(View v){
        if(sopa.equals("") || entrada.equals("") || proteina.equals("") || jugo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
            //showDialog("Valores vacíos", "Ingrese todos los valores correctamente.", MainActivity.class);
        }
        else {
            //TODO ARMAR EL PEDIDO PARA ENVIARLO A LA DB
            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
            //showDialog("Informadión agregada", "La información del crearRestaurante ha sido agregada satisfactoriamente. " +
            //"A continuación, ingrese el menú que va a ofrecer.", CrearMenu.class);
        }
    }

}
