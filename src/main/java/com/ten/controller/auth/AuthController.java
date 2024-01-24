package com.ten.controller.auth;

import com.ten.config.SecurityUtil;
import com.ten.jwt.AuthTokens;
import com.ten.jwt.JwtTokenProvider;
import com.ten.request.member.LoginRequest;
import com.ten.request.member.MemberCreateRequest;
import com.ten.response.member.MemberCreateResponse;
import com.ten.service.auth.AuthService;
import com.ten.service.redis.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/save")
    public ResponseEntity<MemberCreateResponse> saveMember(@RequestBody MemberCreateRequest request) {
        return ResponseEntity.ok(authService.saveMember(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PatchMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        String refreshToken = redisService.getValues(String.valueOf(SecurityUtil.currentMemberId()));
        authService.logout(accessToken, refreshToken);
        return ResponseEntity.ok().build();
    }
}



































