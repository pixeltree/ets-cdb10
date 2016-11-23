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
    public void getLatitudeDirectories() {
        TileLatitudeFolder minFolder = new TileLatitudeFolder(-1);
        TileLatitudeFolder maxFolder = new TileLatitudeFolder(1);

        ArrayList<TileLatitudeFolder> folders = TileLatitudeFolder.getInclusiveDirectories(minFolder, maxFolder);

        Assert.assertEquals(folders.get(0).getFolderName(), "S01");
        Assert.assertEquals(folders.get(1).getFolderName(), "N00");
        Assert.assertEquals(folders.get(2).getFolderName(), "N01");
    }

    @Test
    public void compareLatitudeDirectories() {
        Assert.assertEquals(new TileLatitudeFolder('S', 0).compareTo(new TileLatitudeFolder('S', 1)), -1);
        Assert.assertEquals(new TileLatitudeFolder('S', 89).compareTo(new TileLatitudeFolder('N', 0)), -1);
        Assert.assertEquals(new TileLatitudeFolder('N', 0).compareTo(new TileLatitudeFolder('N', 1)), -1);
    }
}
