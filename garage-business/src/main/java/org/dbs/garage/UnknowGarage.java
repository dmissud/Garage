package org.dbs.garage;

public class UnknowGarage extends Exception {
    public UnknowGarage(String garageName) {
        super("garage Unknow"+garageName);
    }
}
