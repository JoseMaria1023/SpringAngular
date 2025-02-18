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
    private final EspecialidadRepository especialidadRepository;  
    private final ParticipanteConverter participanteConverter;

    public ParticipanteService(ParticipanteRepository participanteRepository, EspecialidadRepository especialidadRepository, ParticipanteConverter participanteConverter) {
        this.participanteRepository = participanteRepository;
        this.especialidadRepository = especialidadRepository;
        this.participanteConverter = participanteConverter;
    }

    public List<ParticipanteDTO> TraerCompetidoresParticipantesPorEspecialidad(long especialidadId) {
        List<Participante> participantes = participanteRepository.findByEspecialidad_idEspecialidad(especialidadId);
        return participantes.stream()
                            .map(participanteConverter::entityToDto)
                            .collect(Collectors.toList());
    }

    public ParticipanteDTO editarParticipante(Long id, ParticipanteDTO participanteDTO) {
        Participante participanteExistente = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        participanteExistente.setNombre(participanteDTO.getNombre());
        participanteExistente.setApellidos(participanteDTO.getApellidos());
        participanteExistente.setCentro(participanteDTO.getCentro());

        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        participanteExistente.setEspecialidad(especialidad);

        Participante participanteGuardado = participanteRepository.save(participanteExistente);

        return participanteConverter.entityToDto(participanteGuardado);
    }

    public ParticipanteDTO guardarParticipante(ParticipanteDTO participanteDTO) {
        Participante participante = participanteConverter.dtoToEntity(participanteDTO);

        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        participante.setEspecialidad(especialidad);

        Participante savedParticipante = participanteRepository.save(participante);
        return participanteConverter.entityToDto(savedParticipante);
    }
}
