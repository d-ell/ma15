package ma15.brickcollector.Test;


import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

import org.junit.Test;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.Settings;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.util.TestHelper;

public class UserManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    public UserManagerTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testKeepLoginTrue() throws Exception {
        Settings.loadSettings(mActivity);

        boolean oldKeepLogin = Boolean.parseBoolean(Settings.getKeepLogin());
        String oldUserHash = UserManager.getInstance().getUserHash();

        Settings.setKeepLogin("true");

        String userHash = TestHelper.loginAndGetUserHash(Constants.TESTUSER_NAME, Constants.TESTUSER_PW);
        assertEquals("UserHash is incorrect", Constants.TESTUSER_HASH, userHash);

        UserManager.getInstance().setUserHash(userHash,null);
        Settings.saveSettings(mActivity);

        Settings.loadSettings(mActivity);

        assertEquals("Keep Login is false",Settings.getKeepLogin(),"true");
        assertEquals("Saved userHash is incorrect",UserManager.getInstance().getUserHash(),userHash);

        //Restore old values
        Settings.setKeepLogin(String.valueOf(oldKeepLogin));
        UserManager.getInstance().setUserHash(oldUserHash,null);

        Settings.saveSettings(mActivity);
    }

    public void testKeepLoginFalse() throws Exception {
        Settings.loadSettings(mActivity);

        boolean oldKeepLogin = Boolean.parseBoolean(Settings.getKeepLogin());
        String oldUserHash = UserManager.getInstance().getUserHash();

        Settings.setKeepLogin("false");

        String userHash = TestHelper.loginAndGetUserHash(Constants.TESTUSER_NAME, Constants.TESTUSER_PW);
        assertEquals("UserHash is incorrect", Constants.TESTUSER_HASH, userHash);

        UserManager.getInstance().setUserHash(userHash,null);
        Settings.saveSettings(mActivity);

        Settings.loadSettings(mActivity);

        assertEquals("Keep Login is true",Settings.getKeepLogin(),"false");
        assertEquals("Saved userHash is incorrect",UserManager.getInstance().getUserHash(),null);

        //Restore old values
        Settings.setKeepLogin(String.valueOf(oldKeepLogin));
        UserManager.getInstance().setUserHash(oldUserHash,null);

        Settings.saveSettings(mActivity);
    }

    public void testTwoLoginsWithKeepLogin() throws Exception {
        Settings.loadSettings(mActivity);

        boolean oldKeepLogin = Boolean.parseBoolean(Settings.getKeepLogin());
        String oldUserHash = UserManager.getInstance().getUserHash();

        Settings.setKeepLogin("true");

        String userHash = TestHelper.loginAndGetUserHash(Constants.TESTUSER_NAME, Constants.TESTUSER_PW);
        assertEquals("UserHash from Testuser is incorrect", Constants.TESTUSER_HASH, userHash);

        UserManager.getInstance().setUserHash(userHash,null);
        Settings.saveSettings(mActivity);

        Settings.loadSettings(mActivity);

        assertEquals("Keep Login is false",Settings.getKeepLogin(),"true");
        assertEquals("Saved userHash (Testuser) is incorrect",UserManager.getInstance().getUserHash(),userHash);

        userHash = TestHelper.loginAndGetUserHash(Constants.TESTUSER1_NAME, Constants.TESTUSER1_PW);
        assertEquals("UserHash from Testuser 1 is incorrect", Constants.TESTUSER1_HASH, userHash);
        UserManager.getInstance().setUserHash(userHash,null);
        Settings.saveSettings(mActivity);

        Settings.loadSettings(mActivity);
        assertEquals("Keep Login is false",Settings.getKeepLogin(),"true");
        assertEquals("Saved userHash (Testuser 1) is incorrect",UserManager.getInstance().getUserHash(),userHash);

        //Restore old values
        Settings.setKeepLogin(String.valueOf(oldKeepLogin));
        UserManager.getInstance().setUserHash(oldUserHash,null);

        Settings.saveSettings(mActivity);
    }
}
