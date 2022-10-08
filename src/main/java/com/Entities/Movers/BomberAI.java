package com.Entities.Movers;

import com.Entities.Bomb.Bomb;
import com.Entities.Bomb.BombManager;
import com.Entities.Bomb.FlameManager;
import com.Entities.Entity;
import com.Entities.Maps.Brick;
import com.Entities.Maps.Items.BombItem;
import com.Entities.Maps.Items.FlameItem;
import com.Entities.Maps.Items.Portal;
import com.Entities.Maps.Items.SpeedItem;
import com.Entities.Movers.Enemies.Dahl;
import com.Entities.Movers.Enemies.Enemy;
import com.Entities.Movers.Enemies.EnemyManager;
import com.Entities.Movers.Enemies.Oneal;
import com.Main;
import javafx.util.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.Random;

public class BomberAI extends Bomber {
    private Random random = new Random();
    private ArrayList<ArrayList<Integer>> mapAI = new ArrayList<>();
    private MovementType direction = MovementType.RIGHT;
    private int escapeTime, stepTime;
    private int steps;

    public BomberAI(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                  BombManager bombManager, EnemyManager enemyManager) {
        super(x, y, speed, map, bombManager, enemyManager);
        for (int i = 0; i < Main.rows; i++) {
            mapAI.add(new ArrayList<>());
            for (int j = 0; j < Main.cols; j++)
                mapAI.get(i).add(0);
        }
        updateTiming();
    }

    public void updateTiming() {
        stepTime = (int) Math.round(Main.defaultSide / speed);
        escapeTime = FlameManager.getFlameLength() * stepTime + stepTime;
    }

