package com.jve.proyecto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jve.proyecto.dto.PruebaDTO;
import com.jve.proyecto.service.PruebaService;

import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
@CrossOrigin(origins = "http://localhost:4200")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @PostMapping("/crear-con-pdf")
    @Operation(summary = "Crear una prueba con archivo PDF", description = "Crea una nueva prueba asociada a una especialidad, con un archivo PDF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prueba creada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PruebaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PruebaDTO> crearPruebaConPDF(
            @Parameter(description = "Archivo PDF asociado a la prueba", required = true)
            @RequestPart("file") MultipartFile file,
            @Parameter(description = "Puntuación máxima de la prueba", required = true)
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima,
            @Parameter(description = "ID de la especialidad asociada a la prueba", required = true)
            @RequestParam("especialidadId") Long especialidadId) {

        PruebaDTO nuevaPrueba = pruebaService.guardarPruebaConPDF(file, puntuacionMaxima, especialidadId);
        return new ResponseEntity<>(nuevaPrueba, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer prueba por ID", description = "Trae los detalles de una prueba mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba encontrada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PruebaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PruebaDTO> getPruebaById(
            @Parameter(description = "ID de la prueba a buscar", required = true)
            @PathVariable Long id) {
        PruebaDTO pruebaDTO = pruebaService.getPruebaById(id);
        return ResponseEntity.ok(pruebaDTO);
    }

    @GetMapping("/todas")
    @Operation(summary = "Traer todas las pruebas", description = "Trae una lista de todas las pruebas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pruebas obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PruebaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PruebaDTO>> traerTodaslaspruebas() {
        List<PruebaDTO> pruebas = pruebaService.traerTodasLasPruebas();
        return ResponseEntity.ok(pruebas);
    }

    @GetMapping("/especialidad/{especialidadId}")
    @Operation(summary = "Traer pruebas por especialidad", description = "Trae las pruebas asociadas a una especialidad específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pruebas de la especialidad obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PruebaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PruebaDTO>> getPruebasByEspecialidad(
            @Parameter(description = "ID de la especialidad", required = true)
            @PathVariable Long especialidadId) {
        List<PruebaDTO> pruebas = pruebaService.getPruebasByEspecialidad(especialidadId);
        return ResponseEntity.ok(pruebas);
    }

    @GetMapping("/ultimo-id")
    @Operation(summary = "Traer el último ID de prueba", description = "Trae el último ID de prueba creado en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Último ID de prueba obtenido con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Long> TraerUltimoIdPrueba() {
        Long ultimoId = pruebaService.TraerUltimoIdPrueba();
        return ResponseEntity.ok(ultimoId);
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar una prueba existente", description = "Permite editar los detalles de una prueba existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba editada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PruebaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PruebaDTO> editarPrueba(
            @Parameter(description = "ID de la prueba a editar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Archivo PDF opcional para la prueba", required = false)
            @RequestParam(value = "file", required = false) MultipartFile file,
            @Parameter(description = "Puntuación máxima de la prueba", required = true)
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima,
            @Parameter(description = "ID de la especialidad asociada a la prueba", required = true)
            @RequestParam("especialidadId") Long especialidadId) {

        PruebaDTO pruebaDTO = new PruebaDTO(null);
        pruebaDTO.setPuntuacionMaxima(puntuacionMaxima);
        pruebaDTO.setEspecialidadId(especialidadId);

        PruebaDTO updatedPrueba = pruebaService.editarPrueba(id, file, pruebaDTO);
        return ResponseEntity.ok(updatedPrueba);
    }
}
