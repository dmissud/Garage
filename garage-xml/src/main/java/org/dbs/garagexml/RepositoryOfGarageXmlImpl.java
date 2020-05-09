package org.dbs.garagexml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.dbs.garage.application.port.out.IRepositoryOfGarage;
import org.dbs.garage.application.port.out.UnknowGarage;
import org.dbs.garage.domain.Garage;

public class RepositoryOfGarageXmlImpl implements IRepositoryOfGarage {

    private static RepositoryOfGarageXmlImpl repositoryOfGarageXml = null;
    private static final Logger logger = LogManager.getLogger(RepositoryOfGarageXmlImpl.class);
    private final DescriptionOfGaragesManager descriptionOfGaragesManager;
    private Map<String, GarageXMLManager> mapOfGarageXMLManager;


    private RepositoryOfGarageXmlImpl() {
        descriptionOfGaragesManager = new DescriptionOfGaragesManager();
        descriptionOfGaragesManager.load();
        mapOfGarageXMLManager = new HashMap<>();
        logger.info("RepositoryOfGarageXmlImpl Create");
    }

    public static IRepositoryOfGarage getInstance() {
        if (RepositoryOfGarageXmlImpl.repositoryOfGarageXml == null) {
            RepositoryOfGarageXmlImpl.repositoryOfGarageXml = new RepositoryOfGarageXmlImpl();
        }
        return RepositoryOfGarageXmlImpl.repositoryOfGarageXml;
    }


    public Garage retrieveGarageByName(String garageName) throws UnknowGarage {
        GarageXMLManager garageXMLManager = mapOfGarageXMLManager.get(garageName);
        if (garageXMLManager == null) {
            garageXMLManager = buildGarageXMLManager(garageName);
        }
        logger.debug(String.format("Garage %s find", garageName));

        return new Garage(garageXMLManager.getGarage());
    }

    private GarageXMLManager buildGarageXMLManager(String garageName) throws UnknowGarage {
        GarageXMLManager garageXMLManager;
        String fileNameForAGarage = this.descriptionOfGaragesManager.retrieveFileNameForGarageByName(garageName);
        if (fileNameForAGarage == null) {
            logger.debug(String.format("Unknow Garage %s", garageName));
            throw new UnknowGarage(garageName);
        }
        garageXMLManager = new GarageXMLManager(fileNameForAGarage);
        this.mapOfGarageXMLManager.put(garageName, garageXMLManager);
        return garageXMLManager;
    }

    public List<String> retrieveNameOfGarageByLocation(String locationName) {
        List<String> lstNameGarageAtLocation = new ArrayList<>();
        for (String nameGarage : this.retrieveNameOfAllGarage()) {
            GarageXMLManager garageXMLManager = mapOfGarageXMLManager.get(nameGarage);
            if (locationName.equals(garageXMLManager.retrieveLocation())) {
                lstNameGarageAtLocation.add(nameGarage);
            }
        }
        return lstNameGarageAtLocation;
    }

    public List<String> retrieveNameOfAllGarage() {
        return new ArrayList<>(this.mapOfGarageXMLManager.keySet());
    }

    @Override
    public void store(Garage garage) {
        GarageXMLManager garageXMLManager = mapOfGarageXMLManager.get(garage.getName());
        if (garageXMLManager == null) {
            String fileName = this.descriptionOfGaragesManager.addGarage(garage.getName());
            garageXMLManager = new GarageXMLManager(garage, fileName);
            this.mapOfGarageXMLManager.put(garage.getName(), garageXMLManager);
        }
        garageXMLManager.store(garage);
        descriptionOfGaragesManager.store();

        logger.info(String.format("RepositoryOfGarageXmlImpl Store : %s", garage.getName()));
    }

}
