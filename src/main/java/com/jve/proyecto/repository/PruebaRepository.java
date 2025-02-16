package com.jve.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Prueba;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
}