package com.jve.proyecto.service;

import com.jve.proyecto.converter.EvaluacionConverter;
import com.jve.proyecto.converter.EvaluacionitemConverter;
import com.jve.proyecto.converter.ItemConverter;
import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.Evaluacion;
import com.jve.proyecto.entity.EvaluacionItem;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.exception.ErrorEvaluacionNotFoundException;
import com.jve.proyecto.exception.ErrorPruebaNotFoundException;
import com.jve.proyecto.repository.EvaluacionItemRepository;
import com.jve.proyecto.repository.EvaluacionRepository;
import com.jve.proyecto.repository.ItemRepository;
import com.jve.proyecto.repository.PruebaRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {
    private final EvaluacionItemRepository evaluacionItemRepository;
    private final EvaluacionitemConverter evaluacionItemConverter;
    private final ItemRepository itemRepository;
    private final PruebaRepository pruebaRepository;
    private final ItemConverter itemConverter;
    private final EvaluacionRepository evaluacionRepository;
    private final EvaluacionConverter evaluacionConverter;


    public EvaluacionItemService(EvaluacionItemRepository evaluacionItemRepository, EvaluacionitemConverter evaluacionItemConverter,ItemRepository itemRepository, 
    PruebaRepository pruebaRepository,ItemConverter itemConverter, EvaluacionRepository evaluacionRepository, EvaluacionConverter evaluacionConverter) {
        this.evaluacionItemRepository = evaluacionItemRepository;
        this.evaluacionItemConverter = evaluacionItemConverter;
        this.itemRepository = itemRepository;
        this.pruebaRepository = pruebaRepository;
        this.itemConverter = itemConverter;
        this.evaluacionRepository= evaluacionRepository;
        this.evaluacionConverter= evaluacionConverter;

    }
    

    public EvaluacionitemDTO guardarEvaluacionItem(EvaluacionitemDTO evaluacionItemDTO) {
        var evaluacionItem = evaluacionItemConverter.dtoToEntity(evaluacionItemDTO);
        var savedItem = evaluacionItemRepository.save(evaluacionItem);
        return evaluacionItemConverter.entityToDto(savedItem);
    }

    public Double calcularMediaPonderada(Long evaluacionId) {
        List<EvaluacionItem> evaluacionItems = evaluacionItemRepository.findByEvaluacion_IdEvaluacion(evaluacionId);
    
        double sumaPonderada = 0.0;
        double sumaPesos = 0.0;
    
        for (EvaluacionItem item : evaluacionItems) {
            double gradoConsecucion = item.getValoracion(); 
            double maxGradoConsecucion = item.getItem().getGradosConsecucion(); 
            double peso = item.getItem().getPeso(); 
    
            if (maxGradoConsecucion > 0) {
                double notaItem = (gradoConsecucion / maxGradoConsecucion) * peso; 
                sumaPonderada += notaItem;
            }
    
            sumaPesos += peso; 
        }
    
        double notaFinal = sumaPesos > 0 ? (sumaPonderada / sumaPesos) * 100 : 0.0;
    
        return notaFinal;
    }
    
    public EvaluacionDTO actualizarNotaFinal(Long evaluacionId) {
    Double mediaPonderada = calcularMediaPonderada(evaluacionId);
    Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId).orElseThrow(() -> new ErrorEvaluacionNotFoundException("Evaluacion no encontrada"));

    evaluacion.setNotaFinal(mediaPonderada);

    evaluacionRepository.save(evaluacion);

    Evaluacion evaluacionActualizada = evaluacionRepository.findById(evaluacionId)
    .orElseThrow(() -> new RuntimeException("No se encontró la evaluación después de guardar"));

    return evaluacionConverter.entityToDto(evaluacionActualizada);
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

    public Optional<Long> TraerIdEvaluacionPorPrueba(Long idPrueba) {
        return evaluacionRepository.findByPrueba_IdPrueba(idPrueba)
                .stream()
                .map(Evaluacion::getIdEvaluacion)
                .findFirst();
    }
    public List<EvaluacionitemDTO> traerTodos() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemConverter::entityToDto)
                .collect(Collectors.toList());
    }
     public String TraerEnunciadoPorPrueba(Long idPrueba) {
        Prueba prueba = pruebaRepository.findById(idPrueba).orElseThrow(() -> new ErrorPruebaNotFoundException("Prueba no encontrada"));
        return prueba.getEnunciado();
    }

    public List<ItemDTO> TraerItemsPorPrueba(Long idPrueba) {
        return itemRepository.findByPrueba_IdPrueba(idPrueba)
                .stream()
                .map(item -> itemConverter.entityToDto(item))  
                .collect(Collectors.toList());
    }


    public List<EvaluacionitemDTO> TraerPorEvaluacion(Long idEvaluacion) {
        return evaluacionItemRepository.findByEvaluacion_IdEvaluacion(idEvaluacion).stream()
                .map(evaluacionItemConverter::entityToDto)
                .collect(Collectors.toList());
    }
}