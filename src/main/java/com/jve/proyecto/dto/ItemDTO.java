package com.jve.proyecto.dto;

import com.jve.proyecto.entity.Item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class ItemDTO {

    private Long idItem;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 2, max = 200, message = "La descripción debe tener entre 2 y 200 caracteres")
    private String descripcion;

    @NotNull(message = "El peso es obligatorio")
    @Min(value = 1, message = "El peso debe ser mayor que 0")
    private Integer peso;

    @NotNull(message = "Los grados de consecución son obligatorios")
    @Min(value = 1, message = "Los grados de consecución deben ser mayores que 0")
    private Integer gradosConsecucion;

    @NotNull(message = "La prueba es obligatoria")
    private Long pruebaId;

   
    public ItemDTO(Long idItem, String descripcion, Integer peso, Integer gradosConsecucion) {
        this.idItem = idItem;
        this.descripcion = descripcion;
        this.peso = peso;
        this.gradosConsecucion = gradosConsecucion;
    }

    public ItemDTO(Item item) {
    }

}
