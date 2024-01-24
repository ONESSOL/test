package com.ten.config;

import com.ten.jwt.JwtAccessDeniedHandler;
import com.ten.jwt.JwtAuthenticationEntryPoint;
import com.ten.jwt.JwtTokenProvider;
import com.ten.oauth2.handler.OAuth2LoginFailureHandler;
import com.ten.oauth2.handler.OAuth2LoginSuccessHandler;
import com.ten.repository.cart.CartRepository;
import com.ten.repository.member.MemberRepository;
import com.ten.service.oauth.CustomOAuth2UserService;
import com.ten.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
                    auth.anyRequest().permitAll();
                })
                .oauth2Login(oauth -> {
                    oauth.successHandler(new OAuth2LoginSuccessHandler(jwtTokenProvider, redisService, memberRepository));
                    oauth.failureHandler(new OAuth2LoginFailureHandler());
                    oauth.userInfoEndpoint(config -> config.userService(new CustomOAuth2UserService(memberRepository, cartRepository)));
                })
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(headers -> {
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .exceptionHandling(exception -> {
                    exception.accessDeniedHandler(jwtAccessDeniedHandler);
                    exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                })
                .apply(new JwtSecurityConfig(jwtTokenProvider, redisService));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
































