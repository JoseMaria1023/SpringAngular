package com.jve.proyecto.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jve.proyecto.converter.PruebaConverter;
import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.entity.Especialidad;
import com.jve.proyecto.repository.PruebaRepository;
import com.jve.proyecto.repository.EspecialidadRepository;

@Service
public class PruebaService {

    private final PruebaRepository pruebaRepository;
    private final EspecialidadRepository especialidadRepository;
    private final PruebaConverter pruebaConverter;
    private final String UPLOAD_DIR = "uploads/pruebas/";

    public PruebaService(PruebaRepository pruebaRepository, EspecialidadRepository especialidadRepository, PruebaConverter pruebaConverter) {
        this.pruebaRepository = pruebaRepository;
        this.especialidadRepository = especialidadRepository;
        this.pruebaConverter = pruebaConverter;

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    public PruebaDTO guardarPruebaConPDF(MultipartFile file, Integer puntuacionMaxima, Long especialidadId) {
        Especialidad especialidad = especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        String fileName = guardarArchivo(file);
        String fileUrl = "/uploads/pruebas/" + fileName;

        PruebaDTO pruebaDTO = new PruebaDTO();
        pruebaDTO.setEnunciado(fileUrl);
        pruebaDTO.setPuntuacionMaxima(puntuacionMaxima);
        pruebaDTO.setEspecialidadId(especialidadId);

        Prueba prueba = pruebaConverter.dtoToEntity(pruebaDTO);
        prueba.setEspecialidad(especialidad);

        Prueba savedPrueba = pruebaRepository.save(prueba);
        return pruebaConverter.entityToDto(savedPrueba);
    }

    private String guardarArchivo(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Path.of(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo PDF", e);
        }
    }
    public Long obtenerUltimoIdPrueba() {
        Prueba prueba = pruebaRepository.findTopByOrderByIdPruebaDesc();
        return prueba != null ? prueba.getIdPrueba() : null; 
    }

    public List<PruebaDTO> traerTodasLasPruebas() {
        return pruebaRepository.findAll().stream()
                .map(pruebaConverter::entityToDto)  
                .collect(Collectors.toList());
    }

    public List<PruebaDTO> getPruebasByEspecialidad(Long especialidadId) {
        return pruebaRepository.findByEspecialidad_IdEspecialidad(especialidadId).stream()
                .map(pruebaConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public PruebaDTO editarPrueba(Long id, MultipartFile file, PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));

        if (file != null && !file.isEmpty()) {
            String fileName = guardarArchivo(file);
            prueba.setEnunciado("/uploads/pruebas/" + fileName);
        }
        prueba.setPuntuacionMaxima(pruebaDTO.getPuntuacionMaxima());
        
        Especialidad especialidad = especialidadRepository.findById(pruebaDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        prueba.setEspecialidad(especialidad);

        Prueba updatedPrueba = pruebaRepository.save(prueba);
        return pruebaConverter.entityToDto(updatedPrueba);
    }

    public PruebaDTO getPruebaById(Long id) {
        Prueba prueba = pruebaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
        return pruebaConverter.entityToDto(prueba);
    }
}
