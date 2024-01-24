package com.ten.response.member;

import com.ten.domain.member.Address;
import com.ten.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDetailResponse {

    private String username;
    private String name;
    private String phoneNum;
    private String email;
    private Address address;

    public static MemberDetailResponse toSave(Member member) {
        MemberDetailResponse response = new MemberDetailResponse();
        response.setUsername(member.getUsername());
        response.setName(member.getName());
        response.setPhoneNum(member.getPhoneNum());
        response.setEmail(member.getEmail());
        response.setAddress(member.getAddress());
        return response;
    }
}
