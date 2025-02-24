package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/crear")
    public ResponseEntity<ItemDTO> crearItem(@RequestBody ItemDTO itemDTO) {
        ItemDTO nuevoItem = itemService.guardarItem(itemDTO);
        return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ItemDTO>> traerTodos() {
        List<ItemDTO> items = itemService.traerTodos();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/crear-multiples")
    public ResponseEntity<?> crearMultiplesItems(@RequestBody List<Item> items) {
    try {
        List<Item> itemsGuardados = itemService.guardarTodos(items);
        return ResponseEntity.ok(itemsGuardados);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar los ítems.");
    }
}
    @PutMapping("/editar/{id}")
    public ResponseEntity<ItemDTO> editarItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        ItemDTO itemActualizado = itemService.editarItem(id, itemDTO);
        return ResponseEntity.ok(itemActualizado);
    }
}
