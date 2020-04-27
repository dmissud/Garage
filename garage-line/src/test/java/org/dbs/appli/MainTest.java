package org.dbs.appli;

import org.dbs.garage.application.*;
import org.dbs.garage.domain.Marque;
import org.dbs.garagememory.RepositoryOfGarageMemoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MainTest {

    public IConsultGarageStock consultGarageStock = null;
    public IEnrichGarageStock enrichGarageStock = null;

    @Test
    @DisplayName("CU Superviser Garage : Scénario consulter ensemble des garages")
    public void consultAllGarageIT() {

        List<GarageDesc> lstGaragesDesc = consultGarageStock.retrieveSupervisionOfGarage();

        assertThat(lstGaragesDesc.size()).isEqualTo(2);
        assertThat(lstGaragesDesc.get(0).getNumberOfCars()).isEqualTo(4);
        assertThat(lstGaragesDesc.get(1).getNumberOfCars()).isEqualTo(7);
    }

    @Test
    @DisplayName("CU Superviser Garage : Ajouter voiture au plus petit stock")
    public void enrichLowGarageWithNewsCarsIT() {

        List<GarageDesc> lstGaragesDesc = consultGarageStock.retrieveSupervisionOfGarage();

        assertThat(lstGaragesDesc.size()).isEqualTo(2);
        assertThat(lstGaragesDesc.get(0).getNumberOfCars()).isEqualTo(4);
        assertThat(lstGaragesDesc.get(1).getNumberOfCars()).isEqualTo(7);

        List<GarageDesc> garageWithLowStock = consultGarageStock.retrieveGarageWithLowStock(5);

        assertThat(garageWithLowStock.size()).isEqualTo(1);
        assertThat(garageWithLowStock.get(0).getNumberOfCars()).isEqualTo(4);

        List<RegisterVehicleCmd> lstVehicleToRegister = new ArrayList<>();
        String nameGarageToEnrich = garageWithLowStock.get(0).getName();
        lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_40000", Marque.DACIA));
        lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_40012", Marque.RENAULT));
        lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_40569", Marque.MERCEDES));
        lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_46709", Marque.OPEL));

        enrichGarageStock.registerVehicleToGarage(lstVehicleToRegister);

        garageWithLowStock = consultGarageStock.retrieveGarageWithLowStock(5);

        assertThat(garageWithLowStock.size()).isEqualTo(0);

    }

    @BeforeEach
    private void linkComponentOfApplication() {
        consultGarageStock = new ConsultGarageStockImpl(RepositoryOfGarageMemoryImpl.getInstance());
        enrichGarageStock = new EnrichGarageStockImpl(RepositoryOfGarageMemoryImpl.getInstance());
    }

}