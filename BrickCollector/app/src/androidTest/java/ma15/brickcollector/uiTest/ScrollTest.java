package ma15.brickcollector.uiTest;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import junit.framework.Assert;

import java.util.ArrayList;

import ma15.brickcollector.R;
import ma15.brickcollector.activity.ListOnlineFetchedSetsActivity;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.util.TestHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by thomas on 09.04.15.
 */
public class ScrollTest extends ActivityInstrumentationTestCase2<ListOnlineFetchedSetsActivity> {

    private static final String TAG = ScrollTest.class.getName();
    ListOnlineFetchedSetsActivity activity = null;

    public ScrollTest() {
        super(ListOnlineFetchedSetsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    private void initActivity(String query) {
        Intent intent = new Intent();
        intent.putExtra("query", query);
        intent.putExtra("theme","");
        intent.putExtra("year","");
        intent.putExtra("bOwn",false);
        intent.putExtra("bWant",false);
        setActivityIntent(intent);

        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testScrollAll() {
        initActivity("Batman");

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNameInList("Batman")));

        int result_counter = 0;
        ArrayList<BrickSet> last_results = activity.getLastResults();
        Assert.assertNotNull(last_results);
        Assert.assertFalse(last_results.isEmpty());
        result_counter += last_results.size();

        /*
        onData(anything()) <- also worked
        onData(is(instanceOf(BrickSet.class)))
                    .inAdapterView(withId(R.id.listview))
                    .atPosition(0)
                    .perform(scrollTo());
        */

        while(!last_results.isEmpty()) {
            Log.d(TAG, "result size: " + last_results.size());
            Log.d(TAG, "result counter: " + result_counter);
            for (BrickSet set : last_results) {
                Log.d(TAG, " " + set.getName());
            }
            // NOTE: SCROLLING TO ITEM
            // workaround for scrolling to a specific item: https://groups.google.com/forum/#!msg/android-test-kit-discuss/k50qXMmJ2BI/pZs-dXskC40J
            onData(instanceOf(Integer.class))
                    .inAdapterView(allOf(withId(R.id.listview), isDisplayed()))
                    .atPosition(result_counter - 1)
                    .check(matches(isDisplayed()));

            last_results = activity.getLastResults();
            result_counter += last_results.size();
        }

        onView(withText(R.string.noMoreDataToLoad));
    }

    public void testNonScroll() {
        initActivity("4526");
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("4526")));

        int result_counter = 0;
        ArrayList<BrickSet> last_results = (ArrayList<BrickSet>) TestHelper.cloneBrickSetList(activity.getLastResults());
        Assert.assertNotNull(last_results);
        Assert.assertFalse(last_results.isEmpty());
        result_counter += last_results.size();

        onData(instanceOf(Integer.class))
                .inAdapterView(allOf(withId(R.id.listview), isDisplayed()))
                .atPosition(result_counter - 1)
                .check(matches(isDisplayed()));

        ArrayList<BrickSet> new_results = activity.getLastResults();

        assertEquals(last_results.size(), new_results.size());
        for(BrickSet r : new_results) {
            for(BrickSet lr : last_results) {
                assertEquals(lr, r);
            }
        }

        onView(not(withText(R.string.noMoreDataToLoad)));

    }

}
