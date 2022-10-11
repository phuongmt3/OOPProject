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
import java.util.Arrays;
import java.util.Random;

public class BomberAI extends Bomber {
    private Random random = new Random();
    private ArrayList<ArrayList<Integer>> mapAI = new ArrayList<>();
    private ArrayList<Brick> items = new ArrayList<>();
    private boolean constructItemList = false;
    private MovementType direction = MovementType.RIGHT;
    private int escapeTime, stepTime;
    private final int putbombrange = 3;

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
        stepTime = (int) (Main.defaultSide / speed) + 2;
        escapeTime = FlameManager.getFlameLength() * stepTime + 1;//+ stepTime
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
                        speed = 6.4;
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
        int remainSteps2 = remainSteps + stepTime * (putbombrange - 1);
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
        int remainSteps2 = remainSteps + stepTime * (putbombrange - 1);
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
        //-1 can't go; -2 enemy pos; -3 flame can be there; 0 safe; 1 enemy will come need to avoid; 2 should put bomb
        System.out.println("in update mapAI");
        for (int y = 0; y < Main.rows; y++)
            for (int x = 0; x < Main.cols; x++) {
                if (!availableArea(x, y))
                    mapAI.get(y).set(x, -1);
                else
                    mapAI.get(y).set(x, 0);
                if (!constructItemList && map.get(y).get(x) instanceof Brick
                        && ((Brick) map.get(y).get(x)).isHasItem())
                    items.add((Brick) map.get(y).get(x));
            }
        constructItemList = true;

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

