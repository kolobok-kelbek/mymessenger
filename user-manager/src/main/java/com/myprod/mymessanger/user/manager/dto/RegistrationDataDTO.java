package com.myprod.mymessanger.user.manager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class RegistrationDataDTO {
    @Getter
    @NotNull
    private final String nickname;

    @Getter
    @NotNull
    private String email;

    @Getter
    @NotNull
    private String password;
}
