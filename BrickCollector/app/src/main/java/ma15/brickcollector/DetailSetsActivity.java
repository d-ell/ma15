package ma15.brickcollector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class DetailSetsActivity extends ActionBarActivity {

    BrickSet set = null;
    public ImageLoader imageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sets);

        set = getIntent().getParcelableExtra("set");
        imageLoader = new ImageLoader(this.getApplicationContext());

        final ImageView image = (ImageView) findViewById(R.id.detail_image);
        RatingBar ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        TextView number = (TextView) findViewById(R.id.detail_number);
        TextView name = (TextView) findViewById(R.id.detail_name);
        TextView theme = (TextView) findViewById(R.id.detail_theme);
        TextView subtheme = (TextView) findViewById(R.id.detail_subtheme);
        TextView year = (TextView) findViewById(R.id.detail_year);
        TextView pieces = (TextView) findViewById(R.id.detail_pieces);
        TextView minifigs = (TextView) findViewById(R.id.detail_minifigs);
        TextView packaging = (TextView) findViewById(R.id.detail_packagingtype);
        TextView review = (TextView) findViewById(R.id.detail_reviewcount);
        TextView availability = (TextView) findViewById(R.id.detail_availability);
        TextView price_uk = (TextView) findViewById(R.id.detail_price_uk);
        TextView price_us = (TextView) findViewById(R.id.detail_price_us);
        TextView price_eu = (TextView) findViewById(R.id.detail_price_eu);
        TextView price_ca = (TextView) findViewById(R.id.detail_price_ca);

        imageLoader.DisplayImage(set.getLargeThumbnailURL(), image);
        ratingbar.setRating(Float.valueOf(set.getRating()));

        String numberVariant = "";
        if(!set.getNumberVariant().equals("0") && !set.getNumberVariant().isEmpty()) {
            numberVariant = set.getNumberVariant();
        }

        number.setText(set.getNumber() + " (" + numberVariant + ")");
        name.setText(set.getName());
        theme.setText(set.getTheme());
        subtheme.setText(set.getSubtheme());
        year.setText(set.getYear());
        pieces.setText(set.getPieces());
        minifigs.setText(set.getMinifigs());
        packaging.setText(set.getPackagingType());
        availability.setText(set.getAvailability());
        review.setText(set.getReviewCount());

        if(!set.getEURetailPrice().equals("0") && !set.getEURetailPrice().isEmpty()) {
            price_eu.setText(set.getEURetailPrice() + " " + getResources().getString(R.string.detail_price_eu_abbr));
        }

        if(!set.getUKRetailPrice().equals("0") && !set.getUKRetailPrice().isEmpty()) {
            price_uk.setText(set.getUKRetailPrice() + " " + getResources().getString(R.string.detail_price_uk_abbr));
        }

        if(!set.getUSRetailPrice().equals("0") && !set.getUSRetailPrice().isEmpty()) {
            price_us.setText(set.getUSRetailPrice() + " " + getResources().getString(R.string.detail_price_us_abbr));
        }

        if(!set.getCARetailPrice().equals("0") && !set.getCARetailPrice().isEmpty()) {
            price_ca.setText(set.getCARetailPrice() + " " + getResources().getString(R.string.detail_price_ca_abbr));
        }

        /*



        private String UKRetailPrice = "";
        private String USRetailPrice = "";
        private String CARetailPrice = "";
        private String EURetailPrice = "";*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detail_sets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
