package org.dbs.garage;

import java.util.ArrayList;
import java.util.List;

public class Vehicule {
    private String idChassis;
    private Marque marque;
//    private List<Option> listOption;
//    private Moteur moteur;
    private int km;

    public Vehicule(String idChassis, Marque marque/*, Moteur moteur*/) {
        this.idChassis = idChassis;
        this.marque = marque;
//        this.moteur = moteur;
//        this.listOption = new ArrayList<>();
    }

}
