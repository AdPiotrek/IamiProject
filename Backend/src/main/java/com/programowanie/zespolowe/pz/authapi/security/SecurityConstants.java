package com.programowanie.zespolowe.pz.authapi.security;

public class SecurityConstants {
    public static final String SECRET = "SoSecret";
    public static final long EXPIRATION_TIME = 14_400_000; // 4 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/user/login",
            "/user/register"
    };

}
