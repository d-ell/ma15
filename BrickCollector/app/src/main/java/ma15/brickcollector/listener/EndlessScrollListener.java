package ma15.brickcollector.listener;

import android.widget.AbsListView;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.connection.LoadSets;

public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = false;
    private LoadSets setLoader;

    public EndlessScrollListener() {
    }
    public EndlessScrollListener(LoadSets activity, int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
        this.setLoader = activity;
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

            if(totalItemCount != 0 && totalItemCount < Integer.parseInt(Constants.BROWSE_PAGE_SIZE)) {
                return;
            }

            setLoader.loadSets(currentPage + 1);

            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}