package com.myprod.mymessenger.user.manager.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PhoneNumberData {
    @NotNull
    @Pattern(regexp = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$")
    private String phoneNumber;

    public PhoneNumberData(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
