package ma15.brickcollector.uiTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.core.AllOf;

import ma15.brickcollector.R;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.util.TestHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class LoginTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = LoginTest.class.getName();
    MainActivity activity;

    public LoginTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {

        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Browse"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        super.tearDown();
    }

    public void testLoginLogout() {

        doLogin();

        //closeDrawer(R.id.drawer_layout);

        // Drawer should be closed again.
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        DrawerActions.openDrawer(R.id.drawer_layout);

        // The drawer should now be open.
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        /*onData(allOf(is(instanceOf(String.class)), is("Logout"))).
                check(ViewAssertions.matches(ViewMatchers.withText("Logout")));*/

        doLogout();

    }

    public static void doLogout() {

        //Only logout when logged in
        if(UserManager.getInstance().checkLogin()) {
            DrawerActions.openDrawer(R.id.drawer_layout);

            TestHelper.drawerTestLoggedIn();

            //click on Item in NavigationDrawer
            onData(allOf(is(instanceOf(String.class)), is("Logout")))
                    .perform(click());

            onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

            DrawerActions.openDrawer(R.id.drawer_layout);

            // The drawer should now be open.
            onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

            onData(allOf(is(instanceOf(String.class)), is("Login"))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }


    public static void doLogin() {
        //Only login when logged out
        if(!UserManager.getInstance().checkLogin()) {
            onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

            DrawerActions.openDrawer(R.id.drawer_layout);

            TestHelper.drawerTestLoggedOut();

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

    public void testDisabledEnabledSearchButton() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));
        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(allOf(is(instanceOf(String.class)), is("Login")))
                .perform(click());

        Integer []ids = {R.id.txtUser, R.id.txtPassword};
        String testStringInput = "a";

        // all fields empty => button diasbled
        for(int id : ids) {
            Espresso.onView(withId(id))
                    .check(matches(withText("")));
        }

        Espresso.onView(withId(R.id.btnLogin)).check(matches( not( isEnabled())));

        for(int id : ids) {
            Espresso.onView(withId(id)).perform(typeText(testStringInput));
        }
        Espresso.onView(withId(R.id.btnLogin)).check(matches(isEnabled()));
        for(int id : ids) {
            Espresso.onView(withId(id)).perform(clearText());
        }
        Espresso.onView(withId(R.id.btnLogin)).check(matches( not( isEnabled())));

    }

}
