package com.ten.service.member;

import com.ten.domain.member.Member;
import com.ten.exception.member.MemberNotFoundException;
import com.ten.exception.member.NewPasswordMisMatchException;
import com.ten.repository.member.MemberRepository;
import com.ten.request.member.AdditionalRequest;
import com.ten.request.member.MemberUpdateRequest;
import com.ten.response.member.MemberDetailResponse;
import com.ten.response.member.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberDetailResponse myInfo(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return MemberDetailResponse.toSave(member);

    }

    @Transactional
    public MemberUpdateResponse update(Long memberId, MemberUpdateRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if (!request.getNewPassword().isEmpty()) {
            if (request.getNewPassword().equals(request.getCheckPassword())) {
                String encodePassword = passwordEncoder.encode(request.getNewPassword());
                member.updateWithPassword(encodePassword, request.getPhoneNum(), request.getEmail(), request.getAddress());
                return MemberUpdateResponse.toSave(member);
            } else {
                throw new NewPasswordMisMatchException();
            }
        } else {
            member.updateWithoutPassword(request.getPhoneNum(), request.getEmail(), request.getAddress());
            return MemberUpdateResponse.toSave(member);
        }
    }

    @Transactional
    public MemberDetailResponse addition(Long memberId, AdditionalRequest request) {

        Member member = memberRepository.findBySocialId(String.valueOf(memberId)).orElseThrow(MemberNotFoundException::new);
        member.additionalAddress(request.getAddress());
        return MemberDetailResponse.toSave(member);
    }

    @Transactional
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}














































