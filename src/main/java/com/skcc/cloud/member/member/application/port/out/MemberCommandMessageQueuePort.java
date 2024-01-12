package com.skcc.cloud.member.member.application.port.out;

public interface MemberCommandMessageQueuePort {
    void sendMessage(String topic, String message);
}
