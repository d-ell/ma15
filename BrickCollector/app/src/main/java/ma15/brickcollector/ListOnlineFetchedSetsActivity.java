package ma15.brickcollector;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;

public class ListOnlineFetchedSetsActivity extends ActionBarActivity {

	List<BrickSet> sets = null;
	ListView list;
	OnlineFetchedSetsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sets);

        sets = getIntent().getParcelableArrayListExtra("mylist");

		list = (ListView) findViewById(R.id.listview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Getting adapter by passing xml data ArrayList
		adapter = new OnlineFetchedSetsAdapter(this, sets);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

                Toast.makeText(ListOnlineFetchedSetsActivity.this, "Clicked.", Toast.LENGTH_SHORT).show();

                /*

				BrickSet brickSet = null;
				List<BrickSet> tmp = sets;

				try {
                    brickSet = (BrickSet) tmp.get(position);
				} catch (ClassCastException e) {
					System.out.println("Could not cast!");
					e.printStackTrace();
				}

				Intent intent = new Intent(getApplicationContext(),
						DetailEditMovieActivity.class);
				// sending data to new activity
				intent.putExtra("movie", movie);
				startActivity(intent); */
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.list_movies, menu);
		return true;
	}
}
