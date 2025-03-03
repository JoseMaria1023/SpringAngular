package com.jve.proyecto.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.EspecialidadConverter;
import com.jve.proyecto.dto.EspecialidadDTO;
import com.jve.proyecto.entity.Especialidad;
import com.jve.proyecto.exception.ErrorEspecialidadNotFoundException;
import com.jve.proyecto.repository.EspecialidadRepository;
@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadConverter especialidadConverter;

    public EspecialidadService(EspecialidadRepository especialidadRepository, EspecialidadConverter especialidadConverter) {
        this.especialidadRepository = especialidadRepository;
        this.especialidadConverter = especialidadConverter;
    }

    public EspecialidadDTO guardarEspecialidad(EspecialidadDTO especialidadDTO) {
        var especialidad = especialidadConverter.dtoToEntity(especialidadDTO);
        var savedEspecialidad = especialidadRepository.save(especialidad);
        return especialidadConverter.entityToDto(savedEspecialidad);
    }

    public List<EspecialidadDTO> TraerTodos() {
        return especialidadRepository.findAll().stream()
                .map(especialidadConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public EspecialidadDTO editarEspecialidad(Long id, EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = especialidadRepository.findById(id).orElseThrow(() -> new ErrorEspecialidadNotFoundException("Especialidad no encontrada"));
        
        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setCodigo(especialidadDTO.getCodigo());
        
        var updatedEspecialidad = especialidadRepository.save(especialidad);
        return especialidadConverter.entityToDto(updatedEspecialidad);
    }
    
    
}