package com.myprod.mymessanger.domain.message;

import com.myprod.mymessanger.domain.user.User;
import lombok.Builder;

import java.util.UUID;

@Builder
public class Room {

  private UUID uuid;
  private String name;
  private User owner;

}
