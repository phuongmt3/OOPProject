package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Main;
import com.Renderer.RendererPass;

import java.util.ArrayList;

public class Pass extends Enemy {
    private RendererPass renderer = new RendererPass();
    private final int stepsPerSquare = (int) Math.round(Main.defaultSide / speed);
    private int steps;

    public Pass(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager, bomber);
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.deletePass();
            enemyManager.removeEnemy(this);
        }

        if (steps == 0) {
            int moveStyle = random.nextInt(5);
            if (moveStyle == 1)
                direction = getRandomMoveDirection();
            else
                direction = getMoveDirectionFindWay();
        }
        else if (!canMoveAndMove(direction)) {
            direction = getRandomMoveDirection();
            steps = stepsPerSquare - steps + 1;
        }
        steps++;
        steps = steps % stepsPerSquare;

        if (!isDead)
            renderer.startAnimation(direction);
        if (colllideOtherEnemy())
            renderer.pauseAnimation(direction);
    }

    @Override
    public void render() throws Exception {
        renderer.renderPass(x, y);
    }
}
