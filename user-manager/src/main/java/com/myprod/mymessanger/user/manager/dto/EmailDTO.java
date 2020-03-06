package com.myprod.mymessanger.user.manager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class EmailDTO {

  @Getter
  private String email;

  @Getter
  private Date createAt;

}
