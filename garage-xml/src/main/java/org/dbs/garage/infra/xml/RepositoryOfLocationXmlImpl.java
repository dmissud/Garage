package org.dbs.garage.infra.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbs.garage.usage.port.out.IRepositoryOfLocation;
import org.dbs.garage.usage.port.out.ExUnknowLocation;
import org.dbs.garage.domain.Location;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryOfLocationXmlImpl implements IRepositoryOfLocation {
    private static final String NUMBER_OF_LOCATION = "numberOfLocation";
    private static final String NOM = "name";
    private static final String ADRESS = "adress";
    private static final Logger logger = LogManager.getLogger(RepositoryOfLocationXmlImpl.class);

    private static RepositoryOfLocationXmlImpl repositoryOfLocationXml = null;

    private Document xmlDocListLocation;
    private Element elementXMLLocations;
    private final Map<String, Location> mapOfLocations;

    public RepositoryOfLocationXmlImpl() {
        this.mapOfLocations = new HashMap<>();
        load();
    }

    private void load() {
        try {
            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            SchemaFactory sfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sfactory.newSchema(XmlGarageProperties.getInstance().getLocationListeXSD());
            factory.setSchema(schema);

            DocumentBuilder builder = factory.newDocumentBuilder();
            this.xmlDocListLocation = builder.parse(
                    XmlGarageProperties.getInstance().getLstLocationFileName().getFile());
            this.elementXMLLocations = this.xmlDocListLocation.getDocumentElement();

            NamedNodeMap lstAttributesOfElementLocations = this.elementXMLLocations.getAttributes();
            Node gName = lstAttributesOfElementLocations.getNamedItem(NUMBER_OF_LOCATION);
            int nbLocations = Integer.parseInt(gName.getTextContent());

            for (int indiceLocation = 1; indiceLocation <= nbLocations; indiceLocation++) {
                String expression = String.format("//location[%d]", indiceLocation);
                Node node = (Node) path.evaluate(expression, this.elementXMLLocations, XPathConstants.NODE);
                NamedNodeMap lstNodeMap = node.getAttributes();
                Node attName = lstNodeMap.getNamedItem(NOM);
                Node adress = lstNodeMap.getNamedItem(ADRESS);
                this.mapOfLocations.put(attName.getTextContent(),
                        new Location(attName.getTextContent(), adress.getTextContent()));
            }
            logger.debug("List of XML location Description initialized");
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            logger.error(e);
        }
    }

    public static IRepositoryOfLocation getInstance() {
        if (RepositoryOfLocationXmlImpl.repositoryOfLocationXml == null) {
            RepositoryOfLocationXmlImpl.repositoryOfLocationXml = new RepositoryOfLocationXmlImpl();
        }
        return RepositoryOfLocationXmlImpl.repositoryOfLocationXml;
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
        save();
    }

    @Override
    public void delete(Location location) throws ExUnknowLocation {
        Location theLocation = this.mapOfLocations.get(location.getName());
        if (theLocation == null) {
            throw new ExUnknowLocation(location.getName());
        } else {
            this.mapOfLocations.remove(location.getName());
            logger.debug(String.format("%s Deleted", location.getName()));
        }
        save();
    }

    private void save() {
        try {
            buildXmlDocLstOfLocations();
            storeXmlDocLstOfLocations();
        } catch (TransformerException | ParserConfigurationException e) {
            logger.error(e);
        }
    }

    private void storeXmlDocLstOfLocations() throws TransformerException {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();

        final DOMSource source = new DOMSource(this.xmlDocListLocation);
        //Code à utiliser pour afficher dans un fichier
        final StreamResult sortie =
                new StreamResult(XmlGarageProperties.getInstance().getLstLocationFileName().getFile());

        transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        //sortie
        transformer.transform(source, sortie);
    }

    private void buildXmlDocLstOfLocations() throws ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        this.xmlDocListLocation = builder.newDocument();
        this.elementXMLLocations = this.xmlDocListLocation.createElement("locations");
        this.elementXMLLocations.setAttribute(NUMBER_OF_LOCATION, String.valueOf(this.mapOfLocations.size()));
        this.xmlDocListLocation.appendChild(this.elementXMLLocations);
        this.xmlDocListLocation.createComment(
                String.format("Generate by %s", RepositoryOfLocationXmlImpl.class.getName()));

        for (Map.Entry<String, Location> entryLocation : this.mapOfLocations.entrySet()) {
            final Element xmlLocation = this.xmlDocListLocation.createElement("location");
            xmlLocation.setAttribute(NOM, entryLocation.getValue().getName());
            xmlLocation.setAttribute(ADRESS, entryLocation.getValue().getAdresse());
            this.elementXMLLocations.appendChild(xmlLocation);
        }
    }

}
