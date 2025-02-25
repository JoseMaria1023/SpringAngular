package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.EvaluacionConverter;
import com.jve.proyecto.dto.EvaluacionDTO;
import com.jve.proyecto.entity.Evaluacion;
import com.jve.proyecto.entity.EvaluacionItem;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.entity.Participante;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.entity.User;
import com.jve.proyecto.repository.EvaluacionItemRepository;
import com.jve.proyecto.repository.EvaluacionRepository;
import com.jve.proyecto.repository.ParticipanteRepository;
import com.jve.proyecto.repository.PruebaRepository;
import com.jve.proyecto.repository.UserRepository;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final EvaluacionConverter evaluacionConverter;
    private final UserRepository userRepository;
    private final PruebaRepository pruebaRepository;
    private final ParticipanteRepository participanteRepository;
    private final EvaluacionItemRepository evaluacionItemRepository;


    public EvaluacionService(EvaluacionRepository evaluacionRepository, EvaluacionConverter evaluacionConverter,UserRepository userRepository, PruebaRepository pruebaRepository,ParticipanteRepository participanteRepository,EvaluacionItemRepository evaluacionItemRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.evaluacionConverter = evaluacionConverter;
        this.userRepository = userRepository;
        this.pruebaRepository = pruebaRepository;
        this.participanteRepository= participanteRepository;
        this.evaluacionItemRepository=evaluacionItemRepository;
    }

    public List<EvaluacionDTO> obtenerTodas() {
        return evaluacionRepository.findAll().stream()
            .map(evaluacionConverter::entityToDto)
            .toList();
    }

    public List<EvaluacionDTO> obtenerEvaluacionesPorPrueba(Long pruebaId) {
        return evaluacionRepository.findByPrueba_IdPrueba(pruebaId).stream()
            .map(evaluacionConverter::entityToDto)
            .toList();
    }
    
    public EvaluacionDTO evaluarParticipante(EvaluacionDTO evaluacionDTO) {
        Participante participante = participanteRepository.findById(evaluacionDTO.getParticipanteId())
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));
    
        Prueba prueba = pruebaRepository.findById(evaluacionDTO.getPruebaId())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
    
        User evaluador = userRepository.findById(evaluacionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Evaluador no encontrado"));
    
        Evaluacion evaluacion = evaluacionConverter.dtoToEntity(evaluacionDTO);
        evaluacion.setParticipante(participante);
        evaluacion.setPrueba(prueba);
        evaluacion.setUser(evaluador);
    
        return evaluacionConverter.entityToDto(evaluacionRepository.save(evaluacion));
    }
    public List<EvaluacionDTO> listarEvaluaciones() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public EvaluacionDTO actualizarEvaluacion(Long id, EvaluacionDTO dto) {
        return evaluacionRepository.findById(id)
            .map(evaluacion -> {
                evaluacion.setNotaFinal(dto.getNotaFinal());
                evaluacionRepository.save(evaluacion);
                return evaluacionConverter.entityToDto(evaluacion);
            })
            .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));
    }
}
