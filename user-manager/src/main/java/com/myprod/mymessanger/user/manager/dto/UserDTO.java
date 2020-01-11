package com.myprod.mymessanger.user.manager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class UserDTO {
    @Getter
    private final UUID uuid;

    @Getter
    private final String name;

    @Getter
    private final String surname;

    @Getter
    private final String lastName;

    @Getter
    private final String nickname;

    @Getter
    private String email;
}
