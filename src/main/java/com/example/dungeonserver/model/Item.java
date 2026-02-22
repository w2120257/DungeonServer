package com.example.dungeonserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "item_value") // <-- IT GOES RIGHT HERE!
    private int value;          // e.g., 10 Gold

    // --- NEW: THE LINK TO THE PLAYER ---
    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore // This prevents an infinite loop when showing JSON!
    private Player player;
}