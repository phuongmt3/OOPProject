package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Main;
import com.Renderer.RendererOneal;

import java.util.ArrayList;

public class Oneal extends Enemy {
    private RendererOneal renderer = new RendererOneal();
    public final double fastSpeed = 2.5, normSpeed;
    private final int nearLimit = 5;
    private int stepsPerSquare = (int) Math.round(Main.defaultSide / speed);
    private int steps;
    public Oneal(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager, bomber);
        normSpeed = speed;
    }

    @Override
    public void render() throws Exception {
        renderer.renderOneal(x, y);
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.deleteOneal();
            enemyManager.removeEnemy(this);
        }

        if (steps == 0) {
            if (Math.abs(x - bomber.getX()) <= nearLimit * Main.defaultSide
                    && Math.abs(y - bomber.getY()) <= nearLimit * Main.defaultSide) {
                x = moveToNeareastSquare(x);
                y = moveToNeareastSquare(y);
                direction = getMoveDirectionToBomber();
                speed = fastSpeed;
                stepsPerSquare = (int) (Main.defaultSide / speed);
            }
            else {
                direction = getRandomMoveDirection();
                speed = normSpeed;
                stepsPerSquare = (int) (Main.defaultSide / speed);
            }
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
