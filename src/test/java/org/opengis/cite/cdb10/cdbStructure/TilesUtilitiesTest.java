package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

/**
 * Created by martin on 2016-11-23.
 */
public class TilesUtilitiesTest {

    @Test
    public void getLatDirectory() {
        Assert.assertEquals(new TileLatitudeFolder(-5.2).getFolderName(), "S06");
        Assert.assertEquals(new TileLatitudeFolder(-11).getFolderName(), "S11");
        Assert.assertEquals(new TileLatitudeFolder(2.5).getFolderName(), "N02");
        Assert.assertEquals(new TileLatitudeFolder(62.3).getFolderName(), "N62");
    }

    @Test
    public void getLonDirectory() {
        Assert.assertEquals(TilesUtilities.getLongDir(-6, 45), "E045");
        Assert.assertEquals(TilesUtilities.getLongDir(62, -160), "W160");
        Assert.assertEquals(TilesUtilities.getLongDir(-5.2, 45.2), "E045");
        Assert.assertEquals(TilesUtilities.getLongDir(-5, 45), "E045");
    }
}
