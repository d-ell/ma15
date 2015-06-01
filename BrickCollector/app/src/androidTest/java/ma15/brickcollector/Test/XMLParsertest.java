package ma15.brickcollector.Test;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.Settings;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.connection.PostRequest;
import ma15.brickcollector.data.BrickSet;

public class XMLParsertest extends AndroidTestCase {


    public static final int EXPECTED_RESULT_LENGTH = 2;
    public static final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "    <ArrayOfSets xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://brickset.com/api/\">\n" +
            "    <sets>\n" +
            "    <setID>8906</setID>\n" +
            "    <number>4526</number>\n" +
            "    <numberVariant>1</numberVariant>\n" +
            "    <name>Batman</name>\n" +
            "    <year>2012</year>\n" +
            "    <theme>DC Comics Super Heroes</theme>\n" +
            "    <themeGroup>Licensed</themeGroup>\n" +
            "    <subtheme>Constraction</subtheme>\n" +
            "    <pieces>40</pieces>\n" +
            "    <minifigs />\n" +
            "    <image>true</image>\n" +
            "    <imageFilename>4526-1</imageFilename>\n" +
            "    <thumbnailURL>http://images.brickset.com/sets/thumbs/tn_4526-1_jpg.jpg</thumbnailURL>\n" +
            "    <largeThumbnailURL>http://images.brickset.com/sets/small/4526-1.jpg</largeThumbnailURL>\n" +
            "    <imageURL>http://images.brickset.com/sets/images/4526-1.jpg</imageURL>\n" +
            "    <bricksetURL>http://brickset.com/sets/4526-1</bricksetURL>\n" +
            "    <owned>false</owned>\n" +
            "    <wanted>false</wanted>\n" +
            "    <qtyOwned>0</qtyOwned>\n" +
            "    <ACMDataCount>0</ACMDataCount>\n" +
            "    <ownedByTotal>1444</ownedByTotal>\n" +
            "    <wantedByTotal>423</wantedByTotal>\n" +
            "    <UKRetailPrice>10.99</UKRetailPrice>\n" +
            "    <USRetailPrice>14.99</USRetailPrice>\n" +
            "    <CARetailPrice>17.99</CARetailPrice>\n" +
            "    <EURetailPrice>14.99</EURetailPrice>\n" +
            "    <rating>4</rating>\n" +
            "    <reviewCount>1</reviewCount>\n" +
            "    <packagingType>Box</packagingType>\n" +
            "    <availability>Retail</availability>\n" +
            "    <instructionsCount>2</instructionsCount>\n" +
            "    <additionalImageCount>3</additionalImageCount>\n" +
            "    <EAN>5702014836785</EAN>\n" +
            "    <UPC>673419166553</UPC>\n" +
            "    <lastUpdated>2012-06-11T08:42:21.443</lastUpdated>\n" +
            "    </sets>\n" +

            "    <sets>\n" +
            "    <setID>9142</setID>\n" +
            "    <number>6857</number>\n" +
            "    <numberVariant>1</numberVariant>\n" +
            "    <name>The Dynamic Duo Funhouse Escape</name>\n" +
            "    <year>2012</year>\n" +
            "    <theme>DC Comics Super Heroes</theme>\n" +
            "    <themeGroup>Licensed</themeGroup>\n" +
            "    <subtheme>Batman</subtheme>\n" +
            "    <pieces>380</pieces>\n" +
            "    <minifigs>5</minifigs>\n" +
            "    <image>true</image>\n" +
            "    <imageFilename>6857-1</imageFilename>\n" +
            "    <thumbnailURL>http://images.brickset.com/sets/thumbs/tn_6857-1_jpg.jpg</thumbnailURL>\n" +
            "    <largeThumbnailURL>http://images.brickset.com/sets/small/6857-1.jpg</largeThumbnailURL>\n" +
            "    <imageURL>http://images.brickset.com/sets/images/6857-1.jpg</imageURL>\n" +
            "    <bricksetURL>http://brickset.com/sets/6857-1</bricksetURL>\n" +
            "    <owned>false</owned>\n" +
            "    <wanted>false</wanted>\n" +
            "    <qtyOwned>0</qtyOwned>\n" +
            "    <ACMDataCount>0</ACMDataCount>\n" +
            "    <ownedByTotal>4807</ownedByTotal>\n" +
            "    <wantedByTotal>1532</wantedByTotal>\n" +
            "    <UKRetailPrice>39.99</UKRetailPrice>\n" +
            "    <USRetailPrice>39.99</USRetailPrice>\n" +
            "    <CARetailPrice>49.99</CARetailPrice>\n" +
            "    <EURetailPrice>49.99</EURetailPrice>\n" +
            "    <rating>5</rating>\n" +
            "    <reviewCount>7</reviewCount>\n" +
            "    <packagingType>Box</packagingType>\n" +
            "    <availability>Retail - limited</availability>\n" +
            "    <instructionsCount>4</instructionsCount>\n" +
            "    <additionalImageCount>5</additionalImageCount>\n" +
            "    <EAN>5702014836846</EAN>\n" +
            "    <UPC />\n" +
            "    <lastUpdated>2014-07-12T18:49:52.907</lastUpdated>\n" +
            "    </sets>\n" +
            "    </ArrayOfSets>";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidXmlBasics() throws Exception {
        final String QUERY = "Batman";

        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_GET_SETS, null);
        String result = postRequest.getSets(QUERY, null, null, null, null, null, null);

