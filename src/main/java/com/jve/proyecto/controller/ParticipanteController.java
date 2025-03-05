package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ParticipanteDTO;
import com.jve.proyecto.service.ParticipanteService;
import com.jve.proyecto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "http://localhost:4200")
public class ParticipanteController {

    private final ParticipanteService participanteService;
    private final UserService userService;

    public ParticipanteController(ParticipanteService participanteService, UserService userService) {
        this.participanteService = participanteService;
        this.userService = userService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo participante", description = "Crea un nuevo participante y lo asocia a la especialidad del usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante creado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ParticipanteDTO> crearParticipante(
            @Parameter(description = "Datos del participante a crear", required = true)
            @Valid @RequestBody ParticipanteDTO participanteDTO, 
            Authentication authentication) {
        String username = authentication.getName();
        Long especialidadId = userService.getEspecialidadByUsername(username);
        participanteDTO.setEspecialidadId(especialidadId); 
        ParticipanteDTO savedParticipante = participanteService.guardarParticipante(participanteDTO);
    
        return ResponseEntity.ok(savedParticipante);
    }

    @GetMapping("/todos")
    @Operation(summary = "Traer todos los participantes", description = "Devuelve una lista de participantes. Si el usuario está autenticado, se filtra por especialidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de participantes obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ParticipanteDTO>> TraerParticipantes(Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();  
            Long especialidadId = userService.getEspecialidadByUsername(username);
            List<ParticipanteDTO> participantes = participanteService.traerParticipantesPorEspecialidad(especialidadId);
            return ResponseEntity.ok(participantes);
        } else {
            List<ParticipanteDTO> participantes = participanteService.traerParticipantes();
            return ResponseEntity.ok(participantes);
        }
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar un participante", description = "Actualiza los datos de un participante existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante actualizado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ParticipanteDTO> editarParticipante(
            @Parameter(description = "ID del participante a editar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos del participante a actualizar", required = true)
            @Valid @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO updatedParticipante = participanteService.editarParticipante(id, participanteDTO);
        return ResponseEntity.ok(updatedParticipante);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar un participante por ID", description = "Trae los datos de un participante por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante encontrado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ParticipanteDTO> TraerParticipantePorId(
            @Parameter(description = "ID del participante a buscar", required = true)
            @PathVariable Long id) {
        ParticipanteDTO participanteDTO = participanteService.buscarParticipantePorId(id);
        return ResponseEntity.ok(participanteDTO);
    }
}
