package org.dbs.garage.usage.port.out;

import java.util.List;

public interface IConsultGarageStock {
    List<GarageDesc> retrieveSupervisionOfGarage();
    List<GarageDesc> retrieveGarageWithLowStock(int minimalStock);

    GarageStockDesc retrieveGarageStockByName(String nameOfGarage) throws ExUnknowGarage;
}
