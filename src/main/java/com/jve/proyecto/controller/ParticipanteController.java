package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ParticipanteDTO;
import com.jve.proyecto.service.ParticipanteService;
import com.jve.proyecto.service.UserService;

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
    public ResponseEntity<ParticipanteDTO> crearParticipante(@Valid @RequestBody ParticipanteDTO participanteDTO, Authentication authentication) {
        String username = authentication.getName();
        Long especialidadId = userService.getEspecialidadByUsername(username);
        participanteDTO.setEspecialidadId(especialidadId); 
        ParticipanteDTO savedParticipante = participanteService.guardarParticipante(participanteDTO);
    
        return ResponseEntity.ok(savedParticipante);
    }

    @GetMapping("/todos")
public ResponseEntity<List<ParticipanteDTO>> TraerParticipantes(Authentication authentication) {
    if (authentication != null) {
        String username = authentication.getName();  
        Long especialidadId = userService.getEspecialidadByUsername(username);
        
        List<ParticipanteDTO> participantes = participanteService.TraerParticipantesPorEspecialidad(especialidadId);
        return ResponseEntity.ok(participantes);
    } else {
        List<ParticipanteDTO> participantes = participanteService.TraerParticipantes();
        return ResponseEntity.ok(participantes);
    }
}


    @PutMapping("/editar/{id}")
    public ResponseEntity<ParticipanteDTO> editarParticipante(@PathVariable Long id, @Valid @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO updatedParticipante = participanteService.editarParticipante(id, participanteDTO);
        return ResponseEntity.ok(updatedParticipante);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ParticipanteDTO> TraerParticipantePorId(@PathVariable Long id) {
    ParticipanteDTO participanteDTO = participanteService.buscarParticipantePorId(id);
    return ResponseEntity.ok(participanteDTO);
}
}
