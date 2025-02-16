package com.jve.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}