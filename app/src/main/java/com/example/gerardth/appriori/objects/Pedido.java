package com.example.gerardth.appriori.objects;

/**
 * Created by Gerardth on 21/11/2016.
 */

public class Pedido {

    public String key, nombre, sopa, entrada, proteina, jugo;


    public Pedido (String nombre, String sopa, String entrada, String proteina, String jugo){
        this.nombre = nombre;
        this.sopa = sopa;
        this.entrada = entrada;
        this.proteina = proteina;
        this.jugo = jugo;
    }

    public Pedido(){}

    @Override
    public String toString(){
        return "Nombre del solicitante: " + nombre + "\n" +
                "Sopa: " + sopa + "\n" +
                "Entrada: " + entrada + "\n" +
                "Proteína: " + proteina + "\n" +
                "Jugo: " + jugo;
    }
}