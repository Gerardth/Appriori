package com.example.gerardth.appriori;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardth.appriori.objects.Appriori;
import com.example.gerardth.appriori.objects.Menu;
import com.example.gerardth.appriori.objects.Restaurante;

import java.util.ArrayList;

/**
 * Created by Gerardth on 25/09/2016.
 */
public class CrearMenu extends AppCompatActivity {

    public Menu menu;
    public Restaurante restaurante;

    EditText txtSopa;
    EditText txtEntrada;
    EditText txtProteina;
    EditText txtJugo;

    public ArrayList<String> sopa1 = new ArrayList<String>();
    public ArrayList<String> entrada1 = new ArrayList<String>(); //frijol o verdura
    public ArrayList<String> proteina1 = new ArrayList<String>();
    public ArrayList<String> jugo1 = new ArrayList<String>();

    String nombreRestaurante;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        nombreRestaurante = intent.getStringExtra("nombreRestaurante");

    }

    public void crearMenu(View v){
        txtSopa = (EditText)findViewById(R.id.sopa);
        txtEntrada = (EditText)findViewById(R.id.entrada);
        txtProteina = (EditText)findViewById(R.id.proteina);
        txtJugo = (EditText)findViewById(R.id.jugo);

        String sopa = txtSopa.getText().toString();
        String entrada = txtEntrada.getText().toString();
        String proteina = txtProteina.getText().toString();
        String jugo = txtJugo.getText().toString();

        //System.out.println("SOPA " + sopa1.get(0));
        System.out.println("HOLAAAAAA " + sopa.contains("\n"));

        sopa1.add(sopa);
        entrada1.add(entrada); //frijol o verdura
        proteina1.add(proteina);
        jugo1.add(jugo);

        if(sopa.equals("") || entrada.equals("") || proteina.equals("") || jugo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
            //showDialog("Valores vacíos", "Ingrese todos los valores correctamente.", MainActivity.class);
        }
        else {
            menu = new Menu(sopa1, entrada1, proteina1, jugo1);
            restaurante = Appriori.darInstancia().getRestaurante(nombreRestaurante);
            restaurante.setMenu(menu);
            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
            //showDialog("Informadión agregada", "La información del crearRestaurante ha sido agregada satisfactoriamente. " +
            //"A continuación, ingrese el menú que va a ofrecer.", CrearMenu.class);
        }
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
