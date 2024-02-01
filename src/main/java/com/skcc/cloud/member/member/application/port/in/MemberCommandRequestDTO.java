package com.skcc.cloud.member.member.application.port.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberCommandRequestDTO {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    @Schema(description = "회원 이메일", nullable = false, example = "born2k@gmail.com")
    String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    @Schema(description = "회원 이름", nullable = false, example = "김아키")
    String name;

}
