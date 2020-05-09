package org.dbs.garagexml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class XmlGarageProperties {
    private static final Logger logger = LogManager.getLogger(XmlGarageProperties.class);

    static final String DATA_GARAGE_LISTE_XSD = "garage_liste.xsd";
    static final String DATA_GARAGE_XSD = "garage.xsd";
    static final String GARAGE_LIST_FILE = "garage_liste.xml";
    static final String GARAGE_XML_PROPERTIES = "garage_xml.properties";

    private String dbDirectory = null;
    private URL lstGarageFileName = null;
    private URL garageListeXSD;
    private URL garageXSD;

    private static XmlGarageProperties xmlGarageProperties = null;

    static XmlGarageProperties getInstance() {
        if (XmlGarageProperties.xmlGarageProperties == null) {
            XmlGarageProperties.xmlGarageProperties = new XmlGarageProperties();
        }
        return XmlGarageProperties.xmlGarageProperties;
    }

    private XmlGarageProperties() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            ClassLoader classLoader =
                    DescriptionOfGaragesManager.class.getClassLoader();

            InputStream propertiesStream = classLoader.getResourceAsStream(GARAGE_XML_PROPERTIES);
            this.garageListeXSD = classLoader.getResource(DATA_GARAGE_LISTE_XSD);
            this.garageXSD = classLoader.getResource(DATA_GARAGE_XSD);

            Properties prop = new Properties();

            if (propertiesStream == null) {
                logger.error("Sorry, unable to find garage_xml.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(propertiesStream);

            //get the property value and print it out
            this.dbDirectory = prop.getProperty("db.directory");
            this.lstGarageFileName =
                    new URL("file:/".concat(this.dbDirectory).concat("/").concat(GARAGE_LIST_FILE));
            logger.info(this.lstGarageFileName);

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public URL getGarageListeXSD() {
        return this.garageListeXSD;
    }

    public URL getGarageXSD() {
        return this.garageXSD;
    }

    URL getLstGarageFileName() {
        return this.lstGarageFileName;
    }

    String getDbDirectory() {
        return this.dbDirectory;
    }

    URL giveURL(String fileName) {
        URL url = null;
        try {
            url = new URL("file:/".concat(this.dbDirectory).concat("/").concat(fileName));
        } catch (MalformedURLException e) {
            logger.error(e);
        }
        return url;
    }
}
