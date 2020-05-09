package org.dbs.garage.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.application.port.in.IEnrichGarageStock;
import org.dbs.garage.application.port.in.RegisterGarageCmd;
import org.dbs.garage.application.port.in.RegisterVehicleCmd;
import org.dbs.garage.application.port.out.IRepositoryOfGarage;
import org.dbs.garage.application.port.out.UnknowGarage;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Vehicle;

import java.util.List;

public class EnrichGarageStockImpl implements IEnrichGarageStock {

    private final IRepositoryOfGarage repositoryOfGarage;
    private static final Logger logger = LogManager.getLogger(EnrichGarageStockImpl.class);
    public EnrichGarageStockImpl(IRepositoryOfGarage repositoryOfGarage) {
        this.repositoryOfGarage = repositoryOfGarage;
    }

    @Override
    public void registerVehicleToGarage(List<RegisterVehicleCmd> lstVehicleToRegisterCmd) {
        for(RegisterVehicleCmd aVehicleToRegisterCmd: lstVehicleToRegisterCmd) {
            try {
                Garage garage = repositoryOfGarage.retrieveGarageByName(aVehicleToRegisterCmd.getNameOfGarage());
                garage.registerVehicle(new Vehicle(aVehicleToRegisterCmd.getIdChassis(),
                        aVehicleToRegisterCmd.getMarque()));
                repositoryOfGarage.store(garage);
            } catch (UnknowGarage | ExceptionVehicleReference ex) {
                logger.error(ex);
            }
        }
    }

    @Override
    public void registerGarage(RegisterGarageCmd aNewGarageCmd) {
        Garage garage = new Garage(aNewGarageCmd.getName(), aNewGarageCmd.getLocation());
        repositoryOfGarage.store(garage);
    }
}
