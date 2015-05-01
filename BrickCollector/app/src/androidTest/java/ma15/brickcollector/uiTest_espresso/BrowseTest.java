package ma15.brickcollector.uiTest_espresso;

import android.provider.CalendarContract;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.List;
import java.util.Map;

import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;
import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;
import ma15.brickcollector.data.BrickSet;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by thomas on 09.04.15.
 */
public class BrowseTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public BrowseTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }


    public void testBrowseBatman() {

        Espresso.onView(ViewMatchers.withId(R.id.txtQuery)).perform(ViewActions.typeText("Batman"));

        Espresso.onView(ViewMatchers.withId(R.id.btnGo)).perform(ViewActions.click());

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(matchBatman("Batman")));

    }


    private static Matcher<View> matchBatman(String batman) {
        return matchBatman(equalTo(batman));
    }

    private static Matcher<View> matchBatman(final Matcher<String> dataMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {

                if (!(view instanceof ListView)) {
                    return false;
                }

                ListView listView = (ListView) view;
                OnlineFetchedSetsAdapter adapter = (OnlineFetchedSetsAdapter) listView.getAdapter();
                List<BrickSet> data = adapter.getData();

                for (int i = 0; i < data.size(); i++) {

                    if (dataMatcher.matches(data.get(i).getName())) {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with content: " + description);
            }
        };
    }

}
