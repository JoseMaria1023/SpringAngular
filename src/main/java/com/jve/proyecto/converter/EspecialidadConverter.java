package com.jve.proyecto.converter;

import com.jve.proyecto.dto.EspecialidadDTO;
import com.jve.proyecto.entity.Especialidad;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadConverter {

    private final ModelMapper modelMapper;

    public EspecialidadConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EspecialidadDTO entityToDto(Especialidad entity) {
        return modelMapper.map(entity, EspecialidadDTO.class);
    }

    public Especialidad dtoToEntity(EspecialidadDTO especialidadDTO) {
        return modelMapper.map(especialidadDTO, Especialidad.class);
    }
}