package ma15.brickcollector.connection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HTTPDispatcher {

    final static String TAG = HTTPDispatcher.class.getName();

    public HTTPDispatcher() {
        // TODO Auto-generated constructor stub
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null
                || connectivity.getActiveNetworkInfo() == null
                || !connectivity.getActiveNetworkInfo()
                .isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    // <Params, Progress, Result>
    public class doPostRequest extends AsyncTask<String, Integer, String> {

        private Callback callback = null;
        private Activity activity = null;

        private ProgressDialog progressDialog = null;

        public doPostRequest(Activity activity, Callback callback, ProgressBar progress) {
            this.callback = callback;
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            // we should also xml parse here ?
            return getSets(params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String xml) {
            progressDialog.dismiss();
            callback.handleResponse(xml);

        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Loading Dialog");
            progressDialog = new ProgressDialog((Context) activity);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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
            callback.handleResponse(null);
            progressDialog.dismiss();
        }

        public String getSets(String query, String theme, String year, String user_hash) {

            // move this
            final String key = "F1PE-3JWB-BwfC";

            String url = "http://brickset.com/api/v2.asmx/getSets";
            // String contentParameter = "apiKey";
            // int length = contentParameter.length() + key.length() + 1;

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                List<NameValuePair> pairs = new ArrayList<>();
                pairs.add(new BasicNameValuePair("apiKey", key));
                pairs.add(new BasicNameValuePair("userHash", user_hash));
                pairs.add(new BasicNameValuePair("query", query));
                pairs.add(new BasicNameValuePair("theme", theme));
                pairs.add(new BasicNameValuePair("subtheme", ""));
                pairs.add(new BasicNameValuePair("setNumber", ""));
                pairs.add(new BasicNameValuePair("year", year));
                pairs.add(new BasicNameValuePair("owned", ""));
                pairs.add(new BasicNameValuePair("wanted", ""));
                pairs.add(new BasicNameValuePair("orderBy", ""));
                pairs.add(new BasicNameValuePair("pageSize", ""));
                pairs.add(new BasicNameValuePair("pageNumber", ""));
                pairs.add(new BasicNameValuePair("userName", ""));

                UrlEncodedFormEntity urlEncodeEntity = new UrlEncodedFormEntity(pairs);
                long length = urlEncodeEntity.getContentLength();
                httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                httppost.setHeader("Content-Length", String.valueOf(length));
                httppost.setEntity(new UrlEncodedFormEntity(pairs));

                /* debugging ***/
                for(Header head : httppost.getAllHeaders()) {
                    Log.v(TAG, head.getName() + ":" + head.getValue());
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                httppost.getEntity().writeTo(out);
                Log.v(TAG, "Entity: " + out.toString());
                Log.v(TAG, "Entity Length: " + out.toString().length());
                /* debugging ***/

                HttpResponse httpResponse = client.execute(httppost);

                String result = EntityUtils.toString(httpResponse.getEntity());

                Log.d(TAG, "POST result: " + result);

                // dummy for progress
                publishProgress(100);

                return result;

                /*

                if (result != null && result.length() > 0) {
                    return true;
                } else {

                }*/

            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            }

            return "";
        }

        /*
		private String getSets(String urlToRead) {
			URL url;
			HttpURLConnection conn;
			BufferedReader rd;
			String line;
			final int capacity_estimate = 100;
			StringBuffer result = new StringBuffer(capacity_estimate);

			try {

				url = new URL(urlToRead);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));

				int length = conn.getContentLength();
				long total = 0;

				while ((line = rd.readLine()) != null) {
					total += line.length();
					publishProgress((int) (total * 100 / length));
					if (isCancelled())
						return null;
					result.append(line);
				}

				rd.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result.toString();
		}

	}*/

    }
}
