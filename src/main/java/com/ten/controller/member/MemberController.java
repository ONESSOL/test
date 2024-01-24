package com.ten.controller.member;

import com.ten.config.SecurityUtil;
import com.ten.request.member.AdditionalRequest;
import com.ten.request.member.MemberUpdateRequest;
import com.ten.response.member.MemberDetailResponse;
import com.ten.response.member.MemberUpdateResponse;
import com.ten.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my_info")
    public ResponseEntity<MemberDetailResponse> myInfo() {
        return ResponseEntity.ok(memberService.myInfo(SecurityUtil.currentMemberId()));
    }

    @PutMapping("/update")
    public ResponseEntity<MemberUpdateResponse> update(@RequestBody MemberUpdateRequest request) {
        return ResponseEntity.ok(memberService.update(SecurityUtil.currentMemberId(), request));
    }

    @PutMapping("/addition")
    public ResponseEntity<MemberDetailResponse> addition(@RequestBody AdditionalRequest request) {
        return ResponseEntity.ok(memberService.addition(SecurityUtil.currentMemberId(), request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete() {
        memberService.delete(SecurityUtil.currentMemberId());
        return ResponseEntity.ok().build();
    }
}














































