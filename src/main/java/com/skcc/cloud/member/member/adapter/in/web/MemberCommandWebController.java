package com.skcc.cloud.member.member.adapter.in.web;

import com.skcc.cloud.member.member.application.port.in.MemberCommandRequestDTO;
import com.skcc.cloud.member.member.application.port.in.MemberCommandUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "회원 관리", description = "회원의 등록, 변경, 탈퇴를 위한 API입니다.")
public class MemberCommandWebController {

    private final MemberCommandUseCase memberCommandUseCase;

    @Operation(summary = "회원 등록", description = "신규 회원 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Object> registerMember(@RequestBody @Validated MemberCommandRequestDTO memberCommandRequestDTO) {
        log.debug("[Controller] MemberCommandController Called - registerMember");
        memberCommandUseCase.registerMember(memberCommandRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
