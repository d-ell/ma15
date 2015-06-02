package ma15.brickcollector.uiTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.core.AllOf;

import ma15.brickcollector.R;
import ma15.brickcollector.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;

public class RegisterTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = RegisterTest.class.getName();

    public RegisterTest() {
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
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Browse"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        super.tearDown();
    }

    public void testRegisterWebViewEnabled() {

        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        DrawerActions.openDrawer(R.id.drawer_layout);

        // The drawer should now be open.
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));


        // click on Item in NavigationDrawer
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Register")))
                .perform(click());
        Espresso.onView(withId(R.id.webview)).check(matches(isDisplayed()));
    }

    public void testActionStop() {
        testRegisterWebViewEnabled();

        onView(withId(R.id.action_stop)).check(matches(isDisplayed()));
        onView(withId(R.id.action_stop)).check(matches(isEnabled()));

        onView(withId(R.id.action_stop)).perform(click());

        onView(withId(R.id.action_stop)).check(doesNotExist());
    }
}
