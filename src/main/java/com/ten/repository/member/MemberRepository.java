package com.ten.repository.member;

import com.ten.domain.member.Member;
import com.ten.domain.member.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByUsername(String username);

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<Member> findBySocialId(String socialId);

    void deleteBySocialId(String socialId);
}
