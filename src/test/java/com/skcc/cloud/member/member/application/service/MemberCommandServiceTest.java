package com.skcc.cloud.member.member.application.service;

import com.skcc.cloud.member.member.application.port.in.MemberQueryRequestDTO;
import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import com.skcc.cloud.member.member.application.port.out.MemberQueryPersistencePort;
import com.skcc.cloud.member.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
@Slf4j
@Transactional
class MemberCommandServiceTest {

    @Autowired
    MemberCommandPersistencePort memberCommandPersistencePort;
    @Autowired
    MemberQueryPersistencePort memberQueryPersistencePort;

    @Test
    public void joinMember(){
        Member member = memberCommandPersistencePort.insertMember(new Member(null, "silverte@sk.com", "김아키"));
        Assertions.assertThat(member.getName()).isEqualTo("김아키");
    }

    @Test
    public void searchAllAnd(){
        MemberQueryRequestDTO memberQueryRequestDTO = new MemberQueryRequestDTO();
        // memberQueryRequestDTO.setSearchKeys(new ArrayList<>(){{add(MemberKeyType.name);}});
        Page<Member> members = memberQueryPersistencePort.searchAllAnd(
                memberQueryRequestDTO.getSearchKeys(),
                memberQueryRequestDTO.getSearchValues(),
                memberQueryRequestDTO.of());
        Assertions.assertThat(members.stream().count()).isNotNegative();
    }
}