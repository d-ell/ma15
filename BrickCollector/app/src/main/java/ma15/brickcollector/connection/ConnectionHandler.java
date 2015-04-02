package ma15.brickcollector.connection;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 26.03.15.
 */
public final class ConnectionHandler {

    final static String TAG = "connection";

    public static boolean checkApiKey(String key) {

        String url = "http://brickset.com/api/v2.asmx/checkKey";
        String contentParameter = "apiKey";
        int length = contentParameter.length() + key.length() + 1;

        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("apiKey", key));
            httppost.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse httpResponse = client.execute(httppost);

            String result = EntityUtils.toString(httpResponse.getEntity());

            Log.d(TAG, "Key: " + key);
            Log.d(TAG, result);

            if (result != null && result.length() > 0) {
                return true;
            }

        } catch (IOException e) {
            Log.d(TAG, "IOException");
        }

        return false;
    }

}


