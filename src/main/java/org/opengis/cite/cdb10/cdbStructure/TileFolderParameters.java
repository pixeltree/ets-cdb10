package org.opengis.cite.cdb10.cdbStructure;

public class TileFolderParameters {
    public final Double minLat;
    public final Double minLon;
    public final Double maxLat;
    public final Double maxLon;

    public TileFolderParameters(String latlong) {
        String[] latlongsplit = latlong.split("_");

        minLat = latlongsplit.length > 0 && latlongsplit[0].length() > 0 ? Double.parseDouble(latlongsplit[0]) : null;
        minLon = latlongsplit.length > 1 && latlongsplit[1].length() > 0 ? Double.parseDouble(latlongsplit[1]) : null;
        maxLat = latlongsplit.length > 2 ? Double.parseDouble(latlongsplit[2]) : null;
        maxLon = latlongsplit.length > 3 ? Double.parseDouble(latlongsplit[3]) : null;
    }

    public Double getMinLat() {
        return minLat;
    }

    public Double getMinLon() {
        return minLon;
    }

    public Double getMaxLat() {
        return maxLat;
    }

    public Double getMaxLon() {
        return maxLon;
    }

    public boolean hasAllLatLonParameters() {
        return minLat != null && minLon != null && maxLat != null && maxLon != null;
    }

    public boolean onlyHasMinValues() {
        return minLat != null && minLon != null;
    }

    public boolean onlyHasMaxValues() {
        return maxLat != null && maxLon != null;
    }

    public boolean hasNoLatLonParameters() {
        return minLat == null && minLon == null && maxLat == null && maxLon == null;
    }
}
