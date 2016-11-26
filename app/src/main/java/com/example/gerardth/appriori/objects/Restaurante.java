package com.example.gerardth.appriori.objects;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Gerardth on 23/10/2016.
 */

public class Restaurante {

    private String id;
    public String nombre, descripcion, direccion;
    public LatLng coord;
    public Menu menu;

    public Restaurante(String id, String nombre, String descripcion, String direccion, LatLng coord, Menu menu){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.coord = coord;
        this.menu = menu;
        //TODO ALMACENAR ESTA INFORMACION EN LA BASE DE DATOS
    }

    public void setMenu(Menu newMenu){ menu = newMenu; }
}
