package com.myprod.mymessanger.domain.user;

import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class User {

  private final UUID uuid;

  private final String name;

  private final String surname;

  private final String lastName;

  private final String username;
}
