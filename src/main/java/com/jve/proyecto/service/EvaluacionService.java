package com.jve.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.EvaluacionConverter;
import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.entity.Evaluacion;
import com.jve.proyecto.entity.Participante;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.entity.User;
import com.jve.proyecto.repository.EvaluacionRepository;
import com.jve.proyecto.repository.ParticipanteRepository;
import com.jve.proyecto.repository.PruebaRepository;
import com.jve.proyecto.repository.UserRepository;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final ParticipanteRepository participanteRepository;
    private final PruebaRepository pruebaRepository;
    private final UserRepository userRepository;

    public EvaluacionService(
        EvaluacionRepository evaluacionRepository,
        ParticipanteRepository participanteRepository,
        PruebaRepository pruebaRepository,
        UserRepository userRepository
    ) {
        this.evaluacionRepository = evaluacionRepository;
        this.participanteRepository = participanteRepository;
        this.pruebaRepository = pruebaRepository;
        this.userRepository = userRepository;
    }

    public List<EvaluacionDTO> obtenerTodas() {
        List<Evaluacion> evaluaciones = evaluacionRepository.findAll();
        return evaluaciones.stream().map(this::entityToDto).toList();
    }
    public List<EvaluacionDTO> obtenerEvaluacionesPorPrueba(Long pruebaId) {
        List<EvaluacionDTO> evaluaciones = evaluacionRepository.findByPrueba_IdPrueba(pruebaId).stream()
            .map(evaluacion -> new EvaluacionDTO(
                evaluacion.getIdEvaluacion(),
                evaluacion.getNotaFinal(),
                evaluacion.getParticipante().getIdParticipante()  
            ))
            .collect(Collectors.toList());
        
        return evaluaciones;
    }

    
    public EvaluacionDTO guardarEvaluacion(EvaluacionDTO dto) {
        Evaluacion evaluacion = dtoToEntity(dto);
        evaluacion = evaluacionRepository.save(evaluacion);
        return entityToDto(evaluacion);
    }

    public EvaluacionDTO actualizarEvaluacion(Long id, EvaluacionDTO dto) {
        return evaluacionRepository.findById(id)
            .map(evaluacion -> {
                evaluacion.setNotaFinal(dto.getNotaFinal());
                evaluacionRepository.save(evaluacion);
                return entityToDto(evaluacion);
            })
            .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));
    }

    private EvaluacionDTO entityToDto(Evaluacion evaluacion) {
        return new EvaluacionDTO(
            evaluacion.getIdEvaluacion(),
            evaluacion.getParticipante().getIdParticipante(),
            evaluacion.getPrueba().getIdPrueba(),
            evaluacion.getUser().getIdUser(),
            evaluacion.getNotaFinal()
        );
    }

    private Evaluacion dtoToEntity(EvaluacionDTO dto) {
        Participante participante = participanteRepository.findById(dto.getParticipanteId())
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));
        Prueba prueba = pruebaRepository.findById(dto.getPruebaId())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new Evaluacion(
            dto.getIdEvaluacion(),
            participante,
            prueba,
            user,
            new ArrayList<>(),
            dto.getNotaFinal()
        );
    }
}