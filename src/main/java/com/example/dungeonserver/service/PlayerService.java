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

    @Autowired
    private MonsterRepository monsterRepository;

    private int getTotalStrength(Player player) {
        int bonus = player.getInventory().stream()
                .mapToInt(Item::getPower)
                .sum();
        return player.getStrength() + bonus;
    }

    public Player exploreDungeon(Long playerId) {
        Player player = getPlayer(playerId);
        if (player.getHealth() <= 0) throw new RuntimeException("You are dead!");

        List<Monster> monsters = monsterRepository.findAll();
        Monster enemy = monsters.get(new Random().nextInt(monsters.size()));

        int totalStrength = getTotalStrength(player);
        int combatSkillBonus = totalStrength / 4;
        int damageTaken = Math.max(0, enemy.getStrength() - (player.getDefense() + combatSkillBonus));

        player.setHealth(Math.max(0, player.getHealth() - damageTaken));

        String msg = "A " + enemy.getName() + " appeared! ";
        msg += "Total Power: " + totalStrength + ". Damage Taken: " + damageTaken + ". ";

        if (player.getHealth() > 0) {
            int lootBonus = totalStrength / 2;
            int totalGold = enemy.getGoldDrop() + lootBonus;
            player.setGold(player.getGold() + totalGold);
            player.setExperience(player.getExperience() + 25);
            msg += "Defeated! Found " + totalGold + " gold.";

            if (player.getExperience() >= 100) {
                player.setLevel(player.getLevel() + 1);
                player.setExperience(0);
                player.setMaxHealth(player.getMaxHealth() + 20);
                player.setHealth(player.getMaxHealth());
                player.setStrength(player.getStrength() + 5);
                msg += " LEVEL UP! Now Level " + player.getLevel() + "!";
            }
        } else {
            msg += "YOU DIED! Visit the healer.";
        }

        player.setStatusMessage(msg);
        return playerRepository.save(player);
    }

    public Player buyItem(Long playerId, String itemName, int power, int cost) {
        Player player = getPlayer(playerId);
        if (player.getGold() < cost) {
            player.setStatusMessage("You need " + cost + " gold for the " + itemName + "!");
            return player;
        }
        player.setGold(player.getGold() - cost);
        Item newItem = new Item(itemName, "WEAPON", power, cost / 2, player);
        player.getInventory().add(newItem);
        player.setStatusMessage("Bought " + itemName + "! Power +" + power);
        return playerRepository.save(player);
    }

    public Player spawnPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        player.setStrength(10 + (int)(Math.random() * 5));
        player.setDefense(5 + (int)(Math.random() * 3));
        player.setGold(50);
        Item sword = new Item("Rusty Sword", "WEAPON", 3, 5, player);
        player.getInventory().add(sword);
        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Player healPlayer(Long playerId) {
        Player player = getPlayer(playerId);
        if (player.getGold() < 15) throw new RuntimeException("Not enough gold!");
        player.setGold(player.getGold() - 15);
        player.setHealth(player.getMaxHealth());
        player.setStatusMessage("Healed to full!");
        return playerRepository.save(player);
    }
}