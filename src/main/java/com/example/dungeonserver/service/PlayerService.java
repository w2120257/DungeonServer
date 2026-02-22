package com.example.dungeonserver.service;

import com.example.dungeonserver.model.Item;
import com.example.dungeonserver.model.Player;
import com.example.dungeonserver.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player spawnPlayer(String name) {
        Player player = new Player();
        player.setName(name);

        // Game Logic: Roll for stats
        int bonusStr = (int)(Math.random() * 5);
        player.setStrength(10 + bonusStr);

        int bonusDef = (int)(Math.random() * 3);
        player.setDefense(5 + bonusDef);

        player.setGold(50);

        // --- NEW: STARTER LOOT ---
        Item starterWeapon = new Item();
        starterWeapon.setName("Rusty Sword");
        starterWeapon.setType("WEAPON");
        starterWeapon.setPower(3);
        starterWeapon.setValue(5);

        // Link the item to the player
        starterWeapon.setPlayer(player);

        // Put the item in the player's inventory
        player.getInventory().add(starterWeapon);

        // Save the player (This will automatically save the item too because of CascadeType.ALL)
        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found!"));
    }

    // --- NEW: HEALING ACTION ---
    public Player healPlayer(Long playerId) {
        Player player = getPlayer(playerId);

        // Rule 1: Are they dead?
        if (player.getHealth() <= 0) {
            throw new RuntimeException("You are dead! Healing won't help you now.");
        }

        // Rule 2: Are they already full health?
        if (player.getHealth() >= player.getMaxHealth()) {
            throw new RuntimeException("You are already at full health!");
        }

        // Rule 3: Do they have enough gold? (Cost: 15 Gold)
        if (player.getGold() < 15) {
            throw new RuntimeException("Not enough gold! The healer demands 15 gold.");
        }

        // Action: Take gold, restore health
        player.setGold(player.getGold() - 15);
        player.setHealth(player.getMaxHealth());

        return playerRepository.save(player);
    }

    // --- NEW: GAMEPLAY ACTION ---
    public Player exploreDungeon(Long playerId) {
        // 1. Find the player in the database
        Player player = getPlayer(playerId);

        // 2. Check if they are dead
        if (player.getHealth() <= 0) {
            throw new RuntimeException("This player is dead and cannot explore!");
        }

        // 3. Simulate a fight (RNG)
        int damageTaken = (int)(Math.random() * 10);
        int goldFound = (int)(Math.random() * 20) + 5;
        int xpGained = 25;

        // 4. Update the player's stats
        player.setHealth(player.getHealth() - damageTaken);
        player.setGold(player.getGold() + goldFound);
        player.setExperience(player.getExperience() + xpGained);

        // Simple level-up logic
        if (player.getExperience() >= 100) {
            player.setLevel(player.getLevel() + 1);
            player.setExperience(player.getExperience() - 100); // Reset XP
            player.setMaxHealth(player.getMaxHealth() + 20);
            player.setHealth(player.getMaxHealth()); // Full heal on level up!
            player.setStrength(player.getStrength() + 2);
        }

        // 5. Save the updated player back to the database
        return playerRepository.save(player);
    }
}
//ready