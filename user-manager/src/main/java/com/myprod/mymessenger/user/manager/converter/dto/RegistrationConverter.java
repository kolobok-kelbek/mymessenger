package com.myprod.mymessenger.user.manager.converter.dto;

import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.request.Sign;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationConverter implements DtoConverter<Sign, User> {
    @Override
    public User convertDto(Sign registrationDTO) {
        return User.builder()
                .phone(registrationDTO.getPhone())
                .password(passwordEncoder().encode(registrationDTO.getPassword()))
                .enabled(true)
                .roles(registrationDTO.getRoleList())
                .build();
    }

    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