            if (Math.abs(enemy.getY() - moveToNeareastSquare(enemy.getY())) < 0.1) {
                //left then up, down
                for (int leftcnt = 0; leftcnt <= stepTime * putbombrange; leftcnt++) {
                    double newX = roundCoordinate(enemy.getX() - enemyspeed * leftcnt);
                    int posx = curpos(newX);
                    if (posx < 0 || mapAI.get(cury).get(posx) == -1)
                        break;
                    else if (mapAI.get(cury).get(posx) == 0) {
                        if (leftcnt <= stepTime)
                            mapAI.get(cury).set(posx, 1);
                        else mapAI.get(cury).set(posx, 2);
                    }
                    if (Math.abs(newX - moveToNeareastSquare(newX)) < 0.1)
                        moveUpDownAfterLeftRight(leftcnt, enemy, cury, posx, enemyspeed);
                }
                //right then up, down
                for (int rightcnt = 0; rightcnt <= stepTime * putbombrange; rightcnt++) {
                    double newX = roundCoordinate(enemy.getX() + enemyspeed * rightcnt);
                    int posx = nextpos(newX) > -1 ? nextpos(newX) : curpos(newX);
                    if (posx >= Main.cols || mapAI.get(cury).get(posx) == -1)
                        break;
                    else if (mapAI.get(cury).get(posx) == 0) {
                        if (rightcnt <= stepTime)
                            mapAI.get(cury).set(posx, 1);
                        else mapAI.get(cury).set(posx, 2);
                    }
                    if (Math.abs(newX - moveToNeareastSquare(newX)) < 0.1)
                        moveUpDownAfterLeftRight(rightcnt, enemy, cury, posx, enemyspeed);
                }
            } else {
                //up then left, right
                for (int upcnt = 0; upcnt <= stepTime * putbombrange; upcnt++) {
                    double newY = roundCoordinate(enemy.getY() - enemyspeed * upcnt);
                    int posy = curpos(newY);
                    if (posy < 0 || mapAI.get(posy).get(curx) == -1)
                        break;
                    else if (mapAI.get(posy).get(curx) == 0) {
                        if (upcnt <= stepTime)
                            mapAI.get(posy).set(curx, 1);
                        else mapAI.get(posy).set(curx, 2);
                    }
                    if (Math.abs(newY - moveToNeareastSquare(newY)) < 0.1)
                        moveLeftRightAfterUpDown(upcnt, enemy, curx, posy, enemyspeed);
                }
                //down then left, right
                for (int downcnt = 0; downcnt <= stepTime * putbombrange; downcnt++) {
                    double newY = roundCoordinate(enemy.getY() + enemyspeed * downcnt);
                    int posy = nextpos(newY) > -1 ? nextpos(newY) : curpos(newY);
                    if (posy >= Main.rows || mapAI.get(posy).get(curx) == -1)
                        break;
                    else if (mapAI.get(posy).get(curx) == 0) {
                        if (downcnt <= stepTime)
                            mapAI.get(posy).set(curx, 1);
                        else mapAI.get(posy).set(curx, 2);
                    }
                    if (Math.abs(newY - moveToNeareastSquare(newY)) < 0.1)
                        moveLeftRightAfterUpDown(downcnt, enemy, curx, posy, enemyspeed);
                }
            }
        }

        for (int i = 0; i < bombManager.countBomb(); i++) {
            Bomb curbomb = bombManager.getBomb(i);
            int idx = (int) Math.round(curbomb.getX() / Main.defaultSide);
            int idy = (int) Math.round(curbomb.getY() / Main.defaultSide);
            for (MovementType type : MovementType.values())
                for (int len = 1; len <= FlameManager.getFlameLength(); len++) {
                    int flamey = idy + type.y * len;
                    int flamex = idx + type.x * len;
                    if (mapAI.get(flamey).get(flamex) == -1)
                        break;
                    if (curbomb.countdown() < escapeTime)
                        mapAI.get(flamey).set(flamex, -1);
                    else if (mapAI.get(flamey).get(flamex) == 0 || mapAI.get(flamey).get(flamex) == 2)
                        mapAI.get(flamey).set(flamex, -3);
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
        int[] cntFreeSquares = new int[4];
        tracking.add(new Pair<>(curx, cury));
        for (MovementType type : MovementType.values())
            if (type != MovementType.STILL && validCoordination(curx + type.x, cury + type.y)
                    && (mapAI.get(cury + type.y).get(curx + type.x) == 0 || mapAI.get(cury + type.y).get(curx + type.x) == 2
                    || mapAI.get(cury + type.y).get(curx + type.x) == -3)) {
                loang.add(new Triple<>(curx + type.x, cury + type.y, type));
                tracking.add(new Pair<>(curx + type.x, cury + type.y));
                cntFreeSquares[type.ordinal()]++;
            }
        while (!loang.isEmpty()) {
            Triple<Integer, Integer, MovementType> cur = loang.get(0);
            loang.remove(0);
            if (safeWay.contains(cur.getThird()) && cntFreeSquares[cur.getThird().ordinal()] > 9)
                continue;
            if (!safeWay.contains(cur.getThird()) && ifInBombPos && mapAI.get(cur.getSecond()).get(cur.getFirst()) != -3
                    && !(cur.getSecond() == cury && Math.abs(cur.getFirst() - curx) <= FlameManager.getFlameLength())
                    && !(cur.getFirst() == curx && Math.abs(cur.getSecond() - cury) <= FlameManager.getFlameLength()))
                safeWay.add(cur.getThird());
            if (!safeWay.contains(cur.getThird()) && !ifInBombPos && mapAI.get(cur.getSecond()).get(cur.getFirst()) != -3)
                safeWay.add(cur.getThird());

            for (MovementType type : MovementType.values()) {
                Triple<Integer, Integer, MovementType> next = new Triple<>(cur.getFirst() + type.x, cur.getSecond() + type.y, cur.getThird());
                if (type != MovementType.STILL && validCoordination(next.getFirst(), next.getSecond())
                        && !tracking.contains(new Pair<>(next.getFirst(), next.getSecond()))
                        && (mapAI.get(next.getSecond()).get(next.getFirst()) == 0 || mapAI.get(next.getSecond()).get(next.getFirst()) == 2
                        || mapAI.get(next.getSecond()).get(next.getFirst()) == -3)) {
                    loang.add(next);
                    tracking.add(new Pair<>(next.getFirst(), next.getSecond()));
                    cntFreeSquares[type.ordinal()]++;
                }
            }
        }
        safeWay.sort((MovementType a, MovementType b) -> (cntFreeSquares[b.ordinal()] - cntFreeSquares[a.ordinal()]));
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
                ArrayList<MovementType> safeway = chooseSafeWay(idx, idy, true);
                if (safeway.isEmpty())
                    direction = MovementType.STILL;
                else direction = safeway.get(0);
                canMoveAndMove(direction);
                System.out.println(idx + " " + idy + " " + direction);
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
    private ArrayList<MovementType> getMoveDirectionToEntity(Entity other) {
        double deltaX = x - other.getX();
        double deltaY = y - other.getY();
        MovementType dirx = MovementType.LEFT, diry = MovementType.UP;
        if (deltaX < 0)
            dirx = MovementType.RIGHT;
        if (deltaY < 0)
            diry = MovementType.DOWN;
        ArrayList<MovementType> ans = new ArrayList<>();
        ans.add(dirx); ans.add(diry);
        return ans;
    }
    private MovementType getRandomMoveDirection() {
        ArrayList<MovementType> safeWay = chooseSafeWay(curpos(x), curpos(y), false);
        System.out.print("Safe Way: ");
        for (MovementType dir : safeWay)
            System.out.print(dir + " ");
        System.out.println();

        int takeRandom = random.nextInt(3);
        MovementType dir = direction;
        if (enemyManager.allDead() && items.size() == 1)
            return getMoveDirectionToEntity(items.get(0)).get(random.nextInt(2));
        if (takeRandom == 1 || !safeWay.contains(dir)) {
            if (safeWay.isEmpty())
                dir = MovementType.STILL;
            else {
                boolean nearEnemy = false;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++) {
                    int val = mapAI.get(curpos(y) + j).get(curpos(x) + i);
                    if (val == -2 || val == 1 || val == 2) {
                        nearEnemy = true;
                        break;
                    }
                }
                int choices = items.size() == 1 ? 5 : 8;
                int way = random.nextInt(choices);
                if (!nearEnemy && way >= 5 && bombManager.canPutBomb()) {
                    //chase item, update at the same time.
                    for (int i = 0; i < items.size(); i++) {
                        if (!items.get(i).isHasItem()) {
                            items.remove(i);
                            i--;
                        }
                        else if (items.get(i) instanceof Portal && !enemyManager.allDead());
                        else {
                            ArrayList<MovementType> movelist = getMoveDirectionToEntity(items.get(i));
                            for (MovementType move : movelist)
                                if (safeWay.contains(move)) {
                                    System.out.println("curpos: " + curpos(x) + " " + curpos(y) + " " + move + " chase items");
                                    return move;
                                }
                        }
                    }
                }
                if (!nearEnemy && way == 4 && bombManager.canPutBomb()) {
                    //chase enemy
                    for (int i = 0; i < enemyManager.countEnemies(); i++) {
                        ArrayList<MovementType> moveList = getMoveDirectionToEntity(enemyManager.getEnemy(i));
                        for (MovementType move : moveList)
                            if (safeWay.contains(move)) {
                                System.out.println("curpos: " + curpos(x) + " " + curpos(y) + " " + move + " chase enemy");
                                return move;
                            }
                    }
                }
                dir = safeWay.get(0);
            }
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
            chooseSafeWay(0,0,false);
            if (inBombPosition());
            else if (putBombWhenNearEnemy());
            else if (putBombWhenMeetBrick());
            else {
                System.out.println("In random move");
                direction = getRandomMoveDirection();
                canMoveAndMove(direction);
            }
        }
        else
            canMoveAndMove(direction);

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
