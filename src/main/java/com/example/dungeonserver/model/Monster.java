package com.example.dungeonserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // e.g., "Goblin", "Cave Troll"
    private int health;       // e.g., 20
    private int strength;     // e.g., 5
    private int goldDrop;     // How much gold the player gets for winning
}