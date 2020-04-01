package org.dbs.garagememory;

import org.dbs.garage.*;

import java.util.Map;
import java.util.TreeMap;

public class RepositoryOfGarageMemory implements IRepositoryOfGarage {

    public static final String SUPER_TUTURE = "Super Tuture";
    public static final String PUTEAU = "Puteau";
    private static final String BELLE_TUTURE = "Belle Voiture de luxe";
    private static RepositoryOfGarageMemory repositoryOfGarageMemory = null;

    private Map<String, Garage> lstOfGarage;
    private int idChassis;

    private RepositoryOfGarageMemory() {
        this.lstOfGarage = new TreeMap<>();
        idChassis = 1;
        initMemoryDataBase();
    }

    private void initMemoryDataBase() {

        try {
            Garage garage = initGarageSupperTuture();
            this.lstOfGarage.put(garage.getName(), garage);
            garage = initGarageBelleTuture();
            this.lstOfGarage.put(garage.getName(), garage);
        } catch (Exception_Vehicule_Reference exception_vehicule_reference) {
            exception_vehicule_reference.printStackTrace();
        }

    }

    private Garage initGarageSupperTuture() throws Exception_Vehicule_Reference {
        Garage myGarage;
        myGarage = new Garage(SUPER_TUTURE, PUTEAU);
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Dacia));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Dacia));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Peugeot));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Renault));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Toyota));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Toyota));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Citroen));

        return myGarage;
    }

    private Garage initGarageBelleTuture() throws Exception_Vehicule_Reference {
        Garage myGarage;
        myGarage = new Garage(BELLE_TUTURE, PUTEAU);
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Mercedes));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Mercedes));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Tesla));
        myGarage.registerVehicle(new Vehicle("ID_"+idChassis++, Marque.Tesla));

        return myGarage;
    }

    public static IRepositoryOfGarage getInstance() {
        if (RepositoryOfGarageMemory.repositoryOfGarageMemory == null) {
            RepositoryOfGarageMemory.repositoryOfGarageMemory = new RepositoryOfGarageMemory();
        }
        return RepositoryOfGarageMemory.repositoryOfGarageMemory;
    }

    @Override
    public Garage retrieveGarageByName(String garageName) throws UnknowGarage {
        if (lstOfGarage.containsKey(garageName)) {
            return lstOfGarage.get(garageName);
        } else {
            throw new UnknowGarage(garageName);
        }
    }
}
