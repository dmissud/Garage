package org.dbs.garage.application;

public class UnknowGarage extends Exception {
    public UnknowGarage(String garageName) {
        super("garage Unknow"+garageName);
    }
}
