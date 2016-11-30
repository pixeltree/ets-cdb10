package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

import java.util.ArrayList;

/**
 * Created by martin on 2016-11-24.
 */
public class TileLatitudeFolderTest {

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

    @Test
    public void equals() {
        Assert.assertNotEquals(new TileLatitudeFolder('S', 0), new TileLatitudeFolder('S', 1));
        Assert.assertNotEquals(new TileLatitudeFolder('S', 89),new TileLatitudeFolder('N', 0));
        Assert.assertNotEquals(new TileLatitudeFolder('N', 0), new TileLatitudeFolder('N', 1));
        Assert.assertEquals(new TileLatitudeFolder('N', 0), new TileLatitudeFolder('N', 0));
    }
}
