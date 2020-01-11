package com.myprod.mymessanger.user.manager.assembler;

import com.myprod.mymessanger.user.manager.dto.RegistrationDataDTO;
import com.myprod.mymessanger.user.manager.entity.Role;
import com.myprod.mymessanger.user.manager.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RegistrationDataAssembler {
    public User readDTO(RegistrationDataDTO registrationDataDTO, Collection<Role> roleList) {
        return User.builder()
                .nickname(registrationDataDTO.getNickname())
                .email(registrationDataDTO.getEmail())
                .password(registrationDataDTO.getPassword())
                .roles(roleList)
                .build();
    }
}
