package org.dbs.garage.application;

import org.assertj.core.api.Assertions;
import org.dbs.garage.domain.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultGarageStockImplTest {

    @Mock
    IRepositoryOfGarage repositoryOfGarage;

    @Mock
    Garage garageN1;

    @Mock
    Garage garageN2;

    @Mock
    Garage garageN3;

    ConsultGarageStockImpl consultGarageStock;
    @BeforeEach
    void setUp() {
        consultGarageStock = new ConsultGarageStockImpl(repositoryOfGarage);
    }

    @Test
    @DisplayName("Consultation de la description des garages")
    void retrieveSupervisionOfGarage() {
        // GIVEN
        List<String> lstGarage = new ArrayList<>();
        lstGarage.add("N1");
        lstGarage.add("N2");
        lstGarage.add("N3");
        when(repositoryOfGarage.retrieveNameOfAllGarage()).thenReturn(lstGarage);
        // Ici le Problème. signature de l'interface 
        // Garage retrieveGarageByName(String garageName) throws UnknowGarage;
        when(repositoryOfGarage.retrieveGarageByName("N1")).thenReturn(garageN1);
        
        // ??? Cette façon de faire me semble un peu lourde
        when(garageN1.getName()).thenReturn("N1");
        when(garageN1.getLocation()).thenReturn("Loc1");
        when(garageN1.giveNumberOfVehicule()).thenReturn(5);
        when(garageN2.getName()).thenReturn("N2");
        when(garageN2.getLocation()).thenReturn("Loc1");
        when(garageN2.giveNumberOfVehicule()).thenReturn(9);
        when(garageN3.getName()).thenReturn("N3");
        when(garageN3.getLocation()).thenReturn("Loc2");
        when(garageN3.giveNumberOfVehicule()).thenReturn(8);

        // WHEN
        List<GarageDescription> resultsDescriptionGarage = consultGarageStock.retrieveSupervisionOfGarage();

        // THEN
        Assertions.assertThat(resultsDescriptionGarage.size()).isEqualTo(3);
        
        GarageDescription descGarage = resultsDescriptionGarage.get(0);
        Assertions.assertThat(descGarage.getName()).isEqualTo("N1");
        Assertions.assertThat(descGarage.getLocation()).isEqualTo("Loc1");
        Assertions.assertThat(descGarage.getNumberOfCars()).isEqualTo(5);
    }

}