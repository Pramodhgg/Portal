package org.jobportal.portal.constants;

public class ApplicationConstants {

    private ApplicationConstants() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }

    public static final String JWT_SECRET_KEY = "JWT_SECRET_KEY";
    public static final String JWT_SECRET_DEFAULT_VALUE = "jxgEQhdjsdhfiafjxklske9123bjruf324";
    public static final String JWT_HEADER = "Authorization";
    public static final String ROLE_JOB_SEEKER = "ROLE_JOB_SEEKER";
    public static final String ACTIVE_STATUS = "ACTIVE";

}
