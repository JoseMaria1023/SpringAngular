package com.jve.proyecto.converter;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.entity.Evaluacion;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionConverter {

    private final ModelMapper modelMapper;

    public EvaluacionConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EvaluacionDTO entityToDto(Evaluacion entity) {
        return modelMapper.map(entity, EvaluacionDTO.class);
    }

    public Evaluacion dtoToEntity(EvaluacionDTO dto) {
        return modelMapper.map(dto, Evaluacion.class);
    }
}
