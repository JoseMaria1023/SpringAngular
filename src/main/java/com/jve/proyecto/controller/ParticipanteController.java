package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ParticipanteDTO;
import com.jve.proyecto.service.ParticipanteService;
import com.jve.proyecto.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    // Crear un nuevo participante, asociándolo a la especialidad del experto
    @PostMapping
    public ResponseEntity<ParticipanteDTO> crearParticipante(@Valid @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO savedParticipante = participanteService.guardarParticipante(participanteDTO);

        return  ResponseEntity
                .ok(savedParticipante);

        /*return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        "Respuesta BAD_REQUEST"
                );*/
    }

    // Obtener todos los participantes de la especialidad del experto autenticado
    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> obtenerCompetidores() {
        String username = "expertoUsername";  // Obtén el username del experto autenticado
        String especialidad = userService.getEspecialidadByUsername(username);  // Obtén la especialidad del experto
        
        // Obtener los participantes de la especialidad del experto
        List<ParticipanteDTO> participantes = participanteService.obtenerParticipantesPorEspecialidad(Integer.parseInt(especialidad));
        return ResponseEntity.ok(participantes);
    }
    public ResponseEntity<List<ParticipanteDTO>> obtenerParticipantesPorEspecialidad(@PathVariable Integer especialidadId) {
        List<ParticipanteDTO> participantes = participanteService.obtenerParticipantesPorEspecialidad(especialidadId);
        return ResponseEntity.ok(participantes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> editarParticipante(@PathVariable Integer id, @Valid @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO updatedParticipante = participanteService.editarParticipante(id, participanteDTO);
        return ResponseEntity.ok(updatedParticipante);
    }
}
