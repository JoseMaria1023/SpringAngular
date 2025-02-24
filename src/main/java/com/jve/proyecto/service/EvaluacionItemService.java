package com.jve.proyecto.service;

import com.jve.proyecto.converter.EvaluacionitemConverter;
import com.jve.proyecto.converter.ItemConverter;
import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.EvaluacionItem;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.repository.EvaluacionItemRepository;
import com.jve.proyecto.repository.ItemRepository;
import com.jve.proyecto.repository.PruebaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {
    private final EvaluacionItemRepository evaluacionItemRepository;
    private final EvaluacionitemConverter evaluacionItemConverter;
    private final ItemRepository itemRepository;
    private final PruebaRepository pruebaRepository;
    private final ItemConverter itemConverter;

    public EvaluacionItemService(EvaluacionItemRepository evaluacionItemRepository, EvaluacionitemConverter evaluacionItemConverter,ItemRepository itemRepository, PruebaRepository pruebaRepository,ItemConverter itemConverter) {
        this.evaluacionItemRepository = evaluacionItemRepository;
        this.evaluacionItemConverter = evaluacionItemConverter;
        this.itemRepository = itemRepository;
        this.pruebaRepository = pruebaRepository;
        this.itemConverter = itemConverter;

    }

    public EvaluacionitemDTO guardarEvaluacionItem(EvaluacionitemDTO evaluacionItemDTO) {
        var evaluacionItem = evaluacionItemConverter.dtoToEntity(evaluacionItemDTO);
        var savedItem = evaluacionItemRepository.save(evaluacionItem);
        return evaluacionItemConverter.entityToDto(savedItem);
    }

    public List<EvaluacionitemDTO> guardarTodos(List<EvaluacionitemDTO> evaluaciones) {
        List<EvaluacionItem> items = evaluaciones.stream()
                .map(evaluacionItemConverter::dtoToEntity)
                .collect(Collectors.toList());
        List<EvaluacionItem> savedItems = evaluacionItemRepository.saveAll(items);
        return savedItems.stream()
                .map(evaluacionItemConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<EvaluacionitemDTO> traerTodos() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemConverter::entityToDto)
                .collect(Collectors.toList());
    }
     public String obtenerEnunciadoPorPrueba(Long idPrueba) {
        Prueba prueba = pruebaRepository.findById(idPrueba)
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
        return prueba.getEnunciado();
    }

    public List<ItemDTO> obtenerItemsPorPrueba(Long idPrueba) {
        return itemRepository.findByPrueba_IdPrueba(idPrueba)
                .stream()
                .map(item -> itemConverter.entityToDto(item))  
                .collect(Collectors.toList());
    }


    public List<EvaluacionitemDTO> obtenerPorEvaluacion(Long idEvaluacion) {
        return evaluacionItemRepository.findByEvaluacion_IdEvaluacion(idEvaluacion).stream()
                .map(evaluacionItemConverter::entityToDto)
                .collect(Collectors.toList());
    }
}