#  Dungeon Server - RPG Backend Engine

> A robust, scalable Game Server API built with **Spring Boot** and **Java 21**.

##  Overview
**Dungeon Server** is a backend engine designed to power a text-based or 2D Dungeon Crawler RPG. Unlike simple CRUD apps, this project focuses on **complex business logic**, including state management, combat formulas, inventory systems, and loot generation.

The goal is to build an "Enterprise-Grade" game server that handles player progression, persistence, and game mechanics via a RESTful API.

## Tech Stack
* **Core:** Java 21, Spring Boot 3.x
* **Database:** H2 (Dev), PostgreSQL (Prod - planned)
* **Persistence:** Spring Data JPA (Hibernate)
* **Tools:** Lombok, Maven, Git
* **Testing:** JUnit 5, Mockito

##  Key Features
### (MVP)
* **Player Creation:** Complex entity generation with base stats (Health, Strength, Defense).
* **Persistence:** Automatic saving of player state to database.
* **REST API:** Endpoints to create and retrieve player data.

### 
* **Inventory System:** Logic for items, equipment slots, and stat modifiers.
* **Exploration Engine:** Grid-based movement logic (X, Y coordinates).

### Roadmap (Upcoming)
* [ ] **Combat Engine:** Turn-based battle logic with RNG and critical hits.
* [ ] **Loot Tables:** Algorithm for random item drops based on monster level.
* [ ] **Shop System:** Buy/Sell logic with Gold currency.
* [ ] **Security:** JWT Authentication for player accounts.


*Created by Sandupama Wedamulla as a showcase of Backend Engineering skills.*