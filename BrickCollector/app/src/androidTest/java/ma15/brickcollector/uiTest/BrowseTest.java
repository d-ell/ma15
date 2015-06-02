package ma15.brickcollector.uiTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.core.AllOf;
import org.hamcrest.core.Is;

import java.util.Arrays;
import java.util.List;

import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;
import ma15.brickcollector.util.TestHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class BrowseTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = BrowseTest.class.getName();

    public BrowseTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Override
    protected void tearDown() throws Exception {

        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(Is.is(instanceOf(String.class)), Is.is("Browse"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        super.tearDown();
    }

    public void testDisabledEnabledSearchButton() {
        Integer []ids = {R.id.txtQuery, R.id.txtTheme, R.id.txtYear};
        String testStringInput = "123";

        // all fields empty => button diasbled
        for(int id : ids) {
            Espresso.onView(withId(id))
                    .check(matches(withText("")));
        }

        Espresso.onView(withId(R.id.btnGo)).check(matches(not(isEnabled())));

        // if at least 1 field is not empty => button enabled
        List<List<Integer>> powerSet = TestHelper.getPowerSetOfArray(Arrays.asList(ids));
        for(List<Integer> set : powerSet) {
            for(Integer value : set) {
                Espresso.onView(withId(value)).perform(typeText(testStringInput));
            }
            Espresso.onView(withId(R.id.btnGo)).check(matches(isEnabled()));
            for(Integer value : set) {
                Espresso.onView(withId(value)).perform(clearText());
            }
            Espresso.onView(withId(R.id.btnGo)).check(matches(not(isEnabled())));
        }
    }


    public void testBrowseBatman() {

        Espresso.onView(ViewMatchers.withId(R.id.txtQuery)).perform(typeText("Batman"));

        Espresso.onView(ViewMatchers.withId(R.id.btnGo)).perform(click());

        //check if Element is in Listview - Based on given String in name of Bricksetdata
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNameInList("Batman")));

        //click if Element is in Listview - Based on given String in name of Bricksetdata
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview), TestHelper.matchBrickSetNameInList("Batman"))).
                perform(click());

        //Espresso.onData(matchString("Batman")).inAdapterView(ViewMatchers.withId(R.id.listview)).atPosition(0).perform(ViewActions.click());

        Espresso.pressBack();
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNameInList("Batman")));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.txtQuery))
                .check(matches(withText("Batman")));
    }

}
