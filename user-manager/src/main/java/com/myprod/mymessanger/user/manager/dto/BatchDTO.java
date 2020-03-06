package com.myprod.mymessanger.user.manager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BatchDTO<T> {

  @Getter
  private long limit;

  @Getter
  private long offset;

  @Getter
  private long number;

  @Getter
  private List<T> list;
}
