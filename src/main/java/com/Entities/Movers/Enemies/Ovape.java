package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Main;
import com.Renderer.RendererOvape;

import java.util.ArrayList;

public class Ovape extends Enemy {
    private RendererOvape renderer = new RendererOvape();
    private final int stepsPerSquare = (int) (Main.defaultSide / speed);
    private int steps;

    public Ovape(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager, bomber);
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.deleteOvape();
            enemyManager.removeEnemy(this);
        }

        if (steps == 0) {
            int moveStyle = random.nextInt(10);
            if (moveStyle == 1)
                direction = getMoveDirectionToBomber();
            else
                direction = getRandomMoveDirection();
        }
        else
            canMoveAndMove(direction);
        steps++;
        steps = steps % stepsPerSquare;

        if (!isDead)
            renderer.startAnimation(direction);
        if (colllideOtherEnemy())
            renderer.pauseAnimation(direction);
    }

    @Override
    public void render() throws Exception {
        renderer.renderOvape(x, y);
    }
}
