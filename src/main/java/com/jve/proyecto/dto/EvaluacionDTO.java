package com.jve.proyecto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class EvaluacionDTO {

    private Long idEvaluacion;

    @NotNull(message = "El participante es obligatorio")
    private Long participanteId;

    @NotNull(message = "La prueba es obligatoria")
    private Long pruebaId;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    @NotNull(message = "La nota final es obligatoria")
    @Min(value = 0, message = "La nota final no puede ser negativa")
    private Double notaFinal;

    private String participanteNombre; 

    public EvaluacionDTO(Long idEvaluacion2, Double notaFinal2, Long idParticipante) {
        //TODO Auto-generated constructor stub
    }
}