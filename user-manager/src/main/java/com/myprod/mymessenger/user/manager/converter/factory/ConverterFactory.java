package com.myprod.mymessenger.user.manager.converter.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprod.mymessenger.user.manager.converter.*;
import com.myprod.mymessenger.user.manager.converter.user.SignToUserConverter;
import com.myprod.mymessenger.user.manager.converter.user.UserToUserViewConverter;
import com.myprod.mymessenger.user.manager.converter.user.UserViewToUserConverter;
import com.myprod.mymessenger.user.manager.model.view.ListView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ConverterFactory {
    private final ObjectMapper jsonMapper;

    private final ModelMapper modelMapper;

    @Autowired
    public ConverterFactory(
            final ObjectMapper jsonMapper,
            final ModelMapper modelMapper,
            final SignToUserConverter signToUserConverter,
            final UserToUserViewConverter userToUserViewConverter,
            final UserViewToUserConverter userViewToUserConverter
            ) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;

        this.modelMapper.addConverter(signToUserConverter);
        this.modelMapper.addConverter(userToUserViewConverter);
        this.modelMapper.addConverter(userViewToUserConverter);
    }

    public <IN, OUT> SimpleConverter<IN, OUT> createSimpleConverter() {
        return new ObjectConverter<>(jsonMapper, modelMapper);
    }

    public <IN, OUT> Converter<Page<IN>, ListView<OUT>, OUT> createPageToListViewConverter() {
        return new PageToListViewConverter<>(createSimpleConverter());
    }
}
