package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

/**
 * Created by martin on 2016-11-23.
 */
public class TilesUtilitiesTest {

    @Test
    public void getLatDirectory_LatitudeLessThanZero() {
        Assert.assertEquals(TilesUtilities.getLatDir(-5.2), "S06");
        Assert.assertEquals(TilesUtilities.getLatDir(-11), "S11");
    }

    @Test
    public void getLatDirectory_LatitudeMoreThanZero() {
        Assert.assertEquals(TilesUtilities.getLatDir(2.5), "N02");
        Assert.assertEquals(TilesUtilities.getLatDir(62.3), "N62");
    }
}
