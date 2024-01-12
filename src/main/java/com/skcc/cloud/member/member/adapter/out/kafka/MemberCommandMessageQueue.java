package com.skcc.cloud.member.member.adapter.out.kafka;

import com.skcc.cloud.member.member.application.port.out.MemberCommandMessageQueuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCommandMessageQueue implements MemberCommandMessageQueuePort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
