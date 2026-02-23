package com.example.dungeonserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor // Required for Hibernate to create empty objects
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private int power;

    @Column(name = "item_value")
    private int value;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;

    // --- THE FIX: THE CONSTRUCTOR ---
    // This allows you to use: new Item(name, type, power, value, player)
    public Item(String name, String type, int power, int value, Player player) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.value = value;
        this.player = player;
    }
}