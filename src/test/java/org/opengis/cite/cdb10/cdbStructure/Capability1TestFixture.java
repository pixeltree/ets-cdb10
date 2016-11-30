package org.opengis.cite.cdb10.cdbStructure;

import net.sf.saxon.exslt.Common;
import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by serene on 2016-11-30.
 */
public class Capability1TestFixture<T extends Common> extends TestFixture<Capability1Tests> {
    public Capability1TestFixture() throws IOException {
        testSuite = new Capability1Tests();
        directories = "001_Elevation";
        latlong = "-90_-180_-89_-179";
        minmaxlod = "001_Elevation@min#1#001_Elevation@max#0";

    }

    @Test
    public void validateTilesFolders_TileFolderExists() throws IOException {
        Path tilesFolder = Files.createDirectories(cdb_root.resolve(Paths.get("Tiles")));
        Files.createDirectories(tilesFolder.resolve(Paths.get("S90", "W180", "001_Elevation", "L00")));

        testSuite.tileDirectoriesAreValid();
    }
}
