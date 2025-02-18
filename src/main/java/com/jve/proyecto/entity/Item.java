package com.jve.proyecto.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idItem")
    private Long idItem;

    @Column(name = "Descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "Peso", nullable = false)
    private Integer peso;

    @Column(name = "Grados_Consecucion", nullable = false)
    private Integer gradosConsecucion;

    @ManyToOne
    @JoinColumn(name = "Prueba_idPrueba")
    private Prueba prueba;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionItem> evaluacionItems = new ArrayList<>();
}