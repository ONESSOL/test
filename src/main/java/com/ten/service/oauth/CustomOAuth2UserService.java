package com.ten.service.oauth;

import com.ten.domain.cart.Cart;
import com.ten.domain.member.Member;
import com.ten.domain.member.SocialType;
import com.ten.oauth2.CustomOAuth2User;
import com.ten.oauth2.OAuthAttributes;
import com.ten.repository.cart.CartRepository;
import com.ten.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private static final String KAKAO = "kakao";
    private static final String NAVER = "naver";

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
        Member member = getMember(extractAttributes, socialType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                String.valueOf(member.getId()),
                member.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.NAVER;
    }

    private Member getMember(OAuthAttributes attributes, SocialType socialType) {
        Member findMember = memberRepository.findBySocialTypeAndSocialId(socialType, attributes.getOauth2UserInfo().getId()).orElse(null);
        if(findMember == null) {
            return saveMember(attributes, socialType);
        }
        return findMember;
    }

    private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
        Member member = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        Cart cart = cartRepository.save(Cart.createCart());
        member.createCart(cart);
        return memberRepository.save(member);
    }
}
