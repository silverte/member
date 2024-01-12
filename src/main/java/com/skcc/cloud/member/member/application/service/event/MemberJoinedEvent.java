package com.skcc.cloud.member.member.application.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloud.member.member.domain.EventStatus;
import com.skcc.cloud.member.member.domain.Member;
import com.skcc.cloud.member.member.domain.Outboxable;
import jakarta.persistence.EnumType;

import java.time.Instant;

public class MemberJoinedEvent implements Outboxable {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final long id;
    private final JsonNode payload;
    private final long timestamp;

    public MemberJoinedEvent(long id, JsonNode payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static MemberJoinedEvent of(long id, String email, String name){
        JsonNode memberInsert = MAPPER.createObjectNode()
                .put("id", id)
                .put("email", email)
                .put("name", name);
        return new MemberJoinedEvent(id, memberInsert);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(id);
    }

    @Override
    public String getAggregateType() {
        return Member.class.getName();
    }

    @Override
    public String getPayload() {
        try{
            return MAPPER.writeValueAsString((payload));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getEventType() {
        return this.getClass().getName();
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public EventStatus getStatus() {
        return EventStatus.INIT;
    }
}
