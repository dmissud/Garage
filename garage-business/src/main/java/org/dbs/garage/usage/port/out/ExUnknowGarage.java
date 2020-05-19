package org.dbs.garage.usage.port.out;

public class ExUnknowGarage extends Exception {
    public ExUnknowGarage(String garageName) {
        super("garage Unknow"+garageName);
    }
}
