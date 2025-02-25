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
        Optional<Evaluacion> evaluacion = evaluacionRepository.findById(evaluacionId);
        if (!evaluacion.isPresent()) {
            throw new RuntimeException("Evaluación no encontrada");
        }

        List<EvaluacionItem> items = evaluacionItemRepository.findByEvaluacion_IdEvaluacion(evaluacionId);
    
        double sumaPonderada = 0;
        double sumaPesos = 0;
    
        for (EvaluacionItem evaluacionItem : items) {
            Item item = evaluacionItem.getItem();
            if (item == null) {
                throw new RuntimeException("Ítem no encontrado en EvaluacionItem");
            }
            double peso = item.getPeso();
            sumaPonderada += evaluacionItem.getValoracion() * peso;
            sumaPesos += peso;
        }
        if (sumaPesos == 0) {
            throw new RuntimeException("La suma de los pesos no puede ser 0");
        }
        return sumaPonderada / sumaPesos;
    }

    @Transactional
public void actualizarNotaFinal(Long evaluacionId) {
    Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
        .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

    List<EvaluacionItem> evaluacionItems = evaluacionItemRepository.findByEvaluacionId(evaluacionId);

    double totalPeso = evaluacionItems.stream().mapToDouble(item -> item.getItem().getPeso()).sum();
    double totalValoracion = evaluacionItems.stream()
        .mapToDouble(item -> item.getValoracion() * item.getItem().getPeso())
        .sum();

    double notaFinal = totalValoracion / totalPeso;
    evaluacion.setNotaFinal(notaFinal); // Suponiendo que la entidad Evaluacion tiene un campo notaFinal

    evaluacionRepository.save(evaluacion);
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