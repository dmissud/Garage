package org.dbs.garage.usage.port.out;

public class GarageDesc {
    private final String name;
    private final String locationName;
    private final int numberOfCars;

    public GarageDesc(String name, String location, int numberOfCars) {
        this.name = name;
        this.locationName = location;
        this.numberOfCars = numberOfCars;
    }

    public String getName() {
        return name;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    @Override
    public String toString() {
        return "["+getName()+" in "+ getLocationName()+" have "+getNumberOfCars()+" vehicle(s)]";
    }
}
