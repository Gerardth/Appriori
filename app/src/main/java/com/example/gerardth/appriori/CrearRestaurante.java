package com.example.gerardth.appriori;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardth.appriori.objects.Restaurante;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearRestaurante extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    Restaurante restaurante;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    private GoogleMap mMap;
    private CameraUpdate camara = null;
    public LatLng centro = null;
    public LatLng coord = null;
    GoogleApiClient apiClient;

    EditText txtNombre;
    EditText txtDescripcion;
    EditText txtDireccion;

    private FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapRestaurante));
        mapFragment.getMapAsync(this);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void editarMapa() {

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//Seteamos el tipo de mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        if(centro != null) camara = CameraUpdateFactory.newLatLngZoom(centro,14);
        else camara = CameraUpdateFactory.newLatLngZoom(new LatLng(4.636130555880344, -74.08310115337372), 16);
        mMap.animateCamera(camara);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                coord = new LatLng(latLng.latitude, latLng.longitude);
                //centro = coord;
                ponerMarcador(coord,"","");
                camara = CameraUpdateFactory.newLatLng(coord);
                mMap.animateCamera(camara);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        editarMapa();
    }

    private void ponerMarcador(LatLng position, String titulo, String info) {

        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo)
                .snippet(info)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    public void crearRestaurante(View v){

        txtNombre = (EditText)findViewById(R.id.nombre);
        txtDescripcion = (EditText)findViewById(R.id.descripcion);
        txtDireccion = (EditText)findViewById(R.id.direccion);

        String nombre = txtNombre.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String direccion = txtDireccion.getText().toString();

        if(nombre.equals("") || descripcion.equals("") || direccion.equals("") || coord == centro ){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
        }
        else {
            crearRestaurante(nombre, descripcion, direccion, coord);
            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CrearMenu.class);
            startActivity(intent);
        }
    }

    public void crearRestaurante(String nombre, String descripcion, String direccion, LatLng coord){
        mDatabase = reference.getReference("restaurantes").child(user.getUid());
        Restaurante restaurante = new Restaurante(nombre, descripcion, direccion, coord);

        mDatabase.setValue(nombre);

        /*mDatabase.child("nombre").setValue(nombre);
        mDatabase.child("descripcion").setValue(descripcion);
        mDatabase.child("direccion").setValue(direccion);
        mDatabase.child("coordenadas").child("latitud").setValue(coord.latitude);
        mDatabase.child("coordenadas").child("longitud").setValue(coord.longitude);*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "No se pudo conectar con Google Play services", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            centro = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        } else {

            /*Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            centro = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());*/
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);
                centro = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                //updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Toast.makeText(getApplicationContext(), "Se ha interrumpido la conexión con Google Play services", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        centro = new LatLng(location.getLatitude(), location.getLongitude());
    }
}
