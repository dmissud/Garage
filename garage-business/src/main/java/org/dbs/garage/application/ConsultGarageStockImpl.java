package org.dbs.garage.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.domain.Garage;

import java.util.ArrayList;
import java.util.List;

public class ConsultGarageStockImpl implements IConsultGarageStock {
    private final IRepositoryOfGarage repositoryOfGarage;

    private static final Logger logger = LogManager.getLogger(ConsultGarageStockImpl.class);

    public ConsultGarageStockImpl(IRepositoryOfGarage repositoryOfGarage) {
        this.repositoryOfGarage = repositoryOfGarage;
    }

    @Override
    public List<GarageDesc> retrieveSupervisionOfGarage() {
        logger.info("retrieveSupervisionOfGarage");

        List<GarageDesc> lstDescOfGarage = new ArrayList<>();
        try {
            List<String> lstNameOfGarage = repositoryOfGarage.retrieveNameOfAllGarage();
            for (String nameOfGarage : lstNameOfGarage) {
                Garage aGarage = repositoryOfGarage.retrieveGarageByName(nameOfGarage);
                GarageDesc garageDescription =
                        new GarageDesc(
                                nameOfGarage,
                                aGarage.getLocation(),
                                aGarage.giveNumberOfVehicule());
                lstDescOfGarage.add(garageDescription);
            }
        } catch (UnknowGarage unknowGarage) {
            logger.error("{}", unknowGarage);
        }

        return lstDescOfGarage;
    }

    @Override
    public List<GarageDesc> retrieveGarageWithLowStock(int minimalStock) {
        logger.info("retrieveSupervisionOfGarage");

        List<GarageDesc> lstDescOfGarage = new ArrayList<>();
        try {
            List<String> lstNameOfGarage = repositoryOfGarage.retrieveNameOfAllGarage();
            for (String nameOfGarage : lstNameOfGarage) {
                Garage aGarage = repositoryOfGarage.retrieveGarageByName(nameOfGarage);
                GarageDesc garageDescription =
                        new GarageDesc(
                                nameOfGarage,
                                aGarage.getLocation(),
                                aGarage.giveNumberOfVehicule());
                if (aGarage.giveNumberOfVehicule() <= minimalStock) {
                    lstDescOfGarage.add(garageDescription);
                }
            }
        } catch (UnknowGarage unknowGarage) {
            logger.error("{}", unknowGarage);
        }

        return lstDescOfGarage;
    }
}
