package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.service.EvaluacionItemService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion-items")
public class EvaluacionItemController {

    private final EvaluacionItemService evaluacionItemService;

    public EvaluacionItemController(EvaluacionItemService evaluacionItemService) {
        this.evaluacionItemService = evaluacionItemService;
    }

    @PostMapping
    public ResponseEntity<EvaluacionitemDTO> crearEvaluacionItem(@Valid @RequestBody EvaluacionitemDTO evaluacionItemDTO) {
        EvaluacionitemDTO savedEvaluacionItem = evaluacionItemService.guardarEvaluacionItem(evaluacionItemDTO);
        return ResponseEntity.ok(savedEvaluacionItem);
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionitemDTO>> TraerTodosLosEvaluacionItems() {
        List<EvaluacionitemDTO> evaluacionItems = evaluacionItemService.TraerTodos();
        return ResponseEntity.ok(evaluacionItems);
    }
}
