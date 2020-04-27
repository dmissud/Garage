package org.dbs.garage.application;

import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Vehicle;

import java.util.List;

public class EnrichGarageStockImpl implements IEnrichGarageStock {

    private final IRepositoryOfGarage repositoryOfGarage;

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
            } catch (UnknowGarage unknowGarage) {
                unknowGarage.printStackTrace();
            } catch (ExceptionVehicleReference exceptionVehicleReference) {
                exceptionVehicleReference.printStackTrace();
            }
        }
    }
}
