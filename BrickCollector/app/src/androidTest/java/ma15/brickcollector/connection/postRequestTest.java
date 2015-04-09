package ma15.brickcollector.connection;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

        HTTPDispatcher.PostRequest postRequest = new HTTPDispatcher().new PostRequest(null, null, null);
        String result = postRequest.getSets(QUERY, null, null, null);
        assertTrue("Result is not empty.", !result.isEmpty());
    }

    @Test
    public void testCheckApiKey() {

        String key = "F1PE-3JWB-BwfC";

        assertEquals(true, ConnectionHandler.checkApiKey(key));

    }
}