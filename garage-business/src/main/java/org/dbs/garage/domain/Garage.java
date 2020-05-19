package org.dbs.garage.domain;

import org.dbs.garage.usage.service.ExceptionVehicleReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Garage implements Comparable<Garage> {
    private final Map<String, Vehicle> vehicules;
    private final String name;
    private String locationName;

    public Garage(String name, String locationName) {
        this.name = name;
        this.locationName = locationName;
        this.vehicules = new HashMap<>();
    }

    public Garage(Garage garage) {
        this.name = garage.getName();
        this.locationName = garage.getLocationName();
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
                ", localisation='" + locationName + '\'' +
                "\nvehicules =" + vehicules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Garage)) return false;
        Garage garage = (Garage) o;
        return name.equals(garage.name) &&
                locationName.equals(garage.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicules, name, locationName);
    }

    @Override
    public int compareTo(Garage garage) {
        if (this == garage) return 0;
        if (!locationName.equals(garage.locationName)) {
            return locationName.compareTo(garage.locationName);
        } else {
            return name.compareTo(garage.name);
        }
    }

    public String getName() {
        return name;
    }

    public String getLocationName() {
        return locationName;
    }

    public int giveNumberOfVehicule() {
        return this.vehicules.size();
    }

    public void changeLocation(Location newLocation) {
        this.locationName = newLocation.getName();
    }
}