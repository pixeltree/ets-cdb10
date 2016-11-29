package org.opengis.cite.cdb10.cdbStructure;

public class TileFolder {
    private TileLatitudeFolder latitudeFolder;
    private String longitudeFolder;

    public TileFolder(double latitude, double longitude) {
        this.latitudeFolder = new TileLatitudeFolder(latitude);

        longitudeFolder = TilesUtilities.getLongDir(latitude, longitude);
    }

    public String getLatitudeFolder() {
        return latitudeFolder.getFolderName();
    }

    public String getLongitudeFolder() {
        return longitudeFolder;
    }
}
