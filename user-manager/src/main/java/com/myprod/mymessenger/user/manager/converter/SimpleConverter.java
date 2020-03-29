package com.myprod.mymessenger.user.manager.converter;

import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;

@FunctionalInterface
public interface SimpleConverter<IN, OUT> extends Converter<IN, OUT, OUT> {
  public OUT convert(IN data, Class<OUT> outClass) throws ConvertException;
}
