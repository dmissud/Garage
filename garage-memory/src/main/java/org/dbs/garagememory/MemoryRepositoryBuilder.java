package org.dbs.garagememory;

import org.dbs.garage.application.ExceptionVehicleReference;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;

import java.util.Map;

public class MemoryRepositoryBuilder {
    public static final String SUPER_TUTURE = "Super Tuture";
    public static final String PUTEAU = "Puteau";
    static final String BELLE_TUTURE = "Belle Voiture de luxe";
    private int idChassis;

    public MemoryRepositoryBuilder() {
        this.idChassis = 10000;
    }

    Garage initGarageSupperTuture() throws ExceptionVehicleReference {
        Garage myGarage;
        myGarage = new Garage(SUPER_TUTURE, PUTEAU);
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.DACIA));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.DACIA));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.PEUGEOT));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.RENAULT));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.TOYOTA));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.TOYOTA));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.CITROEN));

        return myGarage;
    }

    Garage initGarageBelleTuture() throws ExceptionVehicleReference {
        Garage myGarage;
        myGarage = new Garage(BELLE_TUTURE, PUTEAU);
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.MERCEDES));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.MERCEDES));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.TESLA));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.TESLA));

        return myGarage;
    }

    public void enrich(Map<String, Garage> lstOfGarage) throws ExceptionVehicleReference {
        Garage garage = this.initGarageSupperTuture();
        lstOfGarage.put(garage.getName(), garage);
        garage = this.initGarageBelleTuture();
        lstOfGarage.put(garage.getName(), garage);
    }
}