package org.dbs.garagexml;

public class XMLDescOfGarage {
    private final String name;
    private final String fileName;
    private GarageXMLManager garageXMLLoader = null;

    public XMLDescOfGarage(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }
    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public GarageXMLManager getGarageXMLLoader() {
        return this.garageXMLLoader;
    }

    public void setGarageXMLLoader(GarageXMLManager garageXMLLoader) {
        this.garageXMLLoader = garageXMLLoader;
    }

}
