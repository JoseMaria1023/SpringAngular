package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter {

    private final ModelMapper modelMapper;

    public ItemConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemDTO entityToDto(Item entity) {
        return modelMapper.map(entity, ItemDTO.class);
    }

    public Item dtoToEntity(ItemDTO dto) {
        return modelMapper.map(dto, Item.class);
    }
}
