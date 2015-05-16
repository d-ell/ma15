package ma15.brickcollector.uiTest_espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;

import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.List;

import ma15.brickcollector.R;
import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;
import ma15.brickcollector.data.BrickSet;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;

/**
 * Created by thomas on 15.05.15.
 */
public class DetailTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public DetailTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
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

    }
}
