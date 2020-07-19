package com.myprod.mymessenger.user.manager.cucumber.user;

import com.myprod.mymessenger.user.manager.cucumber.AbstractStepDefinition;
import io.cucumber.java.en.When;

public class AdminStepDefinition extends AbstractStepDefinition {
  private String getPath() {
    return domain + "/v1/users";
  }

  @When("^the admin gets users list$")
  public void the_admin_gets_users_list() {
    responseContext.getWithAuth(getPath() + "?limit=50&offset=0");
  }

  @When("^the admin gets user by (.*)$")
  public void the_admin_gets_users_list(String uuid) {
    responseContext.getWithAuth(getPath() + "/" + uuid);
  }
}
