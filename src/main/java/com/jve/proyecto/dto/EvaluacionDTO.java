package com.jve.proyecto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
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


    // Getters y Setters
    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public Long getPruebaId() {
        return pruebaId;
    }

    public void setPruebaId(Long pruebaId) {
        this.pruebaId = pruebaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getParticipanteNombre() {
        return participanteNombre;
    }
    public void setParticipanteNombre(String participanteNombre) {
        this.participanteNombre = participanteNombre;
    }


    public EvaluacionDTO(Long idEvaluacion2, Double notaFinal2, Long idParticipante) {
        //TODO Auto-generated constructor stub
    }
}