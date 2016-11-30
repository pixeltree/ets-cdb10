package org.opengis.cite.cdb10.cdbStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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

    /**
     * Verifies the string is empty.
     */
    @Test
    public void isValidDirectory() {
        if (!checkDirectory(path)) {
            Assert.fail("The CDB location is empty! " + minmaxlod);
        } else {
            String[] selectedDirectories = directories.split("-");
            ArrayList<String> failMessages = new ArrayList<>();
            for (int i = 0; i < selectedDirectories.length; i++) {
                String selectedDirectory = selectedDirectories[i];

                if (selectedDirectory != null && !selectedDirectory.isEmpty()) {
                    String code = selectedDirectory.substring(0, 3);

                    if (directoryIsInTilesFolder(code)) {
                        failMessages.addAll(validateLatLonSplit(selectedDirectory));
                    } else if (code.equals("400")) {
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
        if (!checkDirectory(path + "/" + directoryName + "/" + folderName)) {
            failMessages.add("The dataset " + folderName + " does not exist in" + directoryName + "directory");
        }
        return failMessages;
    }

    private boolean directoryIsInTilesFolder(String code) {
        return code.substring(0, 1).equals("0") ||
                code.substring(0, 1).equals("1") ||
                code.substring(0, 1).equals("2") ||
                code.substring(0, 1).equals("3") ||
                code.equals("401");
    }

    private ArrayList<String> validateLatLonSplit(String selection) {
        ArrayList<String> failMessages = new ArrayList<>();
        String[] latlongSplit = latlong.split("_");

        // if (latlongSplit.length == 2) {
        if (latlongSplit.length == 2 ||
                (latlongSplit.length == 4 && latlongSplit[0].isEmpty() && latlongSplit[1].isEmpty()) ||
                (latlongSplit.length == 4 && !latlongSplit[0].isEmpty() && !latlongSplit[1].isEmpty() && !latlongSplit[2].isEmpty() && !latlongSplit[3].isEmpty())) {
            String[] lodMinMaxSplit = minmaxlod.split("#");
            ArrayList<String> result = getLatLongDir(latlongSplit);
            String LODFolder = "";
            for (int c1 = 0; c1 < result.size(); c1++) {

                if (!checkDirectory(path + "/Tiles/" + result.get(c1))) {
                    failMessages.add("Tile " + result.get(c1) + "does not exist in Tiles directory.");
                } else {

                    if (!checkDirectory(path + "/Tiles/" + result.get(c1) + "/" + selection)) {
                        failMessages.add("The dataset " + selection + " does not exist in " + result.get(c1) + " of Tiles directory");
                    } else {

                        datasetSearchLoop:
                        for (int j = 0; j < lodMinMaxSplit.length; j = j + 2) {
                            String innerSplit[] = lodMinMaxSplit[j].split("@");
                            String dataset = innerSplit[0];
                            int minLOD = -10;
                            int maxLOD = 23;
                            if (dataset.equals(selection)) {
                                if (innerSplit[1].equals("min")) {
                                    if (!lodMinMaxSplit[j + 1].isEmpty())
                                        minLOD = Integer.parseInt(lodMinMaxSplit[j + 1]);
                                    // System.out.println("min " + selection + "= " + minLOD);
                                }
                                if (innerSplit[1].equals("max")) {
                                    if ((lodMinMaxSplit.length % 2) == 0) {
                                        if (!lodMinMaxSplit[j + 1].isEmpty())
                                            maxLOD = Integer.parseInt(lodMinMaxSplit[j + 1]);
                                    } else {
                                        if (j < lodMinMaxSplit.length - 1) {
                                            if (!lodMinMaxSplit[j + 1].isEmpty())
                                                maxLOD = Integer.parseInt(lodMinMaxSplit[j + 1]);
                                        }
                                    }
                                    for (int k = 0; k <= maxLOD; k++) {
                                        if (k < 10)
                                            LODFolder = "L0" + Integer.toString(k);
                                        else
                                            LODFolder = "L" + Integer.toString(k);

                                        if (!checkDirectory(path + "/Tiles/" + result.get(c1) + "/" + dataset + "/" + LODFolder)) {
                                            failMessages.add("The LOD folder " + LODFolder + " does not exist in  " + dataset + " dataset of " + result.get(c1));
                                        }

                                    }

                                    break datasetSearchLoop;
                                }

                            }

                        }
                    }
                }
            }
        }

//                           else if(latlongSplit.length == 4 && latlongSplit[0].isEmpty() && latlongSplit[1].isEmpty()){
//                               ArrayList < String > result = getLatLongDir(latlongSplit);
//                               for(int c1=0;c1<result.size();c1++) {
//                                   isDirectory = checkDirectory(path + "/Tiles/" + result.get(c1)+"/"+selection);
//                                   if (!isDirectory) {
//                                       hasError = true;
//                                       failMessages.add("The dataset " + result.get(c1)+"/"+selection + " does not exist in Tiles directory");
//                                   }
//                               }
//                           }
//
//                           else if(latlongSplit.length == 4 && !latlongSplit[0].isEmpty() && !latlongSplit[1].isEmpty() && !latlongSplit[2].isEmpty() && !latlongSplit[3].isEmpty()){
//                               ArrayList < String > result = getLatLongDir(latlongSplit);
//                               for(int c1=0;c1<result.size();c1++) {
//                                   isDirectory = checkDirectory(path + "/Tiles/" + result.get(c1)+"/"+selection);
//                                   if (!isDirectory) {
//                                       hasError = true;
//                                       failMessages.add("The dataset " + result.get(c1)+"/"+selection + " does not exist in Tiles directory");
//                                   }
//                               }
//                           }
        else {
            failMessages.add("None or Bad selection of geographical coverage to check Tiles directory");
        }
        return failMessages;
    }

    private ArrayList<String> getLatLongDir(String[] latlongSplit) {
        ArrayList<String> latLongDir = new ArrayList<String>();

        String inputMinLat = latlongSplit[0];
        String inputMinLon = latlongSplit[1];
        String inputMaxLat = latlongSplit[2];
        String inputMaxLon = latlongSplit[3];

        // No maximum lat/lon provided
        if (latlongSplit.length == 2) {
            double minLat = Double.parseDouble(inputMinLat);
            double minLong = Double.parseDouble(inputMinLon);

            latLongDir.add(TilesUtilities.getLatDir(minLat) + "/" + TilesUtilities.getLongDir(minLat, minLong));

            //    System.out.println("First two");
        } else {
            // No minimum lat/lon provided
            if (latlongSplit.length == 4 && inputMinLat.isEmpty() && inputMinLon.isEmpty()) {
                double maxLat = Double.parseDouble(inputMaxLat);
                double maxLong = Double.parseDouble(inputMaxLon);
                latLongDir.add(TilesUtilities.getLatDir(maxLat) + "/" + TilesUtilities.getLongDir(maxLat, maxLong));

                //  System.out.println("Last two");
            } else if (latlongSplit.length == 4 &&
                    !inputMinLat.isEmpty() &&
                    !inputMinLon.isEmpty() &&
                    !inputMaxLat.isEmpty() &&
                    !inputMaxLon.isEmpty()) {
                double minLat = Double.parseDouble(inputMinLat);
                double minLong = Double.parseDouble(inputMinLon);
                double maxLat = Double.parseDouble(inputMaxLat);
                double maxLong = Double.parseDouble(inputMaxLon);

                HashSet<TileFolder> tileDirectories = TilesUtilities.getLonDirectories(minLat, minLong, maxLat, maxLong);
                for (TileFolder tileFolder : tileDirectories) {
                    latLongDir.add(tileFolder.getFolderPath());
                }
            }
        }
        return latLongDir;
    }

    private boolean checkDirectory(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
        return true;
    }
}
