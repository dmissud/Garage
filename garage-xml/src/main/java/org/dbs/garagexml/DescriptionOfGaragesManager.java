package org.dbs.garagexml;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DescriptionOfGaragesManager {
    public static final String NUMBER_OF_GARAGE = "numberOfGarage";
    public static final String NOM = "nom";
    public static final String FILE = "file";
    private static final Logger logger = LogManager.getLogger(DescriptionOfGaragesManager.class);

    private Document xmlDocListGarage;
    private Element elementXMLGarages;
    private final Map<String, String> lstOfGarage;

    DescriptionOfGaragesManager() {
        this.lstOfGarage = new HashMap<>();
    }

    void load() {

        try {
            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            this.elementXMLGarages = retrieveRootElementOfListeGarage();
            NamedNodeMap lstAttributesOfElementGarages = this.elementXMLGarages.getAttributes();
            Node gName = lstAttributesOfElementGarages.getNamedItem(NUMBER_OF_GARAGE);
            int nbGarage = Integer.parseInt(gName.getTextContent());

            for (int indiceGarage = 1; indiceGarage <= nbGarage; indiceGarage++) {
                loadAGarageDesc(path, indiceGarage);
            }
            logger.debug("List of XML Garage Description initialized");
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            logger.error(e);
        }
    }

    void store() {
        buildXmlDoxLstOfGarages();
        storeXmlDocLstOfGarages();
    }

    private void buildXmlDoxLstOfGarages() {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            this.xmlDocListGarage = builder.newDocument();
            this.elementXMLGarages = this.xmlDocListGarage.createElement("garages");
            this.elementXMLGarages.setAttribute(NUMBER_OF_GARAGE, String.valueOf(this.lstOfGarage.size()));
            this.xmlDocListGarage.appendChild(this.elementXMLGarages);
            this.xmlDocListGarage.createComment(
                    String.format("Generate by %s",DescriptionOfGaragesManager.class.getName()));

            for(Map.Entry<String, String> entryGarage:this.lstOfGarage.entrySet()) {
                final Element xmlVehicle = this.xmlDocListGarage.createElement("garage");
                xmlVehicle.setAttribute(NOM, entryGarage.getKey());
                xmlVehicle.setAttribute(FILE, entryGarage.getValue());
                this.elementXMLGarages.appendChild(xmlVehicle);
            }
        } catch (ParserConfigurationException ex) {
            logger.error(ex);
        }

    }

    private void storeXmlDocLstOfGarages() {
        try {
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();

            final DOMSource source = new DOMSource(this.xmlDocListGarage);
            //Code à utiliser pour afficher dans un fichier
            final StreamResult sortie =
                    new StreamResult(XmlGarageProperties.getInstance().getLstGarageFileName().getFile());

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

    private Element retrieveRootElementOfListeGarage() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //Ces trois lignes servent à informer que la validation se fait via un fichier XSD
        SchemaFactory sfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //On créé notre schéma XSD
        //Ici, c'est un schéma interne, pour un schéma externe il faut mettre l'URI
        Schema schema = sfactory.newSchema(XmlGarageProperties.getInstance().getGarageListeXSD());
        //On l'affecte à notre factory afin que le document prenne en compte le fichier XSD
        factory.setSchema(schema);

        DocumentBuilder builder = factory.newDocumentBuilder();
        this.xmlDocListGarage = builder.parse(XmlGarageProperties.getInstance().getLstGarageFileName().getFile());
        return this.xmlDocListGarage.getDocumentElement();
    }

    private void loadAGarageDesc(XPath path, int indice) throws XPathExpressionException {
        String expression = String.format("//garage[%d]", indice);
        Node node = (Node) path.evaluate(expression, this.elementXMLGarages, XPathConstants.NODE);
        NamedNodeMap lstNodeMap = node.getAttributes();
        Node attName = lstNodeMap.getNamedItem(NOM);
        Node fileName = lstNodeMap.getNamedItem(FILE);
        this.lstOfGarage.put(attName.getTextContent(), fileName.getTextContent());
    }

    String retrieveFileNameForGarageByName(String garageName) {
        return this.lstOfGarage.get(garageName);
    }

    String addGarage(String garageName) {
        String fileName = retrieveFileNameForGarageByName(garageName);
        if (fileName == null) {
            fileName = RandomStringUtils.randomAlphanumeric(20).concat(".xml");
            logger.debug(String.format("New file %s for %s", fileName, garageName));
            this.lstOfGarage.put(garageName, fileName);
        }
        return fileName;
    }
}