package com.skcc.cloud.member.member.application.port.in;

import com.skcc.cloud.member.member.domain.Member;
import org.springframework.data.domain.Page;

public interface MemberQueryUseCase {
    public Page<Member> queryAllAndMember(final MemberQueryRequestDTO queryRequest);
    public Page<Member> queryAllOrMember(final MemberQueryRequestDTO queryRequest);
}
