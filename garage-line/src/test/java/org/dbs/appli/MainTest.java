package org.dbs.appli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.application.port.in.IEnrichGarageStock;
import org.dbs.garage.application.port.in.RegisterGarageCmd;
import org.dbs.garage.application.port.out.GarageStockDesc;
import org.dbs.garage.application.port.out.IConsultGarageStock;
import org.dbs.garage.application.port.out.UnknowGarage;
import org.dbs.garage.application.service.ConsultGarageStockImpl;
import org.dbs.garage.application.service.EnrichGarageStockImpl;
import org.dbs.garage.application.port.out.GarageDesc;
import org.dbs.garage.application.port.in.RegisterVehicleCmd;
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
    private static final Logger logger = LogManager.getLogger(MainTest.class);

    @Test
    @DisplayName("CU Superviser Garage : Scénario consulter ensemble des garages")
    public void consultAllGarageIT() {

        List<GarageDesc> lstGaragesDesc = consultGarageStock.retrieveSupervisionOfGarage();

        assertThat(lstGaragesDesc.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("CU Superviser Garage : Ajouter voiture au plus petit stock")
    public void enrichLowGarageWithNewsCarsIT() {

        List<GarageDesc> lstGaragesDesc = consultGarageStock.retrieveSupervisionOfGarage();

        assertThat(lstGaragesDesc.size()).isEqualTo(2);
        assertThat(lstGaragesDesc.get(0).getNumberOfCars()).isEqualTo(7);
        assertThat(lstGaragesDesc.get(1).getNumberOfCars()).isEqualTo(4);

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

    @Test
    @DisplayName("CU Superviser Garage : Scénario enregistrer un nouveau garage")
    public void enrichWithNewGarage() {

        GarageStockDesc garages10StockDesc = null;
        try {
            garages10StockDesc = consultGarageStock.retrieveGarageStockByName("Garage 10");
        } catch (UnknowGarage unknowGarage) {
            logger.info(unknowGarage);
        }
        assertThat(garages10StockDesc).isNull();
        RegisterGarageCmd registerGarageCmd = new RegisterGarageCmd("Garage 10", "Location 1");
        enrichGarageStock.registerGarage(registerGarageCmd);
        try {
            garages10StockDesc = consultGarageStock.retrieveGarageStockByName("Garage 10");
        } catch (UnknowGarage unknowGarage) {
            unknowGarage.printStackTrace();
        }
        assertThat(garages10StockDesc).isNotNull();
    }

    @BeforeEach
    private void linkComponentOfApplication() {
        RepositoryOfGarageMemoryImpl repositoryOfGarageMemory =
                (RepositoryOfGarageMemoryImpl) RepositoryOfGarageMemoryImpl.getInstance();
        repositoryOfGarageMemory.initialize();
        consultGarageStock = new ConsultGarageStockImpl(repositoryOfGarageMemory);
        enrichGarageStock = new EnrichGarageStockImpl(repositoryOfGarageMemory);
    }

}