package uz.oliymahad.oliymahadquroncourse.controller.core;

public abstract class ControllerUtils {
    private static final String HOST = "";

    private static final String API = "/api";

    private static final String VERSION = "/v-1.0.0";

    private static final String AUTH = "/auth";

    private static final String HOME = "/home";

    private static final String ALL = "/**";

    public static final String BASE_URI = HOST + API + VERSION;

    public static final String AUTH_URI = BASE_URI + AUTH;

    public static final String FILES_URI = BASE_URI + "/files";

    public static final String EMAIL_VERIFICATION_URI = BASE_URI + "/verification-email";

    public static final String USERS_URI = BASE_URI + "/users";

    public static final String GROUPS_URI = BASE_URI + "/groups";

    public static final String COURSES_URI = BASE_URI + "/courses";

    public static final String QUEUES_URI = BASE_URI + "/queues";

    public static final String SECTIONS_URI = BASE_URI + "/sections";

    public static final String SEARCH_URI = BASE_URI+"/search";

    public static final String[] OPEN_PATH = {
            EMAIL_VERIFICATION_URI,
            BASE_URI + HOME,
            SEARCH_URI + ALL,
            AUTH_URI + ALL,
            "/socket/**",
            "/app/**",
            "/swagger-ui/**",
            "/api-docs/**"
    };

    public static final String[] OPEN_FILES = {
            "/error",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
    };
}
