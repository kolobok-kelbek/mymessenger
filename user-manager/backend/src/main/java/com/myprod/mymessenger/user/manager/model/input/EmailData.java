package com.myprod.mymessenger.user.manager.model.input;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailData {
  @NotNull @Email private String email;

  public EmailData(@Valid @NotNull String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
