package com.skcc.cloud.member.member.application.port.out;

import com.skcc.cloud.member.member.domain.Member;
import com.skcc.cloud.member.member.domain.OutboxEvent;

public interface MemberCommandPersistencePort {
    Member insertMember(Member member);

    Member findByEmail(String email);

    void recordEventToOutboxTable(OutboxEvent outboxEvent);
}
