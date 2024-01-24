package com.ten.response.member;

import com.ten.domain.member.Address;
import com.ten.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberCreateResponse {

    private String username;
    private String name;
    private String phoneNum;
    private String email;
    private Address address;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedTime;

    public static MemberCreateResponse toSave(Member member) {
        MemberCreateResponse response = new MemberCreateResponse();
        response.setUsername(member.getUsername());
        response.setName(member.getName());
        response.setPhoneNum(member.getPhoneNum());
        response.setEmail(member.getEmail());
        response.setAddress(member.getAddress());
        response.setCreatedTime(member.getCreatedTime());
        response.setUpdatedTime(member.getUpdatedTime());
        return response;
    }
}
