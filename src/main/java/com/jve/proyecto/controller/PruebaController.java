package com.jve.proyecto.controller;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.service.PruebaService;
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

    @GetMapping("/todas")
    public ResponseEntity<List<PruebaDTO>> getAllPruebas() {
        List<PruebaDTO> pruebas = pruebaService.getAllPruebas();
        return ResponseEntity.ok(pruebas);
    }
}
