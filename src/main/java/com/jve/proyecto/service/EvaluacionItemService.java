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
            double gradoConsecucion = item.getValoracion(); // 🔹 Valor obtenido (0 a GC_max)
            double maxGradoConsecucion = item.getItem().getGradosConsecucion(); // 🔹 Máximo posible
            double peso = item.getItem().getPeso(); // 🔹 Peso del ítem
    
            // ⚠️ Evitamos divisiones por 0 si algún GC_max está mal configurado
            if (maxGradoConsecucion > 0) {
                double notaItem = (gradoConsecucion / maxGradoConsecucion) * peso; // 🔥 Normalización por su máximo
                sumaPonderada += notaItem;
            }
    
            sumaPesos += peso; // 🔥 Sumamos los pesos
        }
    
        double notaFinal = sumaPesos > 0 ? (sumaPonderada / sumaPesos) * 100 : 0.0;
    
        System.out.println("Evaluación ID: " + evaluacionId);
        System.out.println("Suma ponderada: " + sumaPonderada);
        System.out.println("Suma de pesos: " + sumaPesos);
        System.out.println("Nota final calculada: " + notaFinal);
    
        return notaFinal;
    }
    
    public EvaluacionDTO actualizarNotaFinal(Long evaluacionId) {
    Double mediaPonderada = calcularMediaPonderada(evaluacionId);
    System.out.println("Nota calculada: " + mediaPonderada);

    Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
            .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

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

    public Optional<Long> obtenerIdEvaluacionPorPrueba(Long idPrueba) {
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