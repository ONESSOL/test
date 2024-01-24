package com.ten.request.member;

import com.ten.domain.member.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateRequest {

    private String newPassword;
    private String checkPassword;
    private String phoneNum;
    private String email;
    private Address address;

}
