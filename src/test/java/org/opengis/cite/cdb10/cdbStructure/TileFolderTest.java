package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

/**
 * Created by serene on 2016-11-30.
 */
public class TileFolderTest {

    @Test
    public void equals() {
        TileFolder tileFolder = new TileFolder(new TileLatitudeFolder('S', 90), "W180");

        Assert.assertNotEquals(tileFolder, new TileFolder(new TileLatitudeFolder('N', 90), "W180"));
        Assert.assertNotEquals(tileFolder, new TileFolder(new TileLatitudeFolder('S', 89), "W180"));
        Assert.assertNotEquals(tileFolder, new TileFolder(new TileLatitudeFolder('S', 90), "E180"));
        Assert.assertEquals(tileFolder, new TileFolder(new TileLatitudeFolder('S', 90), "W180"));
    }
}
