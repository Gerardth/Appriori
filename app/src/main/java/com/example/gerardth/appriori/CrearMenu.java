package com.example.gerardth.appriori;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardth.appriori.objects.Menu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by Gerardth on 25/09/2016.
 */
public class CrearMenu extends AppCompatActivity {

    EditText txtSopa;
    EditText txtEntrada;
    EditText txtProteina;
    EditText txtJugo;

    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void crearMenu(View v){
        txtSopa = (EditText)findViewById(R.id.sopa);
        txtEntrada = (EditText)findViewById(R.id.entrada);
        txtProteina = (EditText)findViewById(R.id.proteina);
        txtJugo = (EditText)findViewById(R.id.jugo);

        String delimitador = "\n";

        String[] sopa = txtSopa.getText().toString().split(delimitador);
        String[] entrada = txtEntrada.getText().toString().split(delimitador);
        String[] proteina = txtProteina.getText().toString().split(delimitador);
        String[] jugo = txtJugo.getText().toString().split(delimitador);

        /*String sopa = txtSopa.getText().toString();
        String entrada = txtEntrada.getText().toString();
        String proteina = txtProteina.getText().toString();
        String jugo = txtJugo.getText().toString();

        String[] sopa1 = sopa.split(delimitador);
        String[] entrada1 = entrada.split(delimitador);
        String[] proteina1 = proteina.split(delimitador);
        String[] jugo1 = jugo.split(delimitador);*/


        if(sopa.equals("") || entrada.equals("") || proteina.equals("") || jugo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
        }
        else {
            crearMenu(user.getUid(), sopa, entrada, proteina, jugo);
            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
    }

    public void crearMenu(String id, String[] sopa, String[] entrada, String[] proteina, String[] jugo){
        mDatabase = reference.getReference("restaurantes").child(id).child("menu");
        Menu menu = new Menu(convertirALista(sopa), convertirALista(entrada), convertirALista(proteina), convertirALista(jugo));

        mDatabase.setValue(menu);

        /*mDatabase.child("sopa").setValue(convertirALista(sopa));
        mDatabase.child("entrada").setValue(convertirALista(entrada));
        mDatabase.child("proteina").setValue(convertirALista(proteina));
        mDatabase.child("jugo").setValue(convertirALista(jugo));*/
    }

    private List<String> convertirALista(String[] lista) {
        List<String> list = new ArrayList<String>(){};
        for(int i = 0; i < lista.length; i++) list.add(lista[i]);
        return list;
    }

    /*private void editarMenu(CrearMenu menu){
        txtNombre.setText(evento.nombre);
        txtDescripcion.setText(evento.descripcion);
        txtHora.setText(evento.hora);
        txtDia.setText(evento.dia);
        tipoEvento = evento.tipoEvento;
        txtLugar.setText(evento.lugar);
    }

    public String getId() {
        return id;
    }
    public String toString(){
        return "Descripción: " + descripcion + "\n" + "Hora: " + hora + "\n" + "Día: " + dia + "\n" + "Lugar: " + lugar + "\n" +
                "Tipo de evento: " + tipoEvento;
    }*/
}
