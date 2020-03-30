package org.dbs.garage;

import java.util.ArrayList;
import java.util.List;

public class Garage {
    private List<Vehicule> vehicules;
    private String nom;
    private String localisation;

    public Garage(String nom, String localisation) {
        this.nom = nom;
        this.localisation = localisation;
        this.vehicules = new ArrayList<>();
    }

    public void enregistreVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
    }
}
