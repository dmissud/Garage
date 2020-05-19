package org.dbs.garage.infra.xml;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.port.out.IRepositoryOfGarage;
import org.dbs.garage.usage.port.out.IRepositoryOfLocation;
import org.dbs.garage.usage.service.ExceptionVehicleReference;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class RepositoryOfGarageXmlImplTest {

    private IRepositoryOfGarage repositoryOfGarageXml;
    private IRepositoryOfLocation repositoryOfLocationXml;

    @BeforeEach
    void setUp() {
        repositoryOfLocationXml = RepositoryOfLocationXmlImpl.getInstance();
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

        } catch (ExUnknowGarage | ExceptionVehicleReference exUnknowGarage) {
            exUnknowGarage.printStackTrace();
        }
    }

    @Test
    void createAGarage() {
        try {
            List<String> locations = repositoryOfLocationXml.retrieveNameOfAllLocation();
            int nbLocation = locations.size();
            int result = RandomUtils.nextInt(0, nbLocation);
            String nameOfGarage = RandomStringUtils.randomAlphanumeric(10);
            Garage garage = new Garage(nameOfGarage, locations.get(result));
            repositoryOfGarageXml.store(garage);
            Garage newGarage = repositoryOfGarageXml.retrieveGarageByName(nameOfGarage);
            Assertions.assertThat(garage).isEqualTo(newGarage);
        } catch (ExUnknowGarage exUnknowGarage) {
            Assertions.fail(exUnknowGarage.getMessage());
        }
    }

    @Test
    void store() {
        try {
            Garage garage = repositoryOfGarageXml.retrieveGarageByName("Garage 1");
            String location = RandomStringUtils.randomAlphanumeric(10);
            //garage.changeLocation(location);
            repositoryOfGarageXml.store(garage);
            garage = repositoryOfGarageXml.retrieveGarageByName("Garage 1");
            //Assertions.assertThat(garage.getLocation()).isEqualTo(location);
        } catch (ExUnknowGarage exUnknowGarage) {
            exUnknowGarage.printStackTrace();
        }
    }
}