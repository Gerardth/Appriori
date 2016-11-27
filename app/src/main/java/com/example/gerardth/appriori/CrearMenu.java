package com.example.gerardth.appriori;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardth.appriori.database.FirebaseDB;
import com.example.gerardth.appriori.objects.Appriori;
import com.example.gerardth.appriori.objects.Menu;
import com.example.gerardth.appriori.objects.Restaurante;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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

    FirebaseDB firebase;

    /*public ArrayList<String> sopa1 = new ArrayList<String>();
    public ArrayList<String> entrada1 = new ArrayList<String>(); //frijol o verdura
    public ArrayList<String> proteina1 = new ArrayList<String>();
    public ArrayList<String> jugo1 = new ArrayList<String>();*/

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

        String sopa = txtSopa.getText().toString();
        String entrada = txtEntrada.getText().toString();
        String proteina = txtProteina.getText().toString();
        String jugo = txtJugo.getText().toString();

        String[] sopa1 = sopa.split(delimitador);
        String[] entrada1 = entrada.split(delimitador);
        String[] proteina1 = proteina.split(delimitador);
        String[] jugo1 = jugo.split(delimitador);

        /*sopa1.add(sopa);
        entrada1.add(entrada); //frijol o verdura
        proteina1.add(proteina);
        jugo1.add(jugo);*/

        if(sopa.equals("") || entrada.equals("") || proteina.equals("") || jugo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
            //showDialog("Valores vacíos", "Ingrese todos los valores correctamente.", MainActivity.class);
        }
        else {
            //menu = new Menu(sopa1, entrada1, proteina1, jugo1);
            //restaurante = Appriori.darInstancia().getRestaurante(nombreRestaurante);
            //restaurante.setMenu(menu);
            firebase.crearMenu(user.getUid(), sopa1, entrada1, proteina1, jugo1);
            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
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
