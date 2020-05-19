package org.dbs.garage.infra.memory;

import org.assertj.core.api.Assertions;
import org.dbs.garage.usage.port.out.ExUnknowLocation;
import org.dbs.garage.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Test du repository de localisation en mémoire")
class RepositoryOfLocationMemoryImplTest {

    private static RepositoryOfLocationMemoryImpl repositoryOfLocationMemory;

    @BeforeEach
    void setUp() {
        repositoryOfLocationMemory = (RepositoryOfLocationMemoryImpl) RepositoryOfLocationMemoryImpl.getInstance();
        repositoryOfLocationMemory.initialize();
    }

    @Test
    @DisplayName("Recherche de Localisation par le nom : test Ok et Ko")
    void retrieveLocationByName() {
        boolean exceptionUp = false;
        Location location = null;
        try {
            location = repositoryOfLocationMemory.retrieveLocationByName("Westfield Parly 2");
        } catch (ExUnknowLocation exUnknowLocation) {
            exceptionUp = true;
        }
        Assertions.assertThat(location).isNotNull();
        Assertions.assertThat(exceptionUp).isFalse();
        location = null;
        try {
            location = repositoryOfLocationMemory.retrieveLocationByName("NOT EXIST");
        } catch (ExUnknowLocation exUnknowLocation) {
            exceptionUp = true;
        }
        Assertions.assertThat(location).isNull();
        Assertions.assertThat(exceptionUp).isTrue();
    }

    @Test
    @DisplayName("Recherche du nom de toutes les localisations")
    void retrieveNameOfAllLocation() {
        List<String> lstLocation = repositoryOfLocationMemory.retrieveNameOfAllLocation();
        Assertions.assertThat(lstLocation.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Test bon fonctionnement de Création, Modification puis Suppréssion")
    void storeAndUpdateAndDeleteOk() {
        boolean exceptionUp = false;
        Location location = new Location("TEMOIN", "PAS ADRESSE");

        repositoryOfLocationMemory.store(location);
        Location locationBis = null;
        try {
            locationBis = repositoryOfLocationMemory.retrieveLocationByName("TEMOIN");
        } catch (ExUnknowLocation exUnknowLocation) {
            exceptionUp = true;
        }
        Assertions.assertThat(location).isEqualByComparingTo(locationBis);
        Assertions.assertThat(exceptionUp).isFalse();

        try {
            repositoryOfLocationMemory.delete(locationBis);
        } catch (ExUnknowLocation exUnknowLocation) {
            exceptionUp = true;
        }
        Assertions.assertThat(exceptionUp).isFalse();

        try {
            locationBis = repositoryOfLocationMemory.retrieveLocationByName("TEMOIN");
        } catch (ExUnknowLocation exUnknowLocation) {
            exceptionUp = true;
        }
        Assertions.assertThat(exceptionUp).isTrue();

    }

    @Test
    @DisplayName("Test bonne détection des exceptions pour la  Suppréssion")
    void storeAndUpdateAndDeleteKO() {
        boolean exceptionUp = false;
        Location location = new Location("NOT EXIST", "PAS ADRESSE");

        try {
            repositoryOfLocationMemory.delete(location);
        } catch (ExUnknowLocation exUnknowLocation) {
            exceptionUp = true;
        }
        Assertions.assertThat(location).isNotNull();
        Assertions.assertThat(exceptionUp).isTrue();
    }

}