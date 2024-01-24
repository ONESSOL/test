package com.ten.service.auth;

import com.ten.domain.cart.Cart;
import com.ten.domain.member.Member;
import com.ten.domain.member.Role;
import com.ten.exception.member.UsernameExistException;
import com.ten.jwt.AuthTokenGenerator;
import com.ten.jwt.AuthTokens;
import com.ten.repository.cart.CartRepository;
import com.ten.repository.member.MemberRepository;
import com.ten.request.member.AdditionalRequest;
import com.ten.request.member.LoginRequest;
import com.ten.request.member.MemberCreateRequest;
import com.ten.response.member.MemberCreateResponse;
import com.ten.response.member.MemberDetailResponse;
import com.ten.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

import static com.ten.domain.member.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthTokenGenerator authTokenGenerator;
    private final RedisService redisService;

    @Transactional
    public MemberCreateResponse saveMember(MemberCreateRequest request) {

        Optional<Member> optionalMember = memberRepository.findByUsername(request.getUsername());
        if (optionalMember.isPresent()) {
            throw new UsernameExistException();
        }
        String encodePassword = passwordEncoder.encode(request.getPassword());

        Member member = memberRepository.save(Member.builder()
                .username(request.getUsername())
                .password(encodePassword)
                .name(request.getName())
                .phoneNum(request.getPhoneNum())
                .email(request.getEmail())
                .address(request.getAddress())
                .role(USER)
                .cart(cartRepository.save(Cart.createCart()))
                .build());

        return MemberCreateResponse.toSave(member);
    }

    @Transactional
    public AuthTokens login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthenticationToken();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AuthTokens authToken = authTokenGenerator.generate(authentication);

        redisService.setValues(authentication.getName(),
                authToken.getRefreshToken(),
                Duration.ofMillis(AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME));

        return authToken;
    }

    @Transactional
    public void logout(String accessToken, String refreshToken) {
        if (redisService.checkExistValues(refreshToken)) {
            redisService.deleteValues(refreshToken);
            redisService.setValues(accessToken, "logout", Duration.ofMillis(AuthTokenGenerator.ACCESS_TOKEN_EXPIRE_TIME));
        }
    }

//    @Transactional
//    public MemberDetailResponse addition(AdditionalRequest request) {
//
//
//
//    }
}












































