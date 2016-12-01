package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Before;
import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by serene on 2016-09-06.
 */
public class MetadataTestFixture<T extends CommonFixture> extends TestFixture<T> {

    protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
    protected Path metadataFolder;
    protected Path schemaFolder;

    public MetadataTestFixture() {
        directories = "001_Elevation";
        latlong = "-90_-180_-89_-179";
        minmaxlod = "001_Elevation@min#1#001_Elevation@max#0";
    }

    @Before
    public void createDirectories() throws IOException {
        metadataFolder = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
        schemaFolder = Files.createDirectories(cdb_root.resolve(Paths.get(String.valueOf(metadataFolder), "Schema")));
    }
}
