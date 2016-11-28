package com.example.gerardth.appriori;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ListaPedidos extends ListActivity {

    ArrayList<Pedido> pedidos = new ArrayList<>();
    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lista_pedidos);

        mDatabase = reference.getReference("restaurantes/" + user.getUid());
        mDatabase.child("pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                limpiar();
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    Pedido pedido = snapshot.getValue(Pedido.class);
                    agregar(pedido);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListView listView = (ListView) findViewById(R.id.lista);
        setListAdapter(new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, pedidos));
    }

    public void limpiar(){ pedidos.clear(); }
    public void agregar(Pedido pedido){
        pedidos.add(pedido);
    }
}
