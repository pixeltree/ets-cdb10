package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

/**
 * Created by martin on 2016-12-01.
 */
public class TileFolderParametersTest {

    @Test
    public void parseLatLonParameters_onlyMinLatLonProvided() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180__");

        Assert.assertEquals(parameters.minLat, -90.0);
        Assert.assertEquals(parameters.minLon, -180.0);
        Assert.assertEquals(parameters.maxLat, null);
        Assert.assertEquals(parameters.maxLon, null);
    }

    @Test
    public void parseLatLonParameters_onlyMaxLatLonProvided() {
        TileFolderParameters parameters = new TileFolderParameters("__-90_-180");

        Assert.assertEquals(parameters.minLat, null);
        Assert.assertEquals(parameters.minLon, null);
        Assert.assertEquals(parameters.maxLat, -90.0);
        Assert.assertEquals(parameters.maxLon, -180.0);
    }

    @Test
    public void parseLatLonParameters_minMaxLatLonProvided() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_-90_-180");

        Assert.assertEquals(parameters.minLat, -90.0);
        Assert.assertEquals(parameters.minLon, -180.0);
        Assert.assertEquals(parameters.maxLat, -90.0);
        Assert.assertEquals(parameters.maxLon, -180.0);
    }

    @Test
    public void hasAllLatLonParameters_hasAll() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_-90_-180");

        Assert.assertEquals(parameters.hasAllLatLonParameters(), true);
    }

    @Test
    public void hasAllLatLonParameters_doesNotHaveAll() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180__");

        Assert.assertEquals(parameters.hasAllLatLonParameters(), false);
    }

    @Test
    public void getMinLat_getsCorrectMinLat() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_10_170");

        Assert.assertEquals(parameters.getMinLat(), -90.0);
    }

    @Test
    public void getMaxLat_getsCorrectMaxLat() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_10_170");

        Assert.assertEquals(parameters.getMaxLat(), 10.0);
    }

    @Test
    public void getMinLon_getsCorrectMinLon() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_10_170");

        Assert.assertEquals(parameters.getMinLon(), -180.0);
    }

    @Test
    public void getMaxLon_getsCorrectMaxLon() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_10_170");

        Assert.assertEquals(parameters.getMaxLon(), 170.0);
    }

    @Test
    public void hasNoLatLonParameters_hasLatLon() {
        TileFolderParameters parameters = new TileFolderParameters("-90_-180_10_170");

        Assert.assertEquals(parameters.hasNoLatLonParameters(), false);
    }

    @Test
    public void hasNoLatLonParameters_hasNone() {
        TileFolderParameters parameters = new TileFolderParameters("____");

        Assert.assertEquals(parameters.hasNoLatLonParameters(), true);
    }
}
