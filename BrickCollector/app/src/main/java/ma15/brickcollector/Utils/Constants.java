package ma15.brickcollector.Utils;

public class Constants {

    public static final String BASE_URL = "http://brickset.com/api/v2.asmx/";
    public static final String REGISTER_URL = "http://brickset.com/signup";


    //Requesttypes
    public static final String BROWSE = "BROWSE";
    public static final String LOGIN = "LOGIN";
    public static final String SET_OWN = "SET_OWN";
    public static final String SET_OWN_QUANTITIY = "SET_OWN_QUANTITIY";
    public static final String SET_WANT = "SET_WANT";
    public static final String CHECK_KEY = "CHECK_KEY";

    //Request methods
    public static final String REQUEST_GET_SETS = "getSets";
    public static final String REQUEST_LOGIN = "login";
    public static final String REQUEST_SET_OWN = "setCollection_owns";
    public static final String REQUEST_SET_OWN_QUANTITY = "setCollection_qtyOwned";
    public static final String REQUEST_SET_WANT = "setCollection_wants";
    public static final String REQUEST_CHECK_KEY = "checkKey";

    public static final String TESTUSER_NAME = "ThomasKohl";
    public static final String TESTUSER_PW = "bionicle3439";
    public static final String TESTUSER_HASH = "7HFw_bVFYT";

    public static final String API_KEY = "F1PE-3JWB-BwfC";

    public static final String ERROR = "ERROR";
    public static final String INVALID_KEY = "INVALIDKEY";

    public static final String BROWSE_PAGE_SIZE = "20";

    public static final String RETURN_STRING_INCORRECT_LOGIN = "ERROR: invalid username and/or password";
    public static final String RETURN_STRING_CORRECT_KEY = "OK (v2)";
    public static final String RETURN_STRING_INCORRECT_KEY = "INVALIDKEY";

    public static final String RETURN_STRING_CORRECT_SET_REQUEST = "OK";
    public static final String RETURN_STRING_SET_REQUEST_INCORRECT_USER = "ERROR: invalid userHash";
    public static final String RETURN_STRING_SET_REQUEST_INCORRECT_QUANTITY = "ERROR: invalid quantity";
    public static final String RETURN_STRING_SET_REQUEST_INCORRECT_OWN = "ERROR: owned not 0 or 1";
    public static final String RETURN_STRING_SET_REQUEST_INCORRECT_WANT = "ERROR: wanted not 0 or 1";

}
