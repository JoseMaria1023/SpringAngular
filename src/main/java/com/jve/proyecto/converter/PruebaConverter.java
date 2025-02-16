package com.jve.proyecto.converter;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.entity.Prueba;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PruebaConverter {

    private final ModelMapper modelMapper;

    public PruebaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PruebaDTO entityToDto(Prueba entity) {
        return modelMapper.map(entity, PruebaDTO.class);
    }

    public Prueba dtoToEntity(PruebaDTO dto) {
        return modelMapper.map(dto, Prueba.class);
    }
}
