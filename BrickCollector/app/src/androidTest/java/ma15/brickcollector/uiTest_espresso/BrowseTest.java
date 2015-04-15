package ma15.brickcollector.uiTest_espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;

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

        //Espresso.onData(Mat).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

}
