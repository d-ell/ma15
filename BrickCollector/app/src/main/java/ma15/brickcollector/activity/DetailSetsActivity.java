package ma15.brickcollector.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ma15.brickcollector.connection.PostRequest;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.image.ImageLoader;
import ma15.brickcollector.R;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.connection.Callback;
import ma15.brickcollector.connection.HTTPDispatcher;


public class DetailSetsActivity extends ActionBarActivity implements Callback {

    BrickSet set = null;
    public ImageLoader imageLoader = null;

    final static String TAG = DetailSetsActivity.class.getName();
    private CheckBox ckbOwn;
    private CheckBox ckbWant;
    private EditText txtOwnQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sets);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        ckbOwn = (CheckBox) findViewById(R.id.chbx_detail_own);
        ckbWant = (CheckBox) findViewById(R.id.chbx_detail_wish);
        txtOwnQuantity = (EditText) findViewById(R.id.txt_detail_own_quantity);

        LinearLayout collectionLayout = (LinearLayout) findViewById(R.id.layout_collection);

        if (UserManager.getInstance().getUserHash() != null) {
            setCollectionUIValues();

            ckbOwn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!HTTPDispatcher.isConnected(DetailSetsActivity.this)) {
                        Toast.makeText(DetailSetsActivity.this,
                                "Not connected.", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }

                    ProgressBar progress = (ProgressBar) DetailSetsActivity.this.findViewById(R.id.progressBar);

                    // start asynchronous search => doGetRequest makes callback
                    // to handleResponse()
                    HTTPDispatcher dispatcher = new HTTPDispatcher();
                    new PostRequest(DetailSetsActivity.this, DetailSetsActivity.this, Constants.SET_OWN, progress).
                            execute(UserManager.getInstance().getUserHash(),
                                    set.getSetID(),
                                    isChecked ? "1" : "0");

                    txtOwnQuantity.setEnabled(isChecked);
                }
            });

            ckbWant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!HTTPDispatcher.isConnected(DetailSetsActivity.this)) {
                        Toast.makeText(DetailSetsActivity.this,
                                "Not connected.", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }

                    ProgressBar progress = (ProgressBar) DetailSetsActivity.this.findViewById(R.id.progressBar);

                    // start asynchronous search => doGetRequest makes callback
                    // to handleResponse()
                    HTTPDispatcher dispatcher = new HTTPDispatcher();
                    new PostRequest(DetailSetsActivity.this, DetailSetsActivity.this, Constants.SET_WANT, progress).
                            execute(UserManager.getInstance().getUserHash(),
                                    set.getSetID(),
                                    isChecked ? "1" : "0");
                }
            });

            //TODO: wird nie aufgerufen, da edittext focus nicht verliert
            txtOwnQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        if(!HTTPDispatcher.isConnected(DetailSetsActivity.this)) {
                            Toast.makeText(DetailSetsActivity.this,
                                    "Not connected.", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        ProgressBar progress = (ProgressBar) DetailSetsActivity.this.findViewById(R.id.progressBar);

                        // start asynchronous search => doGetRequest makes callback
                        // to handleResponse()
                        HTTPDispatcher dispatcher = new HTTPDispatcher();
                        new PostRequest(DetailSetsActivity.this, DetailSetsActivity.this, Constants.SET_OWN_QUANTITIY, progress).
                                execute(UserManager.getInstance().getUserHash(),
                                        set.getSetID(),
                                        txtOwnQuantity.getText().toString());
                    }
                }
            });

        } else {
            collectionLayout.setVisibility(View.GONE);
        }

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

    }

    private void setCollectionUIValues() {
        txtOwnQuantity.setText(set.getQtyOwned());
        ckbOwn.setChecked(set.isOwned().toLowerCase().equals("true"));
        ckbWant.setChecked(set.isWanted().toLowerCase().equals("true"));
        if(!set.isOwned().toLowerCase().equals("true")) {
            txtOwnQuantity.setEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detail_sets, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void handleResponse(String requestMethod, String xml) {
        Log.d(TAG, "Login result: " + xml);

        String xmlResult = XmlParser.getXMLResultString(xml);
        Log.d(TAG, "xml result: " + xmlResult);

        if(xmlResult == null || xmlResult.contains(Constants.ERROR) ||
                xmlResult.contains(Constants.INVALID_KEY)) {
            this.setCollectionUIValues();
            return;
        }

        if (requestMethod.equals(Constants.SET_OWN) && ckbOwn.isChecked()) {
            txtOwnQuantity.setText("1");
            set.setOwned("true");
            set.setQtyOwned("1");
        } else if (requestMethod.equals(Constants.SET_OWN) && !ckbOwn.isChecked()) {
            txtOwnQuantity.setText("0");
            set.setOwned("false");
            set.setQtyOwned("0");
        } else if (requestMethod.equals(Constants.SET_WANT)) {
            set.setWanted(ckbWant.isChecked() ? "true" : "false");
        } else if (requestMethod.equals(Constants.SET_OWN_QUANTITIY)) {
            set.setQtyOwned(txtOwnQuantity.getText().toString());
        }
    }
}
