package com.ten.oauth2;

import com.ten.domain.cart.Cart;
import com.ten.domain.member.Member;
import com.ten.domain.member.Role;
import com.ten.domain.member.SocialType;
import com.ten.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.ten.oauth2.userinfo.NaverOAuth2UserInfo;
import com.ten.oauth2.userinfo.Oauth2UserInfo;
import com.ten.repository.cart.CartRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import static com.ten.domain.member.Role.GUEST;
import static com.ten.domain.member.SocialType.KAKAO;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private Oauth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, Oauth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    protected OAuthAttributes() {
    }

    public static OAuthAttributes of(SocialType socialType, String usernameAttributeName, Map<String, Object> attributes) {

        if(socialType.equals(KAKAO)) {
            return ofKakao(usernameAttributeName, attributes);
        }
        return ofNaver(usernameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofNaver(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(SocialType socialType, Oauth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .username(oauth2UserInfo.getNickName())
                .role(GUEST)
                .email(oauth2UserInfo.getEmail())
                .build();
    }
}




































