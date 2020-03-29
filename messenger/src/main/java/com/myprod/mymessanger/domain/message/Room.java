package com.myprod.mymessanger.domain.message;

import com.myprod.mymessanger.domain.user.User;
import java.util.UUID;
import lombok.Builder;

@Builder
public class Room {

  private UUID uuid;
  private String name;
  private User owner;
}
