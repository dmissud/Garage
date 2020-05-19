package org.dbs.garage.usage.port.in;

public class ExGarageGotVehicles extends Exception {
    public ExGarageGotVehicles(String garageName, int numberOfVehicule) {
        super(String.format("%s have %d", garageName, numberOfVehicule));
    }
}
