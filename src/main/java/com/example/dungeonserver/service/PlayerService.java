package com.example.dungeonserver.service;

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

        // Game Logic: Roll for stats (Like D&D)
        // Base Strength is 10, plus a random roll of 0-5
        int bonusStr = (int)(Math.random() * 5);
        player.setStrength(10 + bonusStr);

        // Base Defense is 5, plus a random roll of 0-3
        int bonusDef = (int)(Math.random() * 3);
        player.setDefense(5 + bonusDef);

        // Give them starter gold
        player.setGold(50);

        return playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found!"));
    }
}