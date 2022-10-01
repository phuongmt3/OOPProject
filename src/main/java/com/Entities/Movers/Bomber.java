package com.Entities.Movers;

import com.Entities.Bomb.BombManager;
import com.Entities.Bomb.FlameManager;
import com.Entities.Entity;
import com.Entities.Maps.Brick;
import com.Entities.Maps.Items.BombItem;
import com.Entities.Maps.Items.FlameItem;
import com.Entities.Maps.Items.Portal;
import com.Entities.Maps.Items.SpeedItem;
import com.Entities.Movers.Enemies.EnemyManager;
import com.Main;
import com.Renderer.RendererBomber;

import java.util.ArrayList;

public class Bomber extends Mover {
    private RendererBomber renderer;
    private boolean renderDead = false;
    public Bomber(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                  BombManager bombManager, EnemyManager enemyManager) {
        super(x, y, speed, map, bombManager, enemyManager);
        renderer = new RendererBomber(x, y);
    }

    @Override
    public void update() {
        System.out.println(x + " " + y);
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
                        setX(j * Main.defaultSide);
                        setY(i * Main.defaultSide);
                    }
                    else if (tile instanceof Portal && enemyManager.allDead()) {
                        System.out.println("WINNER!!!");
                    }
                }
            }
    }

    public void render(MovementType dir) throws Exception {
        if (!isDead)
            renderer.startAnimation(dir);
    }

    @Override
    public String getClassName() {
        return "Bomber";
    }

    @Override
    public void render() throws Exception {
        if (isDead() && !renderDead) {
            renderer.deleteBomber();
            renderDead = true;
        }
        else if (!isDead())
            renderer.renderBomber(x, y);
    }
}