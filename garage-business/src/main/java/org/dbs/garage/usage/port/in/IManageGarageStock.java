package org.dbs.garage.usage.port.in;

import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.port.out.ExUnknowLocation;

import java.util.List;

public interface IManageGarageStock {
    void registerVehicleToGarage(List<RegisterVehicleCmd> lstVehicleToRegister);
    void registerGarage(RegisterGarageCmd aNewGarageCmd) throws ExUnknowLocation;
    void unRegisterGarage(UnregisterGarageCmd unregisterGarageCmd) throws ExUnknowGarage, ExGarageGotVehicles;
}
