package com.myprod.mymessenger.user.manager.cucumber;

import com.myprod.mymessenger.user.manager.Application;
import com.myprod.mymessenger.user.manager.converter.factory.ConverterFactory;
import com.myprod.mymessenger.user.manager.cucumber.context.ResponseContext;
import io.cucumber.spring.CucumberContextConfiguration;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(value = "test")
@CucumberContextConfiguration
public abstract class AbstractStepDefinition {
  protected final Logger logger = LoggerFactory.getLogger(AbstractStepDefinition.class);

  protected final String domain = "http://localhost:8080";

  @Autowired protected ConverterFactory converterFactory;

  @Autowired protected ResponseContext responseContext;

  @Autowired protected Flyway flyway;

  public void reinitDatabase() {
    flyway.clean();
    flyway.migrate();
  }
}
