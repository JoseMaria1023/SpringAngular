package com.jve.proyecto.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.repository.PruebaRepository;


@Service
public class PruebaService {

    private final PruebaRepository pruebaRepository;

    public PruebaService(PruebaRepository pruebaRepository) {
        this.pruebaRepository = pruebaRepository;
    }

    public List<PruebaDTO> getAllPruebas() {
        List<Prueba> pruebas = pruebaRepository.findAll();
        return pruebas.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    private PruebaDTO entityToDto(Prueba prueba) {
        PruebaDTO dto = new PruebaDTO();
        dto.setIdPrueba(prueba.getIdPrueba());
        dto.setEnunciado(prueba.getEnunciado());
        dto.setEspecialidadId(prueba.getEspecialidad().getIdEspecialidad());
        dto.setPuntuacionMaxima(prueba.getPuntuacionMaxima());
        return dto;
    }
}