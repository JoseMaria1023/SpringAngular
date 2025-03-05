package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.dto.EvaluacionitemDTO;
import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.service.EvaluacionItemService;
import com.jve.proyecto.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Evaluar items", description = "Guarda las evaluaciones para los ítems relacionados con una prueba")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluaciones guardadas con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionitemDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EvaluacionitemDTO>> evaluarItems(
            @Parameter(description = "Lista de evaluaciones para los ítems", required = true)
            @Valid @RequestBody List<EvaluacionitemDTO> evaluaciones) {
        List<EvaluacionitemDTO> savedEvaluaciones = evaluacionItemService.guardarTodos(evaluaciones);
        return ResponseEntity.ok(savedEvaluaciones);
    }

    @GetMapping
    @Operation(summary = "Traer todos los ítems evaluados", description = "Devuelve una lista de todos los ítems evaluados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ítems evaluados obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionitemDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EvaluacionitemDTO>> traerTodosLosEvaluacionItems() {
        List<EvaluacionitemDTO> evaluacionItems = evaluacionItemService.traerTodos();
        return ResponseEntity.ok(evaluacionItems);
    }

    @GetMapping("/prueba/{idPrueba}")
    @Operation(summary = "Traer ID de evaluación por prueba", description = "Devuelve el ID de la evaluación asociada a una prueba específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ID de evaluación obtenido con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Long> TraerIdEvaluacion(@PathVariable Long idPrueba) {
        return evaluacionItemService.TraerIdEvaluacionPorPrueba(idPrueba)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/items/prueba/{idPrueba}")
    @Operation(summary = "Traer ítems por prueba", description = "Devuelve una lista de los ítems asociados a una prueba específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ítems obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ItemDTO.class))),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ItemDTO>> TraerItemsPorPrueba(@PathVariable Long idPrueba) {
        List<ItemDTO> items = evaluacionItemService.TraerItemsPorPrueba(idPrueba);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/actualizar-nota/{idEvaluacion}")
    @Operation(summary = "Actualizar la nota final de una evaluación", description = "Actualiza la nota final de una evaluación específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota final actualizada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EvaluacionDTO> actualizarNotaFinal(
            @Parameter(description = "ID de la evaluación a actualizar", required = true)
            @PathVariable Long idEvaluacion) {
        try {
            EvaluacionDTO evaluacionDTO = evaluacionItemService.actualizarNotaFinal(idEvaluacion);
            return ResponseEntity.ok(evaluacionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
