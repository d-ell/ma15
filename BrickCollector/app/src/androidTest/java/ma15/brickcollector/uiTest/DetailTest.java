package ma15.brickcollector.uiTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import org.hamcrest.core.AllOf;

import ma15.brickcollector.R;
import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.util.TestHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


public class DetailTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = DetailTest.class.getName();
    MainActivity activity;

    public DetailTest() {
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

    public void testDetailPage() {

        LoginTest.doLogin();

        Espresso.onView(ViewMatchers.withId(R.id.txtQuery)).perform(typeText("4526"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnGo)).perform(click());

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNumberInList("4526")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview), TestHelper.matchBrickSetNumberInList("4526"))).
                perform(click());

        //test wishlist
        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_wish)).
                perform(ViewActions.scrollTo(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

        //test ownList
        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_own)).
                perform(ViewActions.scrollTo(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isChecked()));

        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_own_quantity)).
                check(ViewAssertions.matches(ViewMatchers.withText("1")));

        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_own)).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

        Espresso.pressBack();
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNumberInList("4526")));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.txtQuery))
                .check(matches(withText("4526")));

        LoginTest.doLogout();

    }

    public void testDetailPageChange() {
        LoginTest.doLogin();

        Espresso.onView(ViewMatchers.withId(R.id.txtQuery)).perform(typeText("10240"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnGo)).perform(click());

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNumberInList("10240")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview), TestHelper.matchBrickSetNumberInList("10240"))).
                perform(click());

        // manipulate data
        //test wishlist
        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_wish)).
                perform(ViewActions.scrollTo(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isChecked()));

        //test ownList
        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_own)).
                perform(ViewActions.scrollTo(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isChecked()));

        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_own_quantity)).
                check(ViewAssertions.matches(ViewMatchers.withText("1")))
                .perform(clearText())
                .perform(typeText("15"), ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
                .check(ViewAssertions.matches(ViewMatchers.withText("15")))
                .perform(closeSoftKeyboard());

        // back
        Espresso.pressBack();

        // go into set again
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNumberInList("10240")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview), TestHelper.matchBrickSetNumberInList("10240"))).
                perform(click());

        // test wishlist
        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_wish)).
                perform(ViewActions.scrollTo(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

        // test ownlist txt
        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_own_quantity)).
                check(ViewAssertions.matches(ViewMatchers.withText("15")));

        // test ownlist
        Espresso.onView(ViewMatchers.withId(R.id.chbx_detail_own)).
                perform(ViewActions.scrollTo(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isChecked())).
                perform(ViewActions.click(), closeSoftKeyboard()).
                check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

        // should be 0 again
        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_own_quantity)).
                check(ViewAssertions.matches(ViewMatchers.withText("0")));

        // orignal state
        Espresso.pressBack();
        Espresso.onView(allOf(ViewMatchers.withId(R.id.listview))).
                check(ViewAssertions.matches(TestHelper.matchBrickSetNumberInList("10240")));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.txtQuery))
                .check(matches(withText("10240")));

        LoginTest.doLogout();
    }
}
