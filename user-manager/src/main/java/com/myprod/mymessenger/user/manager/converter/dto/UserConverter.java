package com.myprod.mymessenger.user.manager.converter.dto;

import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import org.springframework.stereotype.Service;

@Service
public class UserConverter implements EntityConverter<UserView, User> {
    @Override
    public UserView convertEntity(User user) {
        return UserView.builder()
                .uuid(user.getId())
                .phone(user.getPhone())
                .nickname(user.getUsername())
                .name(user.getFirstName())
                .surname(user.getSurname())
                .lastName(user.getLastName())
                .build();
    }
}
