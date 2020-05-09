package org.dbs.garage.application.port.in;

import java.util.List;

public interface IEnrichGarageStock {
    void registerVehicleToGarage(List<RegisterVehicleCmd> lstVehicleToRegister);
    void registerGarage(RegisterGarageCmd aNewGarageCmd);
}
