package com.example.gerardth.appriori;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashScreen extends Activity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (user != null) {
                        redireccionar();
                    }else{
                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                    }

                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    private void redireccionar() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = reference.getReference("usuarios/" + user.getUid());
        System.out.println("DATABASEEE" + mDatabase);
        final String[] tipo = new String[1];
        tipo[0] = "tmp";
        mDatabase.child("tipo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    tipo[0] = dataSnapshot.getValue(String.class).toString();
                    Intent intent = new Intent();
                    System.out.println("USUARIOOOO" + tipo[0]);
                    switch(tipo[0]){
                        case "dueño":
                            intent = new Intent(SplashScreen.this, ListaPedidos.class);
                            //intent = new Intent(SplashScreen.this, MapsActivity.class);
                            break;

                        case "usuario":
                            intent = new Intent(SplashScreen.this, MapsActivity.class);
                            break;
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}