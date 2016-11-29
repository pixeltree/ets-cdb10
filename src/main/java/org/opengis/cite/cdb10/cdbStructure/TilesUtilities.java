package org.opengis.cite.cdb10.cdbStructure;

import java.util.ArrayList;

/**
 * Created by martin on 2016-11-23.
 */
public class TilesUtilities {
    public static String getLongDir(double latitude, double longitude) {
        String LongDir = "";

        int dLonCellBasic = 1;
        int dLonCell = dLonCellBasic * getDLonZone(latitude);
        int sideIDIndex = ((int) ((longitude + 180) / dLonCell)) * getDLonZone(latitude);
        int nBSliceIDIndexEq = 2 * 180 / dLonCellBasic;
        if (longitude < 0) {
            int val = Math.abs(nBSliceIDIndexEq / 2 - sideIDIndex);
            LongDir = String.format("W%03d", val);
        } else if (longitude >= 0) {
            int val = Math.abs(sideIDIndex - nBSliceIDIndexEq / 2);
            LongDir = String.format("E%03d", val);
        }

        return LongDir;
    }

    static int getDLonZone(double latitude) {
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

    public static ArrayList<TileFolder> getLonDirectories(double minLat, double minLon, double maxLat, double maxLon) {
        ArrayList<TileFolder> folders = new ArrayList<>();

        for (int lat = (int) Math.floor(minLat); lat < Math.ceil(maxLat); lat++) {
            for (int lon = (int) Math.floor(minLon); lon < Math.ceil(maxLon); lon++) {
                folders.add(new TileFolder(lat, lon));
            }
        }
        return folders;
    }
}

