package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ParticipanteDTO;
import com.jve.proyecto.entity.Participante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ParticipanteConverter {

    private final ModelMapper modelMapper;

    public ParticipanteConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ParticipanteDTO entityToDto(Participante entity) {
        return modelMapper.map(entity, ParticipanteDTO.class);
    }

    public Participante dtoToEntity(ParticipanteDTO dto) {
        return modelMapper.map(dto, Participante.class);

    }
}
