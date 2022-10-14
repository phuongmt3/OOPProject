package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Entities.Movers.Mover;
import com.Main;
import com.Renderer.RendererBalloom;
import com.Renderer.RendererDoria;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Doria extends Enemy {
    private final int stepsPerSquare = (int) Math.round(Main.defaultSide / speed);
    private int steps;

    public Doria(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager, bomber);
        renderer = new RendererDoria();
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.delete();
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
}
