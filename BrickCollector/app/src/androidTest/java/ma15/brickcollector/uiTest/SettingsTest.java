package ma15.brickcollector.uiTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.core.AllOf;

import ma15.brickcollector.R;
import ma15.brickcollector.Utils.Settings;
import ma15.brickcollector.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class SettingsTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = DetailTest.class.getName();
    MainActivity activity;

    public SettingsTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Browse"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        super.tearDown();
    }

    public void testLoadSettingsWhenLoggedIn() {

        LoginTest.doLogin();
        Settings.loadSettings(activity);

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Settings"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        onView(withId(R.id.txtPageSize)).
                check(matches(withText(Settings.getPageSize())));

        boolean shouldBeChecked = Boolean.parseBoolean(Settings.getKeepLogin());

        if(shouldBeChecked) {
            onView(withId(R.id.chbxKeepLogin)).
                    check(matches(isChecked()));
        } else {
            onView(withId(R.id.chbxKeepLogin)).
                    check(matches(isNotChecked()));
        }

        LoginTest.doLogout();

    }

    public void testLoadSettingsWhenLoggedOut() {

        Settings.loadSettings(activity);

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Settings"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        onView(withId(R.id.txtPageSize)).
                check(matches(withText(Settings.getPageSize())));

        boolean shouldBeChecked = Boolean.parseBoolean(Settings.getKeepLogin());

        if(shouldBeChecked) {
            onView(withId(R.id.chbxKeepLogin)).
                    check(matches(isChecked()));
        } else {
            onView(withId(R.id.chbxKeepLogin)).
                    check(matches(isNotChecked()));
        }

    }

    public void testSaveSettings() {
        Settings.loadSettings(activity);
        String oldPageSize = Settings.getPageSize();
        String newPageSize = "50";
        boolean wasChecked = Boolean.parseBoolean(Settings.getKeepLogin());

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Settings"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        onView(withId(R.id.txtPageSize)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.txtPageSize)).perform(typeText(newPageSize), closeSoftKeyboard());

        onView(withId(R.id.chbxKeepLogin)).perform(click());

        if(wasChecked) {
            onView(withId(R.id.chbxKeepLogin)).check(matches(isNotChecked()));
        } else {
            onView(withId(R.id.chbxKeepLogin)).check(matches(isChecked()));
        }

        onView(withId(R.id.btnSave)).perform(click());

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Browse"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Settings"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        onView(withId(R.id.txtPageSize)).
                check(matches(withText(newPageSize)));

        if(wasChecked) {
            onView(withId(R.id.chbxKeepLogin)).
                    check(matches(isNotChecked()));
        } else {
            onView(withId(R.id.chbxKeepLogin)).
                    check(matches(isChecked()));
        }

        //Restore old values
        onView(withId(R.id.txtPageSize)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.txtPageSize)).perform(typeText(oldPageSize), closeSoftKeyboard());

        onView(withId(R.id.chbxKeepLogin)).perform(click());

        if(wasChecked) {
            onView(withId(R.id.chbxKeepLogin)).check(matches(isChecked()));
        } else {
            onView(withId(R.id.chbxKeepLogin)).check(matches(isNotChecked()));
        }

        onView(withId(R.id.btnSave)).perform(click());
    }

    public void testDisabledEnabledSaveButton() {
        Settings.loadSettings(activity);

        openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Settings"))).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        onView(withId(R.id.txtPageSize)).
                check(matches(withText(Settings.getPageSize())));
        onView(withId(R.id.btnSave)).check(matches(isEnabled()));

        onView(withId(R.id.txtPageSize)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSave)).check(matches(not(isEnabled())));

        onView(withId(R.id.txtPageSize)).
                perform(typeText(Settings.getPageSize()), closeSoftKeyboard());
        onView(withId(R.id.btnSave)).check(matches(isEnabled()));
    }
}
