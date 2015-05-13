package ma15.brickcollector.connection;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.data.BrickSet;

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
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_OWN, null);
        //TIE Fighter with number 75095
        String result = postRequest.setCollectionOwn(Constants.TESTUSER_HASH,"24083","1");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Set_collection_own request did not work",
                Constants.RETURN_STRING_CORRECT_SET_REQUEST,result);

        postRequest = new PostRequest(null, null, Constants.REQUEST_GET_SETS, null);
        result = postRequest.getSets("75095", null, null, Constants.TESTUSER_HASH, null, null, null);
        ArrayList<BrickSet> sets = XmlParser.getSets(result);
        assertNotNull("Sets are null",sets);
        assertEquals("More than one set found",sets.size(),1);
        BrickSet set = sets.get(0);
        assertNotNull("Set is null",set);
        assertEquals("Set not owned",set.isOwned(),"true");

        result = postRequest.setCollectionOwn(Constants.TESTUSER_HASH,"24083","0");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Set_collection_own request did not work",
                Constants.RETURN_STRING_CORRECT_SET_REQUEST,result);
    }

    @Test
    public void testSetCollectionOwnIncorrectUser() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_OWN, null);
        String result = postRequest.setCollectionOwn("","24083","1");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Userhash is correct but should not",
                Constants.RETURN_STRING_SET_REQUEST_INCORRECT_USER,result);
    }

    @Test
    public void testSetCollectionOwnIncorrectParameter() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_OWN, null);
        String result = postRequest.setCollectionOwn(Constants.TESTUSER_HASH,"24083","2");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Owned parameter is correct but should not",
                Constants.RETURN_STRING_SET_REQUEST_INCORRECT_OWN,result);
    }

    @Test
    public void testSetCollectionWant() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_WANT, null);
        //TIE Fighter with number 75095
        String result = postRequest.setCollectionWant(Constants.TESTUSER_HASH,"24083","1");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Set_collection_want request did not work",
                Constants.RETURN_STRING_CORRECT_SET_REQUEST,result);

        postRequest = new PostRequest(null, null, Constants.REQUEST_GET_SETS, null);
        result = postRequest.getSets("75095", null, null, Constants.TESTUSER_HASH, null, null, null);
        ArrayList<BrickSet> sets = XmlParser.getSets(result);
        assertNotNull("Sets are null",sets);
        assertEquals("More than one set found",sets.size(),1);
        BrickSet set = sets.get(0);
        assertNotNull("Set is null",set);
        assertEquals("Set not wanted",set.isWanted(),"true");

        result = postRequest.setCollectionWant(Constants.TESTUSER_HASH,"24083","0");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Set_collection_want request did not work",
                Constants.RETURN_STRING_CORRECT_SET_REQUEST,result);
    }

    @Test
    public void testSetCollectionWantIncorrectUser() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_WANT, null);
        String result = postRequest.setCollectionWant("","24083","1");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Userhash is correct but should not",
                Constants.RETURN_STRING_SET_REQUEST_INCORRECT_USER,result);
    }

    @Test
    public void testSetCollectionWantIncorrectParameter() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_WANT, null);
        String result = postRequest.setCollectionWant(Constants.TESTUSER_HASH,"24083","2");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Wanted parameter is correct but should not",
                Constants.RETURN_STRING_SET_REQUEST_INCORRECT_WANT,result);
    }

    @Test
    public void testSetCollectionOwnQuantity() throws Exception {
        String testQuantity = "5";
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_OWN_QUANTITY, null);
        //TIE Fighter with number 10240
        String result = postRequest.setCollectionOwnQuantity(Constants.TESTUSER_HASH,"22578",testQuantity);
        result = XmlParser.getXMLResultString(result);
        assertEquals("Set_collection_own_quantity request did not work",
                Constants.RETURN_STRING_CORRECT_SET_REQUEST,result);

        postRequest = new PostRequest(null, null, Constants.REQUEST_GET_SETS, null);
        result = postRequest.getSets("10240", null, null, Constants.TESTUSER_HASH, null, null, null);
        ArrayList<BrickSet> sets = XmlParser.getSets(result);
        assertNotNull("Sets are null",sets);
        assertEquals("More than one set found",sets.size(),1);
        BrickSet set = sets.get(0);
        assertNotNull("Set is null",set);
        assertEquals("Set not owned",set.isOwned(),"true");
        assertEquals("Own quantity not " + testQuantity,set.getQtyOwned(),testQuantity);
        result = postRequest.setCollectionOwn(Constants.TESTUSER_HASH,"22578","0");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Set_collection_own request did not work",
                Constants.RETURN_STRING_CORRECT_SET_REQUEST,result);
    }

    @Test
    public void testSetCollectionOwnQuantityIncorrectUser() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_OWN_QUANTITY, null);
        String result = postRequest.setCollectionOwnQuantity("","22578","5");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Userhash is correct but should not",
                Constants.RETURN_STRING_SET_REQUEST_INCORRECT_USER,result);
    }

    @Test
    public void testSetCollectionOwnQuantityIncorrectParameter() throws Exception {
        PostRequest postRequest = new PostRequest(null, null, Constants.REQUEST_SET_OWN_QUANTITY, null);
        String result = postRequest.setCollectionOwnQuantity(Constants.TESTUSER_HASH,"22578","1000");
        result = XmlParser.getXMLResultString(result);
        assertEquals("Quantity parameter is correct but should not",
                Constants.RETURN_STRING_SET_REQUEST_INCORRECT_QUANTITY,result);
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