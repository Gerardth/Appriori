package com.example.gerardth.appriori;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gerardth.appriori.objects.Menu;
import com.example.gerardth.appriori.objects.Restaurante;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gerardth on 27/11/2016.
 */

public class ListaRestaurantes extends ListActivity {

    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<Restaurante> restaurantes = new ArrayList<>();
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mDatabase = reference.getReference("restaurantes");
        final Query restauranteQuery = mDatabase.orderByChild("coordenadas");
        restauranteQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                limpiar();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot snap = iterator.next();

                    LatLng coord = new LatLng(Double.parseDouble(snap.child("coordenadas").child("latitud").getValue().toString()),
                            Double.parseDouble(snap.child("coordenadas").child("longitud").getValue().toString()));

                    Restaurante rest = new Restaurante(snap.child("nombre").getValue().toString(),
                            snap.child("descripcion").getValue().toString(), snap.child("direccion").getValue().toString(), coord);
                    rest.id = snap.getKey();
                    agregar(rest);
                }
                //ListView listView = (ListView) findViewById(R.id.lista);
                //ArrayAdapter<Restaurante> adapter = new ArrayAdapter<Restaurante>(getApplicationContext(), android.R.layout.simple_list_item_1, restaurantes);
                //listView.setListAdapter(adapter);
                //setListAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, restaurantes));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mList = (ListView) findViewById(R.id.lista);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // seleccionar un evento
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                // selected item
                String nombre = ((TextView) view).getText().toString();

                Intent i = new Intent(getApplicationContext(), HacerPedido.class);
                i.putExtra("restaurante", obtenerId(nombre));
                startActivity(i);
            }
        });
    }

    private String obtenerId(String nombre) {
        for(int i = 0; i < restaurantes.size(); i++){
            if(restaurantes.get(i).nombre.equals(nombre)) return restaurantes.get(i).id;
        }
        return null;
    }

    public void limpiar(){ restaurantes.clear(); }

    public void agregar(Restaurante restaurante){
        restaurantes.add(restaurante);
    }

    private void goLogin(){
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cerrarSesion:
                FirebaseAuth.getInstance().signOut();
                goLogin();

                return true;
            case R.id.mapa:
                Intent i = new Intent(this, ListaRestaurantes.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
        }
        return false;
    }
}
