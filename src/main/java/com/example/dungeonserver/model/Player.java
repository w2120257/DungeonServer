package com.example.dungeonserver.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Stats
    private int health;
    private int maxHealth;
    private int strength;
    private int defense;
    private int gold;

    // Progression
    private int level;
    private int experience;

    // --- NEW: THE INVENTORY ---
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Item> inventory = new ArrayList<>();

    // Constructor
    public Player() {
        this.level = 1;
        this.experience = 0;
        this.gold = 0;
        this.maxHealth = 100;
        this.health = 100;
        this.strength = 10;
        this.defense = 5;
    }
}