package org.dbs.garage.application.service;

public class ExceptionVehicleReference extends Exception {
    public ExceptionVehicleReference(String identification) {
        super("ExceptionVehicleReference " + identification);
    }
}
