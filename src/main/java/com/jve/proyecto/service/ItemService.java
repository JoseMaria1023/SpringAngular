package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.ItemConverter;
import com.jve.proyecto.dto.ItemDTO;
import com.jve.proyecto.entity.Item;
import com.jve.proyecto.entity.Prueba;
import com.jve.proyecto.repository.ItemRepository;
import com.jve.proyecto.repository.PruebaRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final PruebaRepository pruebaRepository;
    private final ItemConverter itemConverter;

    public ItemService(ItemRepository itemRepository, PruebaRepository pruebaRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.pruebaRepository = pruebaRepository;
        this.itemConverter = itemConverter;
    }

    public ItemDTO guardarItem(ItemDTO itemDTO) {
        Prueba prueba = pruebaRepository.findById(itemDTO.getPruebaId())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));

        Item item = itemConverter.dtoToEntity(itemDTO);
        item.setPrueba(prueba);

        Item savedItem = itemRepository.save(item);
        return itemConverter.entityToDto(savedItem);
    }

    public List<ItemDTO> traerTodos() {
        return itemRepository.findAll().stream()
                .map(itemConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public ItemDTO editarItem(Long id, ItemDTO itemDTO) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setDescripcion(itemDTO.getDescripcion());
            item.setPeso(itemDTO.getPeso());
            item.setGradosConsecucion(itemDTO.getGradosConsecucion());
            if (itemDTO.getPruebaId() != null) {
                Prueba prueba = pruebaRepository.findById(itemDTO.getPruebaId())
                        .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
                item.setPrueba(prueba);
            }
            Item updatedItem = itemRepository.save(item);
            return itemConverter.entityToDto(updatedItem);
        } else {
            throw new RuntimeException("Item no encontrado");
        }
    }
}