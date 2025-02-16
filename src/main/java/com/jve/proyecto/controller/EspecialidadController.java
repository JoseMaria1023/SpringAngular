package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EspecialidadDTO;
import com.jve.proyecto.service.EspecialidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Añadido para controlar roles
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    // Crear nueva especialidad (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede crear
    public ResponseEntity<EspecialidadDTO> crearEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO savedEspecialidad = especialidadService.guardarEspecialidad(especialidadDTO);
        return ResponseEntity.ok(savedEspecialidad);
    }

    // Obtener todas las especialidades (ADMIN y EXPERTO)
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EXPERTO')") // Ambos roles pueden ver
    public ResponseEntity<List<EspecialidadDTO>> obtenerTodasLasEspecialidades() {
        List<EspecialidadDTO> especialidades = especialidadService.obtenerTodos();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/rol")
//    @PreAuthorize("hasRole('ROLE_EXPERTO')")
    public ResponseEntity<String> soyExperto() {
        return  ResponseEntity
                .ok("Tienes el rol de EXPERTO");
    }

    // Editar una especialidad (solo ADMIN)
    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede editar
    public ResponseEntity<EspecialidadDTO> editarEspecialidad(
            @PathVariable("id") Long id,
            @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO updatedEspecialidad = especialidadService.editarEspecialidad(id, especialidadDTO);
        return ResponseEntity.ok(updatedEspecialidad);
    }
}
