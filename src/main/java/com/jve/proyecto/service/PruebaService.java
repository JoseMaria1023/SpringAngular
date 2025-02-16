package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.repository.PruebaRepository;

@Service
public class PruebaService {
    private final PruebaRepository pruebaRepository;
    private final ModelMapper modelMapper;

    public PruebaService(PruebaRepository pruebaRepository) {
        this.pruebaRepository = pruebaRepository;
        this.modelMapper = new ModelMapper();
    }

    public PruebaDTO guardarPrueba(PruebaDTO pruebaDTO) {
        Prueba prueba = modelMapper.map(pruebaDTO, Prueba.class);
        Prueba savedPrueba = pruebaRepository.save(prueba);
        return modelMapper.map(savedPrueba, PruebaDTO.class);
    }

    public List<PruebaDTO> obtenerTodos() {
        return pruebaRepository.findAll().stream()
                .map(prueba -> modelMapper.map(prueba, PruebaDTO.class))
                .collect(Collectors.toList());
    }
}