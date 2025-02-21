package com.jve.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.service.PruebaService;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @PostMapping("/crear-con-pdf")
    public ResponseEntity<PruebaDTO> crearPruebaConPDF(
            @RequestPart("file") MultipartFile file,
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima,
            @RequestParam("especialidadId") Long especialidadId) {

        PruebaDTO nuevaPrueba = pruebaService.guardarPruebaConPDF(file, puntuacionMaxima, especialidadId);
        return new ResponseEntity<>(nuevaPrueba, HttpStatus.CREATED);
    }

    @GetMapping("/todas")
    public ResponseEntity<?> obtenerTodas() {
        return ResponseEntity.ok(pruebaService.traerTodas());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<PruebaDTO> editarPrueba(
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("prueba") PruebaDTO pruebaDTO) {
        PruebaDTO pruebaActualizada = pruebaService.editarPrueba(id, file, pruebaDTO);
        return ResponseEntity.ok(pruebaActualizada);
    }
    @GetMapping("/especialidad/{especialidadId}")
    public ResponseEntity<List<PruebaDTO>> getPruebasByEspecialidad(@PathVariable Long especialidadId) {
    List<PruebaDTO> pruebas = pruebaService.getPruebasByEspecialidad(especialidadId);
    return ResponseEntity.ok(pruebas);
}
}