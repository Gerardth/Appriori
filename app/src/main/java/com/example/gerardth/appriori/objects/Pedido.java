package com.example.gerardth.appriori.objects;

/**
 * Created by Gerardth on 21/11/2016.
 */

public class Pedido {

    String sopa, entrada, proteina, jugo;

    public Pedido(){}

    public Pedido (String sopa, String entrada, String proteina, String jugo){
        this.sopa = sopa;
        this.entrada = entrada;
        this.proteina = proteina;
        this.jugo = jugo;
    }
}
