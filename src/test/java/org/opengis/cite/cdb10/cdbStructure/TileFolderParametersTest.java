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
}
