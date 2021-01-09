package com.myprod.mymessenger.user.manager.cucumber;

import com.myprod.mymessenger.user.manager.converter.SimpleConverter;
import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;
import com.myprod.mymessenger.user.manager.model.Json;
import com.myprod.mymessenger.user.manager.model.input.Sign;
import io.cucumber.java.en.When;

public class AuthStepDefinition extends AbstractStepDefinition {
  @When("^the client authentication with phone number is (.*) and password is (.*)$")
  public void the_client_authentication(String phone, String password) throws ConvertException {
    var sign = Sign.builder().phone(phone).password(password).build();

    SimpleConverter<Object, Json> converter = converterFactory.createSimpleConverter();
    var json = converter.convert(sign, Json.class);

    responseContext.post(domain + "/auth/signin", json);
  }

  @When("^the client logout$")
  public void the_client_logout() {
    responseContext.get(domain + "/auth/logout");
  }
}
