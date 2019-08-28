package com.company;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Installer {


    private static com.company.Mod mod;
    private static BufferedReader reader;
    private File file;
    private static Car car;
    private static final String NO_PROPERTY_FILE_IN_MOD  = "В моде отсутвует файл свойств, установка будет выполнена и мод будет работать";
    private static final String INSTALLATION_ERROR  = "Установка не будет выполнена, отсутствуют необходимые файлы";
    private static final String INSTALLATION_FINISH  = "Установка завершена!";

    public void setModPropertiesPath() throws NullPointerException {
        for(File f : Objects.requireNonNull(file.listFiles())){
            if(f.getName().endsWith("txt") || f.getName().endsWith("xml")) {
                mod.setConfigPath(mod.getPath() + File.separator + f.getName());
            }
        }
        if(mod.getConfigPath() == null){
            System.out.println(NO_PROPERTY_FILE_IN_MOD);
        }
    }

    public static void setModProperties() throws IOException {
        reader = new BufferedReader(new FileReader(mod.getConfigPath()));
        StringBuilder builder = new StringBuilder();
        while (reader.ready()){
            builder.append(reader.readLine() + "\n");
        }
        mod.setCarProperties(builder.toString());
    }

    public static void setModDataPath(){
        mod.setDataPath(mod.getPath() + File.separator + "data");
    }

    public static void setModExportPath(){
        mod.setExportPath(mod.getPath() + File.separator + "export");
    }

    public void install(com.company.Game game, com.company.Mod mod) throws Exception {

        setModPropertiesPath();
        setModDataPath();
        setModExportPath();

        if(mod.getConfigPath() != null) {
            setModProperties();
        }
        else if(mod.getDataPath() != null && mod.getExportPath() != null){
            copyDataAndExportFoldersToGameDir();
        }
        else if (mod.getDataPath() == null || mod.getExportPath() == null){
            ConsoleHelper.write(INSTALLATION_ERROR);
            System.exit(1);
        }
        if(mod.getConfigPath() != null) {
            setPlayerCarsPath();
            editPlayerCarsXML();
        }
        else{
            ConsoleHelper.write(INSTALLATION_FINISH);
        }
    }

    public void copyDataAndExportFoldersToGameDir() throws IOException{
        File folder = new File(mod.getPath());

        File[] listOfFiles = folder.listFiles();

        Path destDir = Paths.get(com.company.Game.getGame().getPath());
        assert listOfFiles != null;
        for (File file : listOfFiles){
                if(file.isDirectory()){
                    FileUtils.copyDirectoryToDirectory(file, destDir.toFile());
                }
            }
    }
    private static Document stringToDocument(String xmlStr) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = builderFactory.newDocumentBuilder();
            return docBuilder.parse(new InputSource(new StringReader(xmlStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Car getCarXML(){
        Document xmlCarProperties = stringToDocument(mod.getCarProperties());
        assert xmlCarProperties != null;
        NodeList nodeList = xmlCarProperties.getElementsByTagName("Car");
        Car car = new Car();
        Node node = nodeList.item(0);
        if(Node.ELEMENT_NODE == node.getNodeType()){
            Element element = (Element) node;
            car.setName(element.getAttribute("Name"));
            car.setABS(element.getAttribute("ABS"));
            car.setAT(element.getAttribute("AT"));
            car.setMT(element.getAttribute("MT"));
            car.setDisplayName(element.getElementsByTagName("DisplayName").item(0).getTextContent());
            car.setDescription(element.getElementsByTagName("Description").item(0).getTextContent());
        }

        return car;
    }

    public static void editPlayerCarsXML() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        car = getCarXML();

        File xmlFile = new File(Game.getGame().getPlayerCarsPath());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        NodeList nodeList = doc.getElementsByTagName("Cars");
        Element rootElement = (Element) nodeList.item(0);
        rootElement.appendChild(getCar(doc, car));


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);


        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(new File(Game.getGame().getPlayerCarsPath()));


        transformer.transform(source, console);
        transformer.transform(source, file);
        System.out.println("Заполнение XML файла закончено");



    }

    private static Node getCar(Document doc, Car car){
        Element c = doc.createElement("Car");
        c.setAttribute("Name", car.getName());
        c.setAttribute("ABS", car.getABS());
        c.setAttribute("MT", car.getMT());
        c.setAttribute("AT", car.getAT());

        c.appendChild(getCarElements(doc, c, "DisplayName", car.getDisplayName()));

        c.appendChild(getCarElements(doc, c, "Description", car.getDescription()));

        return c;
    }

    private static Node getCarElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static void setPlayerCarsPath(){
        Game.getGame().setPlayerCarsPath(Game.getGame().getPath() +
                File.separator +
                "data" +
                File.separator +
                "config" +
                File.separator +
                "player_cars.xml");
    }

    public Installer(Mod mod){
        file = new File(mod.getPath());
        Installer.mod = mod;
    }


}
