package com.jve.proyecto.controller;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.service.PruebaService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @PostMapping
    public ResponseEntity<PruebaDTO> crearPrueba(@Valid @RequestBody PruebaDTO pruebaDTO) {
        PruebaDTO savedPrueba = pruebaService.guardarPrueba(pruebaDTO);
        return ResponseEntity.ok(savedPrueba);
    }

    @GetMapping
    public ResponseEntity<List<PruebaDTO>> obtenerTodasLasPruebas() {
        List<PruebaDTO> pruebas = pruebaService.obtenerTodos();
        return ResponseEntity.ok(pruebas);
    }
}
