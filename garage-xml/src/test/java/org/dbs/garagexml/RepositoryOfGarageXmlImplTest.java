package org.dbs.garagexml;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.dbs.garage.application.port.out.IRepositoryOfGarage;

import org.dbs.garage.application.port.out.UnknowGarage;
import org.dbs.garage.application.service.ExceptionVehicleReference;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositoryOfGarageXmlImplTest {

    private IRepositoryOfGarage repositoryOfGarageXml;

    @BeforeEach
    void setUp() {
        repositoryOfGarageXml = RepositoryOfGarageXmlImpl.getInstance();
    }

    @Test
    void retrieveGarageByName() {
        try {
            Garage garage = repositoryOfGarageXml.retrieveGarageByName("Garage 1");
            garage.registerVehicle(new Vehicle("ID_TEST_01", Marque.NEXUS));
            int nbVehicle = garage.giveNumberOfVehicule();

            Garage garage_bis = repositoryOfGarageXml.retrieveGarageByName("Garage 1");
            int nbVehicle_bis = garage_bis.giveNumberOfVehicule();
            Assertions.assertThat(nbVehicle_bis).isNotEqualTo(nbVehicle);

        } catch (UnknowGarage | ExceptionVehicleReference unknowGarage) {
            unknowGarage.printStackTrace();
        }
    }

    @Test
    void createAGarage() {
        try {
            String nameOfGarage = RandomStringUtils.randomAlphanumeric(10);
            Garage garage = new Garage(nameOfGarage, RandomStringUtils.randomAlphanumeric(10));
            repositoryOfGarageXml.store(garage);
            Garage newGarage = repositoryOfGarageXml.retrieveGarageByName(nameOfGarage);
            Assertions.assertThat(garage).isEqualTo(newGarage);
        } catch (UnknowGarage unknowGarage) {
            Assertions.fail(unknowGarage.getMessage());
        }
    }

    @Test
    void store() {
        try {
            Garage garage = repositoryOfGarageXml.retrieveGarageByName("Garage 1");
            String location = RandomStringUtils.randomAlphanumeric(10);
            garage.changeLocation(location);
            repositoryOfGarageXml.store(garage);
            garage = repositoryOfGarageXml.retrieveGarageByName("Garage 1");
            Assertions.assertThat(garage.getLocation()).isEqualTo(location);
        } catch (UnknowGarage unknowGarage) {
            unknowGarage.printStackTrace();
        }
    }
}