        assertTrue("Result is not empty.", !result.isEmpty());

        ArrayList<BrickSet> brickSets = XmlParser.getSets(result);
        assertNotNull(brickSets);
        assertTrue(brickSets.size() <= Integer.parseInt(Settings.getPageSize()));
    }

    @Test
    public void testValidXmlFromString() throws Exception {


        ArrayList<BrickSet> brickSets = XmlParser.getSets(xml);
        assertNotNull(brickSets);
        assertTrue(brickSets.size() == EXPECTED_RESULT_LENGTH);

        BrickSet firstBrick = brickSets.get(0);
        assertEquals(firstBrick.getSetID(), "8906");
        assertEquals(firstBrick.getNumber(), "4526");
        assertEquals(firstBrick.getNumberVariant(), "1");
        assertEquals(firstBrick.getName(), "Batman");
        assertEquals(firstBrick.getYear(), "2012");
        assertEquals(firstBrick.getTheme(), "DC Comics Super Heroes");
        assertEquals(firstBrick.getSubtheme(), "Constraction");
        assertEquals(firstBrick.getPieces(), "40");
        assertEquals(firstBrick.getMinifigs(), "");
        assertEquals(firstBrick.getImageFilename(), "4526-1");
        assertEquals(firstBrick.getThumbnailURL(), "http://images.brickset.com/sets/thumbs/tn_4526-1_jpg.jpg");
        assertEquals(firstBrick.getLargeThumbnailURL(), "http://images.brickset.com/sets/small/4526-1.jpg");
        assertEquals(firstBrick.getBricksetURL(), "http://brickset.com/sets/4526-1");
        assertEquals(firstBrick.isOwned(), "false");
        assertEquals(firstBrick.isWanted(), "false");
        assertEquals(firstBrick.getQtyOwned(), "0");
        assertEquals(firstBrick.getOwnedByTotal(), "1444");
        assertEquals(firstBrick.getWantedByTotal(), "423");
        assertEquals(firstBrick.getUKRetailPrice(), "10.99");
        assertEquals(firstBrick.getUSRetailPrice(), "14.99");
        assertEquals(firstBrick.getCARetailPrice(), "17.99");
        assertEquals(firstBrick.getEURetailPrice(), "14.99");
        assertEquals(firstBrick.getRating(), "4");
        assertEquals(firstBrick.getReviewCount(), "1");
        assertEquals(firstBrick.getPackagingType(), "Box");
        assertEquals(firstBrick.getAvailability(), "Retail");
        assertEquals(firstBrick.getInstructionsCount(), "2");
        assertEquals(firstBrick.getAdditionalImageCount(), "3");
    }

    @Test
    public void testInvalidXmlFromString() throws Exception {


        ArrayList<BrickSet> brickSets = XmlParser.getSets(xml);
        assertNotNull(brickSets);
        assertTrue(brickSets.size() == EXPECTED_RESULT_LENGTH);

        BrickSet firstBrick = brickSets.get(0);

        assertFalse(firstBrick.getSetID().equals("8888"));
    }
}
