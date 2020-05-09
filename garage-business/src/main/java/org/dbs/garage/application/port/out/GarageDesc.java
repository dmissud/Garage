package org.dbs.garage.application.port.out;

public class GarageDesc {
    private final String name;
    private final String location;
    private final int numberOfCars;

    public GarageDesc(String name, String location, int numberOfCars) {
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
