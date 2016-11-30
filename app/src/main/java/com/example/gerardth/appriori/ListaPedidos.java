package com.example.gerardth.appriori;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardth.appriori.objects.Pedido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gerardth on 27/11/2016.
 */

public class ListaPedidos extends Activity{

    ArrayList<Pedido> pedidos = new ArrayList<>();
    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        listView = (ListView) findViewById(R.id.list);

        mDatabase = reference.getReference("restaurantes/" + user.getUid());
        mDatabase.child("pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                limpiar();
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot snap = iterator.next();
                    Pedido pedido = snap.getValue(Pedido.class);
                    pedido.key = snap.getKey();
                    agregar(pedido);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pedidos);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = ((TextView) view).getText().toString();
                String[] nombre = string.split("\n");
                preguntar(nombre[0]);
                return false;
            }
        });
    }

    private void preguntar(final String nom) {
        new AlertDialog.Builder(ListaPedidos.this)
                .setMessage("¿Desea marcar este pedido como completado?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String tmp = obtenerKey(nom);
                        mDatabase = reference.getReference("restaurantes").child(user.getUid()).child("pedido").child(tmp);
                        mDatabase.removeValue();
                        Toast.makeText(getApplicationContext(), "Pedido marcado como atendido.", Toast.LENGTH_SHORT).show();
                        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(ListaPedidos.this, android.R.layout.simple_list_item_1, pedidos);
                        listView.setAdapter(adapter);
                    }
                }).setNegativeButton("No", null).show();
    }

    public void limpiar(){ pedidos.clear(); }

    public void agregar(Pedido pedido){
        pedidos.add(pedido);
    }

    public String obtenerKey(String nombre){
        for(int i = 0; i < pedidos.size(); i++){
            String name = "Nombre del solicitante: " + pedidos.get(i).nombre;
            if(name.equals(nombre)){
                String s = pedidos.get(i).key;
                pedidos.remove(i);
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_owner, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cerrarSesion:
                FirebaseAuth.getInstance().signOut();
                goLogin();

                return true;
        }
        return false;
    }

    private void goLogin(){
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
