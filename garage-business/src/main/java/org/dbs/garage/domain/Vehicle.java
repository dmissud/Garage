package org.dbs.garage.domain;

import org.dbs.garage.domain.Marque;

public class Vehicle {
    private String idChassis;
    private Marque marque;
//    private List<Option> listOption;
//    private Moteur moteur;
    private int km;

    public Vehicle(String idChassis, Marque marque/*, Moteur moteur*/) {
        this.idChassis = idChassis;
        this.marque = marque;
//        this.moteur = moteur;
//        this.listOption = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\nVehicle{" +
                "idChassis='" + idChassis + '\'' +
                ", marque=" + marque +
                ", km=" + km +
                "}";
    }

    public String Identification() {
        return this.idChassis;
    }
}
