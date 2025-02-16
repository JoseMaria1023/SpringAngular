package com.jve.proyecto.converter;

import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.entity.EvaluacionItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionitemConverter {

    private final ModelMapper modelMapper;

    public EvaluacionitemConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EvaluacionitemDTO entityToDto(EvaluacionItem entity) {
        return modelMapper.map(entity, EvaluacionitemDTO.class);
    }

    public EvaluacionItem dtoToEntity(EvaluacionitemDTO dto) {
        return modelMapper.map(dto, EvaluacionItem.class);
    }
}
