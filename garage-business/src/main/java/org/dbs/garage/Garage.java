package org.dbs.garage;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Garage implements Comparable<Garage> {
    private Map<String, Vehicle> vehicules;
    private String name;
    private String location;

    public Garage(String nom, String location) {
        this.name = nom;
        this.location = location;
        this.vehicules = new TreeMap<>();
    }

    public void registerVehicle(Vehicle vehicle) throws Exception_Vehicule_Reference {
        if (vehicules.get(vehicle.Identification()) == null) {
            this.vehicules.put(vehicle.Identification(), vehicle);
        } else {
            throw new Exception_Vehicule_Reference(vehicle.Identification());
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
}