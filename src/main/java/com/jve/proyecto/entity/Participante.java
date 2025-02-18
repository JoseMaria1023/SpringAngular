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
@Table(name = "Participante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idParticipante")
    private Long idParticipante;

    @Column(name = "Nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "Apellidos", nullable = false, length = 45)
    private String apellidos;

    @Column(name = "Centro", nullable = false, length = 45)
    private String centro;

    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad")
    private Especialidad especialidad;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

}
