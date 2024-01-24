package com.ten.oauth2.handler;

import com.ten.domain.member.Member;
import com.ten.domain.member.Role;
import com.ten.exception.member.MemberNotFoundException;
import com.ten.jwt.AuthTokenGenerator;
import com.ten.jwt.JwtTokenProvider;
import com.ten.oauth2.CustomOAuth2User;
import com.ten.repository.member.MemberRepository;
import com.ten.service.redis.RedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import static com.ten.jwt.AuthTokenGenerator.ACCESS_TOKEN_EXPIRE_TIME;
import static com.ten.jwt.AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            if(oAuth2User.getRole().equals(Role.GUEST)) {
                long now = new Date().getTime();
                Member findMember = memberRepository.findBySocialId(authentication.getName()).orElseThrow(MemberNotFoundException::new);
                Long subject = findMember.getId();
                String accessToken = jwtTokenProvider.generate(String.valueOf(subject), new Date(now + REFRESH_TOKEN_EXPIRE_TIME), authentication);
                String refreshToken = jwtTokenProvider.generate(String.valueOf(subject), new Date(now + REFRESH_TOKEN_EXPIRE_TIME), authentication);

                redisService.setValues(String.valueOf(subject), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));
                jwtTokenProvider.accessTokenSetHeaders(accessToken, response);
                response.sendRedirect("/oauth2/save");
            } else {
                loginSuccess(response, authentication);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, Authentication authentication) {
        long now = new Date().getTime();
        String subject = authentication.getName();
        String accessToken = jwtTokenProvider.generate(subject, new Date(now + ACCESS_TOKEN_EXPIRE_TIME), authentication);
        String refreshToken = jwtTokenProvider.generate(subject, new Date(now + REFRESH_TOKEN_EXPIRE_TIME), authentication);
        redisService.setValues(subject, refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));
        jwtTokenProvider.accessTokenSetHeaders(accessToken, response);
    }
}





























