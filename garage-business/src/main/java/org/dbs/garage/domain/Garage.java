package org.dbs.garage.domain;

import org.dbs.garage.application.service.ExceptionVehicleReference;

import java.util.*;

public class Garage implements Comparable<Garage> {
    private Map<String, Vehicle> vehicules;
    private String name;
    private String location;

    public Garage(String nom, String location) {
        this.name = nom;
        this.location = location;
        this.vehicules = new HashMap<>();
    }

    public Garage(Garage garage) {
        this.name = garage.getName();
        this.location = garage.getLocation();
        this.vehicules = garage.giveLstVehicleInGarage();
    }

    public Map<String, Vehicle> giveLstVehicleInGarage() {
        Map<String, Vehicle> lstVehicleInGarage = new HashMap<>();
        for (Vehicle vehicle : vehicules.values()) {
            lstVehicleInGarage.put(vehicle.identification(), new Vehicle(vehicle));
        }
        return lstVehicleInGarage;
    }

    public void registerVehicle(Vehicle vehicle) throws ExceptionVehicleReference {
        if (vehicules.get(vehicle.identification()) == null) {
            this.vehicules.put(vehicle.identification(), vehicle);
        } else {
            throw new ExceptionVehicleReference(vehicle.identification());
        }
    }

    @Override
    public String toString() {
        return "Garage{" +
                "nom='" + name + '\'' +
                ", localisation='" + location + '\'' +
                "\nvehicules =" + vehicules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Garage)) return false;
        Garage garage = (Garage) o;
        return name.equals(garage.name) &&
                location.equals(garage.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicules, name, location);
    }

    @Override
    public int compareTo(Garage garage) {
        if (this == garage) return 0;
        if (!location.equals(garage.location)) {
            return location.compareTo(garage.location);
        } else {
            return name.compareTo(garage.name);
        }
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int giveNumberOfVehicule() {
        return this.vehicules.size();
    }

    public void changeLocation(String newLocation) {
        this.location = newLocation;
    }
}