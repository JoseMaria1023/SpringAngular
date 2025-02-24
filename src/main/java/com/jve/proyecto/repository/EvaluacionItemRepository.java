package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.EvaluacionItem;

@Repository
public interface EvaluacionItemRepository extends JpaRepository<EvaluacionItem, Integer> {
    List<EvaluacionItem> findByEvaluacion_IdEvaluacion(Long idEvaluacion);

}