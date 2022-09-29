package com.example.dbproject;

import java.util.ArrayList;

public class Bomber extends Mover {
    private RendererBomber renderer = new RendererBomber();
    private boolean renderDead = false;
    public Bomber(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                  BombManager bombManager, EnemyManager enemyManager) {
        super(x, y, speed, map, bombManager, enemyManager);
    }

    @Override
    public void update() {
        for (int i = 0; i < enemyManager.countEnemies(); i++)
            if (enemyManager.getEnemy(i).checkCollision(this))
                setDead(true);
    }

    @Override
    public void render() throws Exception {
        if (isDead() && !renderDead) {
            renderer.deleteBomber(x, y);
            renderDead = true;
        }
        else if (!isDead())
            renderer.renderBomber(x, y);
    }

    @Override
    public String getClassName() {
        return "Bomber";
    }
}