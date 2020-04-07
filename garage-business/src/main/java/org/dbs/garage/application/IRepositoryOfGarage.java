package org.dbs.garage.application;

import org.dbs.garage.domain.Garage;

public interface IRepositoryOfGarage {
    Garage retrieveGarageByName(String garageName) throws UnknowGarage;
}
