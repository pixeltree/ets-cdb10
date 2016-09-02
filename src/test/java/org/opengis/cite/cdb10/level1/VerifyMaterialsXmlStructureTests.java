package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.MaterialsXmlStructureTest;
import org.opengis.cite.cdb10.MetadataTestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by serene on 2016-09-02.
 */
public class VerifyMaterialsXmlStructureTests extends MetadataTestFixture<MaterialsXmlStructureTest> {

    private static Path VALID_XML_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Materials.xml"));

    public VerifyMaterialsXmlStructureTests() {
        testSuite = new MaterialsXmlStructureTest();
    }

    @Test
    public void verifyMaterialsXmlFileExists_FileExists() throws Exception {
        Files.createFile(metadataFolder.resolve(Paths.get("Materials.xml")));

        // execute
        testSuite.verifyMaterialsXmlFileExists();
    }

    @Test
    public void verifyMaterialsXmlFileExists_FileDoesNotExist() throws Exception {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Materials.xml file.");

        // execute
        testSuite.verifyMaterialsXmlFileExists();
    }

    @Test
    public void verifyMaterialsXmlFileExist_XmlIsValid() throws IOException {
        // setup
        Files.copy(VALID_XML_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyMaterialsXmlFileHasValidXml();
    }

    @Test
    public void verifyMaterialsXmlFileExist_XmlIsNotValid() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Materials.xml")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Materials.xml does not contain valid XML.");

        // execute
        testSuite.verifyMaterialsXmlFileHasValidXml();
    }
}
