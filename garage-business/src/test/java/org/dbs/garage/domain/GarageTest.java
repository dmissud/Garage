package org.dbs.garage.domain;

import org.dbs.garage.application.Exception_Vehicule_Reference;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;
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
            GarageTest.garage.registerVehicle(new Vehicle("ID_1000", Marque.Dacia));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1100", Marque.Dacia));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1200", Marque.Peugeot));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1300", Marque.Renault));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1400", Marque.Toyota));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1500", Marque.Toyota));
            GarageTest.garage.registerVehicle(new Vehicle("ID_1600", Marque.Citroen));
        } catch (Exception_Vehicule_Reference exception_vehicule_reference) {
            exception_vehicule_reference.printStackTrace();
        }
    }

    @Test
    @Tag("Business")
    @DisplayName("Impossibilité d'enregistrer un véhicule en double")
    void enregistreVehiculeDuplicate() {
        boolean exceptionOK = false;
        try {
            this.garage.registerVehicle(new Vehicle("ID_1300", Marque.Renault));
        } catch (Exception_Vehicule_Reference exception_vehicule_reference) {
            exceptionOK = true;
        }
        assertThat(exceptionOK).isTrue();
    }

    @Test
    @Tag("CRUD")
    @DisplayName("Avons nous le même Garage ?")
    void testEquals() {
        Garage aGarage = this.garage;
        assertTrue(garage.equals(aGarage));
        aGarage = new Garage("Nom1", "location1");
        assertTrue(garage.equals(aGarage));
        aGarage = new Garage("Nom2", "location1");
        assertFalse(garage.equals(aGarage));
    }

    @Test
    @Tag("Business")
    @DisplayName("Avons nous le bon nombre de Véhicule dans le Garage ?")
    void numberOfVehicle() {
        assertEquals(7, garage.giveNumberOfVehicule());
        try {
            this.garage.registerVehicle(new Vehicle("ID_2000", Marque.Citroen));
        } catch (Exception_Vehicule_Reference exception_vehicule_reference) {
            exception_vehicule_reference.printStackTrace();
        }
        assertEquals(8, garage.giveNumberOfVehicule());
    }

}

