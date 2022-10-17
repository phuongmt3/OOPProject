package com.Entities.Movers;

import com.Entities.Bomb.BombManager;
import com.Entities.Bomb.FlameManager;
import com.Entities.Entity;
import com.Entities.Maps.Brick;
import com.Entities.Maps.Items.*;
import com.Entities.Movers.Enemies.EnemyManager;
import com.Main;
import com.Renderer.RendererBomber;

import java.util.ArrayList;

public class Bomber extends Mover {
    private boolean renderDead = false;
    private BomberAI bomberAI;
    public Bomber(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                  BombManager bombManager, EnemyManager enemyManager) {
        super(x, y, speed, map, bombManager, enemyManager);
        renderer = new RendererBomber(x, y);
    }

    public void setBomberAI(BomberAI ai) {
        bomberAI = ai;
    }

    protected void checkCollisionEnemy() {
        for (int i = 0; i < enemyManager.countEnemies(); i++)
            if (enemyManager.getEnemy(i).checkCollision(this))
                setDead(true);
    }

    protected void checkTakeItem() {
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
                        bomberAI.updateTiming();
                    }
                    else if (tile instanceof SpeedItem && !((SpeedItem) tile).isUsed()) {
                        ((SpeedItem) tile).useItem();
                        speed = 6.4;
                        bomberAI.updateTiming();
                        setX(j * Main.defaultSide);
                        setY(i * Main.defaultSide);
                    }
                    else if (tile instanceof Portal && enemyManager.allDead()) {
                        Main.win = true;
                    }
                }
            }
    }

    @Override
    public void update() {
        checkCollisionEnemy();
        checkTakeItem();
    }

    public void render(MovementType dir) throws Exception {
        if (!isDead) {
            if (dir == MovementType.STILL)
                renderer.pauseAnimation(dir);
            else
                renderer.startAnimation(dir);
        }
    }

    public void stopAnimation() {
        ((RendererBomber)renderer).stopAnimation(null);
    }

    public void showStartAnimation() {
        ((RendererBomber)renderer).showStartAnimation();
    }

    @Override
    public String getClassName() {
        return "Bomber";
    }

    @Override
    public void render() throws Exception {
        if (isDead() && !renderDead) {
            renderer.delete();
            renderDead = true;
        }
        else if (!isDead())
            renderer.render(x, y);
    }

    public void clear() {
        renderer.clear();
    }
}