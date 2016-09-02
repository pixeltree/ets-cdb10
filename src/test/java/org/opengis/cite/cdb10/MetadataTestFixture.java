package org.opengis.cite.cdb10;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by serene on 2016-09-02.
 */
public abstract class MetadataTestFixture<T extends CommonFixture> extends TestFixture<T> {
    protected static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");

    protected Path metadataFolder;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createMetadataDirectory() throws IOException {
        metadataFolder = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
    }

}
