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

    //get bouncy when in the same row/col with bomber
    protected MovementType getMoveDirectionForDahl() {
        int overallDirection = random.nextInt(10);
        Mover.MovementType dir = direction;
        int changeDirection = random.nextInt(2);

        if (overallDirection == 0) {
            //up or down direction
            if (changeDirection == 0)
                dir = Mover.MovementType.values()[random.nextInt(2) * 2 + 1];
            else if (dir != MovementType.UP && dir != MovementType.DOWN)
                dir = Mover.MovementType.values()[random.nextInt(2) * 2 + 1];
            if (canMoveAndMove(dir))
                return dir;
            else if (canMoveAndMove(MovementType.values()[4 - dir.ordinal()]))
                return MovementType.values()[4 - dir.ordinal()];
            else return getRandomMoveDirection();
        }
        //left or right direction
        if (changeDirection == 0)
            dir = Mover.MovementType.values()[random.nextInt(2) * 2];
        else if (dir != MovementType.LEFT && dir != MovementType.RIGHT)
            dir = Mover.MovementType.values()[random.nextInt(2) * 2];
        if (canMoveAndMove(dir))
            return dir;
        else if (canMoveAndMove(MovementType.values()[2 - dir.ordinal()]))
            return MovementType.values()[2 - dir.ordinal()];
        else return getRandomMoveDirection();
    }

    @Override
    public String getClassName() {
        return "Enemy";
    }
}
