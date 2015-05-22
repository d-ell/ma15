package ma15.brickcollector.connection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.Settings;

/**
 * Created by thomas on 29.04.15.
 */
// <Params, Progress, Result>
public class PostRequest extends AsyncTask<String, Integer, String> {

    final static String TAG = PostRequest.class.getName();

    private Callback callback = null;
    private Activity activity = null;
    private String requestMethod;

    private ProgressDialog progressDialog = null;

    public PostRequest(Activity activity, Callback callback, String requestMethod, ProgressBar progress) {
        this.callback = callback;
        this.activity = activity;
        this.requestMethod = requestMethod;
    }

    @Override
    protected String doInBackground(String... params) {
        // we should also xml parse here ?

        if (requestMethod.equals(Constants.BROWSE)) {
            return getSets(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        } else if (requestMethod.equals(Constants.LOGIN)) {
            return getLogin(params[0], params[1]);
        } else if (requestMethod.equals(Constants.SET_OWN)) {
            return setCollectionOwn(params[0], params[1], params[2]);
        } else if (requestMethod.equals(Constants.SET_WANT)) {
            return setCollectionWant(params[0], params[1], params[2]);
        } else if (requestMethod.equals(Constants.SET_OWN_QUANTITIY)) {
            return setCollectionOwnQuantity(params[0], params[1], params[2]);
        } else if (requestMethod.equals(Constants.CHECK_KEY)) {
            return checkKey(params[0]);
        }

        return null;
    }

    private String doRequest(String requestMethod, List<NameValuePair> pairs) {

        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.BASE_URL + requestMethod);

            UrlEncodedFormEntity urlEncodeEntity = new UrlEncodedFormEntity(pairs);
            httppost.setEntity(urlEncodeEntity);


                /* debugging ***
                Log.v(TAG, httppost.toString());
                for(Header head : httppost.getAllHeaders()) {
                    Log.v(TAG, head.getName() + ":" + head.getValue());
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                httppost.getEntity().writeTo(out);
                Log.v(TAG, "Entity: " + out.toString());
                Log.v(TAG, "Entity Length: " + out.toString().length());
                /* debugging ***/

            HttpResponse httpResponse = client.execute(httppost, new BasicHttpContext());

            String result = EntityUtils.toString(httpResponse.getEntity());

            Log.d(TAG, "POST result: " + result);

            // dummy for progress
            // publishProgress(100);

            return result;

        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }

        return "";
    }

    public String getSets(String query, String theme, String year, String user_hash,
                          String owned, String wanted, String pageNumber) {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("apiKey", Constants.API_KEY));
        pairs.add(new BasicNameValuePair("userHash", user_hash));
        pairs.add(new BasicNameValuePair("query", query));
        pairs.add(new BasicNameValuePair("theme", theme));
        pairs.add(new BasicNameValuePair("subtheme", ""));
        pairs.add(new BasicNameValuePair("setNumber", ""));
        pairs.add(new BasicNameValuePair("year", year));
        pairs.add(new BasicNameValuePair("owned", owned));
        pairs.add(new BasicNameValuePair("wanted", wanted));
        pairs.add(new BasicNameValuePair("orderBy", ""));
        pairs.add(new BasicNameValuePair("pageSize", Settings.getPageSize()));
        pairs.add(new BasicNameValuePair("pageNumber", pageNumber));
        pairs.add(new BasicNameValuePair("userName", ""));

        return doRequest(Constants.REQUEST_GET_SETS, pairs);
    }

    public String checkKey(String key) {
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("apiKey", key));
        return doRequest(Constants.REQUEST_CHECK_KEY, pairs);
    }

    public String getLogin(String username, String password) {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("apiKey", Constants.API_KEY));
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", password));

        return doRequest(Constants.REQUEST_LOGIN, pairs);
    }

    public String setCollectionOwn(String userHash, String setID, String own) {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("apiKey", Constants.API_KEY));
        pairs.add(new BasicNameValuePair("userHash", userHash));
        pairs.add(new BasicNameValuePair("setID", setID));
        pairs.add(new BasicNameValuePair("owned", own));

        return doRequest(Constants.REQUEST_SET_OWN, pairs);
    }

    public String setCollectionOwnQuantity(String userHash, String setID, String qtyOwned) {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("apiKey", Constants.API_KEY));
        pairs.add(new BasicNameValuePair("userHash", userHash));
        pairs.add(new BasicNameValuePair("setID", setID));
        pairs.add(new BasicNameValuePair("qtyOwned", qtyOwned));

        return doRequest(Constants.REQUEST_SET_OWN_QUANTITY, pairs);
    }

    public String setCollectionWant(String userHash, String setID, String want) {

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("apiKey", Constants.API_KEY));
        pairs.add(new BasicNameValuePair("userHash", userHash));
        pairs.add(new BasicNameValuePair("setID", setID));
        pairs.add(new BasicNameValuePair("wanted", want));

        return doRequest(Constants.REQUEST_SET_WANT, pairs);
    }


    @Override
    protected void onProgressUpdate(final Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String xml) {
        progressDialog.dismiss();
        callback.handleResponse(requestMethod, xml);

    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "Loading Dialog");
        progressDialog = new ProgressDialog((Context) activity);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        //progressDialog.setMax(100);
        progressDialog.setCancelable(true);
        // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        cancel(true);
                    }
                });
        progressDialog.show();
    }

    @Override
    protected void onCancelled() {
        handleOnCancelled();
    }

    // user cancelled progressdialog => gets called instead of
    // onPostExecute()
    private void handleOnCancelled() {
        callback.handleResponse(null, null);
        progressDialog.dismiss();
    }
}
