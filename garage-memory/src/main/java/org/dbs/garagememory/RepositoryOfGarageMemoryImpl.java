package org.dbs.garagememory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.application.service.ExceptionVehicleReference;
import org.dbs.garage.application.port.out.IRepositoryOfGarage;
import org.dbs.garage.application.port.out.UnknowGarage;
import org.dbs.garage.domain.Garage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RepositoryOfGarageMemoryImpl implements IRepositoryOfGarage {

    private static RepositoryOfGarageMemoryImpl repositoryOfGarageMemory = null;
    private final MemoryRepositoryBuilder memoryRepositoryBuilder = new MemoryRepositoryBuilder();
    private static final Logger logger = LogManager.getLogger(RepositoryOfGarageMemoryImpl.class);

    private Map<String, Garage> lstOfGarage;

    private RepositoryOfGarageMemoryImpl() {
        initialize();
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
            return new Garage(lstOfGarage.get(garageName));
        } else {
            throw new UnknowGarage(garageName);
        }
    }

    @Override
    public List<String> retrieveNameOfGarageByLocation(String locationName) {
        List<String> lstNameGarageAtLocation = new ArrayList<>();
        for(Garage garage:this.lstOfGarage.values()) {
            if (garage.getName().equals(locationName)) {
                lstNameGarageAtLocation.add(garage.getName());
            }
        }
        return lstNameGarageAtLocation;
    }

    @Override
    public List<String> retrieveNameOfAllGarage() {
        return new ArrayList<>(this.lstOfGarage.keySet());
    }

    @Override
    public void store(Garage garage) {
        if (lstOfGarage.containsKey(garage.getName())) {
            this.lstOfGarage.replace(garage.getName(), new Garage(garage));
        } else {
            this.lstOfGarage.put(garage.getName(), new Garage(garage));
        }
    }

    public void initialize() {
        this.lstOfGarage = new TreeMap<>();
        try {
            memoryRepositoryBuilder.enrich(this.lstOfGarage);
        } catch (ExceptionVehicleReference exceptionVehicleReference) {
            logger.error(exceptionVehicleReference);
        }
    }
}
