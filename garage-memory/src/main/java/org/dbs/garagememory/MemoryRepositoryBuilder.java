package org.dbs.garagememory;

import org.dbs.garage.application.Exception_Vehicule_Reference;
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

    Garage initGarageSupperTuture() throws Exception_Vehicule_Reference {
        Garage myGarage;
        myGarage = new Garage(SUPER_TUTURE, PUTEAU);
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Dacia));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Dacia));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Peugeot));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Renault));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Toyota));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Toyota));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Citroen));

        return myGarage;
    }

    Garage initGarageBelleTuture() throws Exception_Vehicule_Reference {
        Garage myGarage;
        myGarage = new Garage(BELLE_TUTURE, PUTEAU);
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Mercedes));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Mercedes));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Tesla));
        myGarage.registerVehicle(new Vehicle("ID_" + idChassis++, Marque.Tesla));

        return myGarage;
    }

    public void enrich(Map<String, Garage> lstOfGarage) throws Exception_Vehicule_Reference {
        Garage garage = this.initGarageSupperTuture();
        lstOfGarage.put(garage.getName(), garage);
        garage = this.initGarageBelleTuture();
        lstOfGarage.put(garage.getName(), garage);
    }
}