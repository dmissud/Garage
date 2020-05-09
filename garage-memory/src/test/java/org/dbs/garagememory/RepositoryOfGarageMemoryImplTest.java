package org.dbs.garagememory;

import org.dbs.garage.application.service.ExceptionVehicleReference;
import org.dbs.garage.application.port.out.IRepositoryOfGarage;
import org.dbs.garage.application.port.out.UnknowGarage;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositoryOfGarageMemoryImplTest {

    private IRepositoryOfGarage repositoryOfGarageMemory;

    @BeforeEach
    void setUp() {
        repositoryOfGarageMemory = RepositoryOfGarageMemoryImpl.getInstance();
    }

    @Test
    void retrieveGarageByName() {
        try {
            Garage garage = repositoryOfGarageMemory.retrieveGarageByName("Garage 1");
            garage.registerVehicle(new Vehicle("ID_TEST_01", Marque.NEXUS));
            int nbVehicle = garage.giveNumberOfVehicule();

            Garage garage_bis = repositoryOfGarageMemory.retrieveGarageByName("Garage 1");
            int nbVehicle_bis = garage_bis.giveNumberOfVehicule();
            Assertions.assertThat(nbVehicle_bis).isNotEqualTo(nbVehicle);

        } catch (UnknowGarage | ExceptionVehicleReference unknowGarage) {
            unknowGarage.printStackTrace();
        }
    }
}