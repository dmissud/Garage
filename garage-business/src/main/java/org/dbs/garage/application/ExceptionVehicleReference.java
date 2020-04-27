package org.dbs.garage.application;

public class ExceptionVehicleReference extends Exception {
    public ExceptionVehicleReference(String identification) {
        super("ExceptionVehicleReference " + identification);
    }
}
