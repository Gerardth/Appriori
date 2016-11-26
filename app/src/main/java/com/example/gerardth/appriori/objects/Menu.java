package com.example.gerardth.appriori.objects;

import java.util.ArrayList;

/**
 * Created by Gerardth on 23/10/2016.
 */

public class Menu {

    public ArrayList<String> sopa;
    public ArrayList<String> entrada; //frijol o verdura
    public ArrayList<String> proteina;
    public ArrayList<String> jugo;

    public Menu(){}

    public Menu(ArrayList<String> sopa, ArrayList<String> entrada, ArrayList<String> proteina, ArrayList<String> jugo){
        this.sopa = sopa;
        this.entrada = entrada;
        this.proteina = proteina;
        this.jugo = jugo;
    }
}
