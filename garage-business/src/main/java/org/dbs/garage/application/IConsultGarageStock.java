package org.dbs.garage.application;

import java.util.List;

public interface IConsultGarageStock {
    List<GarageDesc> retrieveSupervisionOfGarage();
    List<GarageDesc> retrieveGarageWithLowStock(int minimalStock);
}
