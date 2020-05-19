package org.dbs.garage.usage.port.out;

import java.util.HashMap;
import java.util.Map;

public class GarageStockDesc {
    private final GarageDesc garageDesc;
    private final Map<String, VehicleDesc> lstVehicleDesc;


    public GarageDesc getGarageDesc() {
        return garageDesc;
    }

    public Map<String, VehicleDesc> getLstVehicleDesc() {
        return lstVehicleDesc;
    }

    public GarageStockDesc(GarageDesc garageDesc) {
        this.garageDesc = garageDesc;
        lstVehicleDesc = new HashMap<>();
    }

    public void addVehicleDesc(VehicleDesc vehicleDesc) {
        lstVehicleDesc.put(vehicleDesc.getIdChassis(), vehicleDesc);
    }
}
