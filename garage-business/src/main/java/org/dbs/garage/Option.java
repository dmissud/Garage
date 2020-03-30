package org.dbs.garage;

public abstract class Option {

    abstract double getPrice();

    abstract String getName();

    abstract String getDescription();

    boolean isCompatible(Vehicule vehicule, Option option) {
        return true;
    }
}
