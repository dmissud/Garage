package org.dbs.garage.application;

public class UnknowLocation extends Exception {
    public UnknowLocation(String locationName) {
        super("location Unknow"+locationName);
    }
}
