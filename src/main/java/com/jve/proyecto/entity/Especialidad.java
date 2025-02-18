package com.jve.proyecto.entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Especialidad")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecialidad")
    private Long idEspecialidad;

    @Column(name = "Nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "Codigo", nullable = false, length = 45)
    private String codigo;

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<Participante> participantes = new ArrayList<>();

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<Prueba> pruebas = new ArrayList<>();

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<User> users = new ArrayList<>();

}
