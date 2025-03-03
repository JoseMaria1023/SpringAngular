package com.jve.proyecto.dto;

import com.jve.proyecto.entity.Prueba;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDTO {

    private Long idPrueba;

    @NotBlank(message = "El enunciado es obligatorio")
    private String enunciado;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId;

    @NotNull(message = "La puntuación máxima es obligatoria")
    @Min(value = 1, message = "La puntuación máxima debe ser al menos 1")
    private Integer puntuacionMaxima;

    private EspecialidadDTO especialidad;

    public PruebaDTO(Prueba prueba) {
        //TODO Auto-generated constructor stub
    }

}