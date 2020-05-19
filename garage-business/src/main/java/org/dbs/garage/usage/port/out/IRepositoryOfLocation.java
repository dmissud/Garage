package org.dbs.garage.usage.port.out;

import org.dbs.garage.domain.Location;

import java.util.List;

public interface IRepositoryOfLocation {
    Location retrieveLocationByName(String locationName) throws ExUnknowLocation;
    List<String> retrieveNameOfAllLocation();
    void store(Location location);
    void delete(Location location) throws ExUnknowLocation;
}
