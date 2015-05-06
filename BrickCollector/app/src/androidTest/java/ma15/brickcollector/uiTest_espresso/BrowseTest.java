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

import junit.framework.Test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;
import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;
import ma15.brickcollector.data.BrickSet;

import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

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

    public void testDisabledEnabledSearchButton() {
        Integer []ids = {R.id.txtQuery, R.id.txtTheme, R.id.txtYear};
        String testStringInput = "123";

        for(int id : ids) {
            Espresso.onView(withId(id))
                    .check(matches(withText("")));
        }

        Espresso.onView(withId(R.id.btnGo)).check(matches(not(isEnabled())));

        List<List<Integer>> powerSet = TestHelper.getPowerSetOfArray(Arrays.asList(ids));
        for(List<Integer> set : powerSet) {
            for(Integer value : set) {
                Espresso.onView(withId(value)).perform(typeText(testStringInput));
            }
            Espresso.onView(withId(R.id.btnGo)).check(matches(isEnabled()));
            for(Integer value : set) {
                Espresso.onView(withId(value)).perform(clearText());
            }
        }
    }


    public void testBrowseBatman() {

        Espresso.onView(ViewMatchers.withId(R.id.txtQuery)).perform(typeText("Batman"));

        Espresso.onView(ViewMatchers.withId(R.id.btnGo)).perform(click());

        //check if Element is in Listview - Based on given String in name of Bricksetdata
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(matchBatman("Batman")));

        //click if Element is in Listview - Based on given String in name of Bricksetdata
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview), matchBatman("Batman"))).
                perform(click());

        //Espresso.onData(matchString("Batman")).inAdapterView(ViewMatchers.withId(R.id.listview)).atPosition(0).perform(ViewActions.click());


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
