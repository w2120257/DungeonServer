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
        // ... (keep your existing checks here) ...

        player.setGold(player.getGold() - 15);
        player.setHealth(player.getMaxHealth());

        player.setStatusMessage("The town healer worked their magic. Health fully restored!");
        return playerRepository.save(player);
    }

    public Player exploreDungeon(Long playerId) {
        Player player = getPlayer(playerId);
        if (player.getHealth() <= 0) throw new RuntimeException("You are dead!");

        List<Monster> monsters = monsterRepository.findAll();
        Monster enemy = monsters.get(new Random().nextInt(monsters.size()));

        int damageTaken = Math.max(0, enemy.getStrength() - player.getDefense());
        player.setHealth(Math.max(0, player.getHealth() - damageTaken));

        // BUILD THE STORY
        String msg = "A " + enemy.getName() + " appeared! ";
        msg += "It dealt " + damageTaken + " damage. ";

        if (player.getHealth() > 0) {
            player.setGold(player.getGold() + enemy.getGoldDrop());
            player.setExperience(player.getExperience() + 25);
            msg += "You defeated it and found " + enemy.getGoldDrop() + " gold!";

            if (player.getExperience() >= 100) {
                // ... (keep your level up logic) ...
                player.setLevel(player.getLevel() + 1);
                player.setExperience(0);
                player.setMaxHealth(player.getMaxHealth() + 20);
                player.setHealth(player.getMaxHealth());
                msg += " LEVEL UP! You are now Level " + player.getLevel() + "!";
            }
        } else {
            msg += "YOU DIED! Go see the healer.";
        }

        player.setStatusMessage(msg); // Attach the story to the player
        return playerRepository.save(player);
    }




}