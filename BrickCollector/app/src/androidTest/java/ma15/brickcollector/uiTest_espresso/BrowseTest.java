package ma15.brickcollector.uiTest_espresso;

import android.provider.CalendarContract;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.Map;

import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;

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

        //Espresso.onView(ViewMatchers.withId(R.id.listview)).check(ViewAssertions.matches(withAdaptedData(ViewMatchers.withChild(ViewMatchers.withText("4526: Batman")))));
        Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.title), Is.is(IsInstanceOf.instanceOf(Map.class)), ViewMatchers.withText("Batman")));
    }


    private static Matcher<View> withAdaptedData(final Matcher<View> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

}
