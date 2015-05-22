package ma15.brickcollector.Utils;


import android.app.Activity;
import android.content.SharedPreferences;

import ma15.brickcollector.fragment.SettingsFragment;

public class Settings {

    public static final String PREFS_NAME = "BrickCollectorSettingsFile";
    public static final String PAGE_SIZE_KEY = "pageSize";
    public static final String KEEP_LOGIN_KEY = "keepLogin";
    public static final String USER_HASH_KEY = "userHash";

    public static final String BROWSE_PAGE_SIZE_DEFAULT = "20";
    public static final String KEEP_LOGIN_DEFAULT = "true";

    private static String pageSize = BROWSE_PAGE_SIZE_DEFAULT;
    private static String keepLogin = KEEP_LOGIN_DEFAULT;

    public static void loadSettings(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME,0);
        pageSize =
                settings.getString(PAGE_SIZE_KEY, BROWSE_PAGE_SIZE_DEFAULT);
        keepLogin = settings.getString(KEEP_LOGIN_KEY, KEEP_LOGIN_DEFAULT);
        if(Boolean.parseBoolean(keepLogin)) {
            UserManager.getInstance().setUserHash(settings.getString(USER_HASH_KEY, null), null);
        } else {
            UserManager.getInstance().setUserHash(null,null);
        }
    }

    public static void saveSettings(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PAGE_SIZE_KEY, pageSize);
        editor.putString(KEEP_LOGIN_KEY, keepLogin);
        if(Boolean.parseBoolean(keepLogin)) {
            editor.putString(USER_HASH_KEY, UserManager.getInstance().getUserHash());
        } else {
            editor.putString(USER_HASH_KEY, null);
        }

        editor.commit();
    }

    public static String getPageSize() {
        return pageSize;
    }

    public static String getKeepLogin() {
        return keepLogin;
    }

    public static void setPageSize(String value) {
        pageSize = value;
    }

    public static void setKeepLogin(String value) {
        keepLogin = value;
    }
}
