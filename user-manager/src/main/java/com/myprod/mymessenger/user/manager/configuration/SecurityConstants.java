package com.myprod.mymessenger.user.manager.configuration;

public final class SecurityConstants {

    public static final int TOKEN_LIFETIME_MILLISECOND = 864000000;
    public static final int TOKEN_LIFETIME_SECOND = TOKEN_LIFETIME_MILLISECOND / 1000;

    public static final String AUTH_LOGIN_URL = "/api/auth/signin";

    // Signing key for HS512 algorithm
    public static final String JWT_SECRET = "r4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTj";

    // JWT token defaults
    public static final String TOKEN_COOKIE = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    public static final String TOKEN_PARAM_KEY_ROLES = "rol";
    public static final String TOKEN_PARAM_KEY_TYPE = "typ";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
