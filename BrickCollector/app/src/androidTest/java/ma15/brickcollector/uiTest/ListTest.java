package ma15.brickcollector.uiTest;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import ma15.brickcollector.R;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.activity.ListOnlineFetchedSetsActivity;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.util.TestHelper;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.allOf;


public class ListTest extends ActivityInstrumentationTestCase2<ListOnlineFetchedSetsActivity> {

    private static final String TAG = ListTest.class.getName();
    ListOnlineFetchedSetsActivity activity = null;

    public ListTest() {
        super(ListOnlineFetchedSetsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    private void initActivity(boolean bOwn, boolean bWant) {
        Intent intent = new Intent();
        intent.putExtra("query", "");
        intent.putExtra("theme", "");
        intent.putExtra("year", "");
        intent.putExtra("bOwn", bOwn);
        intent.putExtra("bWant", bWant);
        setActivityIntent(intent);

        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWishlist() {
        UserManager.getInstance().setUserHash(Constants.TESTUSER_HASH,null);
        initActivity(false,true);

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("30303")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("42009")));

        ArrayList<BrickSet> last_results = activity.getLastResults();
        assertNotNull(last_results);
        assertEquals(last_results.size(),2);
    }

    public void testOwnlist() {
        UserManager.getInstance().setUserHash(Constants.TESTUSER_HASH,null);
        initActivity(true,false);

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("101")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("161")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("6858")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("6860")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("10937")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(matches(TestHelper.matchBrickSetNumberInList("30303")));

        ArrayList<BrickSet> last_results = activity.getLastResults();
        assertNotNull(last_results);
        assertEquals(last_results.size(),6);
    }
}
