package com.skcc.cloud.member.member.application.port.out;

import com.skcc.cloud.member.member.domain.Member;
import com.skcc.cloud.member.common.outbox.OutboxEvent;

import java.util.Optional;

public interface MemberCommandPersistencePort {
    Member insertMember(Member member);

    Optional<Member> findByEmail(String email);

    void recordEventToOutboxTable(OutboxEvent outboxEvent);
}
