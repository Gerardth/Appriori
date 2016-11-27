package com.example.gerardth.appriori;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gerardth.appriori.objects.Restaurante;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Gerardth on 27/11/2016.
 */

public class ListaRestaurantes extends android.app.ListActivity {

    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView mList = (ListView) findViewById(R.id.list);

        List<Restaurante> lista = obtenerRestaurante();

        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista));
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // seleccionar un evento
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                // selected item
                String nombre = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), HacerPedido.class);
                // sending data to new activity
                i.putExtra("restaurante", nombre);
                startActivity(i);
            }
        });
    }

    public List<Restaurante> obtenerRestaurante(){
        return null;
    }
}
