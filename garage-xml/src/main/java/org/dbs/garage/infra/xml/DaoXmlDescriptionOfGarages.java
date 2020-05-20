package org.dbs.garage.infra.xml;

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


public class DaoXmlDescriptionOfGarages {
    public static final String NUMBER_OF_GARAGE = "numberOfGarage";
    public static final String NOM = "name";
    public static final String FILE = "file";
    private static final Logger logger = LogManager.getLogger(DaoXmlDescriptionOfGarages.class);

    private Document xmlDocListGarage;
    private Element elementXMLGarages;
    private final Map<String, String> mapOfGarages;

    DaoXmlDescriptionOfGarages() {
        this.mapOfGarages = new HashMap<>();
        load();
    }

    private void load() {

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
            this.elementXMLGarages.setAttribute(NUMBER_OF_GARAGE, String.valueOf(this.mapOfGarages.size()));
            this.xmlDocListGarage.appendChild(this.elementXMLGarages);
            this.xmlDocListGarage.createComment(
                    String.format("Generate by %s", DaoXmlDescriptionOfGarages.class.getName()));

            for(Map.Entry<String, String> entryGarage:this.mapOfGarages.entrySet()) {
                final Element xmlGarage = this.xmlDocListGarage.createElement("garage");
                xmlGarage.setAttribute(NOM, entryGarage.getKey());
                xmlGarage.setAttribute(FILE, entryGarage.getValue());
                this.elementXMLGarages.appendChild(xmlGarage);
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
        SchemaFactory sfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sfactory.newSchema(XmlGarageProperties.getInstance().getGarageListeXSD());
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
        this.mapOfGarages.put(attName.getTextContent(), fileName.getTextContent());
    }

    String retrieveFileNameForGarageByName(String garageName) {
        return this.mapOfGarages.get(garageName);
    }

    String addGarage(String garageName) {
        String fileName = retrieveFileNameForGarageByName(garageName);
        if (fileName == null) {
            fileName = RandomStringUtils.randomAlphanumeric(20).concat(".xml");
            logger.debug(String.format("New file %s for %s", fileName, garageName));
            this.mapOfGarages.put(garageName, fileName);
        }
        return fileName;
    }

    public List<String> retrieveNameOfAllgarage() {
        return new ArrayList<>(this.mapOfGarages.keySet());
    }

    public String removeGarage(String garageName) {
        return this.mapOfGarages.remove(garageName);
    }
}