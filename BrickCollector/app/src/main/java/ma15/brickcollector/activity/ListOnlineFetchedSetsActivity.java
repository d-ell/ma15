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

    public static final int CALLBACK_DETAIL_CODE = 1;
    List<BrickSet> sets = null;
	ListView list;
	OnlineFetchedSetsAdapter adapter;
    ArrayList<BrickSet> last_results = new ArrayList<BrickSet>();

    String strQuery = null;
    String strTheme = null;
    String strYear = null;
    boolean bOwn = false;
    boolean bWant = false;
    BrickSet selectedBrickSet = new BrickSet();

    public ArrayList<BrickSet> getLastResults() {
        return last_results;
    }

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
            List<BrickSet> tmp = sets;

            try {
                selectedBrickSet = tmp.get(position);
            } catch (ClassCastException e) {
                System.out.println("Could not cast!");
                e.printStackTrace();
            }

            Intent intent = new Intent(getApplicationContext(),
                    DetailSetsActivity.class);
            // sending data to new activity
            intent.putExtra("set", selectedBrickSet);
            startActivityForResult(intent, CALLBACK_DETAIL_CODE);
			}
		});
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ListOnlineFetchedSetsActivity", "onActivityResult");
        if (requestCode == CALLBACK_DETAIL_CODE) {
            if(resultCode == RESULT_OK){
                BrickSet set = data.getParcelableExtra("set");
                selectedBrickSet.setQtyOwned(set.getQtyOwned());
                selectedBrickSet.setOwned(set.isOwned());
                selectedBrickSet.setWanted(set.isWanted());
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
                    getString(R.string.notConnected), Toast.LENGTH_SHORT)
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

        last_results.clear();
        last_results = XmlParser.getSets(xml);
        if (last_results == null || last_results.isEmpty()) {
            last_results = new ArrayList<BrickSet>();
            Toast.makeText(this, getString(R.string.noMoreDataToLoad), Toast.LENGTH_SHORT).show();
        } else {
            for(BrickSet set : last_results) {
                sets.add(set);
            }

            adapter.notifyDataSetChanged();
        }
    }
}
