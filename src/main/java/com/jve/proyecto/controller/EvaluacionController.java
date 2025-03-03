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
    public ResponseEntity<List<EvaluacionDTO>> TraerTodas() {
        return ResponseEntity.ok(evaluacionService.TraerTodas());
    }

    @GetMapping("/prueba/{idPrueba}")
    public ResponseEntity<List<EvaluacionDTO>> TraerEvaluacionesPorPrueba(@PathVariable Long idPrueba) {
        List<EvaluacionDTO> evaluaciones = evaluacionService.TraerEvaluacionesPorPrueba(idPrueba);
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
