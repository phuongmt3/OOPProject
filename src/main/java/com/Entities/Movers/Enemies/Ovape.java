package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Main;
import com.Renderer.RendererOvape;

import java.util.ArrayList;

public class Ovape extends Enemy {
    private final int stepsPerSquare = (int) Math.round(Main.defaultSide / speed);
    private int steps;

    public Ovape(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager, bomber);
        renderer = new RendererOvape();
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.delete();
            enemyManager.removeEnemy(this);
        }

        if (steps == 0) {
            int moveStyle = random.nextInt(10);
            if (moveStyle == 1)
                direction = getMoveDirectionToBomber();
            else
                direction = getRandomMoveDirection();
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
}
