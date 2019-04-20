package com.rohan90.majdoor.api.tasks.domain.dtos;

import com.rohan90.majdoor.api.tasks.domain.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class UserDTO {
    @NotEmpty
    private String name;
    @Positive
    private long id;

    public UserDTO(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public static User transformToEntity(UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static UserDTO transformToDTO(User entity) {
        UserDTO dto = new UserDTO(entity.getName(), entity.getId());
        return dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
