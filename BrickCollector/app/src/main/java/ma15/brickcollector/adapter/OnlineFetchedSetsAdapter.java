package ma15.brickcollector.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ma15.brickcollector.BrickSet;
import ma15.brickcollector.ImageLoader;
import ma15.brickcollector.R;

public class OnlineFetchedSetsAdapter extends BaseAdapter {
 
    private Activity activity;
    private List<BrickSet> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader; 
 
    public OnlineFetchedSetsAdapter(Activity a, List<BrickSet> sets) {
        activity = a;
        data = sets;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView) vi.findViewById(R.id.title);
        TextView pieces = (TextView) vi.findViewById(R.id.pieces);
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image);
        RatingBar ratingbar = (RatingBar)vi.findViewById(R.id.ratingBar_local);

        BrickSet brickSet = data.get(position);

        title.setText(brickSet.getNumber() + ": " + brickSet.getName());
        pieces.setText(activity.getResources().getString(R.string.pieces) + ": " + brickSet.getPieces());
        ratingbar.setRating(Float.valueOf(brickSet.getRating()));

        imageLoader.DisplayImage(brickSet.getThumbnailURL(), thumb_image);
        return vi;
    }
}