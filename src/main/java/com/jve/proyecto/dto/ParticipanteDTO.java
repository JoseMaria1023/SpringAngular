package com.jve.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private Long especialidadId; // Debería ser Integer ya que es el ID de la especialidad

}
