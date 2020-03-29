package com.myprod.mymessenger.user.manager.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;
import com.myprod.mymessenger.user.manager.model.Json;
import javax.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectConverter<In, Out> implements SimpleConverter<In, Out> {

  private final ObjectMapper jsonMapper;

  private final ModelMapper modelMapper;

  @Autowired
  public ObjectConverter(final ObjectMapper jsonMapper, final ModelMapper modelMapper) {
    this.jsonMapper = jsonMapper;
    this.modelMapper = modelMapper;
  }

  @Override
  public Out convert(@NotNull final In data, @NotNull final Class<Out> outClass)
      throws ConvertException {
    if (data instanceof Json) {
      JsonNode orderNode;

      try {
        orderNode = jsonMapper.readTree(data.toString());
      } catch (JsonProcessingException e) {
        throw new ConvertException("Converting error. JSON parse error.", e);
      }

      return modelMapper.map(orderNode, outClass);
    }

    if (outClass.isAssignableFrom(Json.class)) {
      String outData;

      try {
        outData = jsonMapper.writeValueAsString(data);
      } catch (JsonProcessingException e) {
        throw new ConvertException("JSON to object converting error.", e);
      }

      return (Out) new Json(outData);
    }

    return modelMapper.map(data, outClass);
  }
}
