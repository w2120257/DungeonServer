package com.example.dungeonserver.controller;

import com.example.dungeonserver.model.Player;
import com.example.dungeonserver.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // We use @GetMapping here so we can test it easily in our browser...
    // Usually this would be @PostMapping, but browsers love GET.
    // URL: http://localhost:8080/api/player/spawn?name=HeroName
    @GetMapping("/spawn")
    public Player spawn(@RequestParam String name) {
        return playerService.spawnPlayer(name);
    }

    // URL: http://localhost:8080/api/player/1
    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    // URL: http://localhost:8080/api/player/1/explore
    @GetMapping("/{id}/explore")
    public Player explore(@PathVariable Long id) {
        return playerService.exploreDungeon(id);
    }

    // URL: http://localhost:8080/api/player/1/heal
    @GetMapping("/{id}/heal")
    public Player heal(@PathVariable Long id) {
        return playerService.healPlayer(id);
    }
}
//ready