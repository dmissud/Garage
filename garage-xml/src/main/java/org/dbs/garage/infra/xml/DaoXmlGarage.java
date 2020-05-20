package org.dbs.garage.infra.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.out.VehicleDesc;
import org.dbs.garage.usage.service.ExceptionVehicleReference;
import org.dbs.garage.domain.Garage;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.domain.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DaoXmlGarage {
    private static final Logger logger = LogManager.getLogger(DaoXmlGarage.class);
    private static final String NUMBER_OF_VEHICLE = "numberOfVehicle";
    private static final String NAME_OF_GARAGE = "nom";
    private static final String LOCATION_OF_GARAGE = "location";
    private static final String ID_CHASSIS = "idChassis";
    private static final String NAME_MARQUE = "marque";

    private final String garageFileName;
    private final XPath path;
    private Document xmlDocGarage = null;
    private Element xmlGarage;
    private Garage garage;

    DaoXmlGarage(Garage garage, String garageFileName) {
        this.garageFileName = garageFileName;
        this.garage = new Garage(garage);
        XPathFactory xpf = XPathFactory.newInstance();
        this.path = xpf.newXPath();
    }

    DaoXmlGarage(String garageFileName) {
        this.garageFileName = garageFileName;
        this.garage = null;

        XPathFactory xpf = XPathFactory.newInstance();
        this.path = xpf.newXPath();
        loadDocumentGarage();
        buildAGarageFromXmlDocOfGarage();
    }

    Garage getGarage() {
        return garage;
    }

    private void buildAGarageFromXmlDocOfGarage() {
        this.xmlGarage = this.xmlDocGarage.getDocumentElement();
        NamedNodeMap lstAttributesOfElementGarages = this.xmlGarage.getAttributes();

        Node attributValue = lstAttributesOfElementGarages.getNamedItem(DaoXmlGarage.NUMBER_OF_VEHICLE);
        int nbVehicle = Integer.parseInt(attributValue.getTextContent());

        attributValue = lstAttributesOfElementGarages.getNamedItem(DaoXmlGarage.NAME_OF_GARAGE);
        String nom = attributValue.getTextContent();

        attributValue = lstAttributesOfElementGarages.getNamedItem(DaoXmlGarage.LOCATION_OF_GARAGE);
        String location = attributValue.getTextContent();

        this.garage = new Garage(nom, location);

        try {
            for (int indiceVehicle = 1; indiceVehicle <= nbVehicle; indiceVehicle++) {
                VehicleDesc vehicleDesc = loadAVehicleDesc(indiceVehicle);
                Vehicle vehile = new Vehicle(vehicleDesc.getIdChassis(), vehicleDesc.getMarque());
                this.garage.registerVehicle(vehile);
            }
        } catch (ExceptionVehicleReference | XPathExpressionException exceptionVehicleReference) {
            logger.error(exceptionVehicleReference);
        }
    }

    private VehicleDesc loadAVehicleDesc(int indiceVehicle) throws XPathExpressionException {
        String expression = String.format("//vehicle[%d]", indiceVehicle);
        Node node = (Node) this.path.evaluate(expression, this.xmlGarage, XPathConstants.NODE);
        NamedNodeMap lstNodeMap = node.getAttributes();
        Node idChassis = lstNodeMap.getNamedItem(ID_CHASSIS);
        Node marque = lstNodeMap.getNamedItem(NAME_MARQUE);
        String strChassis = idChassis.getTextContent();
        String strMarque = marque.getTextContent();
        Marque marqueVehicle = Marque.valueOf(strMarque);

        return new VehicleDesc(strChassis, marqueVehicle);
    }

    private void loadDocumentGarage() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Ces trois lignes servent à informer que la validation se fait via un fichier XSD
            SchemaFactory sfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //On créé notre schéma XSD
            //Ici, c'est un schéma interne, pour un schéma externe il faut mettre l'URI
            Schema schema = sfactory.newSchema(XmlGarageProperties.getInstance().getGarageXSD());
            //On l'affecte à notre factory afin que le document prenne en compte le fichier XSD
            factory.setSchema(schema);

            DocumentBuilder builder = factory.newDocumentBuilder();
            this.xmlDocGarage =
                    builder.parse(XmlGarageProperties.getInstance().giveURL(this.garageFileName).getFile());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e);
        }
    }

    String retrieveLocationName() {
        return this.garage.getLocationName();
    }

    void store(Garage garage) {
        this.garage = new Garage(garage);
        buildXmlDocOfGarageFromGarage();
        storeXmlDocOfGarage();
    }

    private void buildXmlDocOfGarageFromGarage() {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            this.xmlDocGarage = builder.newDocument();
            this.xmlGarage = this.xmlDocGarage.createElement("garage");
            this.xmlGarage.setAttribute(NAME_OF_GARAGE, garage.getName());
            this.xmlGarage.setAttribute(LOCATION_OF_GARAGE, garage.getLocationName());
            this.xmlGarage.setAttribute(NUMBER_OF_VEHICLE, String.valueOf(garage.giveNumberOfVehicule()));
            this.xmlDocGarage.appendChild(this.xmlGarage);
            this.xmlDocGarage.createComment(String.format("Generate by %s", DaoXmlGarage.class.getName()));

            Map<String, Vehicle> vehicles = this.garage.giveLstVehicleInGarage();
            for(Vehicle vehicle:vehicles.values()) {
                final Element xmlVehicle = this.xmlDocGarage.createElement("vehicle");
                xmlVehicle.setAttribute(ID_CHASSIS, vehicle.identification());
                xmlVehicle.setAttribute(NAME_MARQUE, vehicle.getMarque().toString());
                this.xmlGarage.appendChild(xmlVehicle);
            }
        } catch (ParserConfigurationException ex) {
            logger.error(ex);
        }
    }

    private void storeXmlDocOfGarage() {
        try {
            final DOMSource source = new DOMSource(this.xmlDocGarage);
            //Code à utiliser pour afficher dans un fichier
            final StreamResult sortie =
                    new StreamResult(XmlGarageProperties.getInstance().giveURL(this.garageFileName).getFile());

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            //sortie
            transformer.transform(source, sortie);

        } catch (TransformerException e) {
            logger.error(e);
        }
    }

    public void deleteXmlFile() {
        String fileName = XmlGarageProperties.getInstance().giveURL(this.garageFileName).getFile();
        File file = new File(fileName);
        if (!file.delete()) {
            logger.error(String.format("Impossible de détruire %s", fileName));
        }
        this.xmlDocGarage = null;
        this.xmlGarage = null;
        this.garage = null;
    }
}