    private void checkCollisionEnemy() {
        for (int i = 0; i < enemyManager.countEnemies(); i++)
            if (enemyManager.getEnemy(i).checkCollision(this))
                setDead(true);
    }
    private void checkTakeItem() {
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
                        updateTiming();
                    }
                    else if (tile instanceof SpeedItem && !((SpeedItem) tile).isUsed()) {
                        ((SpeedItem) tile).useItem();
                        speed = 4;
                        updateTiming();
                        setX(j * Main.defaultSide);
                        setY(i * Main.defaultSide);
                    }
                    else if (tile instanceof Portal && enemyManager.allDead()) {
                        System.out.println("WINNER!!!");
                    }
                }
            }
    }
    private void moveUpDownAfterLeftRight(int leftcnt, Enemy enemy, int cury, int posx, double enemyspeed) {
        int remainSteps = stepTime - leftcnt;
        int remainSteps2 = remainSteps + stepTime * 2;
        //up
        double newY = roundCoordinate(enemy.getY() - enemyspeed * remainSteps);
        double newY2 = roundCoordinate(enemy.getY() - enemyspeed * remainSteps2);
        int posy = curpos(newY);
        int posy2 = curpos(newY2);
        for (int jj = cury - 1; jj >= 0 && jj >= posy2 && mapAI.get(jj).get(posx) != -1; jj--)
            if (mapAI.get(jj).get(posx) == 0) {
                if (jj >= posy)
                    mapAI.get(jj).set(posx, 1);
                else mapAI.get(jj).set(posx, 2);
            }
        //down
        newY = roundCoordinate(enemy.getY() + enemyspeed * remainSteps);
        newY2 = roundCoordinate(enemy.getY() + enemyspeed * remainSteps2);
        posy = nextpos(newY) == -1 ? curpos(newY) : nextpos(newY);
        posy2 = nextpos(newY2) == -1 ? curpos(newY2) : nextpos(newY2);
        for (int jj = cury + 1; jj < Main.rows && jj <= posy2 && mapAI.get(jj).get(posx) != -1; jj++)
            if (mapAI.get(jj).get(posx) == 0) {
                if (jj <= posy)
                    mapAI.get(jj).set(posx, 1);
                else mapAI.get(jj).set(posx, 2);
            }
    }
    private void moveLeftRightAfterUpDown(int upcnt, Enemy enemy, int curx, int posy, double enemyspeed) {
        int remainSteps = stepTime - upcnt;
        int remainSteps2 = remainSteps + stepTime * 2;
        //left
        double newX = roundCoordinate(enemy.getX() - enemyspeed * remainSteps);
        double newX2 = roundCoordinate(enemy.getX() - enemyspeed * remainSteps2);
        int posx = curpos(newX);
        int posx2 = curpos(newX2);
        for (int ii = curx - 1; ii >= 0 && ii >= posx2 && mapAI.get(posy).get(ii) != -1; ii--)
            if (mapAI.get(posy).get(ii) == 0) {
                if (ii >= posx)
                    mapAI.get(posy).set(ii, 1);
                else mapAI.get(posy).set(ii, 2);
            }
        //right
        newX = roundCoordinate(enemy.getX() + enemyspeed * remainSteps);
        newX2 = roundCoordinate(enemy.getX() + enemyspeed * remainSteps2);
        posx = nextpos(newX) == -1 ? curpos(newX) : nextpos(newX);
        posx2 = nextpos(newX2) == -1 ? curpos(newX2) : nextpos(newX2);
        for (int ii = curx + 1; ii < Main.cols && ii <= posx2 && mapAI.get(posy).get(ii) != -1; ii++)
            if (mapAI.get(posy).get(ii) == 0) {
                if (ii <= posx)
                    mapAI.get(posy).set(ii, 1);
                else mapAI.get(posy).set(ii, 2);
            }
    }
    private void updateMapAI() {
        //-1 can't go; -2 enemy pos; 0 safe; 1 enemy will come need to avoid; 2 should put bomb
        System.out.println("in update mapAI");
        for (int y = 0; y < Main.rows; y++)
            for (int x = 0; x < Main.cols; x++) {
                if (!availableArea(x, y))
                    mapAI.get(y).set(x, -1);
                else
                    mapAI.get(y).set(x, 0);
            }

        for (int i = 0; i < enemyManager.countEnemies(); i++) {
            Enemy enemy = enemyManager.getEnemy(i);
            double enemyspeed = enemy.speed;
            if (enemy instanceof Oneal)
                enemyspeed = ((Oneal) enemy).fastSpeed;
            else if (enemy instanceof Dahl)
                enemyspeed = ((Dahl) enemy).fastSpeed;
            int curx = curpos(enemy.getX());
            int cury = curpos(enemy.getY());
            int nextx = nextpos(enemy.getX());
            int nexty = nextpos(enemy.getY());
            mapAI.get(cury).set(curx, -2);
            if (nextx > -1)
                mapAI.get(cury).set(nextx, -2);
            if (nexty > -1)
                mapAI.get(nexty).set(curx, -2);

            System.out.println("Enemy " + curx + " " + cury + ":" + enemy.getX() + " " + enemy.getY());

            if (enemy.getY() == moveToNeareastSquare(enemy.getY())) {
                //left then up, down
                for (int leftcnt = 0; leftcnt <= stepTime * 3; leftcnt++) {
                    double newX = roundCoordinate(enemy.getX() - enemyspeed * leftcnt);
                    int posx = curpos(newX);
                    if (posx < 0 || mapAI.get(cury).get(posx) == -1)
                        break;
                    else if (mapAI.get(cury).get(posx) == 0) {
                        if (leftcnt <= stepTime)
                            mapAI.get(cury).set(posx, 1);
                        else mapAI.get(cury).set(posx, 2);
                    }
                    if (newX == moveToNeareastSquare(newX))
                        moveUpDownAfterLeftRight(leftcnt, enemy, cury, posx, enemyspeed);
                }
                //right then up, down
                for (int rightcnt = 0; rightcnt <= stepTime * 3; rightcnt++) {
                    double newX = roundCoordinate(enemy.getX() + enemyspeed * rightcnt);
                    int posx = nextpos(newX) > -1 ? nextpos(newX) : curpos(newX);
                    if (posx >= Main.cols || mapAI.get(cury).get(posx) == -1)
                        break;
                    else if (mapAI.get(cury).get(posx) == 0) {
                        if (rightcnt <= stepTime)
                            mapAI.get(cury).set(posx, 1);
                        else mapAI.get(cury).set(posx, 2);
                    }
                    if (newX == moveToNeareastSquare(newX))
                        moveUpDownAfterLeftRight(rightcnt, enemy, cury, posx, enemyspeed);
                }
            } else {
                //up then left, right
                for (int upcnt = 0; upcnt <= stepTime * 3; upcnt++) {
                    double newY = roundCoordinate(enemy.getY() - enemyspeed * upcnt);
                    int posy = curpos(newY);
                    if (posy < 0 || mapAI.get(posy).get(curx) == -1)
                        break;
                    else if (mapAI.get(posy).get(curx) == 0) {
                        if (upcnt <= stepTime)
                            mapAI.get(posy).set(curx, 1);
                        else mapAI.get(posy).set(curx, 2);
                    }
                    if (newY == moveToNeareastSquare(newY))
                        moveLeftRightAfterUpDown(upcnt, enemy, curx, posy, enemyspeed);
                }
                //down then left, right
                for (int downcnt = 0; downcnt <= stepTime * 3; downcnt++) {
                    double newY = roundCoordinate(enemy.getY() + enemyspeed * downcnt);
                    int posy = nextpos(newY) > -1 ? nextpos(newY) : curpos(newY);
                    if (posy >= Main.rows || mapAI.get(posy).get(curx) == -1)
                        break;
                    else if (mapAI.get(posy).get(curx) == 0) {
                        if (downcnt <= stepTime)
                            mapAI.get(posy).set(curx, 1);
                        else mapAI.get(posy).set(curx, 2);
                    }
                    if (newY == moveToNeareastSquare(newY))
                        moveLeftRightAfterUpDown(downcnt, enemy, curx, posy, enemyspeed);
                }
            }
        }

        for (int i = 0; i < bombManager.countBomb(); i++) {
            Bomb curbomb = bombManager.getBomb(i);
            if (curbomb.countdown() >= escapeTime)  //handle consecutive explosion (update timer of each bomb when put new one)
                continue;
            int idx = (int) Math.round(curbomb.getX() / Main.defaultSide);
            int idy = (int) Math.round(curbomb.getY() / Main.defaultSide);
            for (MovementType type : MovementType.values())
                for (int len = 1; len <= FlameManager.getFlameLength(); len++) {
                    int flamey = idy + type.y * len;
                    int flamex = idx + type.x * len;
                    if (mapAI.get(flamey).get(flamex) == -1)
                        break;
                    mapAI.get(flamey).set(flamex, -1);
                }
        }
    }
    private boolean nearBrick(int curx, int cury) {
        if (curx - 1 >= 0 && map.get(cury).get(curx - 1) instanceof Brick
                && !((Brick) map.get(cury).get(curx - 1)).isExposed())
            return true;
        if (cury - 1 >= 0 && map.get(cury - 1).get(curx) instanceof Brick
                && !((Brick) map.get(cury - 1).get(curx)).isExposed())
            return true;
        if (curx + 1 < Main.cols && map.get(cury).get(curx + 1) instanceof Brick
                && !((Brick) map.get(cury).get(curx + 1)).isExposed())
            return true;
        if (cury + 1 < Main.rows && map.get(cury + 1).get(curx) instanceof Brick
                && !((Brick) map.get(cury + 1).get(curx)).isExposed())
            return true;
        return false;
    }
    private ArrayList<MovementType> chooseSafeWay(int curx, int cury, boolean ifInBombPos) {
        ArrayList<MovementType> safeWay = new ArrayList<>();
        ArrayList<Triple<Integer, Integer, MovementType>> loang = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> tracking = new ArrayList<>();
        for (MovementType type : MovementType.values())
            if (type != MovementType.STILL && validCoordination((curx + type.x) * Main.defaultSide, (cury + type.y) * Main.defaultSide)
                    && (mapAI.get(cury + type.y).get(curx + type.x) == 0 || mapAI.get(cury + type.y).get(curx + type.x) == 2)) {
                loang.add(new Triple<>(curx + type.x, cury + type.y, type));
                tracking.add(new Pair<>(curx + type.x, cury + type.y));
            }
        while (!loang.isEmpty()) {
            Triple<Integer, Integer, MovementType> cur = loang.get(0);
            loang.remove(0);
            if (safeWay.contains(cur.getThird()))
                continue;
            if (ifInBombPos && !(cur.getSecond() == cury && Math.abs(cur.getFirst() - curx) <= FlameManager.getFlameLength())
                    && !(cur.getFirst() == curx && Math.abs(cur.getSecond() - cury) <= FlameManager.getFlameLength()))
                safeWay.add(cur.getThird());
            if (!ifInBombPos)
                safeWay.add(cur.getThird());
            for (MovementType type : MovementType.values()) {
                Triple<Integer, Integer, MovementType> next = new Triple<>(cur.getFirst() + type.x, cur.getSecond() + type.y, cur.getThird());
                if (type != MovementType.STILL && validCoordination(next.getFirst() * Main.defaultSide, next.getSecond() * Main.defaultSide)
                        && !tracking.contains(new Pair<>(next.getFirst(), next.getSecond()))
                        && (mapAI.get(next.getSecond()).get(next.getFirst()) == 0 || mapAI.get(next.getSecond()).get(next.getFirst()) == 2)) {
                    loang.add(next);
                    tracking.add(new Pair<>(next.getFirst(), next.getSecond()));
                }
            }
        }
        return safeWay;
    }
    private boolean putBombWhenMeetBrick() {
        if (bombManager.canPutBomb() && nearBrick(curpos(x), curpos(y))
                && !chooseSafeWay(curpos(x), curpos(y), true).isEmpty()) {
            System.out.println("in meet brick");
            for (int i = 0; i < bombManager.countBomb(); i++) {
                Bomb curbomb = bombManager.getBomb(i);
                int idx = (int) Math.round(curbomb.getX() / Main.defaultSide);
                int idy = (int) Math.round(curbomb.getY() / Main.defaultSide);
                if (idx == curpos(x) && Math.abs(idy - curpos(y)) <= FlameManager.getFlameLength()
                        && curbomb.countdown() <= escapeTime)
                    return false;
                if (idy == curpos(y) && Math.abs(idx - curpos(x)) <= FlameManager.getFlameLength()
                        && curbomb.countdown() <= escapeTime)
                    return false;
            }
            bombManager.addBomb(new Bomb(x, y, bombManager, this, enemyManager, map));
            System.out.println(direction);
            return true;
        }
        return false;
    }
    private boolean inBombPosition() {
        System.out.println("in bomb position");
        for (int i = 0; i < bombManager.countBomb(); i++) {
            Bomb curbomb = bombManager.getBomb(i);
            int idx = (int) Math.round(curbomb.getX() / Main.defaultSide);
            int idy = (int) Math.round(curbomb.getY() / Main.defaultSide);
            if (curpos(x) == idx && curpos(y) == idy) {
                ArrayList<MovementType> safeway = chooseSafeWay(curpos(x), curpos(y), true);
                if (safeway.isEmpty())
                    direction = MovementType.STILL;
                else direction = safeway.get(0);
                canMoveAndMove(direction);
                System.out.println(direction);
                return true;
            }
        }
        return false;
    }
    private boolean putBombWhenNearEnemy() {
        System.out.println("near enemy");
        if (mapAI.get(curpos(y)).get(curpos(x)) > 0 && bombManager.canPutBomb()
                && !chooseSafeWay(curpos(x), curpos(y), true).isEmpty()) {
            bombManager.addBomb(new Bomb(x, y, bombManager, this, enemyManager, map));
            return true;
        }
        return false;
    }
    private MovementType getRandomMoveDirection() {
        ArrayList<MovementType> safeWay = chooseSafeWay(curpos(x), curpos(y), false);
        System.out.println(safeWay.size());
        int takeRandom = random.nextInt(3);
        MovementType dir = direction;
        if (takeRandom == 1 || !safeWay.contains(dir)) {
            if (safeWay.isEmpty())
                dir = MovementType.STILL;
            else
                dir = safeWay.get(random.nextInt(safeWay.size()));
        }
        System.out.println("curpos: " + curpos(x) + " " + curpos(y) + " " + dir);
        return dir;
    }
    @Override
    public void update() {
        checkCollisionEnemy();
        checkTakeItem();

        if (y == moveToNeareastSquare(y) && x == moveToNeareastSquare(x)) {
            updateMapAI();
            if (inBombPosition());
            else if (putBombWhenMeetBrick());
            else if (putBombWhenNearEnemy());
            else {
                System.out.println("In random move");
                direction = getRandomMoveDirection();
                canMoveAndMove(direction);
            }
            steps = 0;
        }
        else
            canMoveAndMove(direction);
        steps++;

        try {
            render(direction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printMapAI() {
        for (int y = 0; y < Main.rows; y++) {
            for (int x = 0; x < Main.cols; x++) {
                if (mapAI.get(y).get(x) < 0)
                    System.out.print(mapAI.get(y).get(x) + " ");
                else System.out.print(" " + mapAI.get(y).get(x) + " ");
            }
            System.out.println();
        }
    }
}
