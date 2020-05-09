package org.dbs.garage.application.port.out;

public class UnknowGarage extends Exception {
    public UnknowGarage(String garageName) {
        super("garage Unknow"+garageName);
    }
}
