package com.skcc.cloud.member.member.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String aggregateType;

    @NotNull
    private String  aggregateId;

    @NotNull
    private String eventType;

    @NotNull
    private Long timestamp;

    @NotNull
    @Column(length = 1048576) // 1 MB max
    private String payload;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private EventStatus status;

    public OutboxEvent(String aggregateType, String aggregateId, String eventType, Long timestamp, String payload, EventStatus status) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
    }

    public static OutboxEvent from(Outboxable event) {
        return new OutboxEvent(
                event.getAggregateType(),
                event.getAggregateId(),
                event.getEventType(),
                event.getTimestamp(),
                event.getPayload(),
                event.getStatus()
        );
    }
}
