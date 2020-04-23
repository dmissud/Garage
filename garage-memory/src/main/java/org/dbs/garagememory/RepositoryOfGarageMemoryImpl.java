package org.dbs.garagememory;

import org.dbs.garage.application.Exception_Vehicule_Reference;
import org.dbs.garage.application.IRepositoryOfGarage;
import org.dbs.garage.application.UnknowGarage;
import org.dbs.garage.application.UnknowLocation;
import org.dbs.garage.domain.Garage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RepositoryOfGarageMemoryImpl implements IRepositoryOfGarage {

    private static RepositoryOfGarageMemoryImpl repositoryOfGarageMemory = null;
    private final MemoryRepositoryBuilder memoryRepositoryBuilder = new MemoryRepositoryBuilder();

    private Map<String, Garage> lstOfGarage;

    private RepositoryOfGarageMemoryImpl() {
        this.lstOfGarage = new TreeMap<>();
        initMemoryDataBase();
    }

    private void initMemoryDataBase() {

        try {
            memoryRepositoryBuilder.enrich(this.lstOfGarage);
        } catch (Exception_Vehicule_Reference exceptionVehicleReference) {
            exceptionVehicleReference.printStackTrace();
        }

    }

    public static IRepositoryOfGarage getInstance() {
        if (RepositoryOfGarageMemoryImpl.repositoryOfGarageMemory == null) {
            RepositoryOfGarageMemoryImpl.repositoryOfGarageMemory = new RepositoryOfGarageMemoryImpl();
        }
        return RepositoryOfGarageMemoryImpl.repositoryOfGarageMemory;
    }

    @Override
    public Garage retrieveGarageByName(String garageName) throws UnknowGarage {
        if (lstOfGarage.containsKey(garageName)) {
            return lstOfGarage.get(garageName);
        } else {
            throw new UnknowGarage(garageName);
        }
    }

    @Override
    public List<String> retrieveNameOfGarageByLocation(String locationName) throws UnknowLocation {
        return new ArrayList<>();
    }

    @Override
    public List<String> retrieveNameOfAllGarage() {
        return new ArrayList<>(this.lstOfGarage.keySet());
    }
}
