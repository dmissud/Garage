package org.dbs.garage.infra.xml;

public class XMLDescOfGarage {
    private final String name;
    private final String fileName;
    private DaoXmlGarage garageXMLLoader = null;

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

    public DaoXmlGarage getGarageXMLLoader() {
        return this.garageXMLLoader;
    }

    public void setGarageXMLLoader(DaoXmlGarage garageXMLLoader) {
        this.garageXMLLoader = garageXMLLoader;
    }

}
