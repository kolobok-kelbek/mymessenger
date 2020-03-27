package com.myprod.mymessenger.user.manager.converter.user;

import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.input.Sign;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignToUserConverter implements Converter<Sign, User> {
    @Override
    public User convert(MappingContext<Sign, User> context) {
        Sign sign = context.getSource();

        return User.builder()
                .phone(sign.getPhone())
                .password(passwordEncoder().encode(sign.getPassword()))
                .enabled(true)
                .roles(sign.getRoleList())
                .build();
    }

    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
