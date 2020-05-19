package org.dbs.garage.domain;

import org.dbs.garage.usage.service.ExceptionVehicleReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Gestion des Véhicules")
class GarageTest {

    static private Garage garage;

    @BeforeAll
    static void initGarageForTest() {
        GarageTest.garage = new Garage("Nom1", "location1");
        try {
            GarageTest.garage.registerVehicle(new Vehicle("ID_1000", Marque.DACIA));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1100", Marque.DACIA));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1200", Marque.PEUGEOT));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1300", Marque.RENAULT));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1400", Marque.TOYOTA));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1500", Marque.TOYOTA));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1600", Marque.CITROEN));
        } catch (ExceptionVehicleReference exception_vehicule_reference) {
            exception_vehicule_reference.printStackTrace();
        }
    }

    @Test
    @Tag("Business")
    @DisplayName("Impossibilité d'enregistrer un véhicule en double")
    void enregistreVehiculeDuplicate() {
        boolean exceptionOK = false;
        try {
            GarageTest.garage.registerVehicle(new Vehicle("ID_1300", Marque.RENAULT));
        } catch (ExceptionVehicleReference exception_vehicule_reference) {
            exceptionOK = true;
        }
        assertThat(exceptionOK).isTrue();
    }

    @Test
    @Tag("CRUD")
    @DisplayName("Avons nous le même Garage ?")
    void testEquals() {
        Garage aGarage = GarageTest.garage;
        assertEquals(GarageTest.garage, aGarage);
        aGarage = new Garage("Nom1", "location1");
        assertEquals(GarageTest.garage, aGarage);
        aGarage = new Garage("Nom2", "location1");
        assertNotEquals(GarageTest.garage, aGarage);
    }

    @Test
    @Tag("Business")
    @DisplayName("Avons nous le bon nombre de Véhicule dans le Garage ?")
    void numberOfVehicle() {
        assertEquals(7, garage.giveNumberOfVehicule());
        try {
            GarageTest.garage.registerVehicle(new Vehicle("ID_2000", Marque.CITROEN));
        } catch (ExceptionVehicleReference exception_vehicule_reference) {
            exception_vehicule_reference.printStackTrace();
        }
        assertEquals(8, garage.giveNumberOfVehicule());
    }

}

