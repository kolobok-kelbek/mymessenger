package com.myprod.mymessenger.user.manager.converter.dto;

public interface EntityConverter<TDO, Entity> {
    public TDO convertEntity(Entity entity);
}
