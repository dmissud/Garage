package org.dbs.garage.application;

import org.dbs.garage.domain.Garage;

import java.util.List;

public interface IRepositoryOfGarage {
    Garage retrieveGarageByName(String garageName) throws UnknowGarage;
    List<String> retrieveNameOfGarageByLocation(String locationName) throws UnknowLocation;
    List<String> retrieveNameOfAllGarage();
    void store(Garage garage);
}
