package com.example.dbproject;

public class Mover extends Entity {
    protected boolean isDead = false;
    protected final double speed;
    enum MovementType {
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
    public void render() {}
    public void move(MovementType type) {
        setX(getX() + type.x * speed);
        setY(getY() + type.y * speed);
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public String getClassName() {
        return "Mover";
    }
}
