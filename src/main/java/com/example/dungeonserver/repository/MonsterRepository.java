package com.example.dungeonserver.repository;

import com.example.dungeonserver.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonsterRepository extends JpaRepository<Monster, Long> {
}