package ma15.brickcollector.uiTest_espresso;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import ma15.brickcollector.R;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by thomas on 04.05.15.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public LoginTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }


    public void testLoginLogout() {

        doLogin();

        //closeDrawer(R.id.drawer_layout);

        // Drawer should be closed again.
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        DrawerActions.openDrawer(R.id.drawer_layout);

        // The drawer should now be open.
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        onData(allOf(is(instanceOf(String.class)), is("Logout"))).
                check(ViewAssertions.matches(ViewMatchers.withText("Logout")));

        doLogout();

    }

    public static void doLogout() {

        //click on Item in NavigationDrawer
        onData(allOf(is(instanceOf(String.class)), is("Logout")))
                .perform(click());

        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        DrawerActions.openDrawer(R.id.drawer_layout);

        // The drawer should now be open.
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        onData(allOf(is(instanceOf(String.class)), is("Login"))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    public static void doLogin() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        DrawerActions.openDrawer(R.id.drawer_layout);

        // The drawer should now be open.
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));


        //click on Item in NavigationDrawer
        onData(allOf(is(instanceOf(String.class)), is("Login")))
                .perform(click());


        //now do the login stuff
        onView(withId(R.id.txtUser)).perform(ViewActions.typeText(Constants.TESTUSER_NAME));
        onView(withId(R.id.txtPassword)).perform(ViewActions.typeText(Constants.TESTUSER_PW));

        onView(withId(R.id.btnLogin)).perform(ViewActions.click());
    }

}
