package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.service.EvaluacionService;

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

    @GetMapping("/todas")
    public ResponseEntity<List<EvaluacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(evaluacionService.obtenerTodas());
    }

    @GetMapping("/{idPrueba}") 
public ResponseEntity<List<EvaluacionDTO>> obtenerEvaluacionesPorPrueba(@PathVariable Long idPrueba) {
    List<EvaluacionDTO> evaluaciones = evaluacionService.obtenerEvaluacionesPorPrueba(idPrueba);
    return ResponseEntity.ok(evaluaciones);
}
    @PostMapping("/crear")
    public ResponseEntity<EvaluacionDTO> crearEvaluacion(@RequestBody EvaluacionDTO dto) {
        return ResponseEntity.ok(evaluacionService.guardarEvaluacion(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> actualizarEvaluacion(@PathVariable Long id, @RequestBody EvaluacionDTO dto) {
        return ResponseEntity.ok(evaluacionService.actualizarEvaluacion(id, dto));
    }
}