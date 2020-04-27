package org.dbs.garage.application;

import java.util.List;

public interface IEnrichGarageStock {
    void registerVehicleToGarage(List<RegisterVehicleCmd> lstVehicleToRegister);
}
