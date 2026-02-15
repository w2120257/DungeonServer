package com.example.dungeonserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // e.g., "Rusty Sword"
    private String type;        // e.g., "WEAPON", "ARMOR", "POTION"
    private int power;          // e.g., 5 (Damage or Defense)
    private int value;          // e.g., 10 Gold

    // I will link this to a Player later!
}