package com.jve.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EvaluacionitemDTO {

    private Long idEvaluacionItem;

    @NotNull(message = "La evaluación es obligatoria")
    private Long evaluacionId;

    @NotNull(message = "El ítem es obligatorio")
    private Long itemId;

    @NotNull(message = "La valoración es obligatoria")
    private Integer valoracion;

    @Size(max = 200, message = "La explicación debe tener máximo 200 caracteres")
    private String explicacion;

    
}
