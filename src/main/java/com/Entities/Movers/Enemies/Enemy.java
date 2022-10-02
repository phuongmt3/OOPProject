package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Entities.Movers.Mover;

import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy extends Mover {
    protected Random random = new Random();
    protected MovementType direction = MovementType.UP;
    protected Bomber bomber;

    public Enemy(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                 BombManager bombManager, EnemyManager enemyManager, Bomber bomber) {
        super(x, y, speed, map, bombManager, enemyManager);
        this.bomber = bomber;
    }

    protected MovementType getRandomMoveDirection() {
        int changeDirection = random.nextInt(3);
        Mover.MovementType dir = direction;
        if (changeDirection == 0)
            dir = Mover.MovementType.values()[random.nextInt(4)];
        while (!canMoveAndMove(dir))
            dir = Mover.MovementType.values()[random.nextInt(4)];
        return dir;
    }

    protected MovementType getMoveDirectionToBomber() {
        double deltaX = x - bomber.getX();
        double deltaY = y - bomber.getY();
        MovementType dirx = MovementType.LEFT, diry = MovementType.UP;
        if (deltaX < 0)
            dirx = MovementType.RIGHT;
        if (deltaY < 0)
            diry = MovementType.DOWN;

        int way = random.nextInt(3);
        if (way == 1) {
            if (deltaX != 0 && canMoveAndMove(dirx))
                return dirx;
            else if (canMoveAndMove(diry))
                return diry;
        }
        if (way == 2) {
            if (deltaY != 0 && canMoveAndMove(diry))
                return diry;
            else if (canMoveAndMove(dirx))
                return dirx;
        }
        return getRandomMoveDirection();
    }

    @Override
    public String getClassName() {
        return "Enemy";
    }
}
