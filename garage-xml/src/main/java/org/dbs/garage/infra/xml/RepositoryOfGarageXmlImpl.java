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
    private final DaoXmlDescriptionOfGarages daoXmlDescriptionOfGarages;
    private final Map<String, DaoXmlGarage> mapOfGarageXMLManager;


    private RepositoryOfGarageXmlImpl() {
        daoXmlDescriptionOfGarages = new DaoXmlDescriptionOfGarages();
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
        DaoXmlGarage daoXmlGarage = mapOfGarageXMLManager.get(garageName);
        if (daoXmlGarage == null) {
            daoXmlGarage = buildGarageXMLManager(garageName);
        }
        logger.debug(String.format("Garage %s find", garageName));

        return new Garage(daoXmlGarage.getGarage());
    }

    private DaoXmlGarage buildGarageXMLManager(String garageName) throws ExUnknowGarage {
        DaoXmlGarage daoXmlGarage;
        String fileNameForAGarage = this.daoXmlDescriptionOfGarages.retrieveFileNameForGarageByName(garageName);
        if (fileNameForAGarage == null) {
            logger.debug(String.format("Unknow Garage %s", garageName));
            throw new ExUnknowGarage(garageName);
        }
        daoXmlGarage = new DaoXmlGarage(fileNameForAGarage);
        this.mapOfGarageXMLManager.put(garageName, daoXmlGarage);
        return daoXmlGarage;
    }

    public List<String> retrieveNameOfGarageByLocation(String locationName) {
        List<String> lstNameGarageAtLocation = new ArrayList<>();
        for (String nameGarage : this.retrieveNameOfAllGarage()) {
            DaoXmlGarage daoXmlGarage = mapOfGarageXMLManager.get(nameGarage);
            if (locationName.equals(daoXmlGarage.retrieveLocationName())) {
                lstNameGarageAtLocation.add(nameGarage);
            }
        }
        return lstNameGarageAtLocation;
    }

    public List<String> retrieveNameOfAllGarage() {
        return (this.daoXmlDescriptionOfGarages.retrieveNameOfAllgarage());
    }

    @Override
    public void store(Garage garage) {
        DaoXmlGarage daoXmlGarage = mapOfGarageXMLManager.get(garage.getName());
        if (daoXmlGarage == null) {
            String fileName = this.daoXmlDescriptionOfGarages.addGarage(garage.getName());
            daoXmlGarage = new DaoXmlGarage(garage, fileName);
            this.mapOfGarageXMLManager.put(garage.getName(), daoXmlGarage);
        }
        daoXmlGarage.store(garage);
        daoXmlDescriptionOfGarages.store();

        logger.info(String.format("RepositoryOfGarageXmlImpl Store : %s", garage.getName()));
    }

    @Override
    public void delete(Garage garage) throws ExUnknowGarage {
        DaoXmlGarage daoXmlGarage = mapOfGarageXMLManager.get(garage.getName());
        if (daoXmlGarage == null) {
            throw new ExUnknowGarage(garage.getName());
        }
        mapOfGarageXMLManager.remove(garage.getName());
        String result = this.daoXmlDescriptionOfGarages.removeGarage(garage.getName());
        if (result != null) {
            daoXmlGarage.deleteXmlFile();
            this.daoXmlDescriptionOfGarages.store();
        } else {
            logger.error(
                    String.format("pas present dans la liste donc destruction impossible de %s", garage.getName()));
        }

    }

}
