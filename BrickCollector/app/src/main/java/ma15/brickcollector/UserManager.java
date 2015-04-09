package ma15.brickcollector;

/**
 * Created by thomas on 09.04.15.
 */
public class UserManager {

    private String userHash = "7HFw_bVFYT";
    private static UserManager instance = null;

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }


    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }
}
