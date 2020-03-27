package com.myprod.mymessenger.user.manager.model.input;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;

@Value
@Builder
@RequiredArgsConstructor
public class PaginationQuery {
    @Min(value=0, message="Invalid value of limit.")
    private int limit;

    @Min(value=0, message="Invalid value of offset.")
    private int offset;
}
