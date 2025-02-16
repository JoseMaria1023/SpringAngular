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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Evaluacion")
@Data
@NoArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluacion")
    private Integer idEvaluacion;

    @ManyToOne
    @JoinColumn(name = "Participante_idParticipante")
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "Prueba_idPrueba")
    private Prueba prueba;

    @ManyToOne
    @JoinColumn(name = "User_idUser")
    private User user;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionItem> evaluacionItems = new ArrayList<>();

    @Column(name = "Nota_Final", nullable = false)
    private Double notaFinal;
}
