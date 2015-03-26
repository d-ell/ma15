package ma15.brickcollector.unit;

import android.test.ActivityTestCase;

import ma15.brickcollector.connection.ConnectionHandler;

/**
 * Created by thomas on 26.03.15.
 */
public class ConnectionTest extends ActivityTestCase {

    public void testCheckApiKey() {

        String key = "F1PE-3JWB-BwfC";

        assertEquals(true, ConnectionHandler.checkApiKey(key));

    }

}
