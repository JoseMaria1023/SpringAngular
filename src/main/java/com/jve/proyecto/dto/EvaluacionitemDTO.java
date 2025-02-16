package com.jve.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EvaluacionitemDTO {

    private Integer idEvaluacionItem;

    @NotNull(message = "La evaluación es obligatoria")
    private Integer evaluacionId;

    @NotNull(message = "El ítem es obligatorio")
    private Integer itemId;

    @NotNull(message = "La valoración es obligatoria")
    private Integer valoracion;

    @Size(max = 200, message = "La explicación debe tener máximo 200 caracteres")
    private String explicacion;

    // Getters y Setters
    public Integer getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(Integer idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public Integer getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(Integer evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
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
