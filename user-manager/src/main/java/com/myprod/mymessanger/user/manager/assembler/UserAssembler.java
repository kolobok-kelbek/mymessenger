package com.myprod.mymessanger.user.manager.assembler;

import com.myprod.mymessanger.user.manager.dto.UserDTO;
import com.myprod.mymessanger.user.manager.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserAssembler {
    public UserDTO writeDTO(User user) {
        return UserDTO.builder()
                .uuid(user.getUuid())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .surname(user.getSurname())
                .lastName(user.getLastName())
                .build();
    }
}
