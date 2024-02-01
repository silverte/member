package com.skcc.cloud.member.member.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skcc.cloud.member.common.outbox.OutboxEvent;
import com.skcc.cloud.member.common.outbox.QOutboxEvent;
import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import com.skcc.cloud.member.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCommandPersistence implements MemberCommandPersistencePort {

    private final JPAQueryFactory query;
    private final MemberCommandPersistenceRepository repository;

    @Override
    public Member insertMember(Member member) {
        log.debug("[PersistenceAdapter] MemberCommandPersistence Called - insertMember [{}]", member);
        return repository.save(member);

        /* QueryDsl
        QMember qMember = QMember.member;
        long rows = query.insert(qMember)
                            .columns(qMember.email).values(member.getEmail())
                            .columns(qMember.name).values(member.getName())
                .execute();
        log.debug("insert rows: [{}]", rows);

        return queryFactory.selectFrom(qMember)
                .where(qMember.email.eq(member.getEmail()))
                .fetchOne();
         */
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        log.debug("[PersistenceAdapter] MemberCommandPersistence Called - findByEmail [{}]", email);
        return repository.findByEmail(email);

        /* QueryDsl
        QMember qMember = QMember.member;
        return Optional.ofNullable(query
                .selectFrom(qMember)
                .where(qMember.email.eq(email))
                .fetchOne());
         */
    }

    @Override
    public void recordEventToOutboxTable(OutboxEvent outboxEvent) {
        log.debug("[PersistenceAdapter] MemberCommandPersistence Called - recordEventToOutboxTable [{}]", outboxEvent);

        final QOutboxEvent qOutboxEvent = QOutboxEvent.outboxEvent;
        long rows = query.insert(qOutboxEvent)
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
