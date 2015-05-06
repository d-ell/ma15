package ma15.brickcollector.connection;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.XmlParser;

public class PostRequestTest extends AndroidTestCase {

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
    public void testCorrectLogin() throws Exception {

        String key = loginAndGetUserHash(Constants.TESTUSER_NAME, Constants.TESTUSER_PW);

        assertEquals("Key is incorrect", Constants.TESTUSER_HASH, key);
    }

    @Test
    public void testIncorrectLoginWithPassword() throws Exception {

        String key = loginAndGetUserHash(Constants.TESTUSER_NAME, "");

        assertEquals("Login was successful, but should not",
                Constants.RETURN_STRING_INCORRECT_LOGIN, key);
    }

    @Test
    public void testIncorrectLoginWithUser() throws Exception {

        String key = loginAndGetUserHash("", Constants.TESTUSER_PW);

        assertEquals("Login was successful, but should not",
                Constants.RETURN_STRING_INCORRECT_LOGIN, key);
    }

    @Test
    public void testIncorrectLoginWithUserAndPassword() throws Exception {

        String key = loginAndGetUserHash("", "");

        assertEquals("Login was successful, but should not",
                Constants.RETURN_STRING_INCORRECT_LOGIN, key);
    }

    @Test
    public void testSetCollectionOwn() throws Exception {
        assertTrue(false);
    }

    @Test
    public void testSetCollectionWantQuantity() throws Exception {
        assertTrue(false);
    }

    @Test
    public void testSetCollectionWant() throws Exception {
        assertTrue(false);
    }

    @Test
    public void testCheckCorrectApiKey() {

        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_CHECK_KEY, null);
        String result = postRequest.checkKey(Constants.API_KEY);
        result = XmlParser.getXMLResultString(result);
        assertEquals("Incorrect API Key", Constants.RETURN_STRING_CORRECT_KEY, result);
    }

    @Test
    public void testCheckIncorrectApiKey() {

        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_CHECK_KEY, null);
        String result = postRequest.checkKey("");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Correct API Key but should not", Constants.RETURN_STRING_INCORRECT_KEY, result);
    }

    private String loginAndGetUserHash(String name, String pw) {

        PostRequest postRequest = new PostRequest(null, null, Constants.LOGIN, null);
        String result = postRequest.getLogin(name, pw);

        assertNotNull(result);

        return XmlParser.getUserHash(result);
    }
}