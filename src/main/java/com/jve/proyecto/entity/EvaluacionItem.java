package com.jve.proyecto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Evaluacion_Item")
@Data
@NoArgsConstructor
public class EvaluacionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluacionItem")
    private Integer idEvaluacionItem;

    @ManyToOne
    @JoinColumn(name = "Evaluacion_idEvaluacion")
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "Item_idItem")
    private Item item;

    @Column(name = "Valoracion", nullable = false)
    private Integer valoracion;

    @Column(name = "Explicacion", length = 200)
    private String explicacion;
}
