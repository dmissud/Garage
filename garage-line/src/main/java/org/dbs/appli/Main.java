package org.dbs.appli;

import org.dbs.garage.application.*;
import org.dbs.garagememory.RepositoryOfGarageMemoryImpl;

import java.util.List;

public class Main {

    public static IConsultGarageStock consultGarageStock = null;
    public static final String SUPER_TUTURE = "Super Tuture";
    public static final String PUTEAU = "Puteau";

    public static void main(String[] args) {

        linkComponentOfApplication();

        List<GarageDescription> lstGaragesDesc; ;
        lstGaragesDesc = consultGarageStock.retrieveSupervisionOfGarage();

        for (GarageDescription aGargeDesc: lstGaragesDesc) {
            System.out.println(aGargeDesc);
        }
    }

    private static void linkComponentOfApplication() {
        consultGarageStock = new ConsultGarageStockImpl(RepositoryOfGarageMemoryImpl.getInstance());
    }
}
