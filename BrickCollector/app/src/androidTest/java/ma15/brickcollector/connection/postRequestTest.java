package ma15.brickcollector.connection;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.XmlParser;

public class postRequestTest extends AndroidTestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetSets() throws Exception {
        final String QUERY = "Batman";

        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_GET_SETS, null);
        String result = postRequest.getSets(QUERY, null, null, null, null, null, null);
        assertTrue("Result is not empty.", !result.isEmpty());
    }

    @Test
    public void testGetLogin() throws Exception {

        String key = getUserHash();

        assertEquals("Key is incorrect", "7HFw_bVFYT", key);
    }

    @Test
    public void testSetCollectionOwn() throws Exception {

    }

    @Test
    public void testSetCollectionWantQuantity() throws Exception {

    }

    @Test
    public void testSetCollectionWant() throws Exception {

    }

    @Test
    public void testCheckApiKey() {

        String key = "F1PE-3JWB-BwfC";

        assertEquals(true, ConnectionHandler.checkApiKey(key));

    }


    private String getUserHash() {
        final String name = Constants.TESTUSER_NAME;
        final String pw = Constants.TESTUSER_PW;

        PostRequest postRequest = new PostRequest(null, null, Constants.LOGIN, null);
        String result = postRequest.getLogin(name, pw);

        assertNotNull(result);

        return XmlParser.getUserHash(result);
    }
}