package com.myprod.mymessenger.user.manager.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.jackson.JsonNodeValueReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setFieldMatchingEnabled(true)
        .setSkipNullEnabled(true)
        .setFieldAccessLevel(AccessLevel.PRIVATE)
        .addValueReader(new JsonNodeValueReader());

    return modelMapper;
  }
}
