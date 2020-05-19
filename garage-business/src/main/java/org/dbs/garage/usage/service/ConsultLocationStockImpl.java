package org.dbs.garage.usage.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.out.*;
import org.dbs.garage.domain.Location;

import java.util.ArrayList;
import java.util.List;

public class ConsultLocationStockImpl implements IConsultLocationStock {

    private static final Logger logger = LogManager.getLogger(ConsultLocationStockImpl.class);
    private final IRepositoryOfLocation repositoryOfLocation;

    public ConsultLocationStockImpl(IRepositoryOfLocation repositoryOfLocation) {
        this.repositoryOfLocation = repositoryOfLocation;
    }

    @Override
    public List<LocationDesc> retrieveSupervisionOfLocation() {
        logger.info("retrieveSupervisionOfGarage");

        List<LocationDesc> lstLocationDesc = new ArrayList<>();
        try {
            List<String> lstNameOfLocation = this.repositoryOfLocation.retrieveNameOfAllLocation();
            for (String nameOfLocation : lstNameOfLocation) {
                Location aLocation = repositoryOfLocation.retrieveLocationByName(nameOfLocation);
                LocationDesc locationDescription = new LocationDesc(nameOfLocation, aLocation.getAdresse());
                lstLocationDesc.add(locationDescription);
            }
        } catch (ExUnknowLocation exUnknowLocation) {
            logger.error(exUnknowLocation.getMessage());
        }

        return lstLocationDesc;
    }
}
