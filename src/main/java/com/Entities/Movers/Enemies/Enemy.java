package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Entities.Movers.Mover;
import com.Main;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Enemy extends Mover {
    protected Random random = new Random();
    protected MovementType direction = MovementType.UP;
    protected Bomber bomber;
    protected ArrayList<MovementType> way = new ArrayList<MovementType>();

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

    protected void findWay() {
        ArrayList<Pair<Integer, Integer>> loang = new ArrayList<>();
        Map<Pair<Integer, Integer>, MovementType> tracking = new HashMap<>();

        Pair<Integer, Integer> first = new Pair<>((int) (x / Main.defaultSide), (int) (y / Main.defaultSide));
        loang.add(first);
        tracking.put(first, null);
        Pair<Integer, Integer> goal = new Pair<>((int) (bomber.getX() / Main.defaultSide),
                (int) (bomber.getY() / Main.defaultSide));
        boolean foundWay = false;
        while (!loang.isEmpty() && !foundWay) {
            Pair<Integer, Integer> cur = loang.get(0);
            loang.remove(0);
            if (!availableArea(cur.getKey(), cur.getValue()))
                continue;
            for (MovementType type : MovementType.values()) {
                Pair<Integer, Integer> next = new Pair<>(cur.getKey() + type.x, cur.getValue() + type.y);
                if (validCoordination(next.getKey() * Main.defaultSide, next.getValue() * Main.defaultSide)
                        && !tracking.containsKey(next) && availableArea(next.getKey(), next.getValue())) {
                    loang.add(next);
                    tracking.put(next, type);
                    if (next.equals(goal)) {
                        foundWay = true;
                        break;
                    }
                }
            }
        }
        if (!foundWay)
            return;
        while (tracking.get(goal) != null) {
            MovementType dir = tracking.get(goal);
            way.add(dir);
            goal = new Pair<>(goal.getKey() - dir.x, goal.getValue() - dir.y);
        }
    }

    protected MovementType getMoveDirectionFindWay() {
        findWay();
        if (!way.isEmpty()) {
            MovementType dir = way.get(way.size() - 1);
            way.remove(way.size() - 1);
            canMoveAndMove(dir);
            return dir;
        }
        return getRandomMoveDirection();
    }

    @Override
    public String getClassName() {
        return "Enemy";
    }
}
