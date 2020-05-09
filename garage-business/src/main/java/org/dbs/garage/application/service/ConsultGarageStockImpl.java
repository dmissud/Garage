package org.dbs.garage.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.application.port.out.*;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            logger.error(unknowGarage.getMessage());
        }

        return lstDescOfGarage;
    }

    @Override
    public List<GarageDesc> retrieveGarageWithLowStock(int minimalStock) {
        logger.info("retrieveGarageWithLowStock");

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
            logger.error(unknowGarage.getMessage());
        }

        return lstDescOfGarage;
    }

    @Override
    public GarageStockDesc retrieveGarageStockByName(String nameOfGarage) throws UnknowGarage {
        logger.info("retrieveGarageStockByName");
        Garage aGarage = repositoryOfGarage.retrieveGarageByName(nameOfGarage);
        GarageStockDesc garageStockDesc =
                new GarageStockDesc(new GarageDesc(aGarage.getName(),
                        aGarage.getLocation(),
                        aGarage.giveNumberOfVehicule()));
        Map<String, Vehicle> lstVehicles = aGarage.giveLstVehicleInGarage();
        for (Vehicle vehicle: lstVehicles.values()) {
            garageStockDesc.addVehicleDesc(new VehicleDesc(vehicle.identification(), vehicle.getMarque()));
        }
        return garageStockDesc;
    }
}
