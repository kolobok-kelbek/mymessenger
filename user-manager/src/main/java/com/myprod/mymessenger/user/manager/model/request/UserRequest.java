package com.myprod.mymessenger.user.manager.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserRequest {
    @Getter
    private final String name;

    @Getter
    private final String surname;

    @Getter
    private final String lastName;

    @Getter
    private final String nickname;
}
