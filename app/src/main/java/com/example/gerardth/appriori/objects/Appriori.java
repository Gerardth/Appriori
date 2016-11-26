package com.example.gerardth.appriori.objects;

import java.util.ArrayList;

/**
 * Created by Hogar on 29/10/2015.
 */
public class Appriori {

    private ArrayList<Restaurante> restaurante;
    private static Appriori instancia;

    public Appriori(){
        restaurante = new ArrayList<Restaurante>();
    }

    public static Appriori darInstancia(){
        if(instancia == null){
            instancia = new Appriori();
        }
        return instancia;
    }

    public void agregarRestaurante(Restaurante e){ restaurante.add(e); }

    public boolean eliminarRestaurante(Restaurante e){
        for(int i = 0; i <restaurante.size(); i++){
            if(e.nombre.equals(restaurante.get(i).nombre)){
                restaurante.remove(i);
                return true;
            }
        }
        return false;
    }

    public Restaurante getRestaurante(String nombre){
        Restaurante rest;
        for(int i = 0; i < restaurante.size(); i++){
            if(restaurante.get(i).nombre.equals(nombre)){
                rest = restaurante.get(i);
                return rest;
            }
        }
        return null;
    }

    public ArrayList<Restaurante> getRestaurante() { return restaurante; }
/*
    public CrearRestaurante[] filtrarEventos(String filtro){
        ArrayList<CrearRestaurante> temp = new ArrayList<CrearRestaurante>();
        if(filtro.equals("Todos")){
            for(int i = 0; i < crearRestaurante.size(); i++){
                temp.add(crearRestaurante.get(i));
            }
        }else{
            for(int i = 0; i < crearRestaurante.size(); i++){
                if(filtro.equals(crearRestaurante.get(i).tipoEvento)){
                    temp.add(crearRestaurante.get(i));
                }
            }
        }
        CrearRestaurante[] lista = new CrearRestaurante[temp.size()];
        for(int i = 0; i < temp.size(); i++){
            lista[i] = temp.get(i);
        }
        return lista;
    }

    public CrearRestaurante darEvento(String nombre){
        for(int i = 0; i < crearRestaurante.size(); i++){
            CrearRestaurante evento = crearRestaurante.get(i);
            if(evento.nombre.concat(": ".concat(crearRestaurante.get(i).descripcion)).equals(nombre)){
                return evento;
            }
        }
        return null;
    }
*/}
