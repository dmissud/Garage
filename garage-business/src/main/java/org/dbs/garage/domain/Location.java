package org.dbs.garage.domain;

import java.util.Objects;

public class Location implements Comparable<Location> {
    private final String nom;
    private final String adresse;

    public Location(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    public Location(Location otherLocation) {
        this.nom = otherLocation.getName();
        this.adresse = otherLocation.getAdresse();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return nom.equals(location.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public int compareTo(Location otherLocation) {
        return this.nom.compareTo(otherLocation.getName());
    }

    public String getName() {
        return this.nom;
    }

    public String getAdresse() {
        return adresse;
    }
}
