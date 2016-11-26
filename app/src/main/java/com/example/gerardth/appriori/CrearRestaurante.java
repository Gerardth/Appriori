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

import com.example.gerardth.appriori.objects.Appriori;
import com.example.gerardth.appriori.objects.Restaurante;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CrearRestaurante extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    Restaurante restaurante;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    private GoogleMap mMap;
    private CameraUpdate camara = null;
    public LatLng centro = new LatLng(4.601586, -74.065274);
    public LatLng coord = centro;
    GoogleApiClient apiClient;

    EditText txtNombre;
    EditText txtDescripcion;
    EditText txtDireccion;

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
/*
    private void showDialog(String title, String message, final Class clase) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent = new Intent(getApplicationContext(), clase);
                startActivity(intent);
            }
        });
        AlertDialog dialog= alertDialog.create();
        dialog.show();
    }
*/
    private void editarMapa() {

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);//Seteamos el tipo de mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        camara = CameraUpdateFactory.newLatLngZoom(centro,18);
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
                /*Projection proj = mMap.getProjection();
                Point coordenada = proj.toScreenLocation(latLng);
                centro = new LatLng(coordenada.l);*/
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
        String id = "temporal";

        if(nombre.equals("") || descripcion.equals("") || direccion.equals("") || coord == centro ){
            Toast.makeText(getApplicationContext(), R.string.info_incomplete, Toast.LENGTH_SHORT).show();
            //showDialog("Valores vacíos", "Ingrese todos los valores correctamente.", MainActivity.class);
        }
        else {
            restaurante = new Restaurante(null, nombre, descripcion, direccion, coord, null);
            Appriori.darInstancia().agregarRestaurante(restaurante);
            Toast.makeText(getApplicationContext(), R.string.info_complete, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CrearMenu.class);
            intent.putExtra("nombreRestaurante", nombre);
            startActivity(intent);
            //showDialog("Informadión agregada", "La información del crearRestaurante ha sido agregada satisfactoriamente. " +
                    //"A continuación, ingrese el menú que va a ofrecer.", CrearMenu.class);
        }
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
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            centro = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            System.out.println("CENTROOOO " + centro);

        }
        //...
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
}
