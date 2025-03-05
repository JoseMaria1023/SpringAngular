package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EspecialidadDTO;
import com.jve.proyecto.service.EspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "http://localhost:4200")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear una nueva especialidad", description = "Crea una nueva especialidad en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad creada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EspecialidadDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EspecialidadDTO> crearEspecialidad(
            @Parameter(description = "Datos de la nueva especialidad", required = true)
            @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO savedEspecialidad = especialidadService.guardarEspecialidad(especialidadDTO);
        return ResponseEntity.ok(savedEspecialidad);
    }

    @GetMapping("/todos")
    @Operation(summary = "Traer todas las especialidades", description = "Devuelve una lista de todas las especialidades")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EspecialidadDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EspecialidadDTO>> TraerTodasLasEspecialidades() {
        List<EspecialidadDTO> especialidades = especialidadService.TraerTodos();
        return ResponseEntity.ok(especialidades);
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar una especialidad existente", description = "Actualiza los datos de una especialidad existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad actualizada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EspecialidadDTO.class))),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EspecialidadDTO> editarEspecialidad(
            @Parameter(description = "ID de la especialidad a editar", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Datos de la especialidad a actualizar", required = true)
            @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO updatedEspecialidad = especialidadService.editarEspecialidad(id, especialidadDTO);
        return ResponseEntity.ok(updatedEspecialidad);
    }
}
