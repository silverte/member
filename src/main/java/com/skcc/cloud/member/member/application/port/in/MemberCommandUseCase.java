package com.skcc.cloud.member.member.application.port.in;

public interface MemberCommandUseCase {
    void registerMember(MemberCommandRequestDTO command);
}
