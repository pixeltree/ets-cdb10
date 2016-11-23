package org.opengis.cite.cdb10.cdbStructure;

import java.util.ArrayList;

public class TileLatitudeFolder implements Comparable<TileLatitudeFolder> {
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

    public TileLatitudeFolder(char direction, int cellId) {
        this.direction = direction;
        this.cellId = cellId;
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

    public TileLatitudeFolder increment() {
        char newDirection = direction;
        int newCellId;

        if (getDirection() == 'S') {
            newCellId = cellId - 1;
        } else {
            newCellId = cellId + 1;
        }

        if (getDirection() == 'S' && newCellId == 0) {
            newDirection = 'N';
            newCellId = 0;
        } else if (getDirection() == 'N' && newCellId > 90) {
            return null;
        }

        return new TileLatitudeFolder(newDirection, newCellId);
    }

    public static ArrayList<TileLatitudeFolder> getInclusiveDirectories(TileLatitudeFolder minFolder, TileLatitudeFolder maxFolder) {
        ArrayList<TileLatitudeFolder> folders = new ArrayList<>();

        TileLatitudeFolder currentFolder = minFolder;
        do {
            folders.add(currentFolder);
            currentFolder = currentFolder.increment();
        } while (currentFolder != null && currentFolder.compareTo(maxFolder) <= 0);

        return folders;
    }

    @Override
    public int compareTo(TileLatitudeFolder otherFolder) {
        if (getDirection() == otherFolder.getDirection() && getCellId() == otherFolder.getCellId()) {
            return 0;
        } else if (getDirection() == 'S' && otherFolder.getDirection() == 'N')
            return -1;
        else if (getDirection() == 'N' && otherFolder.getDirection() == 'S')
            return 1;
        else
            return cellId - otherFolder.cellId;
    }
}
