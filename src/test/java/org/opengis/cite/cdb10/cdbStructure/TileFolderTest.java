package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

/**
 * Created by serene on 2016-11-30.
 */
public class TileFolderTest {

    @Test
    public void equals() {
        TileFolder tileFolder = new TileFolder("S90", "W180");

        Assert.assertNotEquals(tileFolder, new TileFolder("N90", "W180"));
        Assert.assertNotEquals(tileFolder, new TileFolder("S89", "W180"));
        Assert.assertNotEquals(tileFolder, new TileFolder("S90", "E180"));
        Assert.assertEquals(tileFolder, new TileFolder("S90", "W180"));
    }
}
