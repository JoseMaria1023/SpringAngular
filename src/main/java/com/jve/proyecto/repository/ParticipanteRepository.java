package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    List<Participante> findByEspecialidad_idEspecialidad(int especialidadId);

}