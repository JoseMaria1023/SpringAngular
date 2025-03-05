package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear un ítem", description = "Crea un nuevo ítem en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ítem creado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ItemDTO> crearItem(
            @Parameter(description = "Datos del ítem a crear", required = true)
            @RequestBody @Valid ItemDTO itemDTO) {
        ItemDTO nuevoItem = itemService.guardarItem(itemDTO);
        return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    @Operation(summary = "Traer todos los ítems", description = "Devuelve una lista de todos los ítems disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ítems obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ItemDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ItemDTO>> traerTodos() {
        List<ItemDTO> items = itemService.traerTodos();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/crear-multiples")
    @Operation(summary = "Crear múltiples ítems", description = "Crea varios ítems en el sistema de una sola vez")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Múltiples ítems creados con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> crearMultiplesItems(
            @Parameter(description = "Lista de ítems a crear", required = true)
            @RequestBody List<ItemDTO> itemDTOs) {
        List<Item> itemsGuardados = itemService.guardarTodos(itemDTOs);
        return ResponseEntity.ok(itemsGuardados);
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar un ítem", description = "Actualiza los datos de un ítem existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem actualizado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ItemDTO> editarItem(
            @Parameter(description = "ID del ítem a editar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos del ítem a actualizar", required = true)
            @RequestBody ItemDTO itemDTO) {
        ItemDTO itemActualizado = itemService.editarItem(id, itemDTO);
        return ResponseEntity.ok(itemActualizado);
    }
}
