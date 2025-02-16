package com.jve.proyecto.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.EspecialidadConverter;
import com.jve.proyecto.dto.EspecialidadDTO;
import com.jve.proyecto.entity.Especialidad;
import com.jve.proyecto.repository.EspecialidadRepository;
@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadConverter especialidadConverter;

    public EspecialidadService(EspecialidadRepository especialidadRepository, EspecialidadConverter especialidadConverter) {
        this.especialidadRepository = especialidadRepository;
        this.especialidadConverter = especialidadConverter;
    }

    // Guardar una nueva especialidad
    public EspecialidadDTO guardarEspecialidad(EspecialidadDTO especialidadDTO) {
        var especialidad = especialidadConverter.dtoToEntity(especialidadDTO);
        var savedEspecialidad = especialidadRepository.save(especialidad);
        return especialidadConverter.entityToDto(savedEspecialidad);
    }

    // Obtener todas las especialidades
    public List<EspecialidadDTO> obtenerTodos() {
        return especialidadRepository.findAll().stream()
                .map(especialidadConverter::entityToDto)
                .collect(Collectors.toList());
    }

    // Editar una especialidad existente
    public EspecialidadDTO editarEspecialidad(Long id, EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(id); 
        if (optionalEspecialidad.isPresent()) {
            Especialidad especialidad = optionalEspecialidad.get();
            // Actualizar los campos de la especialidad
            especialidad.setNombre(especialidadDTO.getNombre());
            especialidad.setDescripcion(especialidadDTO.getDescripcion());
            // Guardar la especialidad actualizada
            var updatedEspecialidad = especialidadRepository.save(especialidad);
            return especialidadConverter.entityToDto(updatedEspecialidad);
        } else {
            throw new RuntimeException("Especialidad no encontrada");
        }
    }
    
}