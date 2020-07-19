package com.myprod.mymessenger.user.manager.cucumber;

import com.myprod.mymessenger.user.manager.converter.SimpleConverter;
import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;
import com.myprod.mymessenger.user.manager.model.Json;
import com.myprod.mymessenger.user.manager.model.input.Sign;
import io.cucumber.java.en.When;

public class RegistrationStepDefinition extends AbstractStepDefinition {
  @When("^the client registration with phone number is (.*) and password is (.*)$")
  public void the_client_registration(String phone, String password) throws ConvertException {
    reinitDatabase();

    var sign = Sign.builder().phone(phone).password(password).build();

    SimpleConverter<Object, Json> converter = converterFactory.createSimpleConverter();
    var json = converter.convert(sign, Json.class);

    responseContext.post(domain + "/auth/signup", json);
  }
}
