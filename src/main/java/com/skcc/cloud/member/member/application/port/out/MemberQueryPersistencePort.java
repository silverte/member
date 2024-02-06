package com.skcc.cloud.member.member.application.port.out;

import com.skcc.cloud.member.member.application.port.in.MemberQueryRequestDTO.MemberKeyType;
import com.skcc.cloud.member.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryPersistencePort {
    public Page<Member> searchAllAnd(final List<MemberKeyType> searchKeys,
                                     final List<String> searchValues,
                                     final Pageable pageable);

    public Page<Member> searchAllOr(final List<MemberKeyType> searchKeys,
                                    final List<String> searchValues,
                                    final Pageable pageable);

}