package org.dbs.garage.domain;

public class Vehicle {
    private final String idChassis;
    private final Marque marque;

    public Vehicle(String idChassis, Marque marque/*, Moteur moteur*/) {
        this.idChassis = idChassis;
        this.marque = marque;
    }

    public Vehicle(Vehicle vehicle) {
        this.idChassis = vehicle.identification();
        this.marque = vehicle.getMarque();
    }

    public Marque getMarque() {
        return this.marque;
    }

    @Override
    public String toString() {
        return "\nVehicle{" +
                "idChassis='" + idChassis + '\'' +
                ", marque=" + marque +
                "}";
    }

    public String identification() {
        return this.idChassis;
    }
}
