package com.example.dbproject;

import java.util.ArrayList;

public class EnemyManager {
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void render() throws Exception {
        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }

    public void update() {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    public boolean allDead() {
        return enemies.isEmpty();
    }
}
