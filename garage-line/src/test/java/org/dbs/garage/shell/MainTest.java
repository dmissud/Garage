package org.dbs.garage.shell;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.in.IManageGarageStock;
import org.dbs.garage.usage.port.in.RegisterGarageCmd;
import org.dbs.garage.usage.port.in.RegisterVehicleCmd;
import org.dbs.garage.usage.port.out.*;
import org.dbs.garage.usage.service.ConsultGarageStockImpl;
import org.dbs.garage.usage.service.ConsultLocationStockImpl;
import org.dbs.garage.usage.service.ManageGarageStockImpl;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.infra.memory.RepositoryOfGarageMemoryImpl;
import org.dbs.garage.infra.memory.RepositoryOfLocationMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test de bout en bout de l'application")
class MainTest {

    public IConsultGarageStock consultGarageStock = null;
    public IConsultLocationStock consultLocationStock = null;
    public IManageGarageStock enrichGarageStock = null;
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
        List<LocationDesc> locations = consultLocationStock.retrieveSupervisionOfLocation();
        int nbLocation = locations.size();
        int result = RandomUtils.nextInt(0, nbLocation);
        String nameOfGarage = RandomStringUtils.randomAlphanumeric(10);

        GarageStockDesc garagesStockDesc = null;
        try {
            garagesStockDesc = consultGarageStock.retrieveGarageStockByName(nameOfGarage);
        } catch (ExUnknowGarage exUnknowGarage) {
            logger.info(exUnknowGarage);
        }
        assertThat(garagesStockDesc).isNull();
        RegisterGarageCmd registerGarageCmd =
                new RegisterGarageCmd(nameOfGarage, locations.get(result).getLocationName());
        try {
            enrichGarageStock.registerGarage(registerGarageCmd);
            garagesStockDesc = consultGarageStock.retrieveGarageStockByName(nameOfGarage);
        } catch (ExUnknowGarage | ExUnknowLocation unknow) {
            logger.error(unknow);
        }
        assertThat(garagesStockDesc).isNotNull();
    }

    @BeforeEach
    private void linkComponentOfApplication() {
        RepositoryOfGarageMemoryImpl repositoryOfGarageMemory =
                (RepositoryOfGarageMemoryImpl) RepositoryOfGarageMemoryImpl.getInstance();
        repositoryOfGarageMemory.initialize();
        RepositoryOfLocationMemoryImpl repositoryOfLocationMemory =
                (RepositoryOfLocationMemoryImpl) RepositoryOfLocationMemoryImpl.getInstance();
        repositoryOfLocationMemory.initialize();
        consultGarageStock = new ConsultGarageStockImpl(repositoryOfGarageMemory);
        enrichGarageStock = new ManageGarageStockImpl(repositoryOfGarageMemory, repositoryOfLocationMemory);
        consultLocationStock = new ConsultLocationStockImpl(repositoryOfLocationMemory);
    }

}