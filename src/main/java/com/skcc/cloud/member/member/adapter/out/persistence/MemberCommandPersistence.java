package com.skcc.cloud.member.member.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skcc.cloud.member.common.annotation.PersistenceAdapter;
import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import com.skcc.cloud.member.member.domain.Member;
import com.skcc.cloud.member.member.domain.OutboxEvent;
import com.skcc.cloud.member.member.domain.QMember;
import com.skcc.cloud.member.member.domain.QOutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCommandPersistence implements MemberCommandPersistencePort {

    private final JPAQueryFactory queryFactory;
    private final ModelMapper modelMapper;

    @Override
    public Member insertMember(Member member) {
        log.debug("[PersistenceAdapter] MemberCommandPersistence Called - insertMember [{}]", member);

        QMember qMember = QMember.member;
        long rows = queryFactory.insert(qMember)
                            .columns(qMember.email).values(member.getEmail())
                            .columns(qMember.name).values(member.getName())
                .execute();
        log.debug("insert rows: [{}]", rows);

        return queryFactory.selectFrom(qMember)
                .where(qMember.email.eq(member.getEmail()))
                .fetchOne();
    }

    @Override
    public Member findByEmail(String email) {
        log.debug("[PersistenceAdapter] MemberCommandPersistence Called - findByEmail [{}]", email);
        QMember qMember = QMember.member;
        return queryFactory
                .selectFrom(qMember)
                .where(qMember.email.eq(email))
                .fetchOne();
    }

    @Override
    public void recordEventToOutboxTable(OutboxEvent outboxEvent) {
        log.debug("[PersistenceAdapter] MemberCommandPersistence Called - recordEventToOutboxTable [{}]", outboxEvent);

        QOutboxEvent qOutboxEvent = QOutboxEvent.outboxEvent;
        long rows = queryFactory.insert(qOutboxEvent)
                .columns(qOutboxEvent.aggregateType).values(outboxEvent.getAggregateType())
                .columns(qOutboxEvent.aggregateId).values(outboxEvent.getAggregateId())
                .columns(qOutboxEvent.eventType).values(outboxEvent.getEventType())
                .columns(qOutboxEvent.payload).values(outboxEvent.getPayload())
                .columns(qOutboxEvent.status).values(outboxEvent.getStatus())
                .columns(qOutboxEvent.timestamp).values((outboxEvent.getTimestamp()))
                .execute();
        log.debug("insert rows: [{}]", rows);
    }
}
