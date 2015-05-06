package ma15.brickcollector.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import ma15.brickcollector.connection.PostRequest;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.listener.EndlessScrollListener;
import ma15.brickcollector.connection.LoadSets;
import ma15.brickcollector.R;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.connection.Callback;
import ma15.brickcollector.connection.HTTPDispatcher;

public class ListOnlineFetchedSetsActivity extends ActionBarActivity implements Callback, LoadSets {

	List<BrickSet> sets = null;
	ListView list;
	OnlineFetchedSetsAdapter adapter;
    String strQuery = null;
    String strTheme = null;
    String strYear = null;
    boolean bOwn = false;
    boolean bWant = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sets);

        //sets = getIntent().getParcelableArrayListExtra("mylist");
        sets = new ArrayList<>();
        strQuery = getIntent().getStringExtra("query");
        strTheme = getIntent().getStringExtra("theme");
        strYear = getIntent().getStringExtra("year");
        bOwn = getIntent().getBooleanExtra("bOwn",false);
        bWant = getIntent().getBooleanExtra("bWant",false);

		list = (ListView) findViewById(R.id.listview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Getting adapter by passing xml data ArrayList
		adapter = new OnlineFetchedSetsAdapter(this, sets);
		list.setAdapter(adapter);

        list.setOnScrollListener(new EndlessScrollListener(this,0));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

            BrickSet brickSet = null;
            List<BrickSet> tmp = sets;

            try {
                brickSet = tmp.get(position);
            } catch (ClassCastException e) {
                System.out.println("Could not cast!");
                e.printStackTrace();
            }

            Intent intent = new Intent(getApplicationContext(),
                    DetailSetsActivity.class);
            // sending data to new activity
            intent.putExtra("set", brickSet);
            startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.list_movies, menu);
		return true;
	}

    public void loadSets(int pageNumber) {
        if(!HTTPDispatcher.isConnected(this)) {
            Toast.makeText(this,
                    "Not connected.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);

        // start asynchronous search => doGetRequest makes callback
        // to handleResponse()
        HTTPDispatcher dispatcher = new HTTPDispatcher();
        new PostRequest(this, this, Constants.BROWSE, progress).execute(strQuery,
                strTheme,
                strYear,
                UserManager.getInstance().getUserHash(),
                bOwn ? "1" : "",
                bWant ? "1" : "",
                Integer.toString(pageNumber));
    }

    @Override
    public void handleResponse(String requestMethod, String xml) {
        if (xml == null || xml.isEmpty()) {
            Toast.makeText(this, "XML is empty. Should never happen", Toast.LENGTH_SHORT).show();
        }

        ArrayList<BrickSet> results = XmlParser.getSets(xml);
        if (results == null || results.isEmpty()) {
            Toast.makeText(this, "No more data to load.", Toast.LENGTH_SHORT).show();
        } else {
            for(BrickSet set : results) {
                sets.add(set);
            }

            adapter.notifyDataSetChanged();
        }
    }
}
