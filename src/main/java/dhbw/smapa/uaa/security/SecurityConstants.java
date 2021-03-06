package dhbw.smapa.uaa.security;

class SecurityConstants {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/signup";
    public static final String LOG_IN_URL = "/login";
    public static final String FREE_PARKINGS_URL = "/getFreeParkings";
    public static final String ALL_PARKINGS_URL = "/getAllParkings";
    public static final String DISTINCT_PARKING_URL = "/getDistinctParking/{parkingId}";

}
