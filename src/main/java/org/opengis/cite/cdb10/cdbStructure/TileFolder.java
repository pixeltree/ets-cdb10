package org.opengis.cite.cdb10.cdbStructure;

import java.util.HashSet;

public class TileFolder {
    private String latitudeFolder;
    private String longitudeFolder;

    public TileFolder(double latitude, double longitude) {
        this.latitudeFolder = TilesUtilities.getLatDir(latitude);
        this.longitudeFolder = TilesUtilities.getLongDir(latitude, longitude);
    }

    public TileFolder(String latitudeFolder, String longitudeFolder) {
        this.latitudeFolder = latitudeFolder;
        this.longitudeFolder = longitudeFolder;
    }

    public static HashSet<TileFolder> getTileFolders(double minLat, double minLon, double maxLat, double maxLon) {
        HashSet<TileFolder> folders = new HashSet<>();

        for (int lat = (int) Math.floor(minLat); lat < Math.ceil(maxLat); lat++) {
            for (int lon = (int) Math.floor(minLon); lon < Math.ceil(maxLon); lon++) {
                folders.add(new TileFolder(lat, lon));
            }
        }

        return folders;
    }

    public String getLatitudeFolder() {
        return latitudeFolder;
    }

    public String getLongitudeFolder() {
        return longitudeFolder;
    }

    public String getFolderPath() {
        return String.format("%s/%s", latitudeFolder, longitudeFolder);
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

