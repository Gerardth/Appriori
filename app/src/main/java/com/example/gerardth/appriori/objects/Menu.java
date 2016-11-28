package com.example.gerardth.appriori.objects;

import java.util.List;

/**
 * Created by Gerardth on 23/10/2016.
 */

public class Menu {

    public List<String> sopa;
    public List<String> entrada; //frijol o verdura
    public List<String> proteina;
    public List<String> jugo;

    public Menu(){}

    public Menu(List<String> sopa, List<String> entrada, List<String> proteina, List<String> jugo){
        this.sopa = sopa;
        this.entrada = entrada;
        this.proteina = proteina;
        this.jugo = jugo;
    }
}
