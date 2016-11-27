package com.example.gerardth.appriori.database;

import android.content.Intent;

import com.example.gerardth.appriori.objects.Pedido;
import com.example.gerardth.appriori.objects.Restaurante;
import com.example.gerardth.appriori.objects.Usuario;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Gerardth on 21/11/2016.
 */

public class FirebaseDB {
    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference(); //referencia a la raiz

    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /*user.addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String value = dataSnapshot.getValue(String.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError){
            databaseError.toException();
        }
    });*/

    public void crearRestaurante(String nombre, String descripcion, String direccion, LatLng coord){
        mDatabase = reference.getReference("Restaurantes").child(user.getUid());

        mDatabase.child("nombre").setValue(nombre);
        mDatabase.child("Descripcion").setValue(descripcion);
        mDatabase.child("direccion").setValue(direccion);
        mDatabase.child("coordenadas").setValue(coord);
    }

    public void crearMenu(String id, String[] sopa, String[] entrada, String[] proteina, String[] jugo){
        mDatabase = reference.getReference("Restaurantes").child(id).child("menu");

        mDatabase.child("sopa").setValue(sopa);
        mDatabase.child("entrada").setValue(entrada);
        mDatabase.child("proteina").setValue(proteina);
        mDatabase.child("jugo").setValue(jugo);
    }

    public void editarMenu(String id){

    }

    public void crearPedido(String id, Pedido pedido){

        String key = mDatabase.child("Restaurantes").child(id).child("menu").push().getKey();
        //mDatabase = reference.getReference("Restaurantes").child(id).child("menu");
    }
}
