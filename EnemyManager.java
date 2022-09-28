package com.example.dbproject;

import java.util.ArrayList;

public class EnemyManager {
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public void addEnemy(Enemy enemy) {}
    public void removeEnemy(Enemy enemy) {}

    public boolean allDead() {
        return enemies.isEmpty();
    }
}
