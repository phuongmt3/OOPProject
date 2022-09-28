package com.example.dbproject;

import java.util.ArrayList;

abstract public class Mover extends Entity {
    protected boolean isDead = false;
    protected final double speed;
    public static enum MovementType {
        LEFT(-1, 0), RIGHT(1, 0),
        UP(0, -1), DOWN(0, 1);
        public final int x, y;
        MovementType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Mover(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
    }
    public void update() {}

    public void move(MovementType type, ArrayList<ArrayList<Entity>> map) {
        if (type == MovementType.LEFT) {
            double newX = x + type.x * speed;
            newX = Math.max(0, Math.min(newX, (Main.cols - 1) * Main.defaultSide));
            int newidXmap = (int) (newX / Main.defaultSide);
            int newidYmap = (int) (y / Main.defaultSide);
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap + 1).get(newidXmap);
            if (tile0 instanceof Wall || (tile0 instanceof Brick && !((Brick) tile0).isExposed()))
                setX(tile0.getX() + tile0.getW());
            else if (checkCollision(tile1) && (tile1 instanceof Wall || (tile1 instanceof Brick && !((Brick) tile1).isExposed())))
                setX(tile1.getX() + tile1.getW());
            else
                setX(newX);
        }
        else if (type == MovementType.RIGHT) {
            double newX = x + type.x * speed;
            newX = Math.max(0, Math.min(newX, (Main.cols - 1) * Main.defaultSide));
            int newidXmap = (int) (newX / Main.defaultSide) + 1;
            int newidYmap = (int) (y / Main.defaultSide);
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap + 1).get(newidXmap);
            if (tile0 instanceof Wall || (tile0 instanceof Brick && !((Brick) tile0).isExposed()))
                setX(tile0.getX() - tile0.getW());
            else if (checkCollision(tile1) && (tile1 instanceof Wall || (tile1 instanceof Brick && !((Brick) tile1).isExposed())))
                setX(tile1.getX() - tile1.getW());
            else
                setX(newX);
        }
        else if (type == MovementType.UP) {
            double newY = y + type.y * speed;
            newY = Math.max(0, Math.min(newY, (Main.rows - 1) * Main.defaultSide));
            int newidXmap = (int) (x / Main.defaultSide);
            int newidYmap = (int) (newY / Main.defaultSide);
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap).get(newidXmap + 1);
            if (tile0 instanceof Wall || (tile0 instanceof Brick && !((Brick) tile0).isExposed()))
                setY(tile0.getY() + tile0.getH());
            else if (checkCollision(tile1) && (tile1 instanceof Wall || (tile1 instanceof Brick && !((Brick) tile1).isExposed())))
                setY(tile1.getY() + tile1.getH());
            else
                setY(newY);
        }
        else {
            double newY = y + type.y * speed;
            newY = Math.max(0, Math.min(newY, (Main.rows - 1) * Main.defaultSide));
            int newidXmap = (int) (x / Main.defaultSide);
            int newidYmap = (int) (newY / Main.defaultSide) + 1;
            Entity tile0 = map.get(newidYmap).get(newidXmap);
            Entity tile1 = map.get(newidYmap).get(newidXmap + 1);
            if (tile0 instanceof Wall || (tile0 instanceof Brick && !((Brick) tile0).isExposed()))
                setY(tile0.getY() - tile0.getH());
            else if (checkCollision(tile1) && (tile1 instanceof Wall || (tile1 instanceof Brick && !((Brick) tile1).isExposed())))
                setY(tile1.getY() - tile1.getH());
            else
                setY(newY);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public String getClassName() {
        return "Mover";
    }
}