package org.dbs.garage;

import org.dbs.garage.application.Exception_Vehicule_Reference;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(exceptionOK).isTrue();
    }

    @Test
    void testEquals() {
//        fail("Not yet Implemented");
    }

    @Test
    void compareTo() {
//        fail("Not yet Implemented");
    }
}

