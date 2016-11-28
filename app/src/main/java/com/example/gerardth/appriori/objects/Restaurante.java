package com.example.gerardth.appriori.objects;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Gerardth on 23/10/2016.
 */

public class Restaurante {

    public String nombre, descripcion, direccion, id;
    public LatLng coord;

    public Restaurante(String nombre, String descripcion, String direccion, LatLng coord){

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.coord = coord;
        //TODO ALMACENAR ESTA INFORMACION EN LA BASE DE DATOS
    }

    public Restaurante(){}

    @Override
    public String toString(){
        return "Nombre: " + nombre + "\n" +
                "Descripción" + descripcion + "\n" +
                "Dirección" + direccion + "\n";
    }
}
