package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.service.EvaluacionItemService;
import com.jve.proyecto.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/evaluacion-items")
@CrossOrigin(origins = "http://localhost:4200")
public class EvaluacionItemController {

    private final EvaluacionItemService evaluacionItemService;
    private final ItemService itemService; 

    public EvaluacionItemController(EvaluacionItemService evaluacionItemService, ItemService itemService) {
        this.evaluacionItemService = evaluacionItemService;
        this.itemService = itemService;
    }

    @PostMapping("/evaluar")
    public ResponseEntity<List<EvaluacionitemDTO>> evaluarItems(@Valid @RequestBody List<EvaluacionitemDTO> evaluaciones) {
        List<EvaluacionitemDTO> savedEvaluaciones = evaluacionItemService.guardarTodos(evaluaciones);
        return ResponseEntity.ok(savedEvaluaciones);
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionitemDTO>> traerTodosLosEvaluacionItems() {
        List<EvaluacionitemDTO> evaluacionItems = evaluacionItemService.traerTodos();
        return ResponseEntity.ok(evaluacionItems);
    }

    @GetMapping("/prueba/{idPrueba}")
    public ResponseEntity<Long> obtenerIdEvaluacion(@PathVariable Long idPrueba) {
        return evaluacionItemService.obtenerIdEvaluacionPorPrueba(idPrueba)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/items/prueba/{idPrueba}")
    public ResponseEntity<List<ItemDTO>> obtenerItemsPorPrueba(@PathVariable Long idPrueba) {
        List<ItemDTO> items = evaluacionItemService.obtenerItemsPorPrueba(idPrueba);
        return ResponseEntity.ok(items);
    }
     @PutMapping("/actualizar-nota/{idEvaluacion}")
    public ResponseEntity<EvaluacionDTO> actualizarNotaFinal(@PathVariable Long idEvaluacion) {
        try {
            EvaluacionDTO evaluacionDTO = evaluacionItemService.actualizarNotaFinal(idEvaluacion);
            return ResponseEntity.ok(evaluacionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); 
        }
    }
}