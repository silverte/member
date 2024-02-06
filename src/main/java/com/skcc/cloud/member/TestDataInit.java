package com.skcc.cloud.member;

import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import com.skcc.cloud.member.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberCommandPersistencePort memberCommandPersistencePort;

    @EventListener(ApplicationReadyEvent.class)
    public void insertTestData(){
        log.info("insert test data");
        memberCommandPersistencePort.insertMember(new Member(null, "김아키", "silverte@sk.com"));
        memberCommandPersistencePort.insertMember(new Member(null, "김모델", "born2k@naver.com"));
        memberCommandPersistencePort.insertMember(new Member(null, "김개발", "km78cho@gmail.com"));
    }
}
