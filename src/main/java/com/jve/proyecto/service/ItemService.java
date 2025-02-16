package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.ItemConverter;
import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public ItemService(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    public ItemDTO guardarItem(ItemDTO itemDTO) {
        var item = itemConverter.dtoToEntity(itemDTO);
        var savedItem = itemRepository.save(item);
        return itemConverter.entityToDto(savedItem);
    }

    public List<ItemDTO> obtenerTodos() {
        return itemRepository.findAll().stream()
                .map(itemConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
