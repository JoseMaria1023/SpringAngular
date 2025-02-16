package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.EvaluacionitemConverter;
import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.repository.EvaluacionItemRepository;

@Service
public class EvaluacionItemService {
    private final EvaluacionItemRepository evaluacionItemRepository;
    private final EvaluacionitemConverter evaluacionItemConverter;

    public EvaluacionItemService(EvaluacionItemRepository evaluacionItemRepository, EvaluacionitemConverter evaluacionItemConverter) {
        this.evaluacionItemRepository = evaluacionItemRepository;
        this.evaluacionItemConverter = evaluacionItemConverter;
    }

    public EvaluacionitemDTO guardarEvaluacionItem(EvaluacionitemDTO evaluacionItemDTO) {
        var evaluacionItem = evaluacionItemConverter.dtoToEntity(evaluacionItemDTO);
        var savedItem = evaluacionItemRepository.save(evaluacionItem);
        return evaluacionItemConverter.entityToDto(savedItem);
    }

    public List<EvaluacionitemDTO> obtenerTodos() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
