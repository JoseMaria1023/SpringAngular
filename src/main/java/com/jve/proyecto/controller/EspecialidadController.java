package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EspecialidadDTO;
import com.jve.proyecto.service.EspecialidadService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/crear")
       public ResponseEntity<EspecialidadDTO> crearEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO savedEspecialidad = especialidadService.guardarEspecialidad(especialidadDTO);
        return ResponseEntity.ok(savedEspecialidad);
    }

    @GetMapping("/todos")
        public ResponseEntity<List<EspecialidadDTO>> obtenerTodasLasEspecialidades() {
        List<EspecialidadDTO> especialidades = especialidadService.obtenerTodos();
        return ResponseEntity.ok(especialidades);
    }

    @PutMapping("/editar/{id}")
         public ResponseEntity<EspecialidadDTO> editarEspecialidad(
            @PathVariable("id") Long id,
            @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO updatedEspecialidad = especialidadService.editarEspecialidad(id, especialidadDTO);
        return ResponseEntity.ok(updatedEspecialidad);
    }
}
