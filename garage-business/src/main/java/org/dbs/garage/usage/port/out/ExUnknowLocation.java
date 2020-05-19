package org.dbs.garage.usage.port.out;

public class ExUnknowLocation extends Exception {
    public ExUnknowLocation(String locationName) {
        super("Location Unknow"+locationName);
    }
}
