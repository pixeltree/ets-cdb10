package org.opengis.cite.cdb10.cdbStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Includes various tests of capability 1.
 */
public class Capability1Tests extends CommonFixture {

    @BeforeClass
    public void obtainTestSubject(ITestContext testContext) {
        Object obj = testContext.getSuite().getAttribute(SuiteAttribute.LEVEL.getName());
        if ((null != obj)) {
            Integer level = Integer.class.cast(obj);
            Assert.assertTrue(level == 1, "Conformance level 1 will not be checked since ics = " + level);
        }
        super.obtainTestSubject(testContext);
    }

    public Capability1Tests() {
    }

    @Test
    public void tileDirectoriesAreValid() {
        String[] selectedDirectories = directories.split("-");
        ArrayList<String> tilesDatasets = TilesUtilities.getTilesDirectories(selectedDirectories);
        ArrayList<String> failMessages = new ArrayList<>();

        for (String dataset : tilesDatasets) {
            failMessages.addAll(new TileFolderValidator(dataset, new TileFolderParameters(latlong), minmaxlod, path).validateLatLonSplit());
        }

        if (!failMessages.isEmpty()) {
            Assert.fail(joinMessages(failMessages));
        }
    }

    @Test
    public void geoTypicalModelsAreValid() {
//        ArrayList<String> tilesDatasets = collectGeoTypicalModelDatasets(directories);
        Assert.fail();
    }

    @Test
    public void movingModelsAreValid() {
        Assert.fail();
    }

    @Test
    public void navigationIsValid() {
        Assert.fail();
    }

    @Test
    public void metaDataDirectoriesAreValid() {
        Assert.fail();
    }

    public String joinMessages(ArrayList<String> failMessages) {
        String finalFailMsg = "\n";
        for (int j = 0; j < failMessages.size(); j++)
            finalFailMsg += failMessages.get(j) + "\n";

        return finalFailMsg;
    }

    /**
     * Verifies the string is empty.
     */
    @Test
    public void isValidDirectory() {
        if (!TileFolderValidator.checkDirectory(path)) {
            Assert.fail("The CDB location is empty! " + minmaxlod);
        } else {
            String[] selectedDirectories = directories.split("-");
            ArrayList<String> failMessages = new ArrayList<>();
            for (int i = 0; i < selectedDirectories.length; i++) {
                String selectedDirectory = selectedDirectories[i];

                if (selectedDirectory != null && !selectedDirectory.isEmpty()) {
                    String code = selectedDirectory.substring(0, 3);

                    if (code.equals("400")) {
                        failMessages.addAll(validateDirectoryExists("Navigation", selectedDirectory));
                    } else if (code.substring(0, 1).equals("5")) {
                        failMessages.addAll(validateDirectoryExists("GTModel", selectedDirectory));
                    } else if (code.substring(0, 1).equals("6")) {
                        failMessages.addAll(validateDirectoryExists("MModel", selectedDirectory));
                    } else if (code.substring(0, 1).equals("7")) {
                        failMessages.addAll(validateDirectoryExists("Metadata", selectedDirectory));
                    } else {
                        failMessages.add("No directory was selected");
                    }
                }
            }

            if (failMessages.size() > 0) {
                String finalFailMsg = "\n";
                for (int j = 0; j < failMessages.size(); j++)
                    finalFailMsg += failMessages.get(j) + "\n";
                Assert.fail(finalFailMsg);
            }
        }
    }

    private ArrayList<String> validateDirectoryExists(String directoryName, String folderName) {
        ArrayList<String> failMessages = new ArrayList<>();
        if (!TileFolderValidator.checkDirectory(path + "/" + directoryName + "/" + folderName)) {
            failMessages.add("The dataset " + folderName + " does not exist in" + directoryName + "directory");
        }
        return failMessages;
    }

}
