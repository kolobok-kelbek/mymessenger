package com.myprod.mymessenger.user.manager.model.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class PhoneView {

    @Getter
    private String number;

    @Getter
    private Date createAt;

}
