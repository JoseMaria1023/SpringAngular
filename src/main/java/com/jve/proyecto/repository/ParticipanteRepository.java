package com.jve.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    List<Participante> findByEspecialidad_idEspecialidad(long especialidadId);
    Optional<Participante> findById(Long id);  // Este método ya existe por defecto en JpaRepository


}