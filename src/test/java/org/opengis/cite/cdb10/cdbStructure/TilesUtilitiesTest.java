package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

import java.util.HashSet;

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

    @Test
    public void getLonDirectories_Case1() {
        HashSet<TileFolder> folders = TilesUtilities.getLonDirectories(-90, -180, -89, -179);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertTrue(folders.contains(new TileFolder(new TileLatitudeFolder('S', 90), "W180")));
    }

    @Test
    public void getLonDirectories_Case2() {
        HashSet<TileFolder> folders = TilesUtilities.getLonDirectories(-90, -180, -88.1, -179);

        Assert.assertEquals(folders.size(), 2);
        Assert.assertTrue(folders.contains(new TileFolder(new TileLatitudeFolder('S', 90), "W180")));
        Assert.assertTrue(folders.contains(new TileFolder(new TileLatitudeFolder('S', 89), "W180")));
    }

    @Test
    public void getLonDirectories_Case3() {
        HashSet<TileFolder> folders = TilesUtilities.getLonDirectories(-90, -180, -89, -168);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertTrue(folders.contains(new TileFolder(new TileLatitudeFolder('S', 90), "W180")));
    }

    @Test
    public void getLonDirectories_Case4() {
        HashSet<TileFolder> folders = TilesUtilities.getLonDirectories(-90, -180, -89, -168.1);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertTrue(folders.contains(new TileFolder(new TileLatitudeFolder('S', 90), "W180")));
    }
}
