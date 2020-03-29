package com.myprod.mymessenger.user.manager.converter;

import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;

@FunctionalInterface
public interface Converter<IN, OUT, TYPE> {
  public OUT convert(IN data, Class<TYPE> outClass) throws ConvertException;
}
