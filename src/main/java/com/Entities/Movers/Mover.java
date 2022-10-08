package com.Entities.Movers;

import com.Entities.Bomb.Bomb;
import com.Entities.Bomb.BombManager;
import com.Entities.Bomb.FlameManager;
import com.Entities.Entity;
import com.Entities.Maps.*;
import com.Entities.Movers.Enemies.*;
import com.Main;

import java.util.ArrayList;
abstract public class Mover extends Entity {
    protected boolean isDead = false;
    protected double speed;
    protected ArrayList<ArrayList<Entity>> map;
    protected BombManager bombManager;
    protected EnemyManager enemyManager;
    public static enum MovementType {
        LEFT(-1, 0), UP(0, -1), RIGHT(1, 0), DOWN(0, 1), STILL(0, 0);
        public final int x, y;
        MovementType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Mover(double x, double y, double speed, ArrayList<ArrayList<Entity>> map, BombManager bombManager, EnemyManager enemyManager) {
        super(x, y);
        this.speed = speed;
        this.bombManager = bombManager;
        this.map = map;
        this.enemyManager = enemyManager;
    }
    abstract public void update();

    private boolean collideBomb() {
        for (int i = 0; i < bombManager.countBomb(); i++) {
            if (checkCollision(bombManager.getBomb(i)))
                return true;
        }
        return false;
    }

    protected boolean colllideOtherEnemy() {
        for (int i = 0; i < enemyManager.countEnemies(); i++) {
            Enemy x = enemyManager.getEnemy(i);
            if (x.getX() == this.x && x.getY() == this.y)
                continue;
            if (checkCollision(x))
                return true;
        }
        return false;
    }

    protected int curpos(double x) {
        if (x < 0)
            return 0;
        return (int) (x / Main.defaultSide);
    }
    protected int nextpos(double x) {
        if (x == moveToNeareastSquare(x))
            return -1;
        return curpos(x) + 1;
    }

    protected double moveToNeareastSquare(double x) {
        long pos = Math.round(x / Main.defaultSide);
        return pos * Main.defaultSide;
    }

    protected double roundCoordinate(double x) {
        double newx = moveToNeareastSquare(x);
        if (Math.abs(x - newx) < 1)
            return newx;
        return x;
    }

    public boolean availableArea(int x, int y) {
        if (this instanceof Doria || this instanceof Pass) {
            for (int i = 0; i < bombManager.countBomb(); i++) {
                Bomb curbomb = bombManager.getBomb(i);
                int idx = (int) Math.round(curbomb.getX() / Main.defaultSide);
                int idy = (int) Math.round(curbomb.getY() / Main.defaultSide);
                if (x == idx && y >= idy - FlameManager.getFlameLength() && y <= idy + FlameManager.getFlameLength())
                    return false;
                if (y == idy && x >= idx - FlameManager.getFlameLength() && x <= idx + FlameManager.getFlameLength())
                    return false;
            }
        }
        //normal condition
        for (int i = 0; i < bombManager.countBomb(); i++) {
            if (map.get(y).get(x).checkCollision(bombManager.getBomb(i)))
                return false;
        }
        return availableAreaInMap(map.get(y).get(x));
    }

    private boolean availableAreaInMap(Entity tile) {
        if (this instanceof Doria || this instanceof Ovape)
            return tile instanceof Grass || tile instanceof Brick;
        //other cases
        if (tile instanceof Grass)
            return true;
        return tile instanceof Brick && ((Brick) tile).isExposed();
    }

