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
        for (int i = 0; i < Main.rows; i++)
            for (int j = 0; j < Main.cols; j++) {
                Entity tile = map.get(i).get(j);
                if (checkCollision(tile) && tile instanceof Brick && ((Brick) tile).isHasItem()) {
                    if (tile instanceof BombItem && !((BombItem) tile).isUsed()) {
                        ((BombItem) tile).useItem();
                        bombManager.increaseCntLimit();
                    }
                    else if (tile instanceof FlameItem && !((FlameItem) tile).isUsed()) {
                        ((FlameItem) tile).useItem();
                        FlameManager.increaseFlameLength();
                    }
                    else if (tile instanceof SpeedItem && !((SpeedItem) tile).isUsed()) {
                        ((SpeedItem) tile).useItem();
                        speed = Main.defaultSide / 4;
                    }
                    else if (tile instanceof Portal && enemyManager.allDead()) {
                        System.out.println("WINNER!!!");
                    }
                }
            }
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