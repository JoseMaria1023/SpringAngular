package com.jve.proyecto.converter;

import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO entityToDto(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    public User dtoToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}
