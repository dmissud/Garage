package org.dbs.garage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GarageTest {

    @Test
    void enregistreVehiculeDuplicate() {
        Garage garage = new Garage("Nom1", "location1");
        boolean exceptionOK = false;
        try {
            garage.registerVehicle(new Vehicle("Id1", Marque.Citroen));
            garage.registerVehicle(new Vehicle("Id1", Marque.Citroen));
        } catch (Exception_Vehicule_Reference exception_vehicule_reference) {
            exceptionOK = true;
        }
        assertTrue(exceptionOK);
    }

    @Test
    void testEquals() {
    }

    @Test
    void compareTo() {
    }
}

