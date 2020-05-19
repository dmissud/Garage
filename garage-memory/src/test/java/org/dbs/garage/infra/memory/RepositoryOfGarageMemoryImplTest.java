package org.dbs.garage.infra.memory;

import org.dbs.garage.usage.service.ExceptionVehicleReference;
import org.dbs.garage.usage.port.out.IRepositoryOfGarage;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RepositoryOfGarageMemoryImplTest {

    private IRepositoryOfGarage repositoryOfGarageMemory;

    @BeforeEach
    void setUp() {
        repositoryOfGarageMemory = RepositoryOfGarageMemoryImpl.getInstance();
    }

    @Test
    @DisplayName("Recherche de Garage par le nom")
    void retrieveGarageByName() {
        try {
            Garage garage = repositoryOfGarageMemory.retrieveGarageByName("Garage 1");
            garage.registerVehicle(new Vehicle("ID_TEST_01", Marque.NEXUS));
            int nbVehicle = garage.giveNumberOfVehicule();

            Garage garage_bis = repositoryOfGarageMemory.retrieveGarageByName("Garage 1");
            int nbVehicle_bis = garage_bis.giveNumberOfVehicule();
            Assertions.assertThat(nbVehicle_bis).isNotEqualTo(nbVehicle);

        } catch (ExUnknowGarage | ExceptionVehicleReference exUnknowGarage) {
            exUnknowGarage.printStackTrace();
        }
    }
}