    public boolean canMove(MovementType type) {
        boolean inBombPosition = collideBomb();
        double newX = roundCoordinate(x + type.x * speed);
        newX = Math.max(0, Math.min(newX, (Main.cols - 1) * Main.defaultSide));
        double newY = roundCoordinate(y + type.y * speed);
        newY = Math.max(0, Math.min(newY, (Main.rows - 1) * Main.defaultSide));
        Entity tile0 = null, tile1 = null;
        int newidXmap = (int) (newX / Main.defaultSide);
        int newidYmap = (int) (newY / Main.defaultSide);

        if (type == MovementType.LEFT) {
            tile0 = map.get(newidYmap).get(newidXmap);
            tile1 = map.get(newidYmap + 1).get(newidXmap);
        }
        else if (type == MovementType.RIGHT) {
            newidXmap ++;
            tile0 = map.get(newidYmap).get(newidXmap);
            tile1 = map.get(newidYmap + 1).get(newidXmap);
        }
        else if (type == MovementType.UP) {
            tile0 = map.get(newidYmap).get(newidXmap);
            tile1 = map.get(newidYmap).get(newidXmap + 1);
        }
        else if (type == MovementType.DOWN) {
            newidYmap ++;
            tile0 = map.get(newidYmap).get(newidXmap);
            tile1 = map.get(newidYmap).get(newidXmap + 1);
        }

        double oldX = x, oldY = y;
        setX(newX); setY(newY);
        if (!inBombPosition && collideBomb()) {
            setX(oldX); setY(oldY);
            return false;
        }
        else if (tile0 instanceof Wall || (tile0 instanceof Brick && !((Brick) tile0).isExposed())) {
            setX(oldX); setY(oldY);
            return false;
        }
        else if (checkCollision(tile1) && (tile1 instanceof Wall || (tile1 instanceof Brick && !((Brick) tile1).isExposed()))) {
            setX(oldX); setY(oldY);
            return false;
        }
        setX(oldX); setY(oldY);
        return true;
    }

    public boolean canMoveAndMove(MovementType type) {
        boolean inBombPosition = collideBomb();
        double newX = roundCoordinate(x + type.x * speed);
        newX = Math.max(0, Math.min(newX, (Main.cols - 1) * Main.defaultSide));
        double newY = roundCoordinate(y + type.y * speed);
        newY = Math.max(0, Math.min(newY, (Main.rows - 1) * Main.defaultSide));
        int newidXmap = (int) (newX / Main.defaultSide);
        int newidYmap = (int) (newY / Main.defaultSide);

        if (type == MovementType.LEFT) {
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap + 1).get(newidXmap);
            setX(newX);
            if (!inBombPosition && collideBomb()) {
                setX(roundCoordinate(x - type.x * speed));
                return false;
            }
            else if (!availableAreaInMap(tile0)) {
                setX(tile0.getX() + tile0.getW());
                return false;
            }
            else if (checkCollision(tile1) && !availableAreaInMap(tile1)) {
                setX(tile1.getX() + tile1.getW());
                return false;
            }
        }
        else if (type == MovementType.RIGHT) {
            newidXmap ++;
            if (newidXmap >= Main.cols)
                return false;
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap + 1).get(newidXmap);
            setX(newX);
            if (!inBombPosition && collideBomb()) {
                setX(roundCoordinate(x - type.x * speed));
                return false;
            }
            else if (!availableAreaInMap(tile0)) {
                setX(tile0.getX() - tile0.getW());
                return false;
            }
            else if (checkCollision(tile1) && !availableAreaInMap(tile1)) {
                setX(tile1.getX() - tile1.getW());
                return false;
            }
        }
        else if (type == MovementType.UP) {
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap).get(newidXmap + 1);
            setY(newY);
            if (!inBombPosition && collideBomb()) {
                setY(roundCoordinate(y - type.y * speed));
                return false;
            }
            else if (!availableAreaInMap(tile0)) {
                setY(tile0.getY() + tile0.getH());
                return false;
            }
            else if (checkCollision(tile1) && !availableAreaInMap(tile1)) {
                setY(tile1.getY() + tile1.getH());
                return false;
            }
        }
        else if (type == MovementType.DOWN) {
            newidYmap ++;
            if (newidYmap >= Main.rows)
                return false;
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap).get(newidXmap + 1);
            setY(newY);
            if (!inBombPosition && collideBomb()) {
                setY(roundCoordinate(y - type.y * speed));
                return false;
            }
            else if (!availableAreaInMap(tile0)) {
                setY(tile0.getY() - tile0.getH());
                return false;
            }
            else if (checkCollision(tile1) && !availableAreaInMap(tile1)) {
                setY(tile1.getY() - tile1.getH());
                return false;
            }
        }
        return true;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        if (this instanceof BomberAI) {
            BomberAI bomber = (BomberAI) this;
            bomber.printMapAI();
            System.out.println(bomber.getX() + " " + bomber.getY());
        }
    }

    @Override
    public String getClassName() {
        return "Mover";
    }
}