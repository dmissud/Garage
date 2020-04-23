package org.dbs.garage.application;

public class GarageDescription {
    private final String name;
    private final String location;
    private final int numberOfCars;

    public GarageDescription(String name, String location, int numberOfCars) {
        this.name = name;
        this.location = location;
        this.numberOfCars = numberOfCars;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    @Override
    public String toString() {
        return "["+getName()+" in "+getLocation()+" have "+getNumberOfCars()+" vehicle(s)]";
    }
}
