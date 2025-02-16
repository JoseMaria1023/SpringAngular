package com.jve.proyecto.service;

import com.jve.proyecto.dto.ParticipanteDTO;
import com.jve.proyecto.entity.Participante;
import com.jve.proyecto.entity.Especialidad; // Importamos la entidad Especialidad
import com.jve.proyecto.repository.ParticipanteRepository;
import com.jve.proyecto.repository.EspecialidadRepository; // Repositorio para obtener la especialidad
import com.jve.proyecto.converter.ParticipanteConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final EspecialidadRepository especialidadRepository;  // Repositorio para obtener la especialidad
    private final ParticipanteConverter participanteConverter;

    public ParticipanteService(ParticipanteRepository participanteRepository, EspecialidadRepository especialidadRepository, ParticipanteConverter participanteConverter) {
        this.participanteRepository = participanteRepository;
        this.especialidadRepository = especialidadRepository;
        this.participanteConverter = participanteConverter;
    }

    // Método para obtener todos los participantes por especialidad
    public List<ParticipanteDTO> obtenerParticipantesPorEspecialidad(int especialidadId) {
        // Obtenemos los participantes filtrados por especialidad
        List<Participante> participantes = participanteRepository.findByEspecialidad_idEspecialidad(especialidadId);
        return participantes.stream()
                            .map(participanteConverter::entityToDto)
                            .collect(Collectors.toList());
    }

    // Método para editar un participante
    public ParticipanteDTO editarParticipante(Integer id, ParticipanteDTO participanteDTO) {
        // Verificar si el participante existe
        Participante participanteExistente = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        // Actualizar los campos del participante con el DTO
        participanteExistente.setNombre(participanteDTO.getNombre());
        participanteExistente.setApellidos(participanteDTO.getApellidos());
        participanteExistente.setCentro(participanteDTO.getCentro());

        // Buscar la especialidad usando el ID
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        // Asignar la especialidad al participante
        participanteExistente.setEspecialidad(especialidad);

        // Guardar el participante actualizado
        Participante participanteGuardado = participanteRepository.save(participanteExistente);

        // Convertir y devolver el participante actualizado como DTO
        return participanteConverter.entityToDto(participanteGuardado);
    }

    // Método para guardar un participante
    public ParticipanteDTO guardarParticipante(ParticipanteDTO participanteDTO) {
        Participante participante = participanteConverter.dtoToEntity(participanteDTO);

        // Buscar la especialidad usando el ID
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        // Asignar la especialidad al participante
        participante.setEspecialidad(especialidad);

        // Guardar el participante
        Participante savedParticipante = participanteRepository.save(participante);
        return participanteConverter.entityToDto(savedParticipante);
    }
}
