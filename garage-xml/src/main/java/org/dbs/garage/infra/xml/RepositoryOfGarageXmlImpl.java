package org.dbs.garage.infra.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.port.out.IRepositoryOfGarage;
import org.dbs.garage.domain.Garage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryOfGarageXmlImpl implements IRepositoryOfGarage {

    private static RepositoryOfGarageXmlImpl repositoryOfGarageXml = null;
    private static final Logger logger = LogManager.getLogger(RepositoryOfGarageXmlImpl.class);
    private final DescriptionOfGaragesManager descriptionOfGaragesManager;
    private final Map<String, GarageXMLManager> mapOfGarageXMLManager;


    private RepositoryOfGarageXmlImpl() {
        descriptionOfGaragesManager = new DescriptionOfGaragesManager();
        mapOfGarageXMLManager = new HashMap<>();
        logger.info("RepositoryOfGarageXmlImpl Create");
    }

    public static IRepositoryOfGarage getInstance() {
        if (RepositoryOfGarageXmlImpl.repositoryOfGarageXml == null) {
            RepositoryOfGarageXmlImpl.repositoryOfGarageXml = new RepositoryOfGarageXmlImpl();
        }
        return RepositoryOfGarageXmlImpl.repositoryOfGarageXml;
    }


    public Garage retrieveGarageByName(String garageName) throws ExUnknowGarage {
        GarageXMLManager garageXMLManager = mapOfGarageXMLManager.get(garageName);
        if (garageXMLManager == null) {
            garageXMLManager = buildGarageXMLManager(garageName);
        }
        logger.debug(String.format("Garage %s find", garageName));

        return new Garage(garageXMLManager.getGarage());
    }

    private GarageXMLManager buildGarageXMLManager(String garageName) throws ExUnknowGarage {
        GarageXMLManager garageXMLManager;
        String fileNameForAGarage = this.descriptionOfGaragesManager.retrieveFileNameForGarageByName(garageName);
        if (fileNameForAGarage == null) {
            logger.debug(String.format("Unknow Garage %s", garageName));
            throw new ExUnknowGarage(garageName);
        }
        garageXMLManager = new GarageXMLManager(fileNameForAGarage);
        this.mapOfGarageXMLManager.put(garageName, garageXMLManager);
        return garageXMLManager;
    }

    public List<String> retrieveNameOfGarageByLocation(String locationName) {
        List<String> lstNameGarageAtLocation = new ArrayList<>();
        for (String nameGarage : this.retrieveNameOfAllGarage()) {
            GarageXMLManager garageXMLManager = mapOfGarageXMLManager.get(nameGarage);
            if (locationName.equals(garageXMLManager.retrieveLocationName())) {
                lstNameGarageAtLocation.add(nameGarage);
            }
        }
        return lstNameGarageAtLocation;
    }

    public List<String> retrieveNameOfAllGarage() {
        return (this.descriptionOfGaragesManager.retrieveNameOfAllgarage());
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

    @Override
    public void delete(Garage garage) throws ExUnknowGarage {
        GarageXMLManager garageXMLManager = mapOfGarageXMLManager.get(garage.getName());
        if (garageXMLManager == null) {
            throw new ExUnknowGarage(garage.getName());
        }
        mapOfGarageXMLManager.remove(garage.getName());
        String result = this.descriptionOfGaragesManager.removeGarage(garage.getName());
        if (result != null) {
            garageXMLManager.deleteXmlFile();
            this.descriptionOfGaragesManager.store();
        } else {
            logger.error(
                    String.format("pas present dans la liste donc destruction impossible de %s", garage.getName()));
        }

    }

}
