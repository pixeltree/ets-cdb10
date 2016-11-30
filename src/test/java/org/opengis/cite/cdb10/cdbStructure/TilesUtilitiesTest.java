package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by martin on 2016-11-23.
 */
public class TilesUtilitiesTest {

    @Test
    public void getLatDirectory() {
        Assert.assertEquals(TilesUtilities.getLatDir(-5.2), "S06");
        Assert.assertEquals(TilesUtilities.getLatDir(-11), "S11");
        Assert.assertEquals(TilesUtilities.getLatDir(2.5), "N02");
        Assert.assertEquals(TilesUtilities.getLatDir(62.3), "N62");
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
        HashSet<TileFolder> folders = TileFolder.getTileFolders(-90, -180, -89, -179);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertTrue(folders.contains(new TileFolder("S90", "W180")));
    }

    @Test
    public void getLonDirectories_Case2() {
        HashSet<TileFolder> folders = TileFolder.getTileFolders(-90, -180, -88.1, -179);

        Assert.assertEquals(folders.size(), 2);
        Assert.assertTrue(folders.contains(new TileFolder("S90", "W180")));
        Assert.assertTrue(folders.contains(new TileFolder("S89", "W180")));
    }

    @Test
    public void getLonDirectories_Case3() {
        HashSet<TileFolder> folders = TileFolder.getTileFolders(-90, -180, -89, -168);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertTrue(folders.contains(new TileFolder("S90", "W180")));
    }

    @Test
    public void getLonDirectories_Case4() {
        HashSet<TileFolder> folders = TileFolder.getTileFolders(-90, -180, -89, -168.1);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertTrue(folders.contains(new TileFolder("S90", "W180")));
    }

    @Test
    public void getLonDirectories_Zone6() {
        HashSet<TileFolder> folders = TileFolder.getTileFolders(-89, -180, -88, -168);

        Assert.assertEquals(folders.size(), 2);
        Assert.assertTrue(folders.contains(new TileFolder("S89", "W180")));
        Assert.assertTrue(folders.contains(new TileFolder("S89", "W174")));
    }

    @Test
    public void getTilesDirectories() {
        String[] selectedDirectories = new String[]{"001_Elevation", "100_GSFeature", "200_VectorMaterial",
                "300_GSModelGeometry", "401_Navigation", "400_NavData", "500_GTModelGeometry", "600_MModelGeometry",
                "700_Metadata"};

        ArrayList<String> tilesDirectories = TilesUtilities.getTilesDirectories(selectedDirectories);

        Assert.assertTrue(tilesDirectories.contains("001_Elevation"));
        Assert.assertTrue(tilesDirectories.contains("100_GSFeature"));
        Assert.assertTrue(tilesDirectories.contains("200_VectorMaterial"));
        Assert.assertTrue(tilesDirectories.contains("300_GSModelGeometry"));
        Assert.assertTrue(tilesDirectories.contains("401_Navigation"));

        Assert.assertFalse(tilesDirectories.contains("400_NavData"));
        Assert.assertFalse(tilesDirectories.contains("500_GTModelGeometry"));
        Assert.assertFalse(tilesDirectories.contains("600_MModelGeometry"));
        Assert.assertFalse(tilesDirectories.contains("700_Metadata"));

    }
}
