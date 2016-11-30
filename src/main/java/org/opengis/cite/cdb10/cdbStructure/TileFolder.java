package org.opengis.cite.cdb10.cdbStructure;

public class TileFolder {
    private TileLatitudeFolder latitudeFolder;
    private String longitudeFolder;

    public TileFolder(double latitude, double longitude) {
        this.latitudeFolder = new TileLatitudeFolder(latitude);

        longitudeFolder = TilesUtilities.getLongDir(latitude, longitude);
    }

    public TileFolder(TileLatitudeFolder latitudeFolder, String longitudeFolder) {
        this.latitudeFolder = latitudeFolder;
        this.longitudeFolder = longitudeFolder;
    }

    public String getLatitudeFolder() {
        return latitudeFolder.getFolderName();
    }

    public String getLongitudeFolder() {
        return longitudeFolder;
    }

    @Override
    public boolean equals(Object obj) {
        TileFolder other = (TileFolder) obj;
        return latitudeFolder.equals(other.latitudeFolder) && longitudeFolder.equals(other.longitudeFolder);
    }

    @Override
    public int hashCode() {
        return getLatitudeFolder().hashCode() + getLongitudeFolder().hashCode();
    }
}
