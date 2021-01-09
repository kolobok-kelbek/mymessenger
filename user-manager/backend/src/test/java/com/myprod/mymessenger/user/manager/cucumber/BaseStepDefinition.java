package com.myprod.mymessenger.user.manager.cucumber;

import static org.junit.Assert.*;

import io.cucumber.java.en.Then;

public class BaseStepDefinition extends AbstractStepDefinition {
  @Then("the response's code is (\\d{3})$")
  public void response_code_is(int code) {
    assertEquals(responseContext.getStatusCode(), code);
  }

  @Then("the response has token")
  public void response_has_token() {
    logger.info(
        "assert \"the response has  token\": AuthorizationCookie - "
            + responseContext.getAuthorizationCookie());

    assertTrue(responseContext.hasAuthToken());
  }

  @Then("the response has not token")
  public void response_has_not_token() {
    logger.info(
        "assert \"the response has not token\": AuthorizationCookie - "
            + responseContext.getAuthorizationCookie());

    assertFalse(responseContext.hasAuthToken());
  }

  @Then("the response's body is empty")
  public void response_body_is_empty() {
    var body = responseContext.getBody();

    logger.info("assert \"the response's body is empty\": body - " + body);

    assertNull(body);
  }

  @Then("the response's body is not empty")
  public void response_body_is_not_empty() {
    var body = responseContext.getBody();

    logger.info("assert \"the response's body is not empty\": body - " + body);

    assertNotNull(body);
    assertFalse(body.isEmpty());
  }
}
