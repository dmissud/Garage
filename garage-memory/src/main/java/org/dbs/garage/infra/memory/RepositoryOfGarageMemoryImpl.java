package org.dbs.garage.infra.memory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.service.ExceptionVehicleReference;
import org.dbs.garage.usage.port.out.IRepositoryOfGarage;
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
    public Garage retrieveGarageByName(String garageName) throws ExUnknowGarage {
        if (lstOfGarage.containsKey(garageName)) {
            logger.debug(String.format("%s retrieved", garageName));
            return new Garage(lstOfGarage.get(garageName));
        } else {
            logger.debug(String.format("%s Unknow", garageName));
            throw new ExUnknowGarage(garageName);
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
        logger.debug("retrieveNameOfGarageByLocation");
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
            logger.debug(String.format("%s Updated", garage.getName()));
        } else {
            this.lstOfGarage.put(garage.getName(), new Garage(garage));
            logger.debug(String.format("%s Created", garage.getName()));
        }
    }

    @Override
    public void delete(Garage garage) throws ExUnknowGarage {
        lstOfGarage.remove(garage.getName());
        logger.debug(String.format("%s Deleted", garage.getName()));
    }

    public void initialize() {
        this.lstOfGarage = new TreeMap<>();
        try {
            memoryRepositoryBuilder.enrich(this.lstOfGarage);
            logger.debug("RepositoryOfGarageMemory initialized");
        } catch (ExceptionVehicleReference exceptionVehicleReference) {
            logger.error(exceptionVehicleReference);
        }
    }
}
