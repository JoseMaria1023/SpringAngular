package com.jve.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    // Getters y Setters
    public Long getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(Long idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public Long getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(Long evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }
}
