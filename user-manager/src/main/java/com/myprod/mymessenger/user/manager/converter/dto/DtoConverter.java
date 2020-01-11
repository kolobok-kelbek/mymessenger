package com.myprod.mymessenger.user.manager.converter.dto;

import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.view.UserView;

public interface DtoConverter<DTO, Entity> {
    public Entity convertDto(DTO dto);
}
