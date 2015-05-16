package ma15.brickcollector.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ma15.brickcollector.R;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.fragment.BrowseFragment;
import ma15.brickcollector.fragment.LoginFragment;
import ma15.brickcollector.fragment.NavigationDrawerFragment;
import ma15.brickcollector.fragment.RegisterFragment;
import ma15.brickcollector.fragment.SetListFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public NavigationDrawerFragment getmNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String[] mDrawerTitlesLogin;
    private String[] mDrawerTitlesLogout;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerTitlesLogin = getResources().getStringArray(R.array.drawer_array_State_Login);
        mDrawerTitlesLogout = getResources().getStringArray(R.array.drawer_array_State_Logout);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        if(UserManager.getInstance().checkLogin()) {
            switch(position) {
                case 0:
                    fragment = BrowseFragment.newInstance(mDrawerTitlesLogin[position]);
                    break;
                case 1:
                    fragment = SetListFragment.newInstance(mDrawerTitlesLogin[position], true, false);
                    break;
                case 2:
                    fragment = SetListFragment.newInstance(mDrawerTitlesLogin[position], false, true);
                    break;
                //logout
                case 3:
                    UserManager.getInstance().setUserHash(null);
                    Toast.makeText(this,
                            getString(R.string.logout_toast), Toast.LENGTH_SHORT)
                            .show();
                    mNavigationDrawerFragment.updateDrawerTitles();
                    mNavigationDrawerFragment.selectItem(0);
                    return;
                //Settings
                case 4:
                    //break;
                    return;
                default:
                    return;
            }
        } else {
            switch(position) {
                case 0:
                    fragment = BrowseFragment.newInstance(mDrawerTitlesLogout[position]);
                    break;
                //login
                case 1:
                    fragment = LoginFragment.newInstance(mDrawerTitlesLogout[position]);
                    break;
                //register
                case 2:
                    fragment = RegisterFragment.newInstance(mDrawerTitlesLogout[position]);
                    break;
                //Settings
                case 3:
                    //break;
                    return;
                default:
                    return;
            }
        }



        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void onSectionAttached(String title) {
        mTitle = title;
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
