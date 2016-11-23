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
                    }
                    else if (code.equals("400")) {
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

    private String getLongDir(double latitude, double longitude) {
        String LongDir = "";
        int dLonCellBasic = 1;
        int dLonCell = dLonCellBasic * getDLonZone(latitude);
        int nBSliceIDIndex = 2 * (int) (180 / dLonCell);
        int sideIDIndex = (int) (((int) ((longitude + 180) / dLonCell)) * getDLonZone(latitude));
        int nBSliceIDIndexEq = 2 * (int) (180 / dLonCellBasic);
        if (longitude < 0) {
            int val = Math.abs(nBSliceIDIndexEq / 2 - sideIDIndex);
            if (val < 10)
                LongDir = "W00" + val;
            else if (val < 100 && val >= 10)
                LongDir = "W0" + val;
            else
                LongDir = "W" + val;
        } else if (longitude >= 0) {
            int val = Math.abs(sideIDIndex - nBSliceIDIndexEq / 2);
            if (val < 10)
                LongDir = "E00" + val;
            else if (val < 100 && val >= 10)
                LongDir = "E0" + val;
            else
                LongDir = "E" + val;
        }

        return LongDir;
    }

    private int getDLonZone(double latitude) {
        int dLonZone = 0;
        if (latitude >= 89 && latitude < 90 || latitude >= -90 && latitude < -89)
            dLonZone = 12;
        else if (latitude >= 80 && latitude < 89 || latitude >= -89 && latitude < -80)
            dLonZone = 6;
        else if (latitude >= 75 && latitude < 80 || latitude >= -80 && latitude < -75)
            dLonZone = 4;
        else if (latitude >= 70 && latitude < 75 || latitude >= -75 && latitude < -70)
            dLonZone = 3;
        else if (latitude >= 50 && latitude < 70 || latitude >= -70 && latitude < -50)
            dLonZone = 2;
        else if (latitude >= -50 && latitude < 50)
            dLonZone = 1;

        return dLonZone;
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

            latLongDir.add(TilesUtilities.getLatDir(minLat) + "/" + getLongDir(minLat, minLong));

            //    System.out.println("First two");
        } else {
            // No minimum lat/lon provided
            if (latlongSplit.length == 4 && inputMinLat.isEmpty() && inputMinLon.isEmpty()) {
                double maxLat = Double.parseDouble(inputMaxLat);
                double maxLong = Double.parseDouble(inputMaxLon);
                latLongDir.add(TilesUtilities.getLatDir(maxLat) + "/" + getLongDir(maxLat, maxLong));

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

                int minLatZone = Integer.parseInt(TilesUtilities.getLatDir(minLat).substring(1));
                int minLongZone = Integer.parseInt(getLongDir(minLat, minLong).substring(1));
                int maxLatZone = Integer.parseInt(TilesUtilities.getLatDir(maxLat).substring(1));
                int maxLongZone = Integer.parseInt(getLongDir(maxLat, maxLong).substring(1));

                ArrayList<String> latDir = new ArrayList<>();
                ArrayList<String> longDir = new ArrayList<>();

                if (minLat * maxLat < 0) {
                    for (int i = 1; i <= minLatZone; i++) {
                        if (i < 10)
                            latDir.add(TilesUtilities.getLatDir(minLat).substring(0, 1) + "0" + i);
                        else
                            latDir.add(TilesUtilities.getLatDir(minLat).substring(0, 1) + i);
                    }

                    for (int j = 0; j < maxLatZone; j++) {
                        if (j < 10)
                            latDir.add(TilesUtilities.getLatDir(maxLat).substring(0, 1) + "0" + j);
                        else
                            latDir.add(TilesUtilities.getLatDir(maxLat).substring(0, 1) + j);
                    }
                }
                if (minLong * maxLong < 0) {
                    for (int m = 1; m <= minLongZone; m++) {
                        // for (int m = 1; m <= minLongZone; m=m+getDLonZone(minLat)) {
                        if (m < 10)
                            longDir.add(getLongDir(minLat, minLong).substring(0, 1) + "00" + m);
                        else if (m >= 10 && m < 100)
                            longDir.add(getLongDir(minLat, minLong).substring(0, 1) + "0" + m);
                        else
                            longDir.add(getLongDir(minLat, minLong).substring(0, 1) + m);
                    }

                    Collections.reverse(longDir);

                    for (int n = 0; n < maxLongZone; n++) {
                        // for (int n = 0; n <= maxLongZone; n=n+getDLonZone(maxLat)) {
                        if (n < 10)
                            longDir.add(getLongDir(maxLat, maxLong).substring(0, 1) + "00" + n);
                        else if (n >= 10 && n < 100)
                            longDir.add(getLongDir(maxLat, maxLong).substring(0, 1) + "0" + n);
                        else
                            longDir.add(getLongDir(maxLat, maxLong).substring(0, 1) + n);
                    }
                }

                if (minLat * maxLat > 0) {
                    int minL = minLatZone;
                    int maxL = maxLatZone;
                    if (minLatZone > maxLatZone) {
                        minL = maxLatZone;
                        maxL = minLatZone;
                    }

                    for (int k = minL; k <= maxL; k++) {
                        if (k < 10)
                            latDir.add(TilesUtilities.getLatDir(minLat).substring(0, 1) + "0" + k);
                        else
                            latDir.add(TilesUtilities.getLatDir(minLat).substring(0, 1) + k);
                    }
                }
                if (minLong * maxLong > 0) {
                    int minLo = minLongZone;
                    int maxLo = maxLongZone;
                    if (minLongZone > maxLongZone) {
                        minLo = maxLongZone;
                        maxLo = minLongZone;
                    }
                    for (int l = minLo; l <= maxLo; l++) {
                        if (l < 10)
                            longDir.add(getLongDir(minLat, minLong).substring(0, 1) + "00" + l);
                        else if (l >= 10 && l < 100)
                            longDir.add(getLongDir(minLat, minLong).substring(0, 1) + "0" + l);
                        else
                            longDir.add(getLongDir(minLat, minLong).substring(0, 1) + l);
                    }
                }

                for (int a = 0; a < latDir.size(); a++) {
                    double zone = Double.parseDouble(latDir.get(a).substring(1));
                    if (latDir.get(a).substring(0, 1).equals("S"))
                        zone = zone * (-1);

                    int dLonZone = getDLonZone(zone);

                    for (int b = 0; b < longDir.size(); b = b + dLonZone) {
                        latLongDir.add(latDir.get(a) + "/" + longDir.get(b));
                    }
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
