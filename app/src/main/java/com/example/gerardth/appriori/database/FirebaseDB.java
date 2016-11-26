package com.example.gerardth.appriori.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gerardth on 21/11/2016.
 */

public class FirebaseDB {
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(); //referencia a la raiz

    /*user.addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String value = dataSnapshot.getValue(String.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError){
            databaseError.toException();
        }
    });

    @Override
    protected void onStart(){}

    public void createUser(FirebaseUser user) {
        Usuario usuario = new Usuario(user.getDisplayName(), user.getEmail(), "dueño");

        mDatabase.child("usuarios").child(user.getUid()).setValue(usuario);
        //mDatabase.child("usuarios").child(user.getUid()).child("username").setValue(user.getDisplayName()); para actualizar una parte
    }*/
}
