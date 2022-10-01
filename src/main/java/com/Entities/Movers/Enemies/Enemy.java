package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Mover;

import java.util.ArrayList;

public abstract class Enemy extends Mover {
    public Enemy(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager) {
        super(x, y, speed, map, bombManager, enemyManager);
        enemyManager = enemyManager;
    }

    @Override
    public String getClassName() {
        return "Enemy";
    }
}
