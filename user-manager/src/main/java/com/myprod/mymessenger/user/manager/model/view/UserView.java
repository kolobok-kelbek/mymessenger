package com.myprod.mymessenger.user.manager.model.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class UserView {
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
    private String phone;
}
