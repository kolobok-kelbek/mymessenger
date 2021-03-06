package com.myprod.mymessanger.domain.message;

import com.myprod.mymessanger.domain.user.User;
import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Message {

  private UUID uuid;
  private Room room;
  private User owner;
  private String message;
}
