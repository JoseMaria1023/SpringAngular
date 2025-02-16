package com.jve.proyecto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EvaluacionDTO {

    private Integer idEvaluacion;

    @NotNull(message = "El participante es obligatorio")
    private Integer participanteId;

    @NotNull(message = "La prueba es obligatoria")
    private Integer pruebaId;

    @NotNull(message = "El usuario es obligatorio")
    private Integer userId;

    @NotNull(message = "La nota final es obligatoria")
    @Min(value = 0, message = "La nota final no puede ser negativa")
    private Double notaFinal;

    // Getters y Setters
    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Integer getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Integer participanteId) {
        this.participanteId = participanteId;
    }

    public Integer getPruebaId() {
        return pruebaId;
    }

    public void setPruebaId(Integer pruebaId) {
        this.pruebaId = pruebaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }
}