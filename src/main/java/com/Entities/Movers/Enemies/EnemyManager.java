package com.Entities.Movers.Enemies;

import com.GameSound;

import java.util.ArrayList;

public class EnemyManager {
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        GameSound.playClip(GameSound.ENEMYDIE);


    }

    public void render() throws Exception {
        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }

    public void update() {
        int oldEnemyCount = enemies.size();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            if (oldEnemyCount != enemies.size()) {
                oldEnemyCount = enemies.size();
                i--;
            }
        }

    }

    public boolean allDead() {
        return enemies.isEmpty();
    }

    public int countEnemies() {
        return enemies.size();
    }

    public Enemy getEnemy(int index) {
        return enemies.get(index);
    }

    public void clear() {
        for (Enemy enemy : enemies) {
            enemy.clear();
        }
    }
}
