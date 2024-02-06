package com.skcc.cloud.member.member.adapter.in.web;

import com.skcc.cloud.member.member.application.port.in.MemberCommandRequestDTO;
import com.skcc.cloud.member.member.application.port.in.MemberCommandUseCase;
import com.skcc.cloud.member.member.application.port.in.MemberQueryRequestDTO;
import com.skcc.cloud.member.member.application.port.in.MemberQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberQueryWebController {

    private final MemberQueryUseCase memberQueryUseCase;

    @Operation(summary = "회원 조회(AND)", description = "회원명, email 주소로 회원을 조회한다.")
    @GetMapping("and")
    public ResponseEntity<Object> searchAllMembersAnd(final MemberQueryRequestDTO memberQueryRequestDTO) {
        log.debug("[Controller] MemberQueryWebController Called - searchMember");
        return new ResponseEntity<>(memberQueryUseCase.queryAllAndMember(memberQueryRequestDTO), HttpStatus.OK);
    }

    @Operation(summary = "회원 조회(OR)", description = "회원명, email 주소로 회원을 조회한다.")
    @GetMapping("or")
    public ResponseEntity<Object> searchAllMembersOr(final MemberQueryRequestDTO memberQueryRequestDTO) {
        log.debug("[Controller] MemberQueryWebController Called - searchMember");
        return new ResponseEntity<>(memberQueryUseCase.queryAllOrMember(memberQueryRequestDTO), HttpStatus.OK);
    }
}
