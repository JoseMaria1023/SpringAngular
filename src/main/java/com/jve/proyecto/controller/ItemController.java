package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.service.ItemService;

import jakarta.validation.Valid;

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

    @PostMapping
    public ResponseEntity<ItemDTO> crearItem(@Valid @RequestBody ItemDTO itemDTO) {
        ItemDTO savedItem = itemService.guardarItem(itemDTO);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> TraerTodosLosItems() {
        List<ItemDTO> items = itemService.TraerTodos();
        return ResponseEntity.ok(items);
    }
}
