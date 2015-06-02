package ma15.brickcollector.uiTest;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import ma15.brickcollector.R;
import ma15.brickcollector.Test.XMLParsertest;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.activity.DetailSetsActivity;
import ma15.brickcollector.activity.ListOnlineFetchedSetsActivity;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.util.TestHelper;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


public class DetailSetTest extends ActivityInstrumentationTestCase2<DetailSetsActivity> {

    private static final String TAG = DetailSetTest.class.getName();
    DetailSetsActivity activity = null;

    public DetailSetTest() {
        super(DetailSetsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    private void initActivity(boolean bLogin, int index) {

        if(bLogin) {
            UserManager.getInstance().setUserHash(Constants.TESTUSER_HASH,null);
        } else {
            UserManager.getInstance().setUserHash(null,null);
        }

        ArrayList<BrickSet> brickSets = XmlParser.getSets(XMLParsertest.xml);
        assertNotNull(brickSets);
        assertTrue(brickSets.size() == XMLParsertest.EXPECTED_RESULT_LENGTH);

        Intent intent = new Intent();
        intent.putExtra("set", brickSets.get(index));
        setActivityIntent(intent);

        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDetailSetLoggedOut() {
        initActivity(false,0);

        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_number))).
                check(matches(withText("4526 (1)")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_name))).
                check(matches(withText("Batman")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_theme))).
                check(matches(withText("DC Comics Super Heroes")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_subtheme))).
                check(matches(withText("Constraction")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_year))).
                check(matches(withText("2012")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_pieces))).
                check(matches(withText("40")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_minifigs))).
                check(matches(withText("")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_packagingtype))).
                check(matches(withText("Box")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_reviewcount))).
                check(matches(withText("1")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_availability))).
                check(matches(withText("Retail")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_uk))).
                check(matches(withText("10.99 GBP")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_us))).
                check(matches(withText("14.99 USD")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_eu))).
                check(matches(withText("14.99 EUR")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_ca))).
                check(matches(withText("17.99 CAD")));
    }

    public void testDetailSetLoggedInNotOwnedAndWanted() {
        initActivity(true,0);

        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_number))).
                check(matches(withText("4526 (1)")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_name))).
                check(matches(withText("Batman")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_theme))).
                check(matches(withText("DC Comics Super Heroes")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_subtheme))).
                check(matches(withText("Constraction")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_year))).
                check(matches(withText("2012")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_pieces))).
                check(matches(withText("40")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_minifigs))).
                check(matches(withText("")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_packagingtype))).
                check(matches(withText("Box")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_reviewcount))).
                check(matches(withText("1")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_availability))).
                check(matches(withText("Retail")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_uk))).
                check(matches(withText("10.99 GBP")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_us))).
                check(matches(withText("14.99 USD")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_eu))).
                check(matches(withText("14.99 EUR")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_ca))).
                check(matches(withText("17.99 CAD")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.chbx_detail_own))).
                check(matches(isNotChecked()));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.chbx_detail_wish))).
                check(matches(isNotChecked()));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.txt_detail_own_quantity))).
                check(matches(withText("0")));
    }

    public void testDetailSetLoggedInOwnedAndWanted() {
        initActivity(true,1);

        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_number))).
                check(matches(withText("6857 (1)")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_name))).
                check(matches(withText("The Dynamic Duo Funhouse Escape")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_theme))).
                check(matches(withText("DC Comics Super Heroes")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_subtheme))).
                check(matches(withText("Batman")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_year))).
                check(matches(withText("2012")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_pieces))).
                check(matches(withText("380")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_minifigs))).
                check(matches(withText("5")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_packagingtype))).
                check(matches(withText("Box")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_reviewcount))).
                check(matches(withText("7")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_availability))).
                check(matches(withText("Retail - limited")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_uk))).
                check(matches(withText("39.99 GBP")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_us))).
                check(matches(withText("39.99 USD")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_eu))).
                check(matches(withText("49.99 EUR")));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.detail_price_ca))).
                check(matches(withText("49.99 CAD")));

        Espresso.onView(allOf(ViewMatchers.withId(R.id.chbx_detail_own))).
                check(matches(isChecked()));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.chbx_detail_wish))).
                check(matches(isChecked()));
        Espresso.onView(allOf(ViewMatchers.withId(R.id.txt_detail_own_quantity))).
                check(matches(withText("3")));
    }
}
