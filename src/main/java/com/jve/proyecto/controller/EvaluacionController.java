package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.service.EvaluacionItemService;
import com.jve.proyecto.service.EvaluacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping("/todas")
    public ResponseEntity<List<EvaluacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(evaluacionService.obtenerTodas());
    }

    @GetMapping("/prueba/{idPrueba}")
    public ResponseEntity<List<EvaluacionDTO>> obtenerEvaluacionesPorPrueba(@PathVariable Long idPrueba) {
        List<EvaluacionDTO> evaluaciones = evaluacionService.obtenerEvaluacionesPorPrueba(idPrueba);
        return ResponseEntity.ok(evaluaciones);
    }

    @PostMapping("/evaluar")
    public ResponseEntity<EvaluacionDTO> evaluarParticipante(@RequestBody EvaluacionDTO evaluacionDTO) {
        return ResponseEntity.ok(evaluacionService.evaluarParticipante(evaluacionDTO));
    }
   

    @GetMapping("/listar")
    public ResponseEntity<List<EvaluacionDTO>> listarEvaluaciones() {
        return ResponseEntity.ok(evaluacionService.listarEvaluaciones());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> actualizarEvaluacion(@PathVariable Long id, @RequestBody EvaluacionDTO dto) {
        return ResponseEntity.ok(evaluacionService.actualizarEvaluacion(id, dto));
    }
}
