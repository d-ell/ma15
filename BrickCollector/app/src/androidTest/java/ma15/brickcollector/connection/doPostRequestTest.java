package ma15.brickcollector.connection;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class doPostRequestTest extends AndroidTestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetSets() throws Exception {
        final String QUERY = "Batman";

        HTTPDispatcher.doPostRequest postRequest = new HTTPDispatcher().new doPostRequest(null, null, null);
        String result = postRequest.getSets(QUERY, null, null, null);
        assertTrue("Result is not empty.", !result.isEmpty());
    }
}