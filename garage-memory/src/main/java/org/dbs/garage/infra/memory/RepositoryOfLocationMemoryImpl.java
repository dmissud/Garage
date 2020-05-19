package org.dbs.garage.infra.memory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.out.ExUnknowLocation;
import org.dbs.garage.usage.port.out.IRepositoryOfLocation;
import org.dbs.garage.domain.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryOfLocationMemoryImpl implements IRepositoryOfLocation {
    private static final Logger logger = LogManager.getLogger(RepositoryOfLocationMemoryImpl.class);
    private static RepositoryOfLocationMemoryImpl repositoryOfLocationMemory;
    private Map<String, Location> mapOfLocations;

    private RepositoryOfLocationMemoryImpl() {
        initialize();
    }

    public static IRepositoryOfLocation getInstance() {
        if (RepositoryOfLocationMemoryImpl.repositoryOfLocationMemory == null) {
            RepositoryOfLocationMemoryImpl.repositoryOfLocationMemory = new RepositoryOfLocationMemoryImpl();
        }
        return RepositoryOfLocationMemoryImpl.repositoryOfLocationMemory;
    }


    public void initialize() {
        mapOfLocations = new HashMap<>();
        Location location;
        location = new Location("Westfield Parly 2", "2 AVENUE CHARLES DE GAULLE LE CHESNAY 78150");
        this.mapOfLocations.put(location.getName(), location);
        location = new Location("Westfield Velizy 2", "2 AVENUE DE L'EUROPE, 78140 VÉLIZY-VILLACOUBLAY, FRANCE");
        this.mapOfLocations.put(location.getName(), location);
        location = new Location("Westfield Forum des Halles", "101 Porte, Rue Berger, 75001 Paris");
        this.mapOfLocations.put(location.getName(), location);
        location = new Location("Westfield Les 4 Temps", "15 Parvis De La Défense, 92092 Puteaux");
        this.mapOfLocations.put(location.getName(), location);
        location = new Location("Centre Commercial Marseille Grand Littoral", "11 Avenue de St antoine, 13015 Marseille");
        this.mapOfLocations.put(location.getName(), location);
        location = new Location("Centre commercial la Valentine", "Route de la Sablière, 13011 Marseille");
        this.mapOfLocations.put(location.getName(), location);
    }

    @Override
    public Location retrieveLocationByName(String locationName) throws ExUnknowLocation {
        Location location = this.mapOfLocations.get(locationName);
        if (location == null) {
            throw new ExUnknowLocation(locationName);
        }
        return new Location(location);
    }

    @Override
    public List<String> retrieveNameOfAllLocation() {
        return new ArrayList<>(this.mapOfLocations.keySet());
    }

    @Override
    public void store(Location location) {
        if (mapOfLocations.containsKey(location.getName())) {
            this.mapOfLocations.replace(location.getName(), new Location(location));
            logger.debug(String.format("%s Updated", location.getName()));
        } else {
            this.mapOfLocations.put(location.getName(), new Location(location));
            logger.debug(String.format("%s Created", location.getName()));
        }
    }

    @Override
    public void delete(Location location) throws ExUnknowLocation {
        Location theLocation = this.mapOfLocations.get(location.getName());
        if (theLocation == null) {
            throw new ExUnknowLocation(location.getName());
        } else {
            this.mapOfLocations.remove(location.getName());
        }
    }
}
