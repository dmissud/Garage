package org.dbs.garage.application.port.out;

import java.util.List;

public interface IConsultGarageStock {
    List<GarageDesc> retrieveSupervisionOfGarage();
    List<GarageDesc> retrieveGarageWithLowStock(int minimalStock);

    GarageStockDesc retrieveGarageStockByName(String nameOfGarage) throws UnknowGarage;
}
