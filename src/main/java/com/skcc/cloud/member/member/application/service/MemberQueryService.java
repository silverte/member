package com.skcc.cloud.member.member.application.service;

import com.skcc.cloud.member.member.application.port.in.MemberQueryRequestDTO;
import com.skcc.cloud.member.member.application.port.in.MemberQueryUseCase;
import com.skcc.cloud.member.member.application.port.out.MemberQueryPersistencePort;
import com.skcc.cloud.member.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberQueryPersistencePort memberQueryPersistencePort;
    @Override
    public Page<Member> queryAllAndMember(MemberQueryRequestDTO queryRequest) {
        log.debug("[Service] MemberQueryService Called - queryAllAndMember [{}]", queryRequest);
        return memberQueryPersistencePort.searchAllAnd(queryRequest.getSearchKeys(), queryRequest.getSearchValues(), queryRequest.of());
    }

    @Override
    public Page<Member> queryAllOrMember(MemberQueryRequestDTO queryRequest) {
        log.debug("[Service] MemberQueryService Called - queryAllOrMember [{}]", queryRequest);
        return memberQueryPersistencePort.searchAllOr(queryRequest.getSearchKeys(), queryRequest.getSearchValues(), queryRequest.of());
    }
}
