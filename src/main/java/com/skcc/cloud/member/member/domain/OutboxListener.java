package com.skcc.cloud.member.member.domain;

import com.skcc.cloud.member.member.application.port.out.MemberCommandMessageQueuePort;
import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OutboxListener {
    private final MemberCommandPersistencePort memberCommandPersistencePort;
    private final MemberCommandMessageQueuePort memberCommandMessageQueuePort;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    private void recordMessageHandler(Outboxable event){

        OutboxEvent outboxEvent = OutboxEvent.from(event);
        memberCommandPersistencePort.recordEventToOutboxTable(outboxEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void sendMessageHandler(Outboxable event) {
        memberCommandMessageQueuePort.sendMessage(event.getEventType(), event.getPayload());
    }
}
