package com.ten.jwt;

import com.ten.exception.token.RefreshTokenNotFoundException;
import com.ten.service.redis.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        if(StringUtils.hasText(token) && doNotLogout(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        if(StringUtils.hasText(token) && !jwtTokenProvider.validateExpiredToken(token)) {
            String subject = jwtTokenProvider.extractSubject(token);
            String redisRefreshToken = redisService.getValues(subject);
            if(redisService.checkExistValues(redisRefreshToken)) {
                redisService.deleteValues(subject);
                long now = new Date().getTime();
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                String accessToken = jwtTokenProvider.generate(subject, new Date(now + AuthTokenGenerator.ACCESS_TOKEN_EXPIRE_TIME), authentication);
                String refreshToken = jwtTokenProvider.generate(subject, new Date(now + AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME), authentication);
                jwtTokenProvider.accessTokenSetHeaders(accessToken, response);
                redisService.setValues(subject, refreshToken, Duration.ofMillis(AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME));
            } else {
                throw new RefreshTokenNotFoundException();
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean doNotLogout(String token) {
        String isLogout = redisService.getValues(token);
        return isLogout.equals("false");
    }
}



























