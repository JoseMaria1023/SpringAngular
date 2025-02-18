package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.EvaluacionConverter;
import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    private final EvaluacionRepository evaluacionRepository;
    private final EvaluacionConverter evaluacionConverter;

    public EvaluacionService(EvaluacionRepository evaluacionRepository, EvaluacionConverter evaluacionConverter) {
        this.evaluacionRepository = evaluacionRepository;
        this.evaluacionConverter = evaluacionConverter;
    }

    public EvaluacionDTO guardarEvaluacion(EvaluacionDTO evaluacionDTO) {
        var evaluacion = evaluacionConverter.dtoToEntity(evaluacionDTO);
        var savedEvaluacion = evaluacionRepository.save(evaluacion);
        return evaluacionConverter.entityToDto(savedEvaluacion);
    }

    public List<EvaluacionDTO> TraerTodas() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
