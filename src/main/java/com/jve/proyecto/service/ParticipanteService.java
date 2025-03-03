package com.jve.proyecto.service;

import com.jve.proyecto.dto.ParticipanteDTO;
import com.jve.proyecto.entity.Participante;
import com.jve.proyecto.exception.ErrorEspecialidadNotFoundException;
import com.jve.proyecto.exception.ErrorParticipanteNotFoundException;
import com.jve.proyecto.entity.Especialidad;
import com.jve.proyecto.repository.ParticipanteRepository;
import com.jve.proyecto.repository.EspecialidadRepository;
import com.jve.proyecto.converter.ParticipanteConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final EspecialidadRepository especialidadRepository;
    private final ParticipanteConverter participanteConverter;

    public ParticipanteService(ParticipanteRepository participanteRepository, 
                               EspecialidadRepository especialidadRepository, 
                               ParticipanteConverter participanteConverter) {
        this.participanteRepository = participanteRepository;
        this.especialidadRepository = especialidadRepository;
        this.participanteConverter = participanteConverter;
    }

    public List<ParticipanteDTO> traerParticipantesPorEspecialidad(long especialidadId) {
        return participanteRepository.findByEspecialidad_idEspecialidad(especialidadId).stream()
                .map(participanteConverter::entityToDto)
                .toList();
    }

    public List<ParticipanteDTO> traerParticipantes() {
        return participanteRepository.findAll().stream()
                .map(participanteConverter::entityToDto)
                .toList();
    }

    public ParticipanteDTO editarParticipante(Long id, ParticipanteDTO participanteDTO) {
        Participante participanteExistente = participanteRepository.findById(id).orElseThrow(() -> new ErrorParticipanteNotFoundException("Participante no encontrado"));

        participanteExistente.setNombre(participanteDTO.getNombre());
        participanteExistente.setApellidos(participanteDTO.getApellidos());
        participanteExistente.setCentro(participanteDTO.getCentro());

        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId()).orElseThrow(() -> new ErrorEspecialidadNotFoundException("Especialidad no encontrada"));
        participanteExistente.setEspecialidad(especialidad);

        return participanteConverter.entityToDto(participanteRepository.save(participanteExistente));
    }

    public ParticipanteDTO guardarParticipante(ParticipanteDTO participanteDTO) {
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId()).orElseThrow(() -> new ErrorEspecialidadNotFoundException("Especialidad no encontrada"));

        Participante participante = participanteConverter.dtoToEntity(participanteDTO);
        participante.setEspecialidad(especialidad);

        return participanteConverter.entityToDto(participanteRepository.save(participante));
    }

    public ParticipanteDTO buscarParticipantePorId(Long id) {
        return participanteRepository.findById(id)
                .map(participanteConverter::entityToDto)
                .orElseThrow(() -> new ErrorParticipanteNotFoundException("Participante no encontrado"));
    }
}
