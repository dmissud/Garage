package org.dbs.garage.usage.service;

public class ExceptionVehicleReference extends Exception {
    public ExceptionVehicleReference(String identification) {
        super("ExceptionVehicleReference " + identification);
    }
}
