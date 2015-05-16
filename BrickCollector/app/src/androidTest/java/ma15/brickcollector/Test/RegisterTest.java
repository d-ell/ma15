package ma15.brickcollector.Test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.util.Log;
import android.webkit.WebView;

import ma15.brickcollector.R;
import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.fragment.BrowseFragment;
import ma15.brickcollector.fragment.RegisterFragment;

/**
 * Created by dan on 06/05/15.
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;

    public RegisterTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    private RegisterFragment startFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();

        RegisterFragment frag = RegisterFragment.newInstance("Register");
        fragmentManager.beginTransaction().replace(R.id.container, frag).commit();

        getInstrumentation().waitForIdleSync();
        return frag;
    }

    public void testFragment() {

        RegisterFragment frag = startFragment();

        for(int i = 0; i < 30; i++) {
            if(frag.isFinished()) {
                break;
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!frag.isFinished()) {
            fail("Could Not Load Webpage.");
        }

        String html = frag.getHtml();

        assertNotNull(html);
        assertFalse("HTML is empty.", html.isEmpty());
    }
}
