package com.example.dungeonserver.service;

import com.example.dungeonserver.model.Item;
import com.example.dungeonserver.model.Player;
import com.example.dungeonserver.model.Monster;
import com.example.dungeonserver.repository.PlayerRepository;
import com.example.dungeonserver.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    // --- NEW: Added the Monster Database connection! ---
    @Autowired
    private MonsterRepository monsterRepository;

    public Player spawnPlayer(String name) {
        Player player = new Player();
        player.setName(name);

        int bonusStr = (int)(Math.random() * 5);
        player.setStrength(10 + bonusStr);

        int bonusDef = (int)(Math.random() * 3);
        player.setDefense(5 + bonusDef);

        player.setGold(50);

        Item starterWeapon = new Item();
        starterWeapon.setName("Rusty Sword");
        starterWeapon.setType("WEAPON");
        starterWeapon.setPower(3);
        starterWeapon.setValue(5);

        starterWeapon.setPlayer(player);
        player.getInventory().add(starterWeapon);

        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found!"));
    }

    public Player healPlayer(Long playerId) {
        Player player = getPlayer(playerId);

        if (player.getHealth() <= 0) {
            throw new RuntimeException("You are dead! Healing won't help you now.");
        }
        if (player.getHealth() >= player.getMaxHealth()) {
            throw new RuntimeException("You are already at full health!");
        }
        if (player.getGold() < 15) {
            throw new RuntimeException("Not enough gold! The healer demands 15 gold.");
        }

        player.setGold(player.getGold() - 15);
        player.setHealth(player.getMaxHealth());

        return playerRepository.save(player);
    }

    // --- NEW: GAMEPLAY ACTION (Now with Real Monsters!) ---
    public Player exploreDungeon(Long playerId) {
        Player player = getPlayer(playerId);

        if (player.getHealth() <= 0) {
            throw new RuntimeException("You are dead! Visit the healer.");
        }

        // 1. Get all monsters from the database
        List<Monster> monsters = monsterRepository.findAll();

        // 2. Pick a random monster
        Random random = new Random();
        Monster enemy = monsters.get(random.nextInt(monsters.size()));

        // 3. Combat Math: Enemy Strength minus Player Defense
        int damageTaken = enemy.getStrength() - player.getDefense();
        if (damageTaken < 0) damageTaken = 0; // Defense completely blocked it!

        player.setHealth(player.getHealth() - damageTaken);

        // 4. Did you survive?
        if (player.getHealth() > 0) {
            player.setGold(player.getGold() + enemy.getGoldDrop());
            player.setExperience(player.getExperience() + 25);

            // Level Up Logic!
            if (player.getExperience() >= 100) {
                player.setLevel(player.getLevel() + 1);
                player.setExperience(0);
                player.setMaxHealth(player.getMaxHealth() + 20);
                player.setHealth(player.getMaxHealth()); // Fully heal on level up
                player.setStrength(player.getStrength() + 5);
                player.setDefense(player.getDefense() + 3);
            }
        } else {
            player.setHealth(0); // Don't let health go below zero
        }

        return playerRepository.save(player);
    }
}