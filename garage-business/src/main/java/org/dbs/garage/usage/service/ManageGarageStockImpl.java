package org.dbs.garage.usage.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.in.*;
import org.dbs.garage.usage.port.out.IRepositoryOfGarage;
import org.dbs.garage.usage.port.out.IRepositoryOfLocation;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.port.out.ExUnknowLocation;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Location;
import org.dbs.garage.domain.Vehicle;

import java.util.List;

public class ManageGarageStockImpl implements IManageGarageStock {

    private IRepositoryOfGarage repositoryOfGarage;
    private IRepositoryOfLocation repositoryOfLocation;

    private static final Logger logger = LogManager.getLogger(ManageGarageStockImpl.class);

    public ManageGarageStockImpl(IRepositoryOfGarage repositoryOfGarage,
                                 IRepositoryOfLocation repositoryOfLocation) {
        this.repositoryOfGarage = repositoryOfGarage;
        this.repositoryOfLocation = repositoryOfLocation;
    }

    public ManageGarageStockImpl() {
        super();
    }

    @Override
    public void registerVehicleToGarage(List<RegisterVehicleCmd> lstVehicleToRegisterCmd) {
        for(RegisterVehicleCmd aVehicleToRegisterCmd: lstVehicleToRegisterCmd) {
            try {
                Garage garage = repositoryOfGarage.retrieveGarageByName(aVehicleToRegisterCmd.getNameOfGarage());
                garage.registerVehicle(new Vehicle(aVehicleToRegisterCmd.getIdChassis(),
                        aVehicleToRegisterCmd.getMarque()));
                repositoryOfGarage.store(garage);
            } catch (ExUnknowGarage | ExceptionVehicleReference ex) {
                logger.error(ex);
            }
        }
    }

    public void setRepositoryOfGarage(IRepositoryOfGarage repositoryOfGarage) {
        this.repositoryOfGarage = repositoryOfGarage;
    }

    public void setRepositoryOfLocation(IRepositoryOfLocation repositoryOfLocation) {
        this.repositoryOfLocation = repositoryOfLocation;
    }

    @Override
    public void registerGarage(RegisterGarageCmd aNewGarageCmd) throws ExUnknowLocation {
        Location location = repositoryOfLocation.retrieveLocationByName(aNewGarageCmd.getLocation());
        Garage garage = new Garage(aNewGarageCmd.getName(), location.getName());
        repositoryOfGarage.store(garage);
    }

    @Override
    public void unRegisterGarage(UnregisterGarageCmd unregisterGarageCmd) throws ExUnknowGarage, ExGarageGotVehicles {
        Garage garage = repositoryOfGarage.retrieveGarageByName(unregisterGarageCmd.getName());
        if (garage.giveNumberOfVehicule() == 0) {
            repositoryOfGarage.delete(garage);
        } else {
            throw new ExGarageGotVehicles(garage.getName(), garage.giveNumberOfVehicule());
        }
    }
}
