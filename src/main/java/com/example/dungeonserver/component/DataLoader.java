package com.example.dungeonserver.component;

import com.example.dungeonserver.model.Monster;
import com.example.dungeonserver.repository.MonsterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final MonsterRepository monsterRepository;

    public DataLoader(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only add monsters if the database is empty
        if (monsterRepository.count() == 0) {
            Monster goblin = new Monster();
            goblin.setName("Sneaky Goblin");
            goblin.setHealth(20);
            goblin.setStrength(12);
            goblin.setGoldDrop(10);

            Monster troll = new Monster();
            troll.setName("Cave Troll");
            troll.setHealth(50);
            troll.setStrength(25);
            troll.setGoldDrop(30);

            monsterRepository.save(goblin);
            monsterRepository.save(troll);

            System.out.println("🗡️ Monsters have populated the dungeon!");
        }
    }
}