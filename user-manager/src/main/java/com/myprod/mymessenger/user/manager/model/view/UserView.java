package com.myprod.mymessenger.user.manager.model.view;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserView {

    private final UUID id;

    private final String firstName;

    private final String surname;

    private final String lastName;

    private final String username;

    private final String mainPhone;

    private final Set<String> emails;

    private final Set<String> phoneNumbers;
}
