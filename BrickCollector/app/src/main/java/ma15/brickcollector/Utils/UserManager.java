package ma15.brickcollector.Utils;

import android.app.Activity;

public class UserManager {

    //private String userHash = "7HFw_bVFYT";
    private String userHash = null;
    private static UserManager instance = null;

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }

    public boolean checkLogin() {
        if(UserManager.getInstance().getUserHash() == null) {
            return false;
        }
        return true;
    }


    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash, Activity activity) {
        this.userHash = userHash;

        if(activity != null) {
            Settings.saveSettings(activity);
        }
    }
}
