package org.opengis.cite.cdb10.cdbStructure;

import org.junit.Test;
import org.testng.Assert;

/**
 * Created by martin on 2016-12-01.
 */
public class TileFolderValidatorTest {

    @Test
    public void validateLatLongSplit_onlyMinLatLongProvided_noExceptionThrown() {
        String selection = "002_MinMaxElevation";
        String latlong = "-90_-180__";
        String minmaxlod = "001_Elevation@min#0#001_Elevation@max#0";

        String path = "";
        TileFolderValidator tileValidator = new TileFolderValidator(selection, new TileFolderParameters(latlong), minmaxlod, path);

        tileValidator.validateLatLonSplit();
    }

    @Test
    public void validateLatLongSplit_onlyMaxLatLongProvided_noExceptionThrown() {
        String selection = "002_MinMaxElevation";
        String latlong = "__-90_-180";
        String minmaxlod = "001_Elevation@min#0#001_Elevation@max#0";

        String path = "";
        TileFolderValidator tileValidator = new TileFolderValidator(selection, new TileFolderParameters(latlong), minmaxlod, path);

        tileValidator.validateLatLonSplit();
    }

    @Test
    public void validateLatLongSplit_minMaxLatLongProvided_noExceptionThrown() {
        String selection = "001_Elevation";
        String latlong = "-90_-180_-89_-179";
        String minmaxlod = "001_Elevation@min#0#001_Elevation@max#0";

        String path = "";
        TileFolderValidator tileValidator = new TileFolderValidator(selection, new TileFolderParameters(latlong), minmaxlod, path);

        tileValidator.validateLatLonSplit();
    }
}

