package org.dbs.garage.infra.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class XmlGarageProperties {
    private static final Logger logger = LogManager.getLogger(XmlGarageProperties.class);

    static final String DATA_GARAGE_LISTE_XSD = "garage_liste.xsd";
    static final String DATA_LOCATION_LISTE_XSD = "location_liste.xsd";
    static final String DATA_GARAGE_XSD = "garage.xsd";
    static final String GARAGE_LIST_FILE = "garage_liste.xml";
    static final String LOCATION_LIST_FILE = "location_liste.xml";
    static final String GARAGE_XML_PROPERTIES = "garage_xml.properties";
    public static final String ENV_XML_GARAGE_DIRECTORY = "ENV_XML_GARAGE_DIRECTORY";

    private String dbDirectory = null;
    private URL lstGarageFileName = null;
    private URL lstlocationFileName = null;
    private URL locationListeXSD;
    private URL garageListeXSD;
    private URL garageXSD;

    private boolean envOk = false;
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

    boolean isEnvOk() {
        return envOk;
    }

    private void setEnvOk(boolean envOk) {
        this.envOk = envOk;
    }

    private void loadProperties() {
        ClassLoader classLoader =
                DaoXmlDescriptionOfGarages.class.getClassLoader();
        this.garageListeXSD = classLoader.getResource(DATA_GARAGE_LISTE_XSD);
        this.garageXSD = classLoader.getResource(DATA_GARAGE_XSD);
        this.locationListeXSD = classLoader.getResource(DATA_LOCATION_LISTE_XSD);

        loadEnvProperties();
        if (!isEnvOk()) {
            loadEnvFromPropertiesFile();
        }
    }

    private void loadEnvProperties()  {
        setEnvOk(false);
        Map<String, String> envs = System.getenv();
        this.dbDirectory = envs.get(ENV_XML_GARAGE_DIRECTORY);
        if (this.dbDirectory != null) {
            try {
                this.lstGarageFileName =
                        new URL("file:/".concat(this.dbDirectory).concat("/").concat(GARAGE_LIST_FILE));
                this.lstlocationFileName =
                        new URL("file:/".concat(this.dbDirectory).concat("/").concat(LOCATION_LIST_FILE));
                setEnvOk(true);
            } catch (MalformedURLException e) {
                logger.error(e);
            }
        }
    }

    private void loadEnvFromPropertiesFile() {
        try {

            ClassLoader classLoader =
                    DaoXmlDescriptionOfGarages.class.getClassLoader();

            InputStream propertiesStream = classLoader.getResourceAsStream(GARAGE_XML_PROPERTIES);

            Properties prop = new Properties();

            if (propertiesStream == null) {
                logger.error("Sorry, unable to find garage_xml.properties");
                setEnvOk(false);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(propertiesStream);

            //get the property value and print it out
            this.dbDirectory = prop.getProperty("db.directory");
            this.lstGarageFileName =
                    new URL("file:/".concat(this.dbDirectory).concat("/").concat(GARAGE_LIST_FILE));
            this.lstlocationFileName =
                    new URL("file:/".concat(this.dbDirectory).concat("/").concat(LOCATION_LIST_FILE));
            logger.info(this.lstGarageFileName);
            setEnvOk(true);

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

    URL getLstLocationFileName() {
        return this.lstlocationFileName;
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

    public URL getLocationListeXSD() {
        return this.locationListeXSD;
    }
}
