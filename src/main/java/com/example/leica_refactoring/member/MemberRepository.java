package com.example.leica_refactoring.member;

import com.example.leica_refactoring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberId(String memberId);
    Member findByUsername(String username);
}
