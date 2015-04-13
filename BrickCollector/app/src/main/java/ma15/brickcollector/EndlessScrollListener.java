package ma15.brickcollector;

import android.widget.AbsListView;
import android.widget.Toast;

public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = false;
    private ListOnlineFetchedSetsActivity activity;

    public EndlessScrollListener() {
    }
    public EndlessScrollListener(ListOnlineFetchedSetsActivity activity, int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
        this.activity = activity;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            /*Toast.makeText(activity,
                    "Load page " + (currentPage + 1), Toast.LENGTH_SHORT)
                    .show();*/

            activity.loadSets(currentPage + 1);

            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}