package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by serene on 2016-09-02.
 */
public class MaterialsXmlStructureTest extends CommonFixture {

    @Test
    public void verifyMaterialsXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Materials.xml")), "Metadata directory should contain Materials.xml file.");

    }

    @Test
    public void verifyMaterialsXmlFileHasValidXml() {
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(Paths.get(path, "Metadata", "Materials.xml")));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Assert.fail("Materials.xml does not contain valid XML.");
        }
    }
}
