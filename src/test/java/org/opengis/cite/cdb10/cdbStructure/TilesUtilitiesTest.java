package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

import java.util.ArrayList;

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
        ArrayList<TileFolder> folders = TilesUtilities.getLonDirectories(-90, -180, -89, -179);

        Assert.assertEquals(folders.size(), 1);
        Assert.assertEquals(folders.get(0).getLatitudeFolder(), "S90");
        Assert.assertEquals(folders.get(0).getLongitudeFolder(), "W180");
    }

    @Test
    public void getLonDirectories_Case2() {
        ArrayList<TileFolder> folders = TilesUtilities.getLonDirectories(-90, -180, -88.1, -179);

        Assert.assertEquals(folders.size(), 2);
        Assert.assertEquals(folders.get(0).getLatitudeFolder(), "S90");
        Assert.assertEquals(folders.get(0).getLongitudeFolder(), "W180");
        Assert.assertEquals(folders.get(1).getLatitudeFolder(), "S89");
        Assert.assertEquals(folders.get(1).getLongitudeFolder(), "W180");
    }
}

