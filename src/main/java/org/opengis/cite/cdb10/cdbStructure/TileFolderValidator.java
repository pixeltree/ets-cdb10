package org.opengis.cite.cdb10.cdbStructure;

import org.testng.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class TileFolderValidator {

    private final String selection;
    private final TileFolderParameters tileFolderParameters;
    private final String minmaxlod;
    private final String path;

    public TileFolderValidator(String selection, TileFolderParameters tileFolderParameters, String minmaxlod, String path) {
        this.selection = selection;
        this.tileFolderParameters = tileFolderParameters;
        this.minmaxlod = minmaxlod;
        this.path = path;
    }

    public ArrayList<String> validateLatLonSplit() {
        ArrayList<String> failMessages = new ArrayList<>();

        if (tileFolderParameters.hasNoLatLonParameters()) {
            Assert.fail("None or Bad selection of geographical coverage to check Tiles directory");
        }

        String[] lodMinMaxSplit = minmaxlod.split("#");
        ArrayList<String> result = getTileDirectories(tileFolderParameters);
        String LODFolder = "";
        for (int c1 = 0; c1 < result.size(); c1++) {
            if (!checkDirectory(path + "/Tiles/" + result.get(c1))) {
                failMessages.add("Tile " + result.get(c1) + " does not exist in Tiles directory.");
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

//                           else if(latlongSplit.length == 4 && latlongSplit[0].isEmpty() && latlongSplit[1].isEmpty()){
//                               ArrayList < String > result = getTileDirectories(latlongSplit);
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
//                               ArrayList < String > result = getTileDirectories(latlongSplit);
//                               for(int c1=0;c1<result.size();c1++) {
//                                   isDirectory = checkDirectory(path + "/Tiles/" + result.get(c1)+"/"+selection);
//                                   if (!isDirectory) {
//                                       hasError = true;
//                                       failMessages.add("The dataset " + result.get(c1)+"/"+selection + " does not exist in Tiles directory");
//                                   }
//                               }
//                           }
        return failMessages;
    }

    static ArrayList<String> getTileDirectories(TileFolderParameters parameters) {
        ArrayList<String> latLongDir = new ArrayList<>();

        if (parameters.hasAllLatLonParameters()) {
            HashSet<TileFolder> tileDirectories = TileFolder.getTileFolders(parameters.getMinLat(),
                    parameters.getMinLon(), parameters.getMaxLat(), parameters.getMaxLon());
            for (TileFolder tileFolder : tileDirectories) {
                latLongDir.add(tileFolder.getFolderPath());
            }
        } else if (parameters.onlyHasMinValues()) {
            latLongDir.add(new TileFolder(parameters.getMinLat(), parameters.getMinLon()).getFolderPath());
        } else if (parameters.onlyHasMaxValues()) {
            latLongDir.add(new TileFolder(parameters.getMaxLat(), parameters.getMaxLon()).getFolderPath());
        }
        return latLongDir;
    }

    static boolean checkDirectory(String path) {
        return Files.isDirectory(Paths.get(path));
    }
}
