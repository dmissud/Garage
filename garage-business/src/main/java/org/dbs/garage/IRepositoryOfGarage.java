package org.dbs.garage;

public interface IRepositoryOfGarage {
    Garage retrieveGarageByName(String garageName) throws UnknowGarage;
}
