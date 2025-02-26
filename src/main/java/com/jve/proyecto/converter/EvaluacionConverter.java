package com.jve.proyecto.converter;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.entity.Evaluacion;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionConverter {

    private final ModelMapper modelMapper;

     public EvaluacionConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configuración personalizada para mapear el nombre del participante
        this.modelMapper.addMappings(new PropertyMap<Evaluacion, EvaluacionDTO>() {
            @Override
            protected void configure() {
                map().setParticipanteNombre(source.getParticipante().getNombre());
            }
        });
    }

    public EvaluacionDTO entityToDto(Evaluacion entity) {
        return modelMapper.map(entity, EvaluacionDTO.class);
    }

    public Evaluacion dtoToEntity(EvaluacionDTO dto) {
        return modelMapper.map(dto, Evaluacion.class);
    }
}
