package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Maps.Grass;
import com.Entities.Movers.Bomber;
import com.Main;
import com.Renderer.RendererDahl;

import java.util.ArrayList;
import java.util.Queue;

public class Dahl extends Enemy {
    private int stepsPerSquare = (int) Math.round(Main.defaultSide / speed);
    private int steps;
    public final double normSpeed, fastSpeed = 10;
    private ArrayList<MovementType> moveWaitList = new ArrayList<MovementType>();

    public Dahl(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager, bomber);
        normSpeed = speed;
        renderer = new RendererDahl();
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.delete();
            enemyManager.removeEnemy(this);
        }

        if (steps == 0) {
            if (!moveWaitList.isEmpty()) {
                while (!canMoveAndMove(direction)) {
                    moveWaitList.remove(0);
                    if (moveWaitList.isEmpty()) {
                        speed = normSpeed;
                        stepsPerSquare = (int) (Main.defaultSide / speed);
                        break;
                    }
                    direction = moveWaitList.get(0);
                }
            }
            else {
                int moveStyle = random.nextInt(100);
                if (moveStyle == 20) {
                    //bouncy x
                    moveWaitList.add(MovementType.LEFT);
                    moveWaitList.add(MovementType.RIGHT);
                    moveWaitList.add(MovementType.LEFT);
                    moveWaitList.add(MovementType.RIGHT);
                    x = moveToNeareastSquare(x);
                    y = moveToNeareastSquare(y);
                    speed = fastSpeed;
                    stepsPerSquare = (int) (Main.defaultSide / speed);
                }
                else if (moveStyle == 80) {
                    //bouncy y
                    moveWaitList.add(MovementType.UP);
                    moveWaitList.add(MovementType.DOWN);
                    moveWaitList.add(MovementType.UP);
                    moveWaitList.add(MovementType.DOWN);
                    x = moveToNeareastSquare(x);
                    y = moveToNeareastSquare(y);
                    speed = fastSpeed;
                    stepsPerSquare = (int) (Main.defaultSide / speed);
                }
                else
                    direction = getMoveDirectionForDahl();
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
