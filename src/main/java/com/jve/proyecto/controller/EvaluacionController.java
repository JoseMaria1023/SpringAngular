package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Traer todas las evaluaciones", description = "Devuelve una lista de todas las evaluaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EvaluacionDTO>> TraerTodas() {
        return ResponseEntity.ok(evaluacionService.TraerTodas());
    }

    @GetMapping("/prueba/{idPrueba}")
    @Operation(summary = "Traer evaluaciones por prueba", description = "Devuelve una lista de evaluaciones asociadas a una prueba específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones por prueba obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EvaluacionDTO>> TraerEvaluacionesPorPrueba(
            @Parameter(description = "ID de la prueba para obtener las evaluaciones asociadas", required = true)
            @PathVariable Long idPrueba) {
        List<EvaluacionDTO> evaluaciones = evaluacionService.TraerEvaluacionesPorPrueba(idPrueba);
        return ResponseEntity.ok(evaluaciones);
    }

    @PostMapping("/evaluar")
    @Operation(summary = "Evaluar a un participante", description = "Crea una nueva evaluación para un participante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación creada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EvaluacionDTO> evaluarParticipante(
            @Parameter(description = "Datos de la evaluación para el participante", required = true)
            @RequestBody EvaluacionDTO evaluacionDTO) {
        return ResponseEntity.ok(evaluacionService.evaluarParticipante(evaluacionDTO));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas las evaluaciones", description = "Devuelve una lista de todas las evaluaciones registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EvaluacionDTO>> listarEvaluaciones() {
        return ResponseEntity.ok(evaluacionService.listarEvaluaciones());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una evaluación", description = "Actualiza los datos de una evaluación existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación actualizada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EvaluacionDTO> actualizarEvaluacion(
            @Parameter(description = "ID de la evaluación a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de la evaluación a actualizar", required = true)
            @RequestBody EvaluacionDTO dto) {
        return ResponseEntity.ok(evaluacionService.actualizarEvaluacion(id, dto));
    }
}
