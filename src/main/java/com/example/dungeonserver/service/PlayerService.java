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
}
//ready