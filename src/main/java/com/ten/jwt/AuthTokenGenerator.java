package com.ten.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {

    public static final String BEARER_TYPE = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 30;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokens generate(Authentication authentication) {

        long now = new Date().getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = authentication.getName();
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt, authentication);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt, authentication);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public Long extractMemberId(String accessToken) {
        return Long.parseLong(jwtTokenProvider.extractSubject(accessToken));
    }
}





















