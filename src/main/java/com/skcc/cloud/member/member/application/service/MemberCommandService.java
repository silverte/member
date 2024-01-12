package com.skcc.cloud.member.member.application.service;

import com.skcc.cloud.member.common.annotation.UseCase;
import com.skcc.cloud.member.member.application.port.in.MemberCommandRequestDTO;
import com.skcc.cloud.member.member.application.port.in.MemberCommandUseCase;
import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import com.skcc.cloud.member.member.application.service.event.MemberJoinedEvent;
import com.skcc.cloud.member.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {

    private final MemberCommandPersistencePort memberCommandPersistencePort;
    private final ApplicationEventPublisher eventPublisher;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void registerMember(MemberCommandRequestDTO command) {
        log.debug("[Service] MemberCommandService Called - registerMember [{}]", command);
        // 회원 등록
        Member member = joinMember(command);

        // External 이벤트 발행
        eventPublisher.publishEvent(MemberJoinedEvent.of(member.getId(), member.getEmail(), member.getName()));
    }

    private Member joinMember(MemberCommandRequestDTO command) {
        // email 중복 체크
        Optional<Member> queryedMember = Optional.ofNullable(memberCommandPersistencePort.findByEmail(command.getEmail()));
        queryedMember.ifPresent(c -> {throw new IllegalArgumentException("이미 존재하는 회원정보 입니다.");});

        // 회원 정보 저장
        Member member = modelMapper.map(command, Member.class);
        return memberCommandPersistencePort.insertMember(member);
    }
}
