package org.opengis.cite.cdb10.cdbStructure;

public class TileLatitudeFolder {
    private char direction;
    private int cellId;

    public TileLatitudeFolder(double latitude) {
        int dLatCell = 1;
        int sliceID = (int) ((latitude + 90) / dLatCell);
        int nBSliceID = 2 * 90 / dLatCell;

        if (latitude < 0) {
            int val = Math.abs(nBSliceID / 2 - sliceID);
            direction = 'S';
            cellId = val;
        } else if (latitude >= 0) {
            int val = Math.abs(sliceID - nBSliceID / 2);
            direction = 'N';
            cellId = val;
        }
    }

    public char getDirection() {
        return direction;
    }

    public int getCellId() {
        return cellId;
    }

    public String getFolderName() {
        return String.format(direction + "%02d", cellId);
    }
}
