package org.opengis.cite.cdb10.cdbStructure;

/**
 * Created by martin on 2016-11-23.
 */
public class TilesUtilities {
    public static String getLatDir(double latitude) {
        int dLatCell = 1;
        int sliceID = (int) ((latitude + 90) / dLatCell);
        int nBSliceID = 2 * 90 / dLatCell;

        String LatDir = "";
        if (latitude < 0) {
            int val = Math.abs(nBSliceID / 2 - sliceID);
            LatDir = String.format("S%02d", val);
        } else if (latitude >= 0) {
            int val = Math.abs(sliceID - nBSliceID / 2);
            LatDir = String.format("N%02d", val);
        }

        return LatDir;
    }
}
