package com.jve.proyecto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PruebaDTO {

    private Long idPrueba;

    @NotBlank(message = "El enunciado es obligatorio")
    private String enunciado;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId;

    @NotNull(message = "La puntuación máxima es obligatoria")
    @Min(value = 1, message = "La puntuación máxima debe ser al menos 1")
    private Integer puntuacionMaxima;

    // Getters y Setters
    public Long getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Long idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    public Integer getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(Integer puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }
}