package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.service.EvaluacionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @PostMapping
    public ResponseEntity<EvaluacionDTO> crearEvaluacion(@Valid @RequestBody EvaluacionDTO evaluacionDTO) {
        EvaluacionDTO savedEvaluacion = evaluacionService.guardarEvaluacion(evaluacionDTO);
        return ResponseEntity.ok(savedEvaluacion);
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionDTO>> TraerTodasLasEvaluaciones() {
        List<EvaluacionDTO> evaluaciones = evaluacionService.TraerTodas();
        return ResponseEntity.ok(evaluaciones);
    }
}
