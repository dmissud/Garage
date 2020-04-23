package org.dbs.garage.application;

import org.dbs.garage.domain.Garage;

import java.util.ArrayList;
import java.util.List;

public class ConsultGarageStockImpl implements IConsultGarageStock {
    private final IRepositoryOfGarage repositoryOfGarage;

    public ConsultGarageStockImpl(IRepositoryOfGarage repositoryOfGarage) {
        this.repositoryOfGarage = repositoryOfGarage;
    }

    @Override
    public List<GarageDescription> retrieveSupervisionOfGarage() {
        List<GarageDescription> lstDescOfGarage = new ArrayList<>();
        try {
            List<String> lstNameOfGarage = repositoryOfGarage.retrieveNameOfAllGarage();
            for (String nameOfGarage : lstNameOfGarage) {
                Garage aGarage = repositoryOfGarage.retrieveGarageByName(nameOfGarage);
                GarageDescription garageDescription =
                        new GarageDescription(
                                nameOfGarage,
                                aGarage.getLocation(),
                                aGarage.giveNumberOfVehicule());
                lstDescOfGarage.add(garageDescription);
            }
        } catch (UnknowGarage unknowGarage) {
            unknowGarage.printStackTrace();
        }
        return lstDescOfGarage;
    }
}
