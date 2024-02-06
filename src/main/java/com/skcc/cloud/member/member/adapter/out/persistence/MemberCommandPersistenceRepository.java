package com.skcc.cloud.member.member.adapter.out.persistence;

import com.skcc.cloud.member.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCommandPersistenceRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
