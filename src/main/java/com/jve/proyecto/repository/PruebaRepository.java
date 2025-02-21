package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Prueba;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {
    List<Prueba> findByEspecialidad_IdEspecialidad(Long especialidadId);

}