package com.jve.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ParticipanteDTO {

    private Long idParticipante;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 45, message = "El nombre debe tener entre 2 y 45 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 45, message = "Los apellidos deben tener entre 2 y 45 caracteres")
    private String apellidos;

    @NotBlank(message = "El centro es obligatorio")
    @Size(min = 2, max = 45, message = "El centro debe tener entre 2 y 45 caracteres")
    private String centro;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId; 

